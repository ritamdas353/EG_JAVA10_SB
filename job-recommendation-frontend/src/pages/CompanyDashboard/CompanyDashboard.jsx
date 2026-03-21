import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Layout from "../../components/layout/Layout";

import ViewProfile from "./ViewProfile";
import EditCompanyProfile from "./EditCompanyProfile";
import Account from "../../components/account/Account";
import CompanyJobs from "./CompanyJobs";

function CompanyDashboard() {

  const navigate = useNavigate();
  const [tab, setTab] = useState("view");
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProfile = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/company/companyProfile", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      });

      const data = await res.json();
      setProfile(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/company/companyProfile", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    })
      .then(res => {
        if (!res.ok) {
          return null;
        }
        return res.json();
      })
      .then(data => {
        setProfile(data);
        setLoading(false);
      })
      .catch(() => {
        setProfile(null);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    if (!loading && profile === null) {
      navigate("/company/createProfile", { replace: true });
    }
  }, [loading, profile, navigate]);

  if (loading) return <p>Loading...</p>;

  return (
    <Layout setTab={setTab}>
      {tab === "view" && <ViewProfile profile={profile} />}

      {tab === "edit" &&
        <EditCompanyProfile
          profile={profile}
          fetchProfile={fetchProfile}
        />
      }

      {tab === "jobs" && <CompanyJobs />}

      {tab === "account" && <Account />}
    </Layout>
  );
}

export default CompanyDashboard;