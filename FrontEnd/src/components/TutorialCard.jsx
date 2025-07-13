// src/components/TutorialCard.jsx

import { useState } from 'react';

export default function TutorialCard({ tutorial }) {
    const [isOpen, setIsOpen] = useState(false);

    // Função para extrair o ID do vídeo do YouTube de uma URL normal
    const getYouTubeEmbedUrl = (url) => {
        let videoId;
        try {
            const urlObj = new URL(url);
            if (urlObj.hostname === 'youtu.be') {
                videoId = urlObj.pathname.slice(1);
            } else {
                videoId = urlObj.searchParams.get('v');
            }
        } catch (e) {
            // Se a URL for inválida ou um ID simples, usa o valor diretamente
            videoId = url;
        }
        return `https://www.youtube.com/embed/${videoId}`;
    };

    return (
        <div className="bg-white rounded-lg shadow-md transition-all duration-300">
            {/* Cabeçalho Clicável */}
            <div
                className="p-5 flex justify-between items-center cursor-pointer hover:bg-gray-50"
                onClick={() => setIsOpen(!isOpen)}
            >
                <h2 className="text-xl font-bold text-gray-800">{tutorial.titulo}</h2>
                <span className={`transform transition-transform duration-300 text-2xl ${isOpen ? 'rotate-180' : ''}`}>
                    &#9660;
                </span>
            </div>

            {/* Conteúdo Expansível */}
            {isOpen && (
                <div className="p-5 border-t border-gray-200">
                    <p className="text-gray-600 mb-6">{tutorial.descricao}</p>
                    
                    <div className="flex flex-col md:flex-row gap-8">
                        {/* Vídeo do YouTube */}
                        <div className="w-full md:w-1/2">
                            <div className="aspect-w-16 aspect-h-9">
                                <iframe
                                    src={getYouTubeEmbedUrl(tutorial.videoUrl)}
                                    title={tutorial.titulo}
                                    frameBorder="0"
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                    allowFullScreen
                                    className="w-full h-full rounded-lg"
                                ></iframe>
                            </div>
                        </div>

                        {/* Passo a Passo */}
                        <div className="w-full md:w-1/2">
                            <h3 className="text-lg font-semibold mb-2 text-gray-700">Passo a Passo:</h3>
                            <ul className="list-decimal list-inside space-y-2 text-gray-600">
                                {tutorial.passos.map((passo, index) => (
                                    <li key={index}>{passo}</li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}