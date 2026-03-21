import React from "react";
import "./SkillCard.css";

const SkillCard = ({ skill, onEdit, onDelete }) => {
  return (
    <div className="skill-card">
      <span>{skill.skill_name}</span>

      <div className="actions">
        <button onClick={() => onEdit(skill)}>Edit</button>
        <button className="delete" onClick={() => onDelete(skill.id)}>
          Delete
        </button>
      </div>
    </div>
  );
};

export default SkillCard;