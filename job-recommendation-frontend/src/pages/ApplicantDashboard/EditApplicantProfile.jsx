import React, { useEffect, useState } from "react";
import "./EditApplicantProfile.css";
import SkillsModal from "../../components/skills/SkillsModal";
import API from "../../services/api";

const EditApplicantProfile = ({ profile, fetchProfile }) => {
  const [formData, setFormData] = useState({
    applicant_name: "",
    applicant_ph_num: "",
    applicant_yrs_of_exp: "",
    applicant_cgpa: "",
    applicant_high_qual: "",
    applicant_address: "",
    skills: [],
  });

  const [originalData, setOriginalData] = useState(null);
  const [showModal, setShowModal] = useState(false);

  // Load initial data
  useEffect(() => {
    if (profile) {
      console.log(profile);
      const initialData = {
        applicant_name: profile.applicant.applicantName || "",
        applicant_ph_num: profile.applicant.applicantPhNumber || "",
        applicant_yrs_of_exp: profile.applicant.yrsOfExperience || "",
        applicant_cgpa: profile.applicant.applicantCGPA || "",
        applicant_high_qual: profile.applicant.applicantHighQual || "",
        applicant_address: profile.applicant.applicantAddress || "",
        skills: profile.applicant.skills.map(skill => ({
          skill_id: skill.id,
          skill_name: skill.skill_name
        })) || [],
      };

      setFormData(initialData);
      setOriginalData(initialData);
    }
  }, [profile]);

  // Detect changes
  const isChanged = () => {
    return JSON.stringify(formData) !== JSON.stringify(originalData);
  };

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Save applicant
  const handleSave = async () => {
    try {
      const token = localStorage.getItem("token");

      await API.put(
        "/api/applicant/updateApplicant",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      saveSkills(formData.skills);
      alert("Profile updated successfully!");
      fetchProfile();
      setOriginalData(formData);
    } catch (err) {
      console.error(err);
      alert("Update failed");
    }
  };

  const saveSkills = async (skills) => {
    try {
      const token = localStorage.getItem("token");

      await API.put(
        "/api/applicant/updateSkills",
        skills.map(s => s.skill_id), // 🔥 IMPORTANT
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setShowModal(false);
      fetchProfile();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="edit-profile-container">
      <h2>Edit Profile</h2>

      <div className="form-grid">
        <div className="form-group">
          <label>Name</label>
          <input
            name="applicant_name"
            value={formData.applicant_name}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Phone Number</label>
          <input
            name="applicant_ph_num"
            value={formData.applicant_ph_num}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Experience (Years)</label>
          <input
            name="applicant_yrs_of_exp"
            value={formData.applicant_yrs_of_exp}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>CGPA</label>
          <input
            name="applicant_cgpa"
            value={formData.applicant_cgpa}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Highest Qualification</label>
          <input
            name="applicant_high_qual"
            value={formData.applicant_high_qual}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Address</label>
          <input
            name="applicant_address"
            value={formData.applicant_address}
            onChange={handleChange}
          />
        </div>

        {/* Skills Section */}
        <div className="form-group full-width">
          <label>Skills:</label>

          <div className="skills-box">
            {formData.skills.map((skill) => (
              <span key={skill.skill_id} className="skill-badge">
                {skill.skill_name}
              </span>
            ))}
          </div>

          <button
            type="button"
            className="edit-skills-btn"
            onClick={() => setShowModal(true)}
          >
            Edit Skills
          </button>
        </div>
      </div>

      <button
        className="save-btn"
        onClick={handleSave}
        disabled={!isChanged()}
      >
        Save Applicant
      </button>

      {showModal && (
        <SkillsModal
          selectedSkills={formData.skills}
          setSelectedSkills={(skills) =>
            setFormData({ ...formData, skills })
          }
          showDoneButton={true}
          closeModal={() => setShowModal(false)}
        />
      )}
    </div>
  );
};

export default EditApplicantProfile;