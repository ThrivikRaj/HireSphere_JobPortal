import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';

const Header = () => {
  const { user, logout } = useAuth();
  const { theme, toggleTheme } = useTheme();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await logout();
      navigate('/login');
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <header className="bg-white dark:bg-gray-800 shadow">
      <nav className="container mx-auto px-6 py-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <Link to="/" className="text-xl font-bold text-gray-800 dark:text-white">
              JobPortal
            </Link>
            <div className="hidden md:flex ml-10 space-x-8">
              <Link to="/jobs" className="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                Jobs
              </Link>
              <Link to="/companies" className="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                Companies
              </Link>
              {user && (
                <>
                  <Link to="/dashboard" className="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                    Dashboard
                  </Link>
                  {user.role === 'JOB_SEEKER' && (
                    <Link to="/applications" className="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                      Applications
                    </Link>
                  )}
                </>
              )}
            </div>
          </div>
          <div className="flex items-center space-x-4">
            <button
              onClick={toggleTheme}
              className="p-2 rounded-lg bg-gray-200 dark:bg-gray-700"
            >
              {theme === 'light' ? '🌙' : '☀️'}
            </button>
            {user ? (
              <div className="flex items-center space-x-4">
                <Link to="/profile" className="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                  Profile
                </Link>
                <button
                  onClick={handleLogout}
                  className="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700"
                >
                  Logout
                </button>
              </div>
            ) : (
              <div className="flex items-center space-x-4">
                <Link to="/login" className="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                  Login
                </Link>
                <Link
                  to="/register"
                  className="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700"
                >
                  Register
                </Link>
              </div>
            )}
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header; 