import React, { useState } from "react";
import "./Account.css";

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

      const response = await fetch("http://localhost:8080/api/user/delUser", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        alert("Failed to delete account");
        return;
      }

      localStorage.clear();
      window.location.href = "/";
    } catch (err) {
      console.error(err);
      alert("Server error");
    }
  };

  const handleUpdatePassword = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await fetch(
        "http://localhost:8080/api/user/updateUserPassword",
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            oldPassword,
            newPassword,
          }),
        }
      );

      if (!response.ok) {
        alert("Password update failed");
        return;
      }

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