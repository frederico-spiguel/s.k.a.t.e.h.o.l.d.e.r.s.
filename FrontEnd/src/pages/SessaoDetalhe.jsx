// src/pages/SessaoDetalhe.jsx

import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import api from '../services/api';

export default function SessaoDetalhe() {
  const [sessao, setSessao] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const { data } = useParams(); // Pega o parâmetro :data da URL

  useEffect(() => {
    // Busca os detalhes da sessão usando a data da URL
    api.get(`/seshs/por-data?data=${data}`)
      .then(response => {
        setSessao(response.data);
      })
      .catch(error => {
        console.error(`Erro ao buscar detalhes da sessão para a data ${data}:`, error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [data]); // Roda este efeito sempre que a data na URL mudar

  if (isLoading) {
    return <div className="text-center p-10">Carregando detalhes da sessão...</div>;
  }

  if (!sessao) {
    return <div className="text-center p-10">Nenhuma sessão encontrada para esta data.</div>;
  }

  // Formata a data para exibição (ex: 03 de julho de 2025)
  const dataFormatada = format(new Date(sessao.data.replace(/-/g, '/')), "dd 'de' MMMM 'de' yyyy", { locale: ptBR });

  return (
    <div className="min-h-screen bg-gray-100 p-4 sm:p-8">
      <div className="max-w-4xl mx-auto">
        <Link to="/sessoes" className="text-blue-600 hover:underline mb-4 inline-block">&larr; Voltar para o Calendário</Link>
        <div className="bg-white rounded-lg shadow-lg p-6">
          <h1 className="text-3xl font-bold text-gray-800">Sessão de {dataFormatada}</h1>
          {sessao.editavel && (
            <span className="mt-1 text-sm bg-green-200 text-green-800 font-semibold px-3 py-1 rounded-full">Sessão Editável</span>
          )}

          <h2 className="text-2xl font-semibold text-gray-700 mt-8 mb-4">Atividades Registradas</h2>
          <div className="space-y-4">
            {sessao.atividades.length > 0 ? (
              sessao.atividades.map(atividade => (
                <div key={atividade.id} className="bg-gray-50 p-4 rounded-md border flex justify-between items-center">
                  <div>
                    <p className="font-bold text-lg text-gray-800">{atividade.trickNome}</p>
                    <p className="text-sm text-gray-600">Obstáculo: {atividade.obstaculo}</p>
                  </div>
                  <span className="text-sm text-gray-500">{format(new Date(atividade.horario), 'HH:mm')}</span>
                </div>
              ))
            ) : (
              <p>Nenhuma atividade registrada para esta sessão.</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}