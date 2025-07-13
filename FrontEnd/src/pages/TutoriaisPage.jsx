// src/pages/TutoriaisPage.jsx

import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import TutorialCard from '../components/TutorialCard'; // Vamos criar este componente a seguir

export default function TutoriaisPage() {
    const [tutoriais, setTutoriais] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        setIsLoading(true);
        // Chamando o endpoint que acabamos de testar
        api.get('/api/tutoriais')
            .then(response => {
                setTutoriais(response.data);
            })
            .catch(err => {
                console.error("Erro ao buscar tutoriais:", err);
                setError("Não foi possível carregar os tutoriais.");
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []); // O array vazio [] garante que rode apenas uma vez

    if (isLoading) {
        return <div className="text-center p-10 bg-gray-100 min-h-screen">Carregando tutoriais...</div>;
    }

    if (error) {
        return <div className="text-center p-10 bg-gray-100 min-h-screen text-red-500">{error}</div>;
    }

    return (
        <div className="min-h-screen bg-gray-100 p-4 sm:p-8">
            <div className="max-w-3xl mx-auto">
                <div className="mb-8">
                    <Link to="/dashboard" className="text-blue-600 hover:underline font-semibold">&larr; Voltar para o Dashboard</Link>
                </div>

                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center">Enciclopédia de Manobras</h1>
                
                <div className="space-y-4">
                    {tutoriais.map(tutorial => (
                        <TutorialCard key={tutorial.id} tutorial={tutorial} />
                    ))}
                </div>
            </div>
        </div>
    );
}