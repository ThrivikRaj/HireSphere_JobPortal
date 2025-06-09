import { api } from './api';

export const authService = {
  async register(userData) {
    try {
      const response = await api.post('/api/auth/signup', {
        name: userData.name,
        email: userData.email,
        password: userData.password,
        role: 'JOB_SEEKER'
      });
      return response.data;
    } catch (error) {
      console.error('Registration error:', error);
      throw error;
    }
  },

  async login(credentials) {
    try {
      if (!credentials.email || !credentials.password) {
        throw new Error('Email and password are required');
      }

      const response = await api.post('/api/auth/login', {
        email: credentials.email,
        password: credentials.password
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (response.data) {
        localStorage.setItem('user', JSON.stringify(response.data.user));
        localStorage.setItem('token', response.data.token);
        return response.data.user;
      } else {
        throw new Error('Invalid response format from server');
      }
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },

  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    return api.post('/api/auth/logout');
  },

  getCurrentUser() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      return JSON.parse(userStr);
    }
    return null;
  },

  isAuthenticated() {
    return !!this.getCurrentUser();
  },

  getToken() {
    return localStorage.getItem('token');
  },

  async updateProfile(profileData) {
    try {
      const response = await api.put('/api/users/profile', profileData);
      const updatedUser = response.data;
      localStorage.setItem('user', JSON.stringify(updatedUser));
      return updatedUser;
    } catch (error) {
      throw error;
    }
  },

  setAuthToken(token) {
    if (token) {
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      delete api.defaults.headers.common['Authorization'];
    }
  },
};