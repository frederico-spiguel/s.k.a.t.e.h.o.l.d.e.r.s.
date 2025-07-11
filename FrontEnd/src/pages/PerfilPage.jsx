// src/pages/PerfilPage.jsx

import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import TrickStatusCard from '../components/TrickStatusCard'; // Vamos criar este componente a seguir

export default function PerfilPage() {
    const [perfil, setPerfil] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        setIsLoading(true);
        // Fazendo a chamada para o nosso novo e poderoso endpoint de perfil
        api.get('/api/perfil')
            .then(response => {
                setPerfil(response.data);
            })
            .catch(err => {
                console.error("Erro ao buscar dados do perfil:", err);
                setError("Não foi possível carregar os dados do perfil.");
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <div className="text-center p-10 bg-gray-100 min-h-screen">Carregando perfil...</div>;
    }

    if (error) {
        return <div className="text-center p-10 bg-gray-100 min-h-screen text-red-500">{error}</div>;
    }

    if (!perfil) {
        return <div className="text-center p-10 bg-gray-100 min-h-screen">Nenhum dado de perfil encontrado.</div>;
    }
    
    // Lógica para ordenar as manobras: "acesas" primeiro
    const manobrasOrdenadas = [...perfil.manobrasStatus].sort((a, b) => b.acceso - a.acceso);

    return (
        <div className="min-h-screen bg-gray-100 p-4 sm:p-8">
            <div className="max-w-4xl mx-auto">
                <div className="mb-8">
                    <Link to="/dashboard" className="text-blue-600 hover:underline font-semibold">&larr; Voltar para o Dashboard</Link>
                </div>

                {/* Seção do Cabeçalho do Perfil */}
                <div className="bg-white p-6 rounded-xl shadow-lg mb-8 text-center">
                    <h1 className="text-4xl font-bold text-gray-800">{perfil.nomeUsuario}</h1>
                    <div className="flex justify-center items-center gap-6 mt-4 text-gray-600">
                        <div className="text-center">
                            <p className="text-2xl font-bold text-yellow-500">{perfil.pontosConquistas}</p>
                            <p className="text-sm">Pontos de Conquista</p>
                        </div>
                        <div className="text-center">
                            <p className="text-2xl font-bold text-blue-500">{perfil.totalSessoes}</p>
                            <p className="text-sm">Sessões</p>
                        </div>
                        <div className="text-center">
                            <p className="text-2xl font-bold text-green-500">{perfil.totalAtividades}</p>
                            <p className="text-sm">Atividades</p>
                        </div>
                    </div>
                     <Link to="/conquistas" className="text-blue-600 hover:underline mt-4 inline-block text-sm">Ver galeria de conquistas</Link>
                </div>

                {/* Seção da Lista de Manobras */}
                <div>
                    <h2 className="text-2xl font-bold text-gray-800 mb-4">Progresso nas Manobras</h2>
                    <div className="space-y-3">
                        {manobrasOrdenadas.map(trickStatus => (
                            <TrickStatusCard key={trickStatus.nomeTrick} trickStatus={trickStatus} />
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}