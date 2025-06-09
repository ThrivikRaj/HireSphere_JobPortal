import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="space-y-16">
      {/* Hero Section */}
      <section className="bg-blue-600 text-white">
        <div className="container mx-auto px-6 py-20">
          <div className="max-w-3xl">
            <h1 className="text-4xl md:text-5xl font-bold mb-6">
              Find Your Dream Job Today
            </h1>
            <p className="text-xl mb-8">
              Connect with top companies and discover opportunities that match your skills and aspirations.
            </p>
            <div className="flex flex-col sm:flex-row gap-4">
              <Link
                to="/jobs"
                className="bg-white text-blue-600 px-6 py-3 rounded-lg font-semibold hover:bg-blue-50 transition-colors"
              >
                Browse Jobs
              </Link>
              <Link
                to="/register"
                className="bg-blue-700 text-white px-6 py-3 rounded-lg font-semibold hover:bg-blue-800 transition-colors"
              >
                Create Account
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="container mx-auto px-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="p-6 bg-white dark:bg-gray-800 rounded-lg shadow">
            <h3 className="text-xl font-semibold mb-4 text-gray-800 dark:text-white">
              For Job Seekers
            </h3>
            <p className="text-gray-600 dark:text-gray-300">
              Create a profile, showcase your skills, and apply to jobs that match your expertise.
            </p>
          </div>
          <div className="p-6 bg-white dark:bg-gray-800 rounded-lg shadow">
            <h3 className="text-xl font-semibold mb-4 text-gray-800 dark:text-white">
              For Employers
            </h3>
            <p className="text-gray-600 dark:text-gray-300">
              Post jobs, find qualified candidates, and manage your hiring process efficiently.
            </p>
          </div>
          <div className="p-6 bg-white dark:bg-gray-800 rounded-lg shadow">
            <h3 className="text-xl font-semibold mb-4 text-gray-800 dark:text-white">
              Smart Matching
            </h3>
            <p className="text-gray-600 dark:text-gray-300">
              Our advanced algorithm matches candidates with the most suitable job opportunities.
            </p>
          </div>
        </div>
      </section>

      {/* Call to Action */}
      <section className="bg-gray-100 dark:bg-gray-900">
        <div className="container mx-auto px-6 py-16 text-center">
          <h2 className="text-3xl font-bold mb-6 text-gray-800 dark:text-white">
            Ready to Start Your Journey?
          </h2>
          <p className="text-xl mb-8 text-gray-600 dark:text-gray-300">
            Join thousands of professionals who found their dream jobs through our platform.
          </p>
          <Link
            to="/register"
            className="bg-blue-600 text-white px-8 py-4 rounded-lg font-semibold hover:bg-blue-700 transition-colors"
          >
            Get Started Now
          </Link>
        </div>
      </section>
    </div>
  );
};

export default Home; 