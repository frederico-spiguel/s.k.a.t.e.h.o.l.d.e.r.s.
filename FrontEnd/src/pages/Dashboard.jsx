// src/pages/Dashboard.jsx
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import RegistrarAtividadeModal from "./RegistrarAtividadeModal.jsx"; // <-- IMPORTA O NOVO MODAL

export default function Dashboard() {
  const [usuario, setUsuario] = useState("Usuário");
  const [pontos, setPontos] = useState(2500);
  const navigate = useNavigate();

  // --- LÓGICA DO MODAL ---
  const [isModalOpen, setIsModalOpen] = useState(false);
  // -----------------------

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) navigate("/login");
    // Futuramente, você pode fazer uma chamada à API aqui para buscar os dados reais do usuário
  }, []);

  return (
    <> {/* Usamos um Fragment <> para poder renderizar o modal fora do div principal */}
      <div className="min-h-screen bg-gradient-to-br from-blue-600 to-blue-400 text-white flex flex-col items-center py-10 px-6 font-sans">
        {/* Logo */}
        <div className="text-center mb-12">
          <div className="text-7xl font-extrabold drop-shadow-md">S</div>
          <h1 className="text-2xl font-semibold tracking-widest">
            S.K.A.T.E.H.O.L.D.E.R.S.
          </h1>
        </div>

        {/* Ícones do menu */}
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-6 mb-16 text-center text-base font-medium">
          {/* Seus ícones aqui... */}
          <div className="flex flex-col items-center hover:scale-105 transition-transform cursor-pointer">
            <div className="text-4xl mb-1">👤</div>
            <span>Meu perfil</span>
          </div>
          <div className="flex flex-col items-center hover:scale-105 transition-transform cursor-pointer">
            <div className="text-4xl mb-1">📈</div>
            <span>Evolução</span>
          </div>
          <div className="flex flex-col items-center hover:scale-105 transition-transform cursor-pointer">
            <div className="text-4xl mb-1">🗓️</div>
            <span>Sessões</span>
          </div>
          <div className="flex flex-col items-center hover:scale-105 transition-transform cursor-pointer">
            <div className="text-4xl mb-1">🏆</div>
            <span>Conquistas</span>
          </div>
        </div>

        {/* Botão principal agora abre o modal */}
        <button
          onClick={() => setIsModalOpen(true)} // <-- A MÁGICA ACONTECE AQUI
          className="bg-white text-blue-600 font-bold text-lg px-12 py-4 rounded-full shadow-lg hover:bg-blue-100 transition-transform hover:scale-105"
        >
          Registrar atividade
        </button>

        {/* Rodapé */}
        <div className="mt-auto w-full flex justify-between items-center text-sm font-medium px-6 pt-16">
          <span className="opacity-80">{usuario}</span>
          <span className="opacity-80">{pontos} pts</span>
        </div>
      </div>

      {/* RENDERIZAÇÃO CONDICIONAL DO MODAL */}
      {isModalOpen && <RegistrarAtividadeModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
}