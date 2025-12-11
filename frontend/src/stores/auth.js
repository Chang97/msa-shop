import { defineStore } from 'pinia';
import http, { toError } from '@/api/http';

const SELLER_PERMISSIONS = ['PRODUCT_MANAGE', 'PRODUCT_CREATE', 'PRODUCT_WRITE'];

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    permissions: [],
    loading: false
  }),
  getters: {
    isAuthenticated: (state) => Boolean(state.user),
    canManageProducts: (state) =>
      SELLER_PERMISSIONS.some((code) => state.permissions.includes(code))
  },
  actions: {
    async login(payload) {
      this.loading = true;
      try {
        const { data } = await http.post('/auth/login', payload);
        this.user = data.user ?? null;
        this.permissions = data.permissions ?? [];
      } catch (err) {
        throw toError(err);
      } finally {
        this.loading = false;
      }
    },
    async logout() {
      try {
        await http.post('/auth/logout');
      } finally {
        this.user = null;
        this.permissions = [];
      }
    },
    async fetchMe() {
      try {
        const { data } = await http.get('/auth/me');
        this.user = data?.user ?? null;
        this.permissions = data?.permissions ?? [];
        return this.user;
      } catch (error) {
        this.user = null;
        this.permissions = [];
        if (error.status == 404 || error.status == 204) {
          return null;
        }
        throw error;
      }
    }
  }
});
