import React from 'react';

export default function CreateClientView({
  clientForm,
  setClientForm,
  onCreateClient,
  clientMessage,
  onSwitchToLogin
}) {

  // Gestion de la saisie des champs classiques et des boutons radio
  const handleChange = (e) => {
    const { name, value } = e.target;
    setClientForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  return (
    <div className="min-h-screen bg-[#F7F9FA] flex flex-col font-sans">

      {/* HEADER ISOLE : Juste le logo, pas de navigation comme demandé */}
      <header className="w-full h-[80px] bg-white border-b border-[#E0E0E0] flex items-center px-8 lg:px-24">
         <div className="flex items-center gap-2 select-none">
            {/* Logo recréé fidèlement depuis ton SVG */}
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
              Créer un compte
            </h1>
            <p className="text-[#9CA3AF] text-sm">
              Rejoignez-nous en quelques clics
            </p>
          </div>

          {/* Alertes Serveur (Feedback API) */}
          {clientMessage && (
            <div className={`p-4 mb-6 rounded-xl text-sm text-center font-bold ${clientMessage.toLowerCase().includes('succes') ? 'bg-[#00A8B0]/10 text-[#00A8B0]' : 'bg-red-50 text-red-600'}`}>
              {clientMessage}
            </div>
          )}

          {/* Formulaire */}
          <form onSubmit={onCreateClient} className="space-y-6">

            {/* Nom Complet */}
            <div>
              <label className="block text-[14px] font-bold text-[#374151] mb-2">Nom Complet</label>
              <input
                type="text"
                name="nom"
                required
                placeholder="John Doe"
                value={clientForm.nom || ''}
                onChange={handleChange}
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-[#00A8B0]/30 focus:border-[#00A8B0] transition-colors"
              />
            </div>

            {/* Adresse Email */}
            <div>
              <label className="block text-[14px] font-bold text-[#374151] mb-2">Adresse Email</label>
              <input
                type="email"
                name="email"
                required
                placeholder="exemple@email.com"
                value={clientForm.email || ''}
                onChange={handleChange}
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-[#00A8B0]/30 focus:border-[#00A8B0] transition-colors"
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
                value={clientForm.password || ''}
                onChange={handleChange}
                className="w-full px-4 py-[14px] rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] text-[#374151] focus:outline-none focus:ring-2 focus:ring-[#00A8B0]/30 focus:border-[#00A8B0] transition-colors"
              />
            </div>

            {/* Choix du Rôle (Radio Buttons) */}
            <div className="pt-2">
              <label className="block text-[14px] font-bold text-[#374151] mb-4">Je suis un :</label>
              <div className="flex gap-10">
                <label className="flex items-center gap-3 cursor-pointer group">
                  <div className="relative flex items-center justify-center">
                    <input
                      type="radio"
                      name="role"
                      value="CLIENT"
                      // On sélectionne CLIENT par défaut si rien n'est coché
                      checked={clientForm.role !== 'PRESTATAIRE'}
                      onChange={handleChange}
                      className="peer appearance-none w-6 h-6 border-2 border-[#E0E0E0] rounded-full checked:border-[#00A8B0] cursor-pointer transition-colors"
                    />
                    <div className="absolute w-3 h-3 bg-[#00A8B0] rounded-full opacity-0 peer-checked:opacity-100 transition-opacity"></div>
                  </div>
                  <span className="text-[#374151] text-sm">Client</span>
                </label>

                <label className="flex items-center gap-3 cursor-pointer group">
                  <div className="relative flex items-center justify-center">
                    <input
                      type="radio"
                      name="role"
                      value="PRESTATAIRE"
                      checked={clientForm.role === 'PRESTATAIRE'}
                      onChange={handleChange}
                      className="peer appearance-none w-6 h-6 border-2 border-[#E0E0E0] rounded-full checked:border-[#00A8B0] cursor-pointer transition-colors"
                    />
                    <div className="absolute w-3 h-3 bg-[#00A8B0] rounded-full opacity-0 peer-checked:opacity-100 transition-opacity"></div>
                  </div>
                  <span className="text-[#374151] text-sm">Prestataire</span>
                </label>
              </div>
            </div>

            {/* Bouton de Soumission */}
            <button
              type="submit"
              className="w-full bg-[#00A8B0] hover:bg-[#009299] text-white font-bold py-[16px] rounded-xl mt-4 transition-all tracking-wide shadow-sm"
            >
              S'INSCRIRE
            </button>
          </form>

          {/* Lien Footer */}
          <div className="mt-8 text-center text-[14px] text-[#374151]">
            Déjà un compte ?{' '}
            <button
              type="button"
              onClick={onSwitchToLogin}
              className="text-[#00A8B0] font-bold hover:underline ml-1 outline-none"
            >
              Se connecter
            </button>
          </div>

        </div>
      </main>
    </div>
  );
}