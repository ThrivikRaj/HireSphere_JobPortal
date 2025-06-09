import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const Companies = () => {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // TODO: Replace with actual API call
    const fetchCompanies = async () => {
      try {
        setLoading(true);
        // const response = await companyService.getAllCompanies();
        // setCompanies(response.data);
        setCompanies([]); // Temporary empty array
      } catch (err) {
        setError('Failed to fetch companies');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCompanies();
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
      <h1 className="text-3xl font-bold mb-8">Companies</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {companies.length === 0 ? (
          <div className="col-span-full text-center py-10">
            <p className="text-gray-500">No companies found</p>
          </div>
        ) : (
          companies.map((company) => (
            <Link
              key={company.id}
              to={`/companies/${company.id}`}
              className="card hover:shadow-lg transition-shadow"
            >
              <div className="flex items-center space-x-4">
                <div className="w-16 h-16 bg-gray-200 rounded-full flex items-center justify-center">
                  {company.logo ? (
                    <img
                      src={company.logo}
                      alt={company.name}
                      className="w-full h-full rounded-full object-cover"
                    />
                  ) : (
                    <span className="text-2xl font-bold text-gray-500">
                      {company.name.charAt(0)}
                    </span>
                  )}
                </div>
                <div>
                  <h3 className="text-xl font-semibold">{company.name}</h3>
                  <p className="text-gray-600 dark:text-gray-300">
                    {company.industry}
                  </p>
                </div>
              </div>
              <div className="mt-4">
                <p className="text-sm text-gray-500">
                  {company.jobCount} open positions
                </p>
              </div>
            </Link>
          ))
        )}
      </div>
    </div>
  );
};

export default Companies; 