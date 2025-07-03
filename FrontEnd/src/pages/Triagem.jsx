import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Triagem({ usuarioId, token }) {
  const [nivel, setNivel] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async () => {
    if (!nivel) return alert("Escolha um nível antes de continuar.");

    try {
      await axios.post(
        "http://localhost:8080/trick-usuario",
        {
          usuarioId: usuarioId,
          trickId: 1, // ID da trick Ollie
          nivel: parseInt(nivel),
          acertos: 0,
          proficiencia: false,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      // Atualizar usuario no backend como fezTriagem = true
      await axios.put(
        `http://localhost:8080/usuarios/${usuarioId}/triagem`,
        null,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      navigate("/dashboard");
    } catch (error) {
      console.error("Erro ao salvar triagem:", error);
      alert("Erro ao salvar triagem.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-6 rounded-xl shadow-lg w-96">
        <h2 className="text-xl font-semibold mb-4">Triagem inicial</h2>
        <p className="mb-4">
          Como você se classifica na manobra <strong>Ollie</strong>?
        </p>

        <div className="space-y-2">
          <label className="block">
            <input
              type="radio"
              name="nivel"
              value="1"
              onChange={(e) => setNivel(e.target.value)}
            />{" "}
            Iniciante
          </label>
          <label className="block">
            <input
              type="radio"
              name="nivel"
              value="2"
              onChange={(e) => setNivel(e.target.value)}
            />{" "}
            Intermediário
          </label>
          <label className="block">
            <input
              type="radio"
              name="nivel"
              value="3"
              onChange={(e) => setNivel(e.target.value)}
            />{" "}
            Avançado
          </label>
        </div>

        <button
          onClick={handleSubmit}
          className="mt-6 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 w-full"
        >
          Finalizar Triagem
        </button>
      </div>
    </div>
  );
}

export default Triagem;
