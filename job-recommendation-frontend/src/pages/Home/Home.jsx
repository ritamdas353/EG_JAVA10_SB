import { useNavigate } from "react-router-dom";
import "./Home.css";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      <button
        className="home-button"
        onClick={() => navigate("/login/applicant")}
      >
        Applicant
      </button>

      <button
        className="home-button"
        onClick={() => navigate("/login/company")}
      >
        Company
      </button>
    </div>
  );
}

export default Home;