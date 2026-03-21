import { useNavigate, useLocation } from "react-router-dom";
import "./Header.css";

function Header({ setTab }) {

  const navigate = useNavigate();
  const location = useLocation();

  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  const path = location.pathname;

  const isAdmin = token && role === "ADMIN";
  const isApplicant = token && role === "APPLICANT";
  const isCompany = token && role === "COMPANY";
  const isAdminDashboard = path.startsWith("/admin/dashboard");
  const isApplicantDashboard = path.startsWith("/applicant/dashboard");
  const isCompanyDashboard = path.startsWith("/company/dashboard");

  const logout = () => {
    localStorage.clear();
    navigate("/", { replace: true });
  };

  // console.log(location.pathname);

  return (
    <header className="header">

      <div
        className="logo"
        onClick={() => navigate("/")}
      >
        JobPortal
      </div>

      <nav className="nav">

        {/* Not logged in */}
        {!token && (
          <>
            <button onClick={() => navigate("/", { replace: true })}>Home</button>
          </>
        )}

        {/* Admin logged in */}
        {isAdmin && (
          <>
            <button onClick={() => {
              logout();
              navigate("/", { replace: true });
            }}>Home</button>

            {isAdminDashboard && (
              <>
              </>
            )}

            <button className="logout-btn" onClick={logout}>Logout</button>
          </>
        )}

        {/* Applicant logged in */}
        {isApplicant && (
          <>
            <button onClick={() => {
              logout();
              navigate("/", { replace: true });
            }
            }>Home</button>

            {isApplicantDashboard && (
              <>
                <button onClick={() => setTab?.("view")}>View Profile</button>
                <button onClick={() => setTab?.("edit")}>Edit Profile</button>
                <button onClick={() => setTab?.("account")}>Account</button>
              </>
            )}

            <button className="logout-btn" onClick={logout}>Logout</button>
          </>
        )}

        {/* Company logged in */}
        {isCompany && (
          <>
           <button onClick={() => {
              logout();
              navigate("/", { replace: true });
            }
            }>Home</button>

            {isCompanyDashboard && (
              <>
                <button onClick={() => setTab?.("view")}>View Profile</button>
                <button onClick={() => setTab?.("jobs")}>Company Jobs</button>
                <button onClick={() => setTab?.("edit")}>Edit Profile</button>
                <button onClick={() => setTab?.("account")}>Account</button>
              </>
            )}

            <button className="logout-btn" onClick={logout}>Logout</button>
          </>
        )}



      </nav>

    </header>
  );
}

export default Header;