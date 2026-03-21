import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Layout from "../../components/layout/Layout";

import ViewProfile from "./ViewProfile";
import EditCompanyProfile from "./EditCompanyProfile";
import Account from "../../components/account/Account";
import CompanyJobs from "./CompanyJobs";

import API from "../../services/api";

function CompanyDashboard() {

  const navigate = useNavigate();
  const [tab, setTab] = useState("view");
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProfile = async () => {
    try {
      const res = await API.get("/api/company/companyProfile", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      });

      const data = await res.data;
      setProfile(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    API.get("/api/company/companyProfile", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    })
      .then(res => {
        setProfile(res.data);
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