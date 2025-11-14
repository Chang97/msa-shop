create table if not exists product (
    id bigserial primary key,
    name varchar(120) not null,
    price numeric(12,2) not null check (price >= 0),
    stock int not null check (stock >= 0),
    created_at timestamptz not null default now()
);

insert into product(name, price, stock) values
('Keyboard 87', 89000, 12),
('Mouse Silent', 32000, 45),
('Monitor 27"', 289000, 7);
