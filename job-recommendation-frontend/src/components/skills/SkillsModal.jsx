import { useEffect, useState } from "react";
import "./SkillModal.css"
import API from "../../services/api";

export default function SkillsModal({
  selectedSkills,
  setSelectedSkills,
  closeModal,
  showDoneButton = false,
  showSaveButton = false,
  showCloseButton = false,
  onSave
}) {
  const [skills, setSkills] = useState([]);
  const [search, setSearch] = useState("");

  // 🔥 Fetch from YOUR API
  useEffect(() => {
    API.get("/api/skill/presentSkills")
      .then(res => {
        if (res.data.success) {
          setSkills(res.data.skills); // ✅ IMPORTANT FIX
        }
      })
      .catch(err => console.error(err));
  }, []);

  const toggleSkill = (skill) => {
    const exists = selectedSkills.find(s => s.skill_id === skill.id);

    if (exists) {
      setSelectedSkills(
        selectedSkills.filter(s => s.skill_id !== skill.id)
      );
    } else {
      setSelectedSkills([
        ...selectedSkills,
        {
          skill_id: skill.id,          // ✅ convert to your required format
          skill_name: skill.skill_name // (for UI only)
        }
      ]);
    }
  };

  const filteredSkills = skills.filter(skill =>
    skill.skill_name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="modal-overlay">
      <div className="modal">

        {/* Search */}
        <input
          placeholder="Search skills..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="search-bar"
        />

        {/* Selected Skills */}

        {/* GRID */}
        <div className="tags">
          {filteredSkills.map(skill => {
            const isSelected = selectedSkills.some(
              s => s.skill_id === skill.id
            );

            return (
              <span
                key={skill.id}
                className={`tag ${isSelected ? "active" : ""}`}
                onClick={() => toggleSkill(skill)}
              >
                {skill.skill_name}
              </span>
            );
          })}
        </div>

        {showDoneButton && (
          <button onClick={closeModal} className="done-btn">
            Done
          </button>
        )}

        <div className="modal-buttons">
          {showSaveButton && (
            <button onClick={onSave} className="btn">
              Save
            </button>
          )}

          {showCloseButton && (
            <button onClick={closeModal} className="btn cancel">
              Cancel
            </button>
          )}
        </div>

      </div>
    </div>
  );
}