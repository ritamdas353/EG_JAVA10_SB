import React, { useEffect, useState } from "react";
import "./EditCompanyProfile.css";
import API from "../../services/api";

const EditCompanyProfile = ({ profile, fetchProfile }) => {
  const [formData, setFormData] = useState({
    comp_name: "",
    details: "",
    location: "",
    website: ""
  });

  const [originalData, setOriginalData] = useState(null);

  // Load initial data
  useEffect(() => {
    if (profile) {
      console.log(profile);
      const initialData = {
        comp_name: profile.company.companyName || "",
        details: profile.company.companyDetails || "",
        location: profile.company.companyLocation || "",
        website: profile.company.companyWebsite || "",
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
        "/api/company/updateCompany",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Profile updated successfully!");
      fetchProfile();
      setOriginalData(formData);
    } catch (err) {
      console.error(err);
      alert("Update failed");
    }
  };

  return (
    <div className="edit-profile-container">
      <h2>Edit Profile</h2>

      <div className="form-grid">
        <div className="form-group">
          <label>Company Name</label>
          <input
            name="comp_name"
            value={formData.comp_name}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Company Details</label>
          <textarea
            name="details"
            value={formData.details}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Location</label>
          <input
            name="location"
            value={formData.location}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Website</label>
          <input
            name="website"
            value={formData.website}
            onChange={handleChange}
          />
        </div>
      </div>

      <button
        className="save-btn"
        onClick={handleSave}
        disabled={!isChanged()}
      >
        Save Company
      </button>
    </div>
  );
};

export default EditCompanyProfile;