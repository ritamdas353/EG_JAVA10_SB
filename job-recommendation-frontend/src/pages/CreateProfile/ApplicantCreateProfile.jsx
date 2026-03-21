import { useState } from "react";
import { useNavigate } from "react-router-dom";
import SkillsModal from "../../components/skills/SkillsModal";
import "./ApplicantCreateProfile.css"
import API from "../../services/api";

export default function ApplicantCreateProfile() {
  const navigate = useNavigate();
  const handleSubmit = async () => {
    try {
      const payload = {
        ...formData,
        skills: formData.skills.map(s => ({
          skill_id: s.skill_id   // 🔥 IMPORTANT (backend format)
        }))
      };

      const res = await API.post(
        "/api/applicant/registerApplicant",
        payload,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
          }
        }
      );

      console.log(res.data);
      alert("Profile created successfully!");
      navigate("/applicant/dashboard", { replace: true });

    } catch (err) {
      console.error(err);
      alert("Error creating profile");
    }
  };
  const [formData, setFormData] = useState({
    applicant_name: "",
    applicant_ph_num: "",
    applicant_yrs_of_exp: "",
    applicant_cgpa: "",
    applicant_high_qual: "",
    applicant_address: "",
    skills: []
  });

  const [showModal, setShowModal] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div className="form-container">
      <h2>Create Profile</h2>

      <div className="form-group">
        <label>Name :</label>
        <input name="applicant_name" placeholder="Enter your name" onChange={handleChange} />
      </div>

      <div className="form-group">
        <label>Phone number:</label>
        <input type="number" name="applicant_ph_num" placeholder="Phone number" onChange={handleChange} onWheel={(e) => e.target.blur()} />
      </div>

      <div className="form-group">
        <label>Experience :</label>
        <input type="number" name="applicant_yrs_of_exp" placeholder="Years of Industry Experience" onChange={handleChange} onWheel={(e) => e.target.blur()} />
      </div>

      <div className="form-group">
        <label>CGPA :</label>
        <input type="number" name="applicant_cgpa" placeholder="CGPA" onChange={handleChange} onWheel={(e) => e.target.blur()} />
      </div>

      <div className="form-group">
        <label>Qualification :</label>
        <input name="applicant_high_qual" placeholder="Highest Qualification" onChange={handleChange} />
      </div>

      <div className="form-group">
        <label>Address :</label>
        <input name="applicant_address" placeholder="Residential Address" onChange={handleChange} />
      </div>

      {/* Skills Section */}
      <div className="skills-section">
        <h4>Skills :</h4>

        <div className="selected-skills">
          {formData.skills.map((skill) => (
            <span key={skill.skill_id} className="skill-tag">
              {skill.skill_name}
            </span>
          ))}
        </div>

        <button onClick={() => setShowModal(true)}>Add Skills</button>
      </div>

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
      <button className="submit-btn" onClick={handleSubmit}>
        Submit Profile
      </button>
    </div>


  );
}