// ViewProfile.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ViewProfile.css";

const ViewProfile = () => {
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem("token"); // or wherever you stored it

      const res = await axios.get(
        "http://localhost:8080/api/company/companyProfile",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setProfile(res.data.company);
    } catch (err) {
      console.error(err);
    }
  };

  if (!profile) return <div>Loading...</div>;

  return (
    <div className="profile-container">
      <h2>Company Profile</h2>

      <table className="profile-table">
        <tbody>
          <tr>
            <td>Company Name</td>
            <td>{profile.companyName}</td>
          </tr>
          <tr>
            <td>Email</td>
            <td>{profile.companyEmail}</td>
          </tr>
          <tr>
            <td>Company Details</td>
            <td>{profile.companyDetails}</td>
          </tr>
          <tr>
            <td>Location</td>
            <td>{profile.companyLocation}</td>
          </tr>
          <tr>
            <td>Website</td>
            <td>{profile.companyWebsite}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default ViewProfile;