<template>
  <div class="app-shell">
    <header class="app-header">
      <h1>MSA Shop</h1>
      <nav>
        <RouterLink to="/orders">주문 목록</RouterLink>
        <RouterLink to="/orders/new">주문 생성</RouterLink>
        <RouterLink to="/profile">프로필</RouterLink>
      </nav>
      <div class="user-info" v-if="auth.isAuthenticated">
        <span>{{ auth.user?.userName ?? auth.user?.loginId }}</span>
        <button type="button" @click="handleLogout">로그아웃</button>
      </div>
    </header>

    <main>
      <div v-if="notification.message" class="toast" :class="notification.variant">
        {{ notification.message }}
      </div>
      <RouterView @notify="notify" />
    </main>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { RouterLink, RouterView, useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const auth = useAuthStore();
const router = useRouter();
const notification = reactive({ message: '', variant: 'info' });

function notify(payload) {
  notification.message = payload.message || '알 수 없는 오류가 발생했습니다.';
  notification.variant = payload.variant || 'error';
  setTimeout(() => {
    notification.message = '';
  }, 3000);
}

async function handleLogout() {
  await auth.logout();
  router.push('/login');
}
</script>
