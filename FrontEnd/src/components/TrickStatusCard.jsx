// src/components/TrickStatusCard.jsx

// Adicionamos a nova propriedade 'onPedirProficiencia'
export default function TrickStatusCard({ trickStatus, onPedirProficiencia }) {
    
    // --- CORREÇÃO DEFINITIVA DO ERRO DE DIGITAÇÃO ---
    // Usando a propriedade correta 'isAceso' que vem do backend.
    const cardStyle = trickStatus.isAceso
        ? "bg-white shadow" // Estilo "Aceso"
        : "bg-gray-200 opacity-60"; // Estilo "Apagado"

    const nivelStyleMap = {
        "Iniciante": "text-gray-500",
        "Intermediário": "text-blue-600 font-semibold",
        "Avançado": "text-purple-700 font-bold",
    };

    const podePedirProficiencia = trickStatus.nivel === 'Intermediário' || trickStatus.nivel === 'Avançado';

    return (
        <div className={`p-4 rounded-lg flex items-center justify-between gap-4 transition-all ${cardStyle}`}>
            {/* Parte Esquerda */}
            <div className="flex-grow">
                <h3 className="text-lg font-bold text-gray-900">{trickStatus.nomeTrick}</h3>
                <div className="flex items-center gap-4 text-sm mt-1">
                    <p className="text-gray-700">Acertos: <span className="font-semibold">{trickStatus.acertos}</span></p>
                    <p>Nível: <span className={nivelStyleMap[trickStatus.nivel] || 'text-gray-500'}>{trickStatus.nivel}</span></p>
                </div>
            </div>

            {/* Parte Direita */}
            <div className="flex-shrink-0 w-40 text-center">
                {trickStatus.proficiencia ? (
                    <span className="text-green-600 font-bold text-lg border-2 border-green-500 rounded-md px-4 py-1">
                        NA BASE!
                    </span>
                ) : (
                    <button
                        // O onClick agora chama a função que recebemos via props
                        onClick={() => onPedirProficiencia(trickStatus)}
                        disabled={!podePedirProficiencia}
                        className="bg-gray-800 text-white font-semibold py-2 px-4 rounded-md text-sm transition enabled:hover:bg-gray-900 disabled:bg-gray-400 disabled:cursor-not-allowed"
                        title={!podePedirProficiencia ? "É necessário ser nível Intermediário para pedir proficiência" : "Pedir proficiência"}
                    >
                        Pedir Proficiência
                    </button>
                )}
            </div>
        </div>
    );
}