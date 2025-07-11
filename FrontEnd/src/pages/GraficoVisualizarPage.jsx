// src/pages/GraficoVisualizarPage.jsx

import { useState, useEffect } from 'react';
import { useSearchParams, Link } from 'react-router-dom';
import { Line } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import api from '../services/api';

// Registra os componentes necessários do Chart.js
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export default function GraficoVisualizarPage() {
    const [searchParams] = useSearchParams();
    const [chartData, setChartData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    // Pega os parâmetros da URL
    const tipo = searchParams.get('tipo');
    const valor = searchParams.get('valor');

    useEffect(() => {
        if (!tipo) {
            setError("Tipo de gráfico não especificado.");
            setIsLoading(false);
            return;
        }

        setIsLoading(true);
        // Chama nosso "Super Endpoint" com os parâmetros da URL
        api.get(`/api/graficos/evolucao?tipo=${tipo}&valor=${valor}`)
            .then(response => {
                // Prepara os dados para o formato que a biblioteca de gráfico espera
                const data = {
                    labels: response.data.map(ponto => `Sessão ${ponto.sessaoIndex}`), // Eixo X: "Sessão 1", "Sessão 2"...
                    datasets: [
                        {
                            label: 'Acertos Acumulados',
                            data: response.data.map(ponto => ponto.valor), // Eixo Y: O valor acumulado
                            fill: false,
                            borderColor: '#3b82f6', // Azul do tema
                            backgroundColor: '#3b82f6',
                            tension: 0.1,
                            pointRadius: 5,
                            pointHoverRadius: 8,
                        },
                    ],
                };
                setChartData(data);
            })
            .catch(err => {
                console.error("Erro ao buscar dados do gráfico:", err);
                setError("Não foi possível carregar os dados para este gráfico.");
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, [tipo, valor]);
    
    // Opções de customização do nosso gráfico
    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: `Evolução de Acertos - ${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`,
                font: { size: 20 },
            },
        },
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    stepSize: 1, // Garante que o eixo Y conte de 1 em 1 se os valores forem pequenos
                }
            }
        }
    };

    if (isLoading) {
        return <div className="text-center p-10">Gerando gráfico...</div>;
    }

    if (error) {
        return <div className="text-center p-10 text-red-500">{error}</div>;
    }

    return (
        <div className="min-h-screen bg-gray-100 p-4 sm:p-8">
            <div className="max-w-4xl mx-auto">
                <div className="mb-8">
                     <Link to="/graficos/selecao" className="text-blue-600 hover:underline font-semibold">&larr; Voltar para a seleção</Link>
                </div>

                {chartData && chartData.labels.length > 0 ? (
                    <div className="bg-white p-6 rounded-xl shadow-lg">
                        <Line options={options} data={chartData} />
                    </div>
                ) : (
                    <div className="text-center p-10 bg-white rounded-xl shadow-lg">
                        <p className="text-gray-600">Não há dados suficientes para gerar este gráfico.</p>
                        <p className="text-sm text-gray-500 mt-2">Tente registrar mais atividades para esta seleção.</p>
                    </div>
                )}
            </div>
        </div>
    );
}