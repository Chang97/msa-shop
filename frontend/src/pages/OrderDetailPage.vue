<template>
  <section class="page">
    <h2>주문 상세</h2>
    <div v-if="orderStore.current">
      <p>주문번호: {{ orderStore.current.orderNumber }}</p>
      <p>상태: <StatusChip :status="orderStore.current.status" /></p>
      <label>
        상태 변경
        <select v-model="nextStatus">
          <option disabled value="">상태 선택</option>
          <option value="CREATED">신규</option>
          <option value="PENDING_PAYMENT">결제대기</option>
          <option value="PAID">결제완료</option>
          <option value="FULFILLED">배송완료</option>
          <option value="CANCELLED">취소</option>
        </select>
      </label>
      <button type="button" @click="updateStatus">변경</button>

      <h3>품목</h3>
      <ul>
        <li v-for="item in orderStore.current.items" :key="item.productId">
          {{ item.productName }} - {{ item.qty }}개 ({{ formatCurrency(item.lineAmount) }})
        </li>
      </ul>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useOrderStore } from '@/stores/order';
import StatusChip from '@/components/StatusChip.vue';

const route = useRoute();
const orderStore = useOrderStore();
const nextStatus = ref('');

function formatCurrency(value) {
  return new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(value ?? 0);
}

async function load() {
  await orderStore.getById(route.params.id);
  nextStatus.value = orderStore.current?.status ?? '';
}

async function updateStatus() {
  if (!nextStatus.value) return;
  await orderStore.changeStatus(route.params.id, nextStatus.value);
}

load();
</script>
