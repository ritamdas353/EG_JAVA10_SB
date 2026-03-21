// ViewProfile.jsx
import React, { useEffect, useState } from "react";
import "./ViewProfile.css";
import SkillsModal from "../../components/skills/SkillsModal";
import API from "../../services/api";

const ViewProfile = () => {
  const [profile, setProfile] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const [selectedSkills, setSelectedSkills] = useState([]);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem("token"); // or wherever you stored it

      const res = await API.get(
        "/api/applicant/applicantProfile",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setProfile(res.data.applicant);
      setSelectedSkills(
        res.data.applicant.skills.map((s) => ({
          skill_id: s.id,
          skill_name: s.skill_name
        }))
      );
    } catch (err) {
      console.error(err);
    }
  };

  const saveSkills = async () => {
    try {
      const token = localStorage.getItem("token");

      await API.put(
        "/api/applicant/updateSkills",
        selectedSkills.map(s => s.skill_id), // 🔥 IMPORTANT
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

  if (!profile) return <div>Loading...</div>;

  return (
    <div className="profile-container">
      <h2>Applicant Profile</h2>

      <table className="profile-table">
        <tbody>
          <tr>
            <td>Name</td>
            <td>{profile.applicantName}</td>
          </tr>
          <tr>
            <td>Email</td>
            <td>{profile.applicantEmail}</td>
          </tr>
          <tr>
            <td>Phone</td>
            <td>{profile.applicantPhNumber}</td>
          </tr>
          <tr>
            <td>Experience</td>
            <td>{profile.yrsOfExperience} years</td>
          </tr>
          <tr>
            <td>CGPA</td>
            <td>{profile.applicantCGPA}</td>
          </tr>
          <tr>
            <td>Qualification</td>
            <td>{profile.applicantHighQual}</td>
          </tr>
          <tr>
            <td>Address</td>
            <td>{profile.applicantAddress}</td>
          </tr>
          <tr>
            <td>Skills</td>
            <td>
              {profile.skills.map((skill) => (
                <span key={skill.id} className="skill-badge">
                  {skill.skill_name}
                </span>
              ))}
            </td>
          </tr>
        </tbody>
      </table>

      <button className="btn" id="edit-skills" onClick={() => setShowModal(true)}>
        Edit Skills
      </button>

      {showModal && (
        <SkillsModal
          selectedSkills={selectedSkills}
          setSelectedSkills={setSelectedSkills}
          closeModal={() => setShowModal(false)}
          showSaveButton={true}          // ✅ only for this page
          showCloseButton={true}
          onSave={saveSkills}            // ✅ save logic here
        />
      )}
    </div>
  );
};

export default ViewProfile;
