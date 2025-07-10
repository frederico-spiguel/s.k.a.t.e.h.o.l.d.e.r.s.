// src/pages/ConquistasPage.jsx (VERSÃO ATUALIZADA)

import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import ConquistaCard from '../components/ConquistaCard';
import ConquistaDetalheModal from '../components/ConquistaDetalheModal'; // <-- Importamos o novo modal

export default function ConquistasPage() {
    const [conquistas, setConquistas] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    
    // --- NOVOS ESTADOS PARA CONTROLAR O MODAL ---
    const [conquistaSelecionada, setConquistaSelecionada] = useState(null);

    useEffect(() => {
        // ... (código de busca de dados continua o mesmo)
        api.get('/api/conquistas')
            .then(response => { setConquistas(response.data); })
            .catch(err => { 
                console.error("Erro ao buscar conquistas:", err);
                setError("Não foi possível carregar as conquistas.");
            })
            .finally(() => { setIsLoading(false); });
    }, []);

    // Função para abrir o modal com a conquista clicada
    const handleCardClick = (conquista) => {
        setConquistaSelecionada(conquista);
    };

    // Função para fechar o modal
    const handleCloseModal = () => {
        setConquistaSelecionada(null);
    };
    
    // Função que será chamada pelo modal após uma reivindicação bem-sucedida
    const handleConquistaReivindicada = (conquistaId) => {
        // Atualiza a lista de conquistas para refletir o novo status "desbloqueada"
        setConquistas(prevConquistas =>
            prevConquistas.map(c => 
                c.id === conquistaId ? { ...c, desbloqueada: true } : c
            )
        );
    };

    // ... (código de renderização de loading e erro continua o mesmo)

    return (
        <div className="min-h-screen bg-gray-900 text-white p-4 sm:p-8">
            <div className="max-w-6xl mx-auto">
                <h1 className="text-4xl font-bold text-center mb-10 tracking-wider">
                    Galeria de Conquistas
                </h1>

                <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
                    {conquistas.map(conquista => (
                        // Adicionamos o onClick a cada card
                        <div key={conquista.id} onClick={() => handleCardClick(conquista)}>
                            <ConquistaCard conquista={conquista} />
                        </div>
                    ))}
                </div>

                 <div className="text-center mt-12">
                    <Link to="/dashboard" className="text-blue-400 hover:text-blue-300 transition-colors">
                        &larr; Voltar para o Dashboard
                    </Link>
                </div>
            </div>

            {/* RENDERIZAÇÃO CONDICIONAL DO MODAL */}
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