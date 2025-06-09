import React from 'react';
import JobCard from './JobCard';

const JobList = ({ jobs, onApply }) => {
  if (jobs.length === 0) {
    return (
      <div className="text-center py-8">
        <h3 className="text-xl text-gray-600">No jobs found matching your criteria</h3>
        <p className="text-gray-500 mt-2">Try adjusting your filters or check back later</p>
      </div>
    );
  }

  return (
    <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
      {jobs.map(job => (
        <JobCard
          key={job.id}
          job={job}
          onApply={() => onApply(job.id)}
        />
      ))}
    </div>
  );
};

export default JobList; 