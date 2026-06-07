import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';
import Footer from './components/Footer.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';

import Home           from './pages/Home.jsx';
import Catalog        from './pages/Catalog.jsx';
import PerfumeDetail  from './pages/PerfumeDetail.jsx';
import Brands         from './pages/Brands.jsx';
import BrandDetail    from './pages/BrandDetail.jsx';
import Parfumers      from './pages/Parfumers.jsx';
import ParfumerDetail from './pages/ParfumerDetail.jsx';
import Login          from './pages/Login.jsx';
import Register       from './pages/Register.jsx';
import Profile        from './pages/Profile.jsx';
import Admin          from './pages/Admin.jsx';
import Notes          from './pages/Notes.jsx';
import NoteDetail     from './pages/NoteDetail.jsx';
import NotFound       from './pages/NotFound.jsx';

export default function App() {
  return (
    <>
      <Navbar />
      <main className="page anim-fade">
        <Routes>
          <Route path="/"               element={<Home />} />
          <Route path="/catalog"        element={<Catalog />} />
          <Route path="/perfume/:id"    element={<PerfumeDetail />} />
          <Route path="/brands"         element={<Brands />} />
          <Route path="/brand/:id"      element={<BrandDetail />} />
          <Route path="/parfumers"      element={<Parfumers />} />
          <Route path="/parfumer/:id"   element={<ParfumerDetail />} />
          <Route path="/notes"          element={<Notes />} />
          <Route path="/note/:id"       element={<NoteDetail />} />
          <Route path="/login"          element={<Login />} />
          <Route path="/register"       element={<Register />} />
          <Route path="/profile" element={
            <ProtectedRoute><Profile /></ProtectedRoute>
          } />
          <Route path="/admin" element={
            <ProtectedRoute requireAdmin><Admin /></ProtectedRoute>
          } />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}
