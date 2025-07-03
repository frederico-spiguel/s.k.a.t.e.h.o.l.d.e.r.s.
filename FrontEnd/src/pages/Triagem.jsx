// src/pages/Triagem.jsx
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Triagem() {
  const [tricks, setTricks] = useState([]);
  const [respostas, setRespostas] = useState({});
  const [erro, setErro] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTricks = async () => {
      try {
        const response = await api.get("/tricks"); // Endpoint confirmado
        setTricks(response.data);
      } catch (err) {
        console.error("Erro ao buscar tricks:", err);
        setErro("Não foi possível carregar as manobras. Tente recarregar a página.");
      } finally {
        setIsLoading(false);
      }
    };
    fetchTricks();
  }, []);

  const handleNivelChange = (trickId, nivel) => {
    setRespostas((prev) => ({ ...prev, [trickId]: nivel }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro("");

    if (Object.keys(respostas).length !== tricks.length) {
      setErro("Por favor, responda o nível para todas as manobras.");
      return;
    }

    const payload = Object.keys(respostas).map(trickId => ({
      trickId: parseInt(trickId),
      nivel: respostas[trickId],
    }));

      console.log("Enviando este payload para o backend:", payload); // <-- ADICIONE ESTA LINHA


    try {
      await api.post("/usuarios/triagem", payload); // Endpoint confirmado
      navigate("/dashboard"); // Sucesso! Vai para o painel principal.
    } catch (err) {
      console.error("Erro ao salvar triagem:", err);
      setErro("Houve um erro ao salvar suas respostas. Tente novamente.");
    }
  };

  if (isLoading) {
    return <div className="flex justify-center items-center min-h-screen">Carregando questionário...</div>;
  }
  
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center p-4">
      <form onSubmit={handleSubmit} className="bg-white p-8 rounded-xl shadow-lg w-full max-w-2xl">
        <h1 className="text-3xl font-bold text-gray-800 text-center mb-2">Questionário de Nível</h1>
        <p className="text-center text-gray-600 mb-8">Para começar, conte pra gente o seu nível em cada manobra.</p>

        {erro && <p className="text-center mb-4 text-red-600">{erro}</p>}
        
        <div className="space-y-6">
          {tricks.map((trick) => (
            <div key={trick.id} className="p-4 border rounded-lg hover:shadow-md transition">
              <h4 className="text-xl font-semibold text-gray-700 mb-3">{trick.nome}</h4>
              <div className="flex flex-col sm:flex-row justify-around">
                {[
                  { label: "Iniciante", value: 1 },
                  { label: "Intermediário", value: 2 },
                  { label: "Avançado", value: 3 },
                ].map((nivel) => (
                  <label key={nivel.value} className="flex items-center space-x-2 cursor-pointer p-2">
                    <input type="radio" name={`trick-${trick.id}`} value={nivel.value} onChange={() => handleNivelChange(trick.id, nivel.value)} required className="h-4 w-4 text-blue-600 focus:ring-blue-500" />
                    <span className="text-gray-700">{nivel.label}</span>
                  </label>
                ))}
              </div>
            </div>
          ))}
        </div>
        
        <button type="submit" className="w-full mt-8 bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition">
          Finalizar e Entrar no App
        </button>
      </form>
    </div>
  );
}