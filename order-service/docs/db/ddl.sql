-- 주문 마스터
CREATE TABLE orders (
  id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  order_number      VARCHAR(32) NOT NULL UNIQUE,            -- 예: ORD-20251115-000123
  user_id           BIGINT NOT NULL,                        -- 외부(Auth) 참조용 숫자
  status            VARCHAR(20) NOT NULL,                   -- CREATED, PENDING_PAYMENT, PAID, CANCELLED, FULFILLED
  currency          CHAR(3) NOT NULL DEFAULT 'KRW',
  subtotal_amount   NUMERIC(18,2) NOT NULL,
  discount_amount   NUMERIC(18,2) NOT NULL DEFAULT 0,
  shipping_fee      NUMERIC(18,2) NOT NULL DEFAULT 0,
  total_amount      NUMERIC(18,2) NOT NULL,
  receiver_name     VARCHAR(80) NOT NULL,
  receiver_phone    VARCHAR(32) NOT NULL,
  shipping_postcode VARCHAR(10) NOT NULL,
  shipping_address1 VARCHAR(255) NOT NULL,
  shipping_address2 VARCHAR(255),
  memo              VARCHAR(255),
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT chk_amounts_nonneg CHECK (
    subtotal_amount >= 0 AND discount_amount >= 0 AND shipping_fee >= 0 AND total_amount >= 0
  )
);

CREATE INDEX idx_orders_user_created   ON orders (user_id, created_at DESC);
CREATE INDEX idx_orders_status_created ON orders (status, created_at DESC);

-- 주문 품목
CREATE TABLE order_item (
  id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  order_id     BIGINT NOT NULL,
  product_id   BIGINT NOT NULL,                  -- 외부(Product) 참조용 숫자
  product_name VARCHAR(200) NOT NULL,            -- 주문 시점 스냅샷
  unit_price   NUMERIC(18,2) NOT NULL,
  qty          INTEGER NOT NULL CHECK (qty > 0),
  line_amount  NUMERIC(18,2) NOT NULL,
  created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_order_item_order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE INDEX idx_order_item_order   ON order_item (order_id);
CREATE INDEX idx_order_item_product ON order_item (product_id);

-- 상태 변경 이력
CREATE TABLE order_status_history (
  id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  order_id    BIGINT NOT NULL,
  from_status VARCHAR(20),
  to_status   VARCHAR(20) NOT NULL,
  reason      VARCHAR(255),
  changed_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
  changed_by  BIGINT,                               -- 변경 주체(시스템/사용자)
  CONSTRAINT fk_status_hist_order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE INDEX idx_status_hist_order ON order_status_history (order_id, changed_at);

-- Outbox (도메인 이벤트)
CREATE TABLE order_outbox (
  id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  aggregate_id BIGINT NOT NULL,                     -- orders.id
  event_type   VARCHAR(80) NOT NULL,                -- ORDER_CREATED, ...
  payload      JSONB NOT NULL,                      -- 이벤트 데이터
  status       VARCHAR(16) NOT NULL DEFAULT 'PENDING',  -- PENDING, PUBLISHED, FAILED
  created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
  published_at TIMESTAMPTZ
);

CREATE INDEX idx_outbox_status_created ON order_outbox (status, created_at);

-- 주문번호 시퀀스/함수(선택)
CREATE SEQUENCE IF NOT EXISTS order_no_seq;
CREATE OR REPLACE FUNCTION next_order_number()
RETURNS varchar AS $$
BEGIN
  RETURN 'ORD-' || to_char(current_date,'YYYYMMDD') || '-' ||
         lpad(nextval('order_no_seq')::text,6,'0');
END;
$$ LANGUAGE plpgsql;
