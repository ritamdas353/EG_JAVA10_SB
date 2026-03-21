import React, { useEffect, useState } from "react";
import "./EditJobModal.css";

const EditJobModal = ({
    job,
    closeModal,
    onSave,
    onCreate,
    showSaveButton = false,
    showCreateJobButton
}) => {
    const [formData, setFormData] = useState({
        title: "",
        details: "",
        min_exp: "",
    });

    const [originalData, setOriginalData] = useState(null);

    // preload data
    useEffect(() => {
        if (job) {
            const data = {
                title: job.jobTitle || "",
                details: job.jobDetails || "",
                min_exp: job.jobMinExp || "",
                skills: job.skillDTOs.map(s => ({
                    skill_id: s.id,
                    skill_name: s.skill_name
                }))
            };
            setFormData(data);
            setOriginalData(data);
        } else {
            const empty = {
                title: "",
                details: "",
                min_exp: "",
                skills: []
            };
            setFormData(empty);
            setOriginalData(empty);
        }
    }, [job]);

    // detect changes
    const isChanged = () => {
        return JSON.stringify(formData) !== JSON.stringify(originalData);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSave = () => {
        onSave(formData);
    };

    return (
        <div className="modal-overlay">
            <div className="edit-job-container">

                <h2>{job ? "Edit Job" : "Create Job"}</h2>

                <div className="form-grid">

                    <div className="form-group full-width">
                        <label>Title</label>
                        <input
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group full-width">
                        <label>Details</label>
                        <textarea
                            name="details"
                            value={formData.details}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Minimum Experience</label>
                        <input
                            name="min_exp"
                            value={formData.min_exp}
                            onChange={handleChange}
                        />
                    </div>

                </div>

                <div className="modal-buttons">

                    {job ? (
                        <button
                            className="save-btn"
                            disabled={!isChanged()}
                            onClick={() => onSave(formData)}
                        >
                            Save Changes
                        </button>
                    ) : (
                        <button
                            className="save-btn"
                            disabled={!isChanged()}
                            onClick={() => onCreate(formData)}
                        >
                            Create Job
                        </button>
                    )}

                    <button className="cancel-btn" onClick={closeModal}>
                        Cancel
                    </button>

                </div>

            </div>
        </div>
    );
};

export default EditJobModal;