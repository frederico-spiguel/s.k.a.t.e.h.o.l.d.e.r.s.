// src/pages/RegistrarAtividadeModal.jsx
import { useState, useEffect } from "react";
import api from '../services/api'; // Nosso cérebro de API

// O componente recebe uma propriedade 'onClose' para que ele possa se fechar
export default function RegistrarAtividadeModal({ onClose }) {
  const [tricks, setTricks] = useState([]);
  const [selectedTrickId, setSelectedTrickId] = useState("");
  const [obstaculo, setObstaculo] = useState("flatground"); // Valor padrão
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Busca a lista de tricks do backend assim que o modal é aberto
  useEffect(() => {
    api.get('/tricks')
      .then(response => {
        setTricks(response.data);
      })
      .catch(err => {
        console.error("Erro ao buscar tricks:", err);
        setError("Não foi possível carregar as manobras.");
      });
  }, []); // O array vazio [] garante que isso só rode uma vez

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!selectedTrickId) {
      setError("Por favor, selecione uma manobra.");
      return;
    }

    setIsSubmitting(true);
    const payload = {
      trickId: parseInt(selectedTrickId),
      obstaculo: obstaculo
    };

    try {
      // Usando o endpoint que definimos no backend
      await api.post('/atividades', payload);
      alert("Atividade registrada com sucesso!"); // Feedback para o usuário
      onClose(); // Fecha o modal com sucesso
    } catch (err) {
      console.error("Erro ao registrar atividade:", err);
      setError("Ocorreu um erro ao registrar. Tente novamente.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    // O Fundo Escuro (Overlay)
    <div 
      className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
      onClick={onClose} // Fecha o modal se clicar fora
    >
      {/* O Conteúdo do Modal */}
      <div 
        className="bg-white text-gray-800 p-8 rounded-xl shadow-2xl w-full max-w-lg relative"
        onClick={e => e.stopPropagation()} // Impede de fechar ao clicar dentro
      >
        <button 
          onClick={onClose} 
          className="absolute top-4 right-4 text-2xl font-bold text-gray-500 hover:text-gray-800"
        >
          &times;
        </button>

        <h2 className="text-2xl font-bold text-center mb-6">Registrar Nova Atividade</h2>
        
        <form onSubmit={handleSubmit}>
          {/* Campo de Seleção de Manobra */}
          <div className="mb-4">
            <label htmlFor="trick-select" className="block text-sm font-medium text-gray-700 mb-1">Manobra</label>
            <select
              id="trick-select"
              value={selectedTrickId}
              onChange={(e) => setSelectedTrickId(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">-- Selecione uma manobra --</option>
              {tricks.map(trick => (
                <option key={trick.id} value={trick.id}>
                  {trick.nome}
                </option>
              ))}
            </select>
          </div>

          {/* Campo de Obstáculo */}
          <div className="mb-6">
            <label htmlFor="obstaculo-input" className="block text-sm font-medium text-gray-700 mb-1">Obstáculo</label>
            <input
              id="obstaculo-input"
              type="text"
              value={obstaculo}
              onChange={(e) => setObstaculo(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {error && <p className="text-sm text-center mb-4 text-red-600">{error}</p>}

          <button
            type="submit"
            disabled={isSubmitting}
            className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition disabled:bg-blue-300"
          >
            {isSubmitting ? "Registrando..." : "Registrar"}
          </button>
        </form>
      </div>
    </div>
  );
}