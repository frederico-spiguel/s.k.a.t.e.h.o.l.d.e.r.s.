// src/pages/GraficoSelecaoPage.jsx

import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api';

export default function GraficoSelecaoPage() {
    const navigate = useNavigate();
    
    // Estado para controlar a primeira escolha (o tipo de gráfico)
    const [tipoGrafico, setTipoGrafico] = useState('');
    
    // Estado para controlar a segunda escolha (o valor específico)
    const [valorSelecionado, setValorSelecionado] = useState('');
    
    // Estado para guardar as opções que vêm da API (tricks, bases)
    const [opcoes, setOpcoes] = useState([]);
    const [isLoadingOpcoes, setIsLoadingOpcoes] = useState(false);

    // Efeito que busca as opções quando o tipo de gráfico muda
    useEffect(() => {
        if (tipoGrafico === 'trick') {
            setIsLoadingOpcoes(true);
            api.get('/api/graficos/opcoes/tricks')
                .then(res => setOpcoes(res.data))
                .catch(err => console.error("Erro ao buscar tricks", err))
                .finally(() => setIsLoadingOpcoes(false));
        } else if (tipoGrafico === 'base') {
            setIsLoadingOpcoes(true);
            api.get('/api/graficos/opcoes/bases')
                .then(res => setOpcoes(res.data))
                .catch(err => console.error("Erro ao buscar bases", err))
                .finally(() => setIsLoadingOpcoes(false));
        } else {
            setOpcoes([]); // Limpa as opções se não for trick nem base
        }
        setValorSelecionado(''); // Reseta a segunda escolha sempre que o tipo mudar
    }, [tipoGrafico]);

    // Função para gerar o gráfico
    const handleGerarGrafico = () => {
        if (!tipoGrafico) return;

        let tipoFinal = tipoGrafico;
        let valorFinal = valorSelecionado;

        if (tipoGrafico === 'todos') {
             valorFinal = '0'; // Valor placeholder para "todos"
        }
        
        if (!valorFinal) {
            alert("Por favor, selecione uma opção.");
            return;
        }

        // Navega para a página do gráfico, passando os parâmetros na URL
        navigate(`/graficos/visualizar?tipo=${tipoFinal}&valor=${valorFinal}`);
    };

    const renderizarSegundaEscolha = () => {
        switch (tipoGrafico) {
            case 'trick':
            case 'base':
                return (
                    <select
                        value={valorSelecionado}
                        onChange={(e) => setValorSelecionado(e.target.value)}
                        className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        disabled={isLoadingOpcoes}
                    >
                        <option value="">{isLoadingOpcoes ? 'Carregando...' : `-- Selecione uma ${tipoGrafico} --`}</option>
                        {opcoes.map(opcao => (
                            <option key={opcao.id} value={opcao.id}>{opcao.nome}</option>
                        ))}
                    </select>
                );
            case 'dificuldade':
                return (
                    <select
                        value={valorSelecionado}
                        onChange={(e) => setValorSelecionado(e.target.value)}
                        className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="">-- Selecione uma dificuldade --</option>
                        <option value="facil">Fácil (1-2)</option>
                        <option value="media">Média (3-6)</option>
                        <option value="dificil">Difícil (7-10)</option>
                    </select>
                );
            case 'todos':
                // Não precisa de segunda escolha
                return <p className="text-gray-500 text-center">Tudo pronto para gerar o gráfico geral!</p>;
            default:
                return null;
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 p-8 flex flex-col items-center">
            <div className="w-full max-w-2xl">
                 <Link to="/dashboard" className="text-blue-600 hover:underline font-semibold mb-8 inline-block">&larr; Voltar para o Dashboard</Link>
                <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center">Gráficos de Evolução</h1>

                <div className="bg-white p-8 rounded-xl shadow-lg space-y-6">
                    {/* Primeira Escolha */}
                    <div>
                        <label className="block text-lg font-semibold text-gray-700 mb-2">1. Escolha o tipo de gráfico:</label>
                        <select
                            value={tipoGrafico}
                            onChange={(e) => setTipoGrafico(e.target.value)}
                            className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            <option value="">-- Selecione uma modalidade --</option>
                            <option value="trick">Por Manobra Específica</option>
                            <option value="base">Por Base de Skate</option>
                            <option value="dificuldade">Por Nível de Dificuldade</option>
                            <option value="todos">Geral (Todas as Manobras)</option>
                        </select>
                    </div>

                    {/* Segunda Escolha (Condicional) */}
                    {tipoGrafico && tipoGrafico !== 'todos' && (
                        <div>
                            <label className="block text-lg font-semibold text-gray-700 mb-2">2. Especifique sua escolha:</label>
                            {renderizarSegundaEscolha()}
                        </div>
                    )}

                    {/* Botão de Ação */}
                    <div className="pt-4">
                        <button
                            onClick={handleGerarGrafico}
                            disabled={!tipoGrafico || (tipoGrafico !== 'todos' && !valorSelecionado)}
                            className="w-full bg-blue-600 text-white font-bold py-3 rounded-lg text-lg hover:bg-blue-700 transition disabled:bg-gray-400 disabled:cursor-not-allowed"
                        >
                            Gerar Gráfico
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}