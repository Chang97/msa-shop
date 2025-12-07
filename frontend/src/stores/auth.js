import { defineStore } from 'pinia';
import http, { toError } from '@/api/http';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    loading: false
  }),
  getters: {
    isAuthenticated: (state) => Boolean(state.user)
  },
  actions: {
    async login(payload) {
      this.loading = true;
      try {
        const { data } = await http.post('/auth/login', payload);
        this.user = data.user;
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
      }
    },
    async fetchMe() {
      try {
        const { data } = await http.get('/auth/me');
        this.user = data;
        return data;
      } catch (error) {
        this.user = null;
        if (error.status == 404 || error.status == 204) {
          return null;
        }
        throw error;
      }
    }
  }
});
