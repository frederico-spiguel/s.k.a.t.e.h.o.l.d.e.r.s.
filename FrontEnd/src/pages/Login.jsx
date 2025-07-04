// src/pages/Login.jsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from '../services/api'; // Usando nossa instância centralizada do Axios

export default function Login() {
  const [login, setLogin] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro("");

    try {
      const response = await api.post("/auth/login", {
        login,
        senha,
      });

      // Pegamos o token E o status da triagem da resposta
      const { token, fezTriagem } = response.data;
      localStorage.setItem("token", token);

      // REDIRECIONAMENTO INTELIGENTE
      if (fezTriagem) {
        navigate("/dashboard");
      } else {
        navigate("/triagem");
      }
      
    } catch (err) {
      console.error(err);
      setErro("Login ou senha inválidos.");
    }
  };

  // Seu JSX (a parte visual) continua idêntico.
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 to-blue-400 text-white flex flex-col items-center justify-center px-6">
      <form onSubmit={handleSubmit} className="bg-white text-blue-800 p-8 rounded-xl shadow-xl w-full max-w-md">
        <h2 className="text-2xl font-bold text-center mb-6">Login</h2>
        <input type="text" placeholder="Login" value={login} onChange={(e) => setLogin(e.target.value)} className="w-full mb-4 p-3 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" />
        <input type="password" placeholder="Senha" value={senha} onChange={(e) => setSenha(e.target.value)} className="w-full mb-4 p-3 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" />
        {erro && <p className="text-sm text-center mb-4 text-red-600">{erro}</p>}
        <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition">Entrar</button>
      </form>
    </div>
  );
}