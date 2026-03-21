import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./Signup.css";
import API from "../../services/api";

function Signup() {

  const { type } = useParams();
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSignup = async () => {

    const response = await API.post("/api/user/sign-up", {
      user_email: email,
      user_password: password,
      user_role: type.toUpperCase()
    });

    const data = await response.data;

    if (data.success) {
      alert("Signup successful!");
      navigate(`/login/${type}`, { replace: true });
    } else {
      alert("Signup failed");
    }
  };

  return (
    <div className="signup-container">

      <h2>{type} Signup</h2>

      <input
        type="email"
        placeholder="Email"
        className="signup-input"
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        className="signup-input"
        onChange={(e) => setPassword(e.target.value)}
      />

      <button className="signup-button" onClick={handleSignup}>
        Sign Up
      </button>

      <p className="signup-text">
        Already have an account?{" "}
        <span
          className="signup-link"
          onClick={() => navigate(`/login/${type}`, { replace: true })}
        >
          Login
        </span>
      </p>

    </div>
  );
}

export default Signup;