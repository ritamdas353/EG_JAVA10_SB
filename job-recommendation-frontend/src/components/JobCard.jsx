import React from "react";
import "./JobCard.css";

const JobCard = ({ job, onEdit, onEditSkills, onDelete, onNotify, minPercentage, setMinPercentage }) => {
  return (
    <div className="job-card">

      {/* LEFT COLUMN */}
      <div className="job-left">

        {/* Row 1 */}
        <div className="job-row">
          <p><strong>ID:</strong> {job.jobId}</p>
          <p><strong>Title:</strong> {job.jobTitle}</p>
          <p><strong>Details:</strong> {job.jobDetails}</p>
          <p><strong>Min Exp:</strong> {job.jobMinExp} yrs</p>
        </div>

        {/* Row 2 */}
        <div className="job-row">
          <strong>Skills:</strong>
          <div className="skills">
            {job.skillDTOs.map((skill) => (
              <span key={skill.id} className="skill-tag">
                {skill.skill_name}
              </span>
            ))}
          </div>
        </div>

        <div className="slider-container">
          <label>
            Match: {Math.round((minPercentage[job.jobId] ?? 0.7) * 100)}%
          </label>

          <input
            type="range"
            min="0"
            max="1"
            step="0.01"
            value={minPercentage[job.jobId] ?? 0.7}
            onChange={(e) =>
              setMinPercentage(prev => ({
                ...prev,
                [job.jobId]: Number(e.target.value)
              }))
            }
          />
        </div>

        {/* Row 3 */}
        <div className="job-row actions">
          <button onClick={() => onEdit(job)}>Edit Job</button>
          <button onClick={() => onEditSkills(job)}>Edit Skills</button>
          <button className="delete" onClick={() => onDelete(job.jobId)}>
            Delete Job
          </button>
        </div>
      </div>

      {/* RIGHT COLUMN */}
      <div className="job-right">

        <button onClick={() => onNotify(job, minPercentage[job.jobId] ?? 0.7)}>
          Notify Matching Candidates
        </button>

      </div>

    </div>
  );
};

export default JobCard;