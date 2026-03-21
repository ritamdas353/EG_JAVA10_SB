import React, { useEffect, useState } from "react";
import axios from "axios";
import SkillCard from "../../components/skills/SkillCard";
import AdminSkillsModal from "../../components/skills/AdminSkillsModal";
import "./AdminDashboard.css";

const AdminDashboard = () => {
    const [skills, setSkills] = useState([]);
    const [search, setSearch] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [selectedSkill, setSelectedSkill] = useState(null);

    const fetchSkills = async () => {
        try {
            const token = localStorage.getItem("token");

            const res = await axios.get(
                "http://localhost:8080/api/skill/presentSkills",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            if (res.data.success) {
                setSkills(res.data.skills);
            }
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchSkills();
    }, []);

    const handleAdd = () => {
        setSelectedSkill(null);
        setShowModal(true);
    };

    const handleEdit = (skill) => {
        setSelectedSkill(skill);
        setShowModal(true);
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Delete this skill?")) return;
        await axios.delete(`http://localhost:8080/api/skill/${id}`);
        fetchSkills();
    };

    const filteredSkills = skills.filter((s) =>
        s.skill_name.toLowerCase().includes(search.toLowerCase())
    );

    return (
        <div className="admin-container">
            <input
                type="text"
                placeholder="Search skills..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                className="search-bar"
            />

            <button className="add-btn" onClick={handleAdd}>
                + Add Skill
            </button>

            <div className="skills-list">
                {filteredSkills.map((skill) => (
                    <SkillCard
                        key={skill.id}
                        skill={skill}
                        onEdit={handleEdit}
                        onDelete={handleDelete}
                    />
                ))}
            </div>

            {showModal && (
                <AdminSkillsModal
                    skill={selectedSkill}
                    closeModal={() => setShowModal(false)}
                    refresh={fetchSkills}
                />
            )}
        </div>
    );
};

export default AdminDashboard;