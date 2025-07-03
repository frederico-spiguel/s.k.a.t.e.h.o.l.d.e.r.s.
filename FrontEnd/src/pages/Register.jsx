import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [login, setLogin] = useState("");
  const [senha, setSenha] = useState("");
  const [mensagem, setMensagem] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensagem("");

    try {
      const response = await axios.post("http://localhost:8080/auth/register", {
        login,
        senha,
      });

      setMensagem("Usuário cadastrado com sucesso!");
      setTimeout(() => navigate("/login"), 1500);
    } catch (err) {
      console.error(err);
      if (err.response?.data?.mensagem) {
        setMensagem(err.response.data.mensagem);
      } else {
        setMensagem("Erro ao registrar usuário.");
      }
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 to-blue-400 text-white flex flex-col items-center justify-center px-6">
      <form
        onSubmit={handleSubmit}
        className="bg-white text-blue-800 p-8 rounded-xl shadow-xl w-full max-w-md"
      >
        <h2 className="text-2xl font-bold text-center mb-6">Cadastro</h2>
        <input
          type="text"
          placeholder="Login"
          value={login}
          onChange={(e) => setLogin(e.target.value)}
          className="w-full mb-4 p-3 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          className="w-full mb-4 p-3 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        {mensagem && (
          <p className="text-sm text-center mb-4 text-blue-600">{mensagem}</p>
        )}
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition"
        >
          Registrar
        </button>
      </form>
    </div>
  );
}
