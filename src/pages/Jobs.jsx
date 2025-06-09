import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import JobList from '../components/jobs/JobList';
import JobFilters from '../components/jobs/JobFilters';
import { jobService } from '../services/jobService';

const Jobs = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({
    title: '',
    location: '',
    employmentType: '',
    minSalary: '',
    maxSalary: '',
    experience: ''
  });
  const { isAuthenticated, isEmployer } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchJobs();
  }, [filters]);

  const fetchJobs = async () => {
    try {
      setLoading(true);
      const data = await jobService.getJobs(filters);
      setJobs(data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch jobs. Please try again later.');
      console.error('Error fetching jobs:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleFilterChange = (newFilters) => {
    setFilters(prev => ({ ...prev, ...newFilters }));
  };

  const handleApply = (jobId) => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    navigate(`/jobs/${jobId}/apply`);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col md:flex-row gap-8">
        <div className="w-full md:w-1/4">
          <JobFilters filters={filters} onFilterChange={handleFilterChange} />
        </div>
        
        <div className="w-full md:w-3/4">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-3xl font-bold text-gray-800">Available Jobs</h1>
            {isEmployer && (
              <button
                onClick={() => navigate('/jobs/create')}
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
              >
                Post a Job
              </button>
            )}
          </div>

          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
              {error}
            </div>
          )}

          {loading ? (
            <div className="flex justify-center items-center h-64">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
            </div>
          ) : (
            <JobList jobs={jobs} onApply={handleApply} />
          )}
        </div>
      </div>
    </div>
  );
};

export default Jobs; 