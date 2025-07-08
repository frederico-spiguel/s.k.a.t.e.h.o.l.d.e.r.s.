// src/pages/Register.jsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from '../services/api'; // Usando nossa instância centralizada do Axios

export default function Register() {
  const [login, setLogin] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState(""); // Mudamos de 'mensagem' para 'erro' para consistência
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro("");
      console.log("Tentando registrar com a nova lógica..."); // <-- LINHA DO ESPIÃO


    try {
      const response = await api.post("/auth/register", {
        login,
        senha,
      });

      // O backend agora retorna o token no cadastro!
      const { token } = response.data;
      localStorage.setItem("token", token);

      // Após o cadastro, sempre vai para a triagem
      navigate("/triagem");

    } catch (err) {
      console.error(err);
      setErro("Este login já está em uso. Tente outro.");
    }
  };
  
  // Seu JSX com pequena adaptação na mensagem de erro
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 to-blue-400 text-white flex flex-col items-center justify-center px-6">
      <form onSubmit={handleSubmit} className="bg-white text-blue-800 p-8 rounded-xl shadow-xl w-full max-w-md">
        <h2 className="text-2xl font-bold text-center mb-6">Cadastro</h2>
        <input type="text" placeholder="Login" value={login} onChange={(e) => setLogin(e.target.value)} className="w-full mb-4 p-3 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" />
        <input type="password" placeholder="Senha" value={senha} onChange={(e) => setSenha(e.target.value)} className="w-full mb-4 p-3 border border-blue-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" />
        {erro && <p className="text-sm text-center mb-4 text-red-600">{erro}</p>}
        <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition">Registrar</button>
      </form>
    </div>
  );
}