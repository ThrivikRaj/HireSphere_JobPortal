import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Dashboard = () => {
  const { user } = useAuth();
  const [stats, setStats] = useState({
    jobCount: 0,
    applicationCount: 0,
    companyCount: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // TODO: Replace with actual API calls
    const fetchStats = async () => {
      try {
        setLoading(true);
        // const response = await dashboardService.getStats();
        // setStats(response.data);
        setStats({
          jobCount: 0,
          applicationCount: 0,
          companyCount: 0,
        });
      } catch (err) {
        console.error('Failed to fetch dashboard stats:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Dashboard</h1>
      
      {user.role === 'JOB_SEEKER' ? (
        <div className="space-y-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="card">
              <h3 className="text-xl font-semibold mb-2">Applications</h3>
              <p className="text-3xl font-bold">{stats.applicationCount}</p>
              <Link
                to="/applications"
                className="text-blue-600 hover:text-blue-700 mt-4 inline-block"
              >
                View Applications
              </Link>
            </div>
            <div className="card">
              <h3 className="text-xl font-semibold mb-2">Available Jobs</h3>
              <p className="text-3xl font-bold">{stats.jobCount}</p>
              <Link
                to="/jobs"
                className="text-blue-600 hover:text-blue-700 mt-4 inline-block"
              >
                Browse Jobs
              </Link>
            </div>
            <div className="card">
              <h3 className="text-xl font-semibold mb-2">Companies</h3>
              <p className="text-3xl font-bold">{stats.companyCount}</p>
              <Link
                to="/companies"
                className="text-blue-600 hover:text-blue-700 mt-4 inline-block"
              >
                View Companies
              </Link>
            </div>
          </div>
          
          <div className="card">
            <h2 className="text-2xl font-semibold mb-4">Recent Applications</h2>
            <p className="text-gray-500">No recent applications</p>
          </div>
        </div>
      ) : (
        <div className="space-y-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="card">
              <h3 className="text-xl font-semibold mb-2">Posted Jobs</h3>
              <p className="text-3xl font-bold">{stats.jobCount}</p>
              <Link
                to="/jobs/new"
                className="text-blue-600 hover:text-blue-700 mt-4 inline-block"
              >
                Post New Job
              </Link>
            </div>
            <div className="card">
              <h3 className="text-xl font-semibold mb-2">Applications</h3>
              <p className="text-3xl font-bold">{stats.applicationCount}</p>
              <Link
                to="/applications"
                className="text-blue-600 hover:text-blue-700 mt-4 inline-block"
              >
                View Applications
              </Link>
            </div>
            <div className="card">
              <h3 className="text-xl font-semibold mb-2">Profile Views</h3>
              <p className="text-3xl font-bold">0</p>
              <Link
                to="/profile"
                className="text-blue-600 hover:text-blue-700 mt-4 inline-block"
              >
                Update Profile
              </Link>
            </div>
          </div>
          
          <div className="card">
            <h2 className="text-2xl font-semibold mb-4">Recent Applications</h2>
            <p className="text-gray-500">No recent applications</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard; 