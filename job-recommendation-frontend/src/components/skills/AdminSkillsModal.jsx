import { useState, useEffect } from "react";
import axios from "axios";
import "./SkillModal.css"; // reuse same CSS 🔥

export default function AdminSkillModal({ skill, closeModal, refresh }) {
  const [name, setName] = useState("");

  useEffect(() => {
    if (skill) setName(skill.skill_name);
  }, [skill]);

  const handleSubmit = async () => {
    const payload = {
      skill_name: name.toUpperCase()
    };

    if (skill) {
      // EDIT
      await axios.put(
        `http://localhost:8080/api/skill/${skill.id}`,
        payload
      );
    } else {
      // ADD
      await axios.post(
        "http://localhost:8080/api/skill/addSkills",
        payload
      );
    }

    refresh();
    closeModal();
  };

  return (
    <div className="modal-overlay">
      <div className="modal">

        <h2>{skill ? "Edit Skill" : "Add Skill"}</h2>

        <input
          type="text"
          placeholder="Enter skill name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          className="search-bar"
        />

        <div className="modal-buttons">
          <button onClick={handleSubmit} className="btn">
            {skill ? "Save Changes" : "Create Skill"}
          </button>

          <button onClick={closeModal} className="btn cancel">
            Cancel
          </button>
        </div>

      </div>
    </div>
  );
}