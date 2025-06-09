import React, { createContext, useState, useContext, useEffect } from 'react';
import { authService } from '../services/authService';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const user = authService.getCurrentUser();
    setUser(user);
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const userData = await authService.login({ email, password });
      setUser(userData);
      setError(null);
      return userData;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  const register = async (userData) => {
    try {
      const response = await authService.register(userData);
      setUser(response);
      setError(null);
      return response;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  const logout = async () => {
    try {
      await authService.logout();
      setUser(null);
      setError(null);
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  const updateProfile = async (profileData) => {
    try {
      const updatedUser = await authService.updateProfile(profileData);
      setUser(updatedUser);
      setError(null);
      return updatedUser;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  };

  const value = {
    user,
    loading,
    error,
    login,
    register,
    logout,
    updateProfile,
    isAuthenticated: !!user,
    isEmployer: user?.role === 'EMPLOYER',
    isJobSeeker: user?.role === 'JOB_SEEKER'
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
}; 