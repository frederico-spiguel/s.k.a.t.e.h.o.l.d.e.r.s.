// src/App.jsx

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

// Páginas
import Login from "./pages/Login.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Register from "./pages/Register.jsx";
import Triagem from "./pages/Triagem.jsx";
import SessoesPage from "./pages/SessoesPage.jsx";
import SessaoDetalhe from "./pages/SessaoDetalhe.jsx";
import ConquistasPage from "./pages/ConquistasPage.jsx";
import PerfilPage from "./pages/PerfilPage.jsx";

// --- NOVAS PÁGINAS DE GRÁFICOS ---
import GraficoSelecaoPage from "./pages/GraficoSelecaoPage.jsx";
import GraficoVisualizarPage from "./pages/GraficoVisualizarPage.jsx";


function App() {
  return (
    <Router>
      <Routes>
        {/* Rotas Públicas */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/triagem" element={<Triagem />} />

        {/* Rotas da Aplicação */}
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/sessoes" element={<SessoesPage />} />
        <Route path="/sessoes/:data" element={<SessaoDetalhe />} />
        <Route path="/conquistas" element={<ConquistasPage />} />
        <Route path="/perfil" element={<PerfilPage />} />
        
        {/* --- ROTAS FINAIS PARA OS GRÁFICOS --- */}
        <Route path="/graficos/selecao" element={<GraficoSelecaoPage />} />
        <Route path="/graficos/visualizar" element={<GraficoVisualizarPage />} />

      </Routes>
    </Router>
  );
}

export default App;