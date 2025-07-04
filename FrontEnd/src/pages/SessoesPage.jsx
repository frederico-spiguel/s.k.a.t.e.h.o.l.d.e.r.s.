// src/pages/SessoesPage.jsx

import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { DayPicker } from 'react-day-picker';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import 'react-day-picker/dist/style.css'; // Estilos padrão do calendário
import api from '../services/api';

export default function SessoesPage() {
  const [diasComSessao, setDiasComSessao] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  // Busca as datas com sessões no backend quando a página carrega
  useEffect(() => {
    api.get('/seshs/datas')
      .then(response => {
        // O backend retorna strings de data, precisamos convertê-las para objetos Date
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
    // Verifica se o dia clicado está na lista de dias com sessão
    const isSeshDay = diasComSessao.some(seshDay => 
      format(seshDay, 'yyyy-MM-dd') === format(data, 'yyyy-MM-dd')
    );

    if (isSeshDay) {
      // Formata a data para YYYY-MM-DD e navega para a página de detalhes
      const dataFormatada = format(data, 'yyyy-MM-dd');
      navigate(`/sessoes/${dataFormatada}`);
    }
  };

  if (isLoading) {
    return <div className="text-center p-10">Carregando calendário...</div>;
  }

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center pt-10">
      <h1 className="text-4xl font-bold text-gray-800 mb-8">Suas Sessões</h1>
      <div className="bg-white p-4 rounded-lg shadow-lg">
        <DayPicker
          mode="single"
          onSelect={handleDayClick}
          locale={ptBR} // Calendário em português
          modifiers={{ highlighted: diasComSessao }} // Diz quais dias devem ser destacados
          modifiersClassNames={{
            highlighted: 'rdp-day_highlighted', // Classe CSS para o destaque
          }}
        />
      </div>
      <p className="mt-4 text-gray-600">Clique em um dia destacado para ver os detalhes da sessão.</p>

      {/* Precisamos adicionar este estilo ao nosso CSS principal (ex: index.css) */}
      <style>{`
        .rdp-day_highlighted { 
          font-weight: bold;
          background-color: #3b82f6; /* Azul do seu tema */
          color: white;
        }
      `}</style>
    </div>
  );
}