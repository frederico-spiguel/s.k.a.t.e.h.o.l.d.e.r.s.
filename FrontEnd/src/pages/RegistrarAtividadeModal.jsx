// src/pages/RegistrarAtividadeModal.jsx (VERSÃO FINAL COM MODO EDIÇÃO)

import { useState, useEffect } from "react";
import api from '../services/api';

/**
 * Modal INTELIGENTE para registrar ou EDITAR uma atividade.
 * @param {boolean} props.isOpen - Controla se o modal está visível.
 * @param {function} props.onClose - Função para fechar o modal.
 * @param {number} [props.sessaoId] - O ID da sessão para o modo "criação".
 * @param {function} props.onSuccess - Callback executado após o sucesso.
 * @param {object} [props.atividadeParaEditar] - OPCIONAL. O objeto da atividade para o modo "edição".
 */
export default function RegistrarAtividadeModal({ isOpen, onClose, sessaoId, onSuccess, atividadeParaEditar }) {
  const [tricks, setTricks] = useState([]);
  const [selectedTrickId, setSelectedTrickId] = useState("");
  const [obstaculo, setObstaculo] = useState("flatground");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Efeito para buscar a lista de tricks (sem alterações)
  useEffect(() => {
    if (isOpen && tricks.length === 0) {
      api.get('/tricks')
        .then(response => setTricks(response.data))
        .catch(err => {
          console.error("Erro ao buscar tricks:", err);
          setError("Não foi possível carregar as manobras.");
        });
    }
  }, [isOpen, tricks.length]);

  // NOVO EFEITO: Preenche o formulário se estivermos em modo de edição
  useEffect(() => {
    if (atividadeParaEditar) {
      // Se uma atividade foi passada via props, estamos editando.
      // Pré-populamos o formulário com os dados dela.
      setSelectedTrickId(atividadeParaEditar.trickId);
      setObstaculo(atividadeParaEditar.obstaculo);
    } else {
      // Caso contrário, garantimos que o formulário esteja limpo para criação.
      setSelectedTrickId("");
      setObstaculo("flatground");
    }
  }, [atividadeParaEditar, isOpen]); // Roda sempre que a atividade ou o estado de abertura mudar

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedTrickId) {
      setError("Por favor, selecione uma manobra.");
      return;
    }
    setIsSubmitting(true);
    setError("");

    const isEditMode = !!atividadeParaEditar; // Flag para saber se estamos editando
    
    let url = '';
    let method = 'post'; // Método HTTP padrão é POST (criação)
    const payload = {
      trickId: parseInt(selectedTrickId),
      obstaculo: obstaculo || "flatground"
    };

    if (isEditMode) {
      // MODO EDIÇÃO: Ajusta a URL e o método para PUT
      url = `/atividades/${atividadeParaEditar.id}`;
      method = 'put';
    } else {
      // MODO CRIAÇÃO: Lógica que já tínhamos
      url = sessaoId ? `/seshs/${sessaoId}/atividades` : '/atividades';
    }

    try {
      // api[method] é uma forma inteligente de chamar api.post() ou api.put() dinamicamente
      const response = await api[method](url, payload); 
      
      // A função onSuccess agora é chamada tanto na criação quanto na edição
      if (onSuccess) {
        onSuccess(response.data);
      }
      onClose(); // Fecha o modal em caso de sucesso

    } catch (err) {
      console.error(`Erro ao ${isEditMode ? 'editar' : 'registrar'} atividade:`, err);
      setError("Ocorreu um erro. Tente novamente.");
    } finally {
      setIsSubmitting(false);
    }
  };

  if (!isOpen) { return null; }

  const isEditMode = !!atividadeParaEditar;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50" onClick={onClose}>
      <div className="bg-white text-gray-800 p-8 rounded-xl shadow-2xl w-full max-w-lg relative" onClick={e => e.stopPropagation()}>
        <button onClick={onClose} className="absolute top-4 right-4 text-2xl font-bold text-gray-500 hover:text-gray-800" aria-label="Fechar modal">&times;</button>
        
        <h2 className="text-2xl font-bold text-center mb-6">{isEditMode ? 'Editar Atividade' : 'Registrar Nova Atividade'}</h2>
        
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="trick-select" className="block text-sm font-medium text-gray-700 mb-1">Manobra</label>
            <select id="trick-select" value={selectedTrickId} onChange={(e) => setSelectedTrickId(e.target.value)} className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" required>
              {/* No modo de criação, mostramos a opção default */}
              {!isEditMode && <option value="">-- Selecione uma manobra --</option>}
              {tricks.map(trick => (<option key={trick.id} value={trick.id}>{trick.nome}</option>))}
            </select>
          </div>
          <div className="mb-6">
            <label htmlFor="obstaculo-input" className="block text-sm font-medium text-gray-700 mb-1">Obstáculo</label>
            <input id="obstaculo-input" type="text" value={obstaculo} onChange={(e) => setObstaculo(e.target.value)} className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          {error && <p className="text-sm text-center mb-4 text-red-600">{error}</p>}
          <button type="submit" disabled={isSubmitting} className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition-colors disabled:bg-blue-300 disabled:cursor-not-allowed">
            {isSubmitting ? (isEditMode ? 'Salvando...' : 'Registrando...') : (isEditMode ? 'Salvar Alterações' : 'Registrar')}
          </button>
        </form>
      </div>
    </div>
  );
}