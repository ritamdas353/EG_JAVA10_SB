import React, { useState } from "react";
import "./Account.css";
import API from "../../services/api";

const Account = () => {
  const email = localStorage.getItem("email");

  const [showPasswordBox, setShowPasswordBox] = useState(false);
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");

  if (!email) {
    return <div>Please login again</div>;
  }

  const handleDeleteAccount = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await API.delete("/api/user/delUser", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      // axios success = no error thrown
      alert("Account deleted successfully");
      localStorage.clear();
      window.location.href = "/";
    }
    catch (err) {
      console.error(err);
      alert(err.response?.data || "Server error");
    }
  };

  const handleUpdatePassword = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await API.put(
        "/api/user/updateUserPassword",
        {
          oldPassword,
          newPassword,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Password updated successfully");

      setOldPassword("");
      setNewPassword("");
      setShowPasswordBox(false);
    } catch (err) {
      console.error(err);
      alert("Server error");
    }
  };

  return (
    <div className="account-container">
      <div className="account-card">
        <h2>Account Details</h2>

        <div className="account-info">
          <label>Email:</label>
          <span>{email}</span>
        </div>

        {/* Dropdown Toggle */}
        <button
          className="dropdown-btn"
          onClick={() => setShowPasswordBox(!showPasswordBox)}
        >
          {showPasswordBox ? "Hide Password Update" : "Update Password"}
        </button>

        {/* Dropdown Content */}
        {showPasswordBox && (
          <div className="password-section">
            <input
              type="password"
              placeholder="Old Password"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              className="account-input"
            />

            <input
              type="password"
              placeholder="New Password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="account-input"
            />

            <button className="update-btn" onClick={handleUpdatePassword}>
              Save Password
            </button>
          </div>
        )}

        <button className="delete-btn" onClick={handleDeleteAccount}>
          Delete Account
        </button>
      </div>
    </div>
  );
};

export default Account;