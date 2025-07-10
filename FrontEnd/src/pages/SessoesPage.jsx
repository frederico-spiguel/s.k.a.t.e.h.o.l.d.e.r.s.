// src/pages/SessoesPage.jsx

import { useState, useEffect } from 'react';
// --- PASSO 1: Adicione 'Link' ao import do react-router-dom ---
import { useNavigate, Link } from 'react-router-dom';
import { DayPicker } from 'react-day-picker';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import 'react-day-picker/dist/style.css';
import api from '../services/api';

export default function SessoesPage() {
  const [diasComSessao, setDiasComSessao] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  // Busca as datas com sessões no backend quando a página carrega
  useEffect(() => {
    api.get('/seshs/datas')
      .then(response => {
        const datas = response.data.map(dataStr => new Date(dataStr.replace(/-/g, '/')));
        setDiasComSessao(datas);
      })
      .catch(error => {
        console.error("Erro ao buscar datas das sessões:", error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  const handleDayClick = (data) => {
    const isSeshDay = diasComSessao.some(seshDay => 
      format(seshDay, 'yyyy-MM-dd') === format(data, 'yyyy-MM-dd')
    );

    if (isSeshDay) {
      const dataFormatada = format(data, 'yyyy-MM-dd');
      navigate(`/sessoes/${dataFormatada}`);
    }
  };

  if (isLoading) {
    return <div className="text-center p-10">Carregando calendário...</div>;
  }

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center pt-10 px-4">
      
      {/* --- PASSO 2: Adicione o Link de Voltar aqui no topo --- */}
      <div className="w-full max-w-md mb-4 text-left">
        <Link to="/dashboard" className="text-blue-600 hover:text-blue-800 font-semibold transition-colors">
          &larr; Voltar para o Dashboard
        </Link>
      </div>

      <h1 className="text-4xl font-bold text-gray-800 mb-8">Suas Sessões</h1>
      <div className="bg-white p-4 rounded-lg shadow-lg">
        <DayPicker
          mode="single"
          onSelect={handleDayClick}
          locale={ptBR}
          modifiers={{ highlighted: diasComSessao }}
          modifiersClassNames={{
            highlighted: 'rdp-day_highlighted',
          }}
        />
      </div>
      <p className="mt-4 text-gray-600">Clique em um dia destacado para ver os detalhes da sessão.</p>

      {/* Estilos para o destaque do calendário */}
      <style>{`
        .rdp-day_highlighted { 
          font-weight: bold;
          background-color: #3b82f6;
          color: white;
          border-radius: 50%;
        }
        .rdp-day_highlighted:hover {
            background-color: #2563eb !important;
        }
      `}</style>
    </div>
  );
}