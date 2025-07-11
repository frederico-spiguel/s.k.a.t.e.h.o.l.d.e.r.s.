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

// --- AQUI ESTÁ A CORREÇÃO ---
// O caminho correto para o arquivo Triagem.jsx
import Triagem from "./pages/Triagem.jsx";

// Páginas de Sessões
import SessoesPage from "./pages/SessoesPage.jsx";
import SessaoDetalhe from "./pages/SessaoDetalhe.jsx";

// Página de Conquistas
import ConquistasPage from "./pages/ConquistasPage.jsx";

// Página de Perfil
import PerfilPage from "./pages/PerfilPage.jsx";

function App() {
  return (
    <Router>
      <Routes>
        {/* Rotas Públicas e de Autenticação */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/triagem" element={<Triagem />} />

        {/* Rotas Principais da Aplicação */}
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/sessoes" element={<SessoesPage />} />
        <Route path="/sessoes/:data" element={<SessaoDetalhe />} />
        <Route path="/conquistas" element={<ConquistasPage />} />
        <Route path="/perfil" element={<PerfilPage />} />

      </Routes>
    </Router>
  );
}

export default App;