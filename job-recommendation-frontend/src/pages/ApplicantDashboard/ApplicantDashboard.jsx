import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Layout from "../../components/layout/Layout";

import ViewProfile from "./ViewProfile";
import EditApplicantProfile from "./EditApplicantProfile";
import Account from "../../components/account/Account";

import API from "../../services/api";

function ApplicantDashboard() {

  const navigate = useNavigate();
  const [tab, setTab] = useState("view");
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProfile = async () => {
    try {
      const res = await API.get("/api/applicant/applicantProfile", {
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
    API.get("/api/applicant/applicantProfile", {
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
      navigate("/applicant/createProfile", { replace: true });
    }
  }, [loading, profile, navigate]);

  if (loading) return <p>Loading...</p>;

  return (
    <Layout setTab={setTab}>
      {tab === "view" && <ViewProfile profile={profile} />}

      {tab === "edit" &&
        <EditApplicantProfile
          profile={profile}
          fetchProfile={fetchProfile}
        />}

      {tab === "account" && <Account />}
    </Layout>
  );
}

export default ApplicantDashboard;