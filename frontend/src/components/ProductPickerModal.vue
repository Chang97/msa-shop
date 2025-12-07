<template>
  <div v-if="open" class="modal-backdrop">
    <div class="modal">
      <header>
        <h3>상품 선택</h3>
        <button type="button" @click="$emit('close')">닫기</button>
      </header>
      <div class="modal-body">
        <input v-model="query" type="search" placeholder="상품명 검색" @keyup.enter="search" />
        <button type="button" @click="search">검색</button>
        <div class="results">
          <div v-if="loading">불러오는 중...</div>
          <ul>
            <li v-for="item in products.results" :key="item.id">
              <div>
                <strong>{{ item.name }}</strong>
                <p>가격: {{ formatCurrency(item.price) }} / 재고: {{ item.stock }}</p>
              </div>
              <button type="button" @click="pick(item)">선택</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useProductStore } from '@/stores/product';

const props = defineProps({
  open: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['close', 'select']);
const products = useProductStore();
const query = ref('');
const loading = ref(false);

async function search() {
  if (!props.open) return;
  loading.value = true;
  try {
    await products.search(query.value, 0, 10);
  } finally {
    loading.value = false;
  }
}

function pick(item) {
  emit('select', item);
  emit('close');
}

function formatCurrency(value) {
  return new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW'
  }).format(value ?? 0);
}
</script>
