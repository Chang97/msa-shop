<template>
  <section class="page">
    <h2>프로필</h2>
    <div v-if="auth.user">
      <p>아이디: {{ auth.user.loginId }}</p>
      <p>이름: {{ auth.user.userName }}</p>
      <p>사용자 ID: {{ auth.user.userId }}</p>
      <button type="button" @click="logout">로그아웃</button>
    </div>
  </section>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';

const auth = useAuthStore();
const router = useRouter();

async function logout() {
  await auth.logout();
  router.push('/login');
}
</script>
