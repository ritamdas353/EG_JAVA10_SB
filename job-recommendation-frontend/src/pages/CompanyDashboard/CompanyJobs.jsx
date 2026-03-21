import React, { useEffect, useState } from "react";
import JobCard from "../../components/JobCard";
import "./CompanyJobs.css";
import SkillsModal from "../../components/skills/SkillsModal";
import EditJobModal from "../../components/EditJobModal/EditJobModal";
import API from "../../services/api";

const CompanyJobs = () => {
    const [jobs, setJobs] = useState([]);
    const [search, setSearch] = useState("");

    const [showSkillsModal, setShowSkillsModal] = useState(false);
    const [selectedJob, setSelectedJob] = useState(null);
    const [selectedSkills, setSelectedSkills] = useState([]);

    const [showEditModal, setShowEditModal] = useState(false);
    const [selectedJobForEdit, setSelectedJobForEdit] = useState(null);

    const [minPercentage, setMinPercentage] = useState({}); // default 70%

    const token = localStorage.getItem("token");

    const fetchJobs = async () => {
        try {
            const res = await API.get(
                "/api/company/companyjobs",
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );
            setJobs(res.data.jobs);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchJobs();
    }, []);

    // 🔥 DELETE JOB
    const handleDelete = async (jobId) => {
        if (!window.confirm("Are you sure you want to delete this job?")) return;

        try {
            await API.delete(
                `/api/job/deleteJob/${jobId}`,
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );

            fetchJobs(); // refresh
        } catch (err) {
            console.error(err);
        }
    };

    // 🔥 NOTIFY
    const handleNotify = async (job, minPercentage) => {
        if (!window.confirm(`Notify applicants with at least ${Math.round(minPercentage * 100)}% skill match?`)) return;

        try {
            await API.post(
                `/api/job/notify/${job.jobId}/${minPercentage}`,
                {},
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );

            alert("Candidates notified!");
        } catch (err) {
            console.error(err);
        }
    };

    // 🔥 EDIT JOB (basic version)
    const handleEdit = (job) => {
        setSelectedJobForEdit(job);
        setShowEditModal(true);
    };

    const saveEditedJob = async (updatedData) => {
        try {
            await API.put(
                `/api/job/${selectedJobForEdit.jobId}`,
                {
                    ...updatedData,
                    min_exp: Number(updatedData.min_exp),
                    skills: updatedData.skills.map(s => ({
                        skill_id: s.skill_id
                    })),
                },
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );

            alert("Job updated!");
            setShowEditModal(false);
            fetchJobs();
        } catch (err) {
            console.error(err);
        }
    };

    // 🔥 EDIT SKILLS (basic version)
    const handleEditSkills = (job) => {
        setSelectedJob(job);

        // preload skills (same format as your modal expects)
        const formattedSkills = job.skillDTOs.map((s) => ({
            skill_id: s.id,
            skill_name: s.skill_name,
        }));

        setSelectedSkills(formattedSkills);
        setShowSkillsModal(true);
    };

    // 🔍 FILTER
    const filteredJobs = jobs.filter((job) =>
        job.jobTitle.toLowerCase().includes(search.toLowerCase()) ||
        job.jobId.toString().includes(search)
    );

    const createJob = async (data) => {
        try {
            await API.post(
                "/api/job/addJob",
                {
                    title: data.title,
                    details: data.details,
                    min_exp: Number(data.min_exp),
                    skills: data.skills.map(s => ({
                        skill_id: s.skill_id
                    }))
                },
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );

            alert("Job created!");
            setShowEditModal(false);
            fetchJobs();
        } catch (err) {
            console.error(err);
        }
    };

    const saveJobSkills = async () => {
        try {
            await API.put(
                "/api/job/updateJobSkills",
                {
                    jobId: selectedJob.jobId,
                    skillIds: selectedSkills.map((s) => s.skill_id),
                },
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );

            alert("Skills updated!");
            setShowSkillsModal(false);
            fetchJobs(); // refresh UI
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="company-jobs-container">

            {/* SEARCH */}
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Search by Job Title or ID..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
            </div>
            <button
                className="add-job-btn"
                onClick={() => {
                    setSelectedJobForEdit(null); // 👈 important
                    setShowEditModal(true);
                }}
            >
                + Add Job
            </button>

            {/* JOB LIST */}
            <div className="jobs-list">
                {filteredJobs.map((job) => (
                    <JobCard
                        key={job.jobId}
                        job={job}
                        onEdit={handleEdit}
                        onEditSkills={handleEditSkills}
                        onDelete={handleDelete}
                        onNotify={handleNotify}
                        minPercentage={minPercentage}
                        setMinPercentage={setMinPercentage}
                    />
                ))}

                {showEditModal && (
                    <EditJobModal
                        job={selectedJobForEdit}
                        closeModal={() => setShowEditModal(false)}
                        onSave={saveEditedJob}
                        onCreate={createJob}   // ✅ ADD THIS
                    />
                )}

                {showSkillsModal && (
                    <SkillsModal
                        selectedSkills={selectedSkills}
                        setSelectedSkills={setSelectedSkills}
                        showSaveButton={true}
                        showCloseButton={true}
                        closeModal={() => setShowSkillsModal(false)}
                        onSave={saveJobSkills}   // 👈 important
                    />
                )}
            </div>
        </div>
    );
};

export default CompanyJobs;