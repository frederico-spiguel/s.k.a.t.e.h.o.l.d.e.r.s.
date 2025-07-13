import { useState } from 'react';
import api from '../services/api';

export default function ProficienciaModal({ trick, onClose, onSuccess }) {
    const [acertos, setAcertos] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const numAcertos = parseInt(acertos, 10);

        if (isNaN(numAcertos) || numAcertos < 0 || numAcertos > 10) {
            setError("Por favor, insira um número de 0 a 10.");
            return;
        }

        setIsLoading(true);
        setError('');

        const payload = {
            trickId: trick.trickId, // <-- CORREÇÃO APLICADA AQUI
            acertosReportados: numAcertos
        };

        try {
            await api.post('/api/perfil/proficiencia', payload);
            alert(`Parabéns! O seu pedido de proficiência em ${trick.nomeTrick} foi registrado!`);
            onSuccess();
            onClose();
        } catch (err) {
            console.error("Erro ao solicitar proficiência:", err);
            const errorMessage = err.response?.data?.message || "Ocorreu um erro no seu pedido.";
            setError(errorMessage);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex justify-center items-center z-50" onClick={onClose}>
            <div className="bg-white p-8 rounded-2xl shadow-lg w-full max-w-md text-center text-gray-800" onClick={e => e.stopPropagation()}>
                <h2 className="text-2xl font-bold text-blue-600 mb-2">MÃO NA MASSA!</h2>
                <h3 className="text-xl font-semibold mb-4">Teste de Proficiência: {trick.nomeTrick}</h3>
                <p className="text-gray-600 mb-6">Tente realizar a manobra 10 vezes e insira abaixo quantas vezes você a realizou corretamente.</p>
                
                <form onSubmit={handleSubmit}>
                    <input
                        type="number"
                        min="0"
                        max="10"
                        value={acertos}
                        onChange={(e) => setAcertos(e.target.value)}
                        placeholder="Nº de acertos (0-10)"
                        className="w-full p-3 text-center text-lg border-2 border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4"
                        required
                    />
                    {error && <p className="text-red-500 text-sm mb-4">{error}</p>}
                    <button
                        type="submit"
                        disabled={isLoading}
                        className="w-full bg-green-500 text-white font-bold py-3 rounded-lg text-lg hover:bg-green-600 transition disabled:bg-gray-400"
                    >
                        {isLoading ? 'Confirmando...' : 'Confirmar Acertos'}
                    </button>
                </form>
            </div>
        </div>
    );
}