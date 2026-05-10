import React from 'react';

export default function AuthView({ 
  loginForm, 
  setLoginForm, 
  onLogin, 
  authMessage, 
  tokenPreview 
}) {

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginForm(prev => ({ ...prev, [name]: value }));
  };

  return (
    <div className="min-h-screen bg-[#F7F9FA] flex flex-col font-sans">
      
      {/* HEADER : Identique aux autres pages pour la cohérence */}
      <header className="w-full h-[80px] bg-white border-b border-[#E0E0E0] flex items-center px-8 lg:px-24">
         <div className="flex items-center gap-2 select-none">
            <svg width="48" height="32" viewBox="0 0 48 32" fill="none" xmlns="http://www.w3.org/2000/svg">
              <circle cx="16" cy="16" r="14.5" stroke="#00A8B0" strokeWidth="3"/>
              <circle cx="31" cy="16" r="14.5" stroke="#00A8B0" strokeWidth="3"/>
            </svg>
            <span className="text-[22px] font-bold text-[#00A8B0] tracking-tight">
              MyAppointment
            </span>
            <span className="ml-2 px-2 py-0.5 bg-gray-800 text-white text-[10px] font-bold rounded uppercase">
              Admin
            </span>
         </div>
      </header>

      {/* CONTENU PRINCIPAL */}
      <main className="flex-grow flex items-center justify-center px-4 py-12">
        <div className="bg-white rounded-[24px] shadow-sm border border-gray-50 p-10 md:p-12 w-full max-w-[520px]">
          
          {/* Titre & Sous-titre */}
          <div className="text-center mb-10">
            <h1 className="text-[32px] font-bold text-gray-800 mb-2 uppercase">
              Back-Office
            </h1>
            <p className="text-[#9CA3AF] text-sm font-medium">
              Cette interface isole la partie securite: l admin se connecte et recupere un token JWT.
            </p>
          </div>

          {/* Feedback API */}
          {authMessage && (
            <div className={`p-4 mb-6 rounded-xl text-sm text-center font-bold ${authMessage.toLowerCase().includes('échec') || authMessage.toLowerCase().includes('failed') ? 'bg-red-50 text-red-600' : 'bg-green-50 text-green-600'}`}>
              {authMessage}
            </div>
          )}

          {/* Formulaire Admin */}
          <form onSubmit={onLogin} className="space-y-6">
            
            {/* Identifiant */}
            <div>
              <label className="block text-[14px] font-bold text-[#374151] mb-2">Identifiant Admin</label>
              <input 
                type="text" 
                name="username" 
                required 
                placeholder="admin" 
                value={loginForm.username || ''} 
                onChange={handleChange} 
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-gray-800/20 focus:border-gray-800 transition-colors" 
              />
            </div>

            {/* Mot de passe */}
            <div>
              <label className="block text-[14px] font-bold text-[#374151] mb-2">Mot de passe</label>
              <input 
                type="password" 
                name="password" 
                required 
                placeholder="••••••••" 
                value={loginForm.password || ''} 
                onChange={handleChange} 
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-gray-800/20 focus:border-gray-800 transition-colors" 
              />
            </div>

            {/* Bouton Admin (Noir pour différencier du client) */}
            <button 
              type="submit" 
              className="w-full bg-gray-800 hover:bg-gray-900 text-white font-bold py-[16px] rounded-xl mt-6 transition-all tracking-wide shadow-sm"
            >
              ACCÉDER AU DASHBOARD
            </button>
          </form>

          {/* Debug Token */}
          {tokenPreview && tokenPreview !== 'non connecté' && (
            <div className="mt-8 p-4 bg-gray-50 rounded-lg border border-dashed border-gray-200">
              <span className="text-[10px] uppercase font-bold text-gray-400 block mb-1">JWT Debugger</span>
              <code className="text-[10px] text-gray-500 break-all">{tokenPreview}</code>
            </div>
          )}

        </div>
      </main>
    </div>
  );
}