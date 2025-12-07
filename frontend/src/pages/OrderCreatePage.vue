<template>
  <section class="page">
    <h2>주문 생성</h2>
    <form @submit.prevent="submit">
      <label>
        수취인 이름
        <input v-model="form.receiverName" required />
      </label>
      <label>
        연락처
        <input v-model="form.receiverPhone" required pattern="\\d{2,3}-?\\d{3,4}-?\\d{4}" />
      </label>
      <label>
        우편번호
        <input v-model="form.postcode" required pattern="\\d{5}" />
      </label>
      <label>
        주소
        <input v-model="form.address1" required />
      </label>
      <label>
        상세 주소
        <input v-model="form.address2" />
      </label>

      <OrderItemsEditor v-model="form.items" @open-picker="showPicker = true" />
      <button type="submit">등록</button>
    </form>

    <ProductPickerModal :open="showPicker" @close="showPicker = false" @select="addItem" />
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import OrderItemsEditor from '@/components/OrderItemsEditor.vue';
import ProductPickerModal from '@/components/ProductPickerModal.vue';
import { useOrderStore } from '@/stores/order';

const router = useRouter();
const orderStore = useOrderStore();
const showPicker = ref(false);

const form = reactive({
  receiverName: '',
  receiverPhone: '',
  postcode: '',
  address1: '',
  address2: '',
  items: []
});

async function submit() {
  if (!form.items.length) {
    alert('최소 1개 이상의 품목을 추가해주세요.');
    return;
  }
  const payload = { ...form, items: form.items.map((item) => ({ ...item })) };
  const { id } = await orderStore.create(payload);
  router.push(`/orders/${id}`);
}

function addItem(product) {
  form.items.push({
    productId: product.id,
    productName: product.name,
    unitPrice: product.price,
    qty: 1
  });
}
</script>
