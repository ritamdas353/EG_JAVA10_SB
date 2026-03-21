import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./CompanyCreateProfile.css"

export default function CompanyCreateProfile() {
  const navigate = useNavigate();
  const handleSubmit = async () => {
    try {

      const res = await axios.post(
        "http://localhost:8080/api/company/registerCompany",
        formData,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            "Content-Type": "application/json"
          }
        }
      );

      console.log(res.data);
      alert("Profile created successfully!");
      navigate("/company/dashboard", { replace: true });

    } catch (err) {
      console.error(err);
      alert("Error creating profile");
    }
  };
  const [formData, setFormData] = useState({
    comp_name: "",
    details: "",
    location: "",
    website: ""
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div className="form-container">
      <h2>Create Profile</h2>

      <div className="form-group">
        <label>Company Name :</label>
        <input name="comp_name" placeholder="Enter company name" onChange={handleChange} />
      </div>

      <div className="form-group">
        <label>Company Details:</label>
        <textarea name="details" placeholder="Add Company Detail" onChange={handleChange} />
      </div>

      <div className="form-group">
        <label>Company Location :</label>
        <input name="location" placeholder="Address/City/District/Landmark" onChange={handleChange} />
      </div>

      <div className="form-group">
        <label>Company Website :</label>
        <input name="website" placeholder="Enter Company Website" onChange={handleChange} />
      </div>

      <button className="submit-btn" onClick={handleSubmit}>
        Submit Profile
      </button>
    </div>


  );
}