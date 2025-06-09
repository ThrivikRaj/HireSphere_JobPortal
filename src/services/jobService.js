import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const jobService = {
  getAllJobs: async () => {
    const response = await axios.get(`${API_URL}/jobs`);
    return response.data;
  },

  getJobById: async (id) => {
    const response = await axios.get(`${API_URL}/jobs/${id}`);
    return response.data;
  },

  createJob: async (jobData) => {
    const token = localStorage.getItem('token');
    const response = await axios.post(
      `${API_URL}/jobs`,
      jobData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  },

  updateJob: async (id, jobData) => {
    const token = localStorage.getItem('token');
    const response = await axios.put(
      `${API_URL}/jobs/${id}`,
      jobData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  },

  deleteJob: async (id) => {
    const token = localStorage.getItem('token');
    await axios.delete(
      `${API_URL}/jobs/${id}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
  },

  searchJobs: async (query) => {
    const response = await axios.get(`${API_URL}/jobs/search`, {
      params: { query },
    });
    return response.data;
  },

  getJobsByCompany: async (companyId) => {
    const response = await axios.get(`${API_URL}/jobs/company/${companyId}`);
    return response.data;
  },

  applyForJob: async (jobId, applicationData) => {
    const token = localStorage.getItem('token');
    const response = await axios.post(
      `${API_URL}/jobs/${jobId}/apply`,
      applicationData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  },
}; 