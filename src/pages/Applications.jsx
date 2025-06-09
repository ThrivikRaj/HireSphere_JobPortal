import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const Applications = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // TODO: Replace with actual API call
    const fetchApplications = async () => {
      try {
        setLoading(true);
        // const response = await applicationService.getUserApplications();
        // setApplications(response.data);
        setApplications([]); // Temporary empty array
      } catch (err) {
        setError('Failed to fetch applications');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchApplications();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-10">
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">My Applications</h1>
      <div className="space-y-6">
        {applications.length === 0 ? (
          <div className="text-center py-10">
            <p className="text-gray-500 mb-4">You haven't applied to any jobs yet</p>
            <Link
              to="/jobs"
              className="btn-primary"
            >
              Browse Jobs
            </Link>
          </div>
        ) : (
          applications.map((application) => (
            <div key={application.id} className="card">
              <div className="flex justify-between items-start">
                <div>
                  <h3 className="text-xl font-semibold">
                    <Link
                      to={`/jobs/${application.job.id}`}
                      className="hover:text-blue-600"
                    >
                      {application.job.title}
                    </Link>
                  </h3>
                  <p className="text-gray-600 dark:text-gray-300">
                    {application.job.company.name}
                  </p>
                </div>
                <span
                  className={`px-3 py-1 rounded-full text-sm ${
                    application.status === 'PENDING'
                      ? 'bg-yellow-100 text-yellow-800'
                      : application.status === 'ACCEPTED'
                      ? 'bg-green-100 text-green-800'
                      : 'bg-red-100 text-red-800'
                  }`}
                >
                  {application.status}
                </span>
              </div>
              <div className="mt-4">
                <p className="text-gray-600 dark:text-gray-300">
                  Applied on: {new Date(application.createdAt).toLocaleDateString()}
                </p>
                {application.coverLetter && (
                  <div className="mt-4">
                    <h4 className="font-semibold mb-2">Cover Letter</h4>
                    <p className="text-gray-600 dark:text-gray-300">
                      {application.coverLetter}
                    </p>
                  </div>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default Applications; 