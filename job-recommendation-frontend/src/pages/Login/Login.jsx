import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import "./Login.css";
import API from "../../services/api";

function Login() {

  const { type } = useParams();
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const response = await API.post("/api/user/login", {
        user_email: email,
        user_password: password
      });

      const data = await response.data;

      if (!data.success) {
        alert("Login failed");
        return;
      }

      // Convert URL role to uppercase
      const selectedRole = type.toUpperCase();

      // Check if selected role matches actual role
      if (data.user_role !== "ADMIN" && type && data.user_role !== selectedRole) {
        alert(`You are registered as ${data.user_role}. Please login from the correct tab.`);
        return;
      }

      // store JWT
      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.user_role);
      localStorage.setItem("email", email);

      const expiryTime = Date.now() + 60 * 60 * 1000; // 1 hour
      localStorage.setItem("tokenExpiry", expiryTime);

      // redirect based on role
      if (data.user_role === "ADMIN") {
        navigate("/admin/dashboard");
      }
      if (data.user_role === "APPLICANT") {
        navigate("/applicant/dashboard");
      }
      if (data.user_role === "COMPANY") {
        navigate("/company/dashboard");
      }

    }
    catch (error) {
      console.error(error);
      alert("Server error");
    }
  };

  return (
    <div className="login-container">
      <h2>{type} Login</h2>

      <input
        type="email"
        placeholder="Email"
        className="login-input"
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        className="login-input"
        onChange={(e) => setPassword(e.target.value)}
      />

      <button className="login-button" onClick={handleLogin}>
        Login
      </button>

      <p className="login-text">
        New here?{" "}
        <span
          className="login-link"
          onClick={() => navigate(`/signup/${type}`, { replace: true })}
        >
          Sign Up
        </span>
      </p>
    </div>
  );
}

export default Login;