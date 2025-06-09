import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className="bg-white dark:bg-gray-800 shadow-inner">
      <div className="container mx-auto px-6 py-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <h3 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">JobPortal</h3>
            <p className="text-gray-600 dark:text-gray-300">
              Connecting talented professionals with great opportunities.
            </p>
          </div>
          <div>
            <h4 className="text-md font-semibold text-gray-800 dark:text-white mb-4">For Job Seekers</h4>
            <ul className="space-y-2">
              <li>
                <Link to="/jobs" className="text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400">
                  Browse Jobs
                </Link>
              </li>
              <li>
                <Link to="/companies" className="text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400">
                  Browse Companies
                </Link>
              </li>
            </ul>
          </div>
          <div>
            <h4 className="text-md font-semibold text-gray-800 dark:text-white mb-4">For Employers</h4>
            <ul className="space-y-2">
              <li>
                <Link to="/register" className="text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400">
                  Post a Job
                </Link>
              </li>
              <li>
                <Link to="/companies" className="text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400">
                  Company Profiles
                </Link>
              </li>
            </ul>
          </div>
          <div>
            <h4 className="text-md font-semibold text-gray-800 dark:text-white mb-4">Legal</h4>
            <ul className="space-y-2">
              <li>
                <Link to="/privacy" className="text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400">
                  Privacy Policy
                </Link>
              </li>
              <li>
                <Link to="/terms" className="text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400">
                  Terms of Service
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className="border-t border-gray-200 dark:border-gray-700 mt-8 pt-8 text-center">
          <p className="text-gray-600 dark:text-gray-300">
            © {new Date().getFullYear()} JobPortal. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer; 