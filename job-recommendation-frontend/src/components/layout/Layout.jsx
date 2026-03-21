import Header from "./Header";
import Footer from "./Footer";
import "./Layout.css";

function Layout({ children, setTab }) {

  return (

    <div className="app-layout">

      <Header setTab={setTab} />

      <main className="page-content">
        {children}
      </main>

      <Footer />

    </div>

  );
}

export default Layout;