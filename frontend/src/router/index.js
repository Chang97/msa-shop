import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/pages/LoginPage.vue'),
    meta: { public: true }
  },
  {
    path: '/',
    redirect: '/orders'
  },
  {
    path: '/orders',
    name: 'orders-list',
    component: () => import('@/pages/OrdersListPage.vue')
  },
  {
    path: '/orders/new',
    name: 'orders-new',
    component: () => import('@/pages/OrderCreatePage.vue')
  },
  {
    path: '/orders/:id',
    name: 'orders-detail',
    component: () => import('@/pages/OrderDetailPage.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('@/pages/ProfilePage.vue')
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach(async (to) => {
  if (to.meta.public) return true;
  const auth = useAuthStore();
  if (!auth.isAuthenticated) {
    try {
      await auth.fetchMe();
    } catch (_) {
      return { path: '/login', query: { redirect: to.fullPath } };
    }
  }
  return auth.isAuthenticated ? true : { path: '/login', query: { redirect: to.fullPath } };
});

export default router;
