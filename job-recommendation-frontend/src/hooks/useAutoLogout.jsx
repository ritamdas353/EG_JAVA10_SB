import { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useRef } from "react";

const useAutoLogout = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const hasLoggedOut = useRef(false);

  const logout = () => {
    if (hasLoggedOut.current) return;
    hasLoggedOut.current = true;

    localStorage.removeItem("token");
    localStorage.removeItem("tokenExpiry");

    alert("Session expired. Please login again.");
    navigate("/", { replace: true });
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    const expiry = Number(localStorage.getItem("tokenExpiry"));

    if (!token || !expiry) return;

    // 🔥 instant check (on route change)
    if (Date.now() > expiry) {
      logout();
    }
  }, [location]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const expiry = Number(localStorage.getItem("tokenExpiry"));

    if (!token || !expiry) return;

    const timeLeft = expiry - Date.now();

    if (timeLeft <= 0) {
      logout();
    } else {
      const timer = setTimeout(logout, timeLeft);
      return () => clearTimeout(timer);
    }
  }, [navigate]);
};

export default useAutoLogout;