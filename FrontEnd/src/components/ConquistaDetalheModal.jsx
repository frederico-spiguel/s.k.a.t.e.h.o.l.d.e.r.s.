// src/components/ConquistaDetalheModal.jsx

import { useState } from 'react';
import api from '../services/api';
import cadeadoImg from '../assets/cadeado.png';

export default function ConquistaDetalheModal({ conquista, onClose, onConquistaReivindicada }) {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    
    if (!conquista) {
        return null; // Não renderiza nada se nenhuma conquista for passada
    }

    const handleReivindicar = async () => {
        setIsLoading(true);
        setError('');
        try {
            // Chamando nosso endpoint de reivindicação!
            await api.post(`/api/conquistas/${conquista.id}/reivindicar`);
            
            // Avisa o componente pai que a conquista foi um sucesso!
            onConquistaReivindicada(conquista.id);

            alert('Parabéns! Conquista desbloqueada!');
            onClose(); // Fecha o modal
        } catch (err) {
            console.error("Erro ao reivindicar conquista:", err);
            // Pega a mensagem de erro amigável que definimos no backend
            const errorMessage = err.response?.data?.message || "Não foi possível reivindicar a conquista.";
            setError(errorMessage);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        // Fundo escuro (Overlay)
        <div 
            className="fixed inset-0 bg-black bg-opacity-70 flex justify-center items-center z-50"
            onClick={onClose} // Fecha o modal se clicar fora
        >
            {/* Conteúdo do Modal */}
            <div 
                className="bg-gray-800 text-white p-8 rounded-2xl shadow-2xl w-full max-w-2xl relative"
                onClick={e => e.stopPropagation()} // Impede de fechar ao clicar dentro
            >
                <button onClick={onClose} className="absolute top-4 right-4 text-2xl font-bold">&times;</button>
                
                <div className="flex flex-col md:flex-row items-center gap-8">
                    {/* Emblema Grande */}
                    <div className="w-40 h-40 flex-shrink-0 bg-gray-700 rounded-full flex items-center justify-center relative">
                        <img 
                            src={conquista.imagemUrl} 
                            alt={conquista.nome} 
                            className={`w-3/4 object-contain ${!conquista.desbloqueada && 'opacity-20'}`}
                        />
                        {!conquista.desbloqueada && (
                            <img 
                                src={cadeadoImg}
                                alt="Bloqueado"
                                className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-1/2 opacity-80"
                            />
                        )}
                    </div>

                    {/* Detalhes e Ações */}
                    <div className="text-center md:text-left">
                        <h2 className="text-3xl font-bold mb-2">{conquista.nome}</h2>
                        <p className="text-gray-400 mb-4">{conquista.descricao}</p>
                        
                        {!conquista.desbloqueada && (
                            <>
                                <button
                                    onClick={handleReivindicar}
                                    disabled={isLoading}
                                    className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-6 rounded-lg transition disabled:bg-gray-500"
                                >
                                    {isLoading ? 'Verificando...' : 'Reivindicar Conquista'}
                                </button>
                                {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
                            </>
                        )}
                         {conquista.desbloqueada && (
                            <p className="text-green-400 font-semibold">✓ Conquista Desbloqueada</p>
                         )}
                    </div>
                </div>
            </div>
        </div>
    );
}