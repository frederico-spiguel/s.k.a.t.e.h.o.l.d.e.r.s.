// src/App.jsx
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

// Suas páginas existentes
import Login from "./pages/Login.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Register from "./pages/Register.jsx";
import Triagem from "./pages/Triagem.jsx";

// Nossas novas páginas para a funcionalidade de Sessões
import SessoesPage from "./pages/SessoesPage.jsx";
import SessaoDetalhe from "./pages/SessaoDetalhe.jsx";

function App() {
  return (
    <Router>
      <Routes>
        {/* Rotas Antigas */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/triagem" element={<Triagem />} />

        {/* --- NOVAS ROTAS PARA A FUNCIONALIDADE DE SESSÕES --- */}
        <Route path="/sessoes" element={<SessoesPage />} />
        <Route path="/sessoes/:data" element={<SessaoDetalhe />} />
        
      </Routes>
    </Router>
  );
}

export default App;