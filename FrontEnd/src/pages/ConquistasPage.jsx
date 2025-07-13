// src/pages/ConquistasPage.jsx

import { useState, useEffect } from 'react';
// --- PASSO 1: Importe 'useNavigate' em vez de 'Link' se não for mais usar ---
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import ConquistaCard from '../components/ConquistaCard';
import ConquistaDetalheModal from '../components/ConquistaDetalheModal';

export default function ConquistasPage() {
    const [conquistas, setConquistas] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [conquistaSelecionada, setConquistaSelecionada] = useState(null);
    
    // --- PASSO 2: Pegue a função 'navigate' ---
    const navigate = useNavigate();

    const fetchConquistas = () => {
        setIsLoading(true);
        api.get('/api/conquistas')
            .then(response => {
                setConquistas(response.data);
            })
            .catch(err => {
                console.error("Erro ao buscar conquistas:", err);
                setError("Não foi possível carregar as conquistas.");
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    useEffect(() => {
        fetchConquistas();
    }, []);

    const handleCardClick = (conquista) => {
        setConquistaSelecionada(conquista);
    };

    const handleCloseModal = () => {
        setConquistaSelecionada(null);
    };
    
    const handleConquistaReivindicada = (conquistaId) => {
        setConquistas(prevConquistas =>
            prevConquistas.map(c => 
                c.id === conquistaId ? { ...c, desbloqueada: true } : c
            )
        );
    };

    if (isLoading) {
        return <div className="text-center p-10">Carregando conquistas...</div>;
    }

    if (error) {
        return <div className="text-center p-10 text-red-500">{error}</div>;
    }

    return (
        <div className="min-h-screen bg-gray-900 text-white p-4 sm:p-8">
            <div className="max-w-6xl mx-auto">

                {/* --- PASSO 3: ADICIONE O BOTÃO INTELIGENTE AQUI NO TOPO --- */}
                <div className="w-full mb-8">
                    <button
                        onClick={() => navigate(-1)} // A mágica acontece aqui!
                        className="text-blue-400 hover:text-blue-300 font-semibold transition-colors"
                    >
                        &larr; Voltar
                    </button>
                </div>
                
                <h1 className="text-4xl font-bold text-center mb-10 tracking-wider">
                    Galeria de Conquistas
                </h1>

                {/* Grid para as cartas de conquista */}
                <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
                    {conquistas.map(conquista => (
                        <div key={conquista.id} onClick={() => handleCardClick(conquista)}>
                            <ConquistaCard conquista={conquista} />
                        </div>
                    ))}
                </div>

                {/* O link antigo no final da página foi removido */}
            </div>

            {/* Renderização Condicional do Modal */}
            {conquistaSelecionada && (
                <ConquistaDetalheModal 
                    conquista={conquistaSelecionada}
                    onClose={handleCloseModal}
                    onConquistaReivindicada={handleConquistaReivindicada}
                />
            )}
        </div>
    );
}