// src/layouts/MainLayout.jsx

import { Outlet } from 'react-router-dom';

export default function MainLayout() {
  return (
    // Este é o 'div' com o gradiente que "roubamos" do seu Dashboard.
    // Ele servirá como fundo para todas as páginas "filhas".
    <div className="min-h-screen bg-gradient-to-br from-blue-600 to-blue-400 text-white font-sans">
      
      {/* O <Outlet /> é o espaço reservado onde o React vai renderizar a página da rota atual 
          (seja o Dashboard, o Perfil, as Conquistas, etc.) */}
      <main className="flex flex-col items-center py-10 px-6">
        <Outlet /> 
      </main>

    </div>
  );
}