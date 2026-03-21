import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import Signup from "./pages/Signup/Signup";
import ApplicantDashboard from "./pages/ApplicantDashboard/ApplicantDashboard";
import CompanyDashboard from "./pages/CompanyDashboard/CompanyDashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import Layout from "./components/layout/Layout";
import ApplicantCreateProfile from "./pages/CreateProfile/ApplicantCreateProfile";
import CompanyCreateProfile from "./pages/CreateProfile/CompanyCreateProfile";
import useAutoLogout from "./hooks/useAutoLogout";
import AdminDashboard from "./pages/AdminDashboard/AdminDashboard"

function App() {
  const AutoLogoutWrapper = () => {
    useAutoLogout();
    return null;
  };
  return (
    <BrowserRouter>
      <AutoLogoutWrapper />
      <Routes>

        <Route path="/" element={
          <Layout>
            <Home />
          </Layout>
        } />

        <Route path="/login/:type" element={
          <Layout>
            <Login />
          </Layout>
        } />

        <Route path="/signup/:type" element={
          <Layout>
            <Signup />
          </Layout>
        } />

        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <Layout>
                <AdminDashboard />
              </Layout>
            </ProtectedRoute>
          }
        />

        <Route
          path="/applicant/dashboard"
          element={
            <ProtectedRoute roleRequired="APPLICANT">
              <ApplicantDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/company/dashboard"
          element={
            <ProtectedRoute roleRequired="COMPANY">
              <CompanyDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/applicant/createProfile"
          element={
            <ProtectedRoute roleRequired="APPLICANT">
              <Layout>
                <ApplicantCreateProfile />
              </Layout>
            </ProtectedRoute>
          }
        />

        <Route
          path="/company/createProfile"
          element={
            <ProtectedRoute roleRequired="COMPANY">
              <Layout>
                <CompanyCreateProfile />
              </Layout>
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;