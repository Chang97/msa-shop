import { defineStore } from 'pinia';
import http, { toError } from '@/api/http';

export const useProductStore = defineStore('products', {
  state: () => ({
    results: [],
    totalElements: 0
  }),
  actions: {
    async search(query = '', page = 0, size = 10) {
      try {
        const { data } = await http.get('/products', {
          params: { query, page, size }
        });
        this.results = data.content ?? data;
        this.totalElements = data.totalElements ?? data.length ?? 0;
        return data;
      } catch (error) {
        throw toError(error);
      }
    },
    async get(id) {
      try {
        const { data } = await http.get(`/products/${id}`);
        return data;
      } catch (error) {
        throw toError(error);
      }
    }
  }
});
