import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { ThemeProvider } from './context/ThemeContext';
import Header from './components/common/Header';
import Footer from './components/common/Footer';
import Home from './pages/Home';
import Jobs from './pages/Jobs';
import Companies from './pages/Companies';
import Applications from './pages/Applications';
import Profile from './pages/Profile';
import Dashboard from './pages/Dashboard';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import PrivateRoute from './components/common/PrivateRoute';

function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <Router>
          <div className="min-h-screen flex flex-col">
            <Header />
            <main className="flex-grow container mx-auto px-4 py-8">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/jobs" element={<Jobs />} />
                <Route path="/companies" element={<Companies />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                
                {/* Protected Routes */}
                <Route path="/dashboard" element={
                  <PrivateRoute roles={['EMPLOYER', 'JOB_SEEKER']}>
                    <Dashboard />
                  </PrivateRoute>
                } />
                <Route path="/applications" element={
                  <PrivateRoute roles={['JOB_SEEKER']}>
                    <Applications />
                  </PrivateRoute>
                } />
                <Route path="/profile" element={
                  <PrivateRoute roles={['EMPLOYER', 'JOB_SEEKER']}>
                    <Profile />
                  </PrivateRoute>
                } />
                
                <Route path="*" element={<Navigate to="/" replace />} />
              </Routes>
            </main>
            <Footer />
          </div>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App; 