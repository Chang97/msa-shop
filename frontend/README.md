# MSA Shop Frontend (Vue 3 + Vite)

Gateway(API Base: `http://localhost:8080/api`)와 연동되는 관리자용 SPA입니다. 로그인 이후 HttpOnly 쿠키에 저장된 JWT를 기반으로 주문 목록/생성/상세 화면을 사용할 수 있습니다.

## 1. 실행 방법

```bash
cd frontend
npm install
npm run dev
```

- 개발 서버: <http://localhost:5173>
- 환경 변수: `.env.development`

```ini
VITE_API_BASE=http://localhost:8080/api
```

## 2. NPM Scripts

| Script        | 설명                               |
| ------------- | ---------------------------------- |
| `npm run dev` | Vite 개발 서버                     |
| `npm run build` | 프로덕션 번들 빌드              |
| `npm run preview` | 빌드 결과 미리보기            |
| `npm run lint` | ESLint(.js/.vue) 검사             |
| `npm run format` | Prettier 코드 정렬              |

## 3. 폴더 구조

```
src/
  api/http.js           # Axios 인스턴스
  components/           # AppShell, StatusChip, ProductPickerModal, OrderItemsEditor
  pages/                # Login, Orders List/Detail/Create, Profile
  router/index.js       # 인증 가드 포함 라우터
  stores/               # auth, order, product Pinia 스토어
  styles/app.css        # 기본 스타일
  main.js               # Vue/Pnia/Router 부트스트랩
```

## 4. 주요 페이지

| Route           | 설명                                      |
|-----------------|-------------------------------------------|
| `/login`        | 로그인 폼 (`POST /auth/login`)            |
| `/orders`       | 주문 목록 + 필터/페이지                  |
| `/orders/new`   | 주문 생성 폼 + 품목 편집/상품 선택       |
| `/orders/:id`   | 주문 상세 + 상태 변경                    |
| `/profile`      | 사용자 정보 표시, `POST /auth/logout`    |

## 5. API 요청 요약

- **인증**: `/auth/login`, `/auth/me`, `/auth/logout`
- **주문**: `/orders`, `/orders/{id}`, `/orders/{id}/status`
- **상품 검색(모달)**: `/products?query=&page=&size=`

모든 요청은 `withCredentials: true` 상태로 Axios가 전송하며, 401 응답 시 `/login`으로 리다이렉트합니다.

## 6. 주의사항

- HttpOnly 쿠키 기반 인증이므로 브라우저/클라이언트에서 별도의 토큰 저장 필요 없음.
- 목록 필터/페이지 파라미터는 Axios `params`로 직렬화됩니다.
- 상태 변경 실패, 검증 에러 등은 AppShell 상단의 toast 메시지로 표시됩니다.

## 7. 빌드 산출물

빌드 결과(`npm run build`)는 `/dist` 폴더에 생성되며, Vite preview 또는 Gateway 정적 호스팅 영역에 배포할 수 있습니다.
