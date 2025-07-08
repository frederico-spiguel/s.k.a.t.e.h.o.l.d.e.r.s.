// src/pages/SessaoDetalhe.jsx

import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import api from '../services/api';
import RegistrarAtividadeModal from './RegistrarAtividadeModal';

export default function SessaoDetalhe() {
  const [sessao, setSessao] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [atividadeParaEditar, setAtividadeParaEditar] = useState(null);
  const { data } = useParams();

  useEffect(() => {
    api.get(`/seshs/por-data?data=${data}`)
      .then(response => { setSessao(response.data); })
      .catch(error => { console.error(`Erro ao buscar detalhes da sessão`, error); setSessao(null); })
      .finally(() => { setIsLoading(false); });
  }, [data]);

  const handleExcluirAtividade = async (atividadeId) => {
    if (!window.confirm("Tem certeza que deseja excluir esta atividade?")) return;
    try {
      await api.delete(`/atividades/${atividadeId}`);
      setSessao(s => ({...s, atividades: s.atividades.filter(a => a.id !== atividadeId)}));
    } catch (error) {
      console.error("Erro ao excluir atividade:", error);
      alert("Não foi possível excluir a atividade.");
    }
  };

  const handleSuccess = (atividadeAtualizada) => {
    if (atividadeParaEditar) {
      setSessao(s => ({...s, atividades: s.atividades.map(a => a.id === atividadeAtualizada.id ? atividadeAtualizada : a)}));
    } else {
      setSessao(s => ({ ...s, atividades: [...s.atividades, atividadeAtualizada] }));
    }
    setIsCreateModalOpen(false);
    setAtividadeParaEditar(null);
  };
  
  if (isLoading) return <div className="text-center p-10">Carregando...</div>;
  if (!sessao) return <div className="text-center p-10">Nenhuma sessão encontrada.</div>;

  // LINHA DE FORMATAÇÃO DA DATA (CORRIGIDA E VERIFICADA)
  const dataFormatada = format(new Date(sessao.data.replace(/-/g, '/')), "dd 'de' MMMM 'de' yyyy", { locale: ptBR });

  return (
    <>
      <div className="min-h-screen bg-gray-100 p-4 sm:p-8">
        <div className="max-w-4xl mx-auto">
          <Link to="/sessoes" className="text-blue-600 hover:underline mb-4 inline-block">&larr; Voltar</Link>
          <div className="bg-white rounded-lg shadow-lg p-6">
            <div className="flex justify-between items-start flex-wrap gap-4">
              <div>
                <h1 className="text-3xl font-bold text-gray-800">Sessão de {dataFormatada}</h1>
                {sessao.editavel && <span className="mt-2 text-sm bg-green-200 text-green-800 font-semibold px-3 py-1 rounded-full inline-block">Sessão Editável</span>}
              </div>
              {sessao.editavel && (
                <button onClick={() => setIsCreateModalOpen(true)} className="bg-blue-600 text-white font-bold py-2 px-4 rounded-lg hover:bg-blue-700 transition-colors shadow">
                  Registrar Atividade
                </button>
              )}
            </div>
            
            <h2 className="text-2xl font-semibold text-gray-700 mt-8 mb-4">Atividades Registradas</h2>
            <div className="space-y-4">
              {sessao.atividades && sessao.atividades.length > 0 ? (
                [...sessao.atividades].sort((a, b) => new Date(a.horario) - new Date(b.horario)).map(atividade => (
                  <div key={atividade.id} className="bg-gray-50 p-4 rounded-md border flex justify-between items-center flex-wrap gap-2">
                    <div className="flex-grow">
                      <p className="font-bold text-lg text-gray-800">{atividade.trickNome}</p>
                      <p className="text-sm text-gray-600">Obstáculo: {atividade.obstaculo}</p>
                    </div>
                    <div className="flex items-center gap-4 flex-shrink-0">
                      {/* LINHA DE FORMATAÇÃO DO HORÁRIO (CORRIGIDA E VERIFICADA) */}
                      <span className="text-sm text-gray-500 font-mono">{format(new Date(atividade.horario), 'HH:mm')}</span>
                      {sessao.editavel && (
                        <div className="flex gap-2 border-l pl-4">
                          <button onClick={() => setAtividadeParaEditar(atividade)} className="text-sm font-medium text-blue-600 hover:text-blue-800 transition-colors">Editar</button>
                          <button onClick={() => handleExcluirAtividade(atividade.id)} className="text-sm font-medium text-red-600 hover:text-red-800 transition-colors">Excluir</button>
                        </div>
                      )}
                    </div>
                  </div>
                ))
              ) : <p className="text-gray-500">Nenhuma atividade registrada.</p>}
            </div>
          </div>
        </div>
      </div>

      <RegistrarAtividadeModal 
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        sessaoId={sessao.id} 
        onSuccess={handleSuccess}
      />
      
      <RegistrarAtividadeModal 
        isOpen={!!atividadeParaEditar}
        onClose={() => setAtividadeParaEditar(null)}
        onSuccess={handleSuccess}
        atividadeParaEditar={atividadeParaEditar}
      />
    </>
  );
}