import React from 'react';

export default function ClientLoginView({
  loginForm,
  setLoginForm,
  onLogin,
  authMessage,
  onSwitchToSignup
}) {

  // Gestion de la saisie
  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  return (
    <div className="min-h-screen bg-[#F7F9FA] flex flex-col font-sans">

      {/* HEADER ISOLE */}
      <header className="w-full h-[80px] bg-white border-b border-[#E0E0E0] flex items-center px-8 lg:px-24">
         <div className="flex items-center gap-2 select-none">
            <svg width="48" height="32" viewBox="0 0 48 32" fill="none" xmlns="http://www.w3.org/2000/svg">
              <circle cx="16" cy="16" r="14.5" stroke="#00A8B0" strokeWidth="3"/>
              <circle cx="31" cy="16" r="14.5" stroke="#00A8B0" strokeWidth="3"/>
            </svg>
            <span className="text-[22px] font-bold text-[#00A8B0] tracking-tight">
              MyAppointment
            </span>
         </div>
      </header>

      {/* CONTENU PRINCIPAL */}
      <main className="flex-grow flex items-center justify-center px-4 py-12">
        <div className="bg-white rounded-[24px] shadow-sm border border-gray-50 p-10 md:p-12 w-full max-w-[520px]">

          {/* Titre & Sous-titre */}
          <div className="text-center mb-10">
            <h1 className="text-[32px] font-bold text-[#00A8B0] mb-2 uppercase">
              Se connecter
            </h1>
            <p className="text-[#9CA3AF] text-sm">
              Veuillez saisir vos identifiants pour continuer
            </p>
          </div>

          {/* Affichage des Erreurs d'Authentification */}
          {authMessage && (
            <div className={`p-4 mb-6 rounded-xl text-sm text-center font-bold ${authMessage.toLowerCase().includes('erreur') || authMessage.toLowerCase().includes('failed') ? 'bg-red-50 text-red-600' : 'bg-[#00A8B0]/10 text-[#00A8B0]'}`}>
              {authMessage}
            </div>
          )}

          {/* Formulaire */}
          <form onSubmit={onLogin} className="space-y-6">

            {/* Adresse Email (Mappé sur 'username' pour correspondre à ton DTO Spring Boot) */}
            <div>
              <label className="block text-[14px] font-bold text-[#374151] mb-2">Adresse Email</label>
              <input
                type="text"
                name="username"
                required
                placeholder="exemple@email.com"
                value={loginForm.username || ''}
                onChange={handleChange}
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-[#00A8B0]/30 focus:border-[#00A8B0] transition-colors"
              />
            </div>

            {/* Mot de passe */}
            <div>
              <div className="flex justify-between items-center mb-2">
                <label className="block text-[14px] font-bold text-[#374151]">Mot de passe</label>
                <button type="button" className="text-[12px] text-[#00A8B0] hover:underline focus:outline-none">
                  Mot de passe oublié ?
                </button>
              </div>
              <input
                type="password"
                name="password"
                required
                placeholder="••••••••"
                value={loginForm.password || ''}
                onChange={handleChange}
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-[#00A8B0]/30 focus:border-[#00A8B0] transition-colors"
              />
            </div>

            {/* Bouton de Connexion */}
            <button
              type="submit"
              className="w-full bg-[#009688] hover:bg-[#00796b] text-white font-bold py-[16px] rounded-xl mt-6 transition-all tracking-wide shadow-sm"
            >
              CONNEXION
            </button>
          </form>

          {/* Lien Footer */}
          <div className="mt-8 text-center text-[14px] text-[#374151]">
            Pas encore de compte ?{' '}
            <button
              type="button"
              onClick={onSwitchToSignup}
              className="text-[#009688] font-bold hover:underline ml-1 underline-offset-4 decoration-[#009688] focus:outline-none"
            >
              S'inscrire
            </button>
          </div>

        </div>
      </main>
    </div>
  );
}