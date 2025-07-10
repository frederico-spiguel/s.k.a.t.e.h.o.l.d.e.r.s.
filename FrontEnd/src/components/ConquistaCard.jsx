// src/components/ConquistaCard.jsx

import cadeadoImg from '../assets/cadeado.png'; // Supondo que você tenha uma imagem de cadeado

export default function ConquistaCard({ conquista }) {
    
    // Define os estilos com base no status de 'desbloqueada'
    const isDesbloqueada = conquista.desbloqueada;

    const cardStyle = isDesbloqueada 
        ? "bg-gradient-to-br from-yellow-400 to-orange-500 shadow-lg shadow-yellow-500/30" // Estilo Desbloqueada
        : "bg-gray-700 border-2 border-gray-600"; // Estilo Bloqueada
    
    const textStyle = isDesbloqueada
        ? "text-gray-900 font-bold"
        : "text-gray-400";

    return (
        <div className={`aspect-[3/4] rounded-xl p-4 flex flex-col items-center justify-between cursor-pointer transition-all duration-300 hover:scale-105 hover:shadow-2xl ${cardStyle}`}>
            
            {/* Emblema da Conquista */}
            <div className="w-full flex-grow flex items-center justify-center relative">
                {isDesbloqueada ? (
                    <img 
                        src={conquista.imagemUrl} // A URL que definimos no backend
                        alt={conquista.nome} 
                        className="w-2/3 object-contain"
                    />
                ) : (
                    <div className="relative w-2/3">
                        <img 
                            src={conquista.imagemUrl} 
                            alt={conquista.nome} 
                            className="w-full object-contain opacity-10" // Imagem apagada
                        />
                        <img 
                            src={cadeadoImg}
                            alt="Bloqueado"
                            className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-1/2 opacity-70"
                        />
                    </div>
                )}
            </div>

            {/* Nome da Conquista */}
            <h3 className={`text-center text-sm mt-2 ${textStyle}`}>
                {conquista.nome}
            </h3>
        </div>
    );
}