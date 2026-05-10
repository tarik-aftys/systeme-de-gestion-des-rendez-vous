import React, { useState } from 'react';

export default function ClientHomeView({ onLogout, onNavigate, userName }) {
  const [searchForm, setSearchForm] = useState({
    motif: '',
    date: ''
  });

  return (
    <div className="min-h-screen bg-[#F7F9FA] flex flex-col font-sans text-[#374151]">

      {/* NAVBAR (Fidèle au SVG) */}
      <nav className="w-full h-[80px] bg-white border-b border-[#E0E0E0] flex items-center justify-between px-8 lg:px-24 sticky top-0 z-50">
        <div className="flex items-center gap-2">
          {/* Logo MyAppointment */}
          <svg width="48" height="32" viewBox="0 0 48 32" fill="none">
            <circle cx="16" cy="16" r="14.5" stroke="#00A8B0" strokeWidth="3"/>
            <circle cx="31" cy="16" r="14.5" stroke="#00A8B0" strokeWidth="3"/>
          </svg>
          <span className="text-[22px] font-bold text-[#00A8B0] tracking-tight">MyAppointment</span>
        </div>

        <div className="hidden md:flex items-center gap-10 text-sm font-bold">
          <button onClick={() => onNavigate('client-home')} className="text-[#00A8B0]">Accueil</button>
          <button className="hover:text-[#00A8B0] transition-colors">Mes Réservations</button>
          <button className="hover:text-[#00A8B0] transition-colors">Explorer</button>
          <button onClick={() => onNavigate('profile')} className="hover:text-[#00A8B0] transition-colors">Profil</button>
        </div>

        <div className="flex items-center gap-4">
            <button onClick={onLogout} className="text-xs text-red-400 hover:text-red-600 font-bold mr-4">Déconnexion</button>
            {/* Icône Profil du SVG */}
            <div className="w-12 h-12 bg-[#F7F9FA] border-2 border-[#E0E0E0] rounded-full flex items-center justify-center cursor-pointer overflow-hidden">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#9CA3AF" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
            </div>
        </div>
      </nav>

      {/* CONTENU PRINCIPAL */}
      <main className="max-w-6xl mx-auto w-full p-8 lg:p-12 space-y-12">

        {/* Message de Bienvenue */}
        <h1 className="text-[32px] font-bold text-[#00A8B0] leading-tight">
          Bonjour {userName || 'Cher Client'}, prêt pour votre prochain rendez-vous ?
        </h1>

        {/* Section : Prochain Rendez-vous */}
        <section>
          <div className="bg-white rounded-[24px] shadow-sm border border-gray-100 p-8 flex flex-col md:flex-row items-center justify-between gap-6">
            <div className="space-y-1">
              <span className="text-[11px] font-bold text-[#9CA3AF] uppercase tracking-[0.15em]">Votre prochain rendez-vous</span>
              <div className="flex items-baseline gap-4 pt-2">
                <h2 className="text-2xl font-bold text-[#374151]">Jeudi 7 Mai 2026 à 10:00</h2>
                <span className="text-[#00A8B0] font-medium text-lg">Consultation Génie Logiciel</span>
              </div>
            </div>
            <button className="text-sm font-bold text-[#9CA3AF] hover:text-[#374151] transition-colors border border-gray-200 px-4 py-2 rounded-lg">
                Modifier
            </button>
          </div>
        </section>

        {/* Section : Recherche Rapide (Formulaire Blanc) */}
        <section className="bg-white rounded-[24px] shadow-sm border border-gray-100 p-10 max-w-3xl">
          <h3 className="text-xl font-bold text-[#00A8B0] mb-8">Recherche rapide</h3>

          <div className="space-y-6">
            {/* Champ Motif */}
            <div>
              <label className="block text-sm font-bold text-[#374151] mb-3 uppercase tracking-wide">Motif de consultation</label>
              <input
                type="text"
                placeholder="Ex: Suivi de projet DSE"
                className="w-full px-5 py-4 rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] focus:ring-2 focus:ring-[#00A8B0]/20 focus:border-[#00A8B0] outline-none transition-all"
                value={searchForm.motif}
                onChange={(e) => setSearchForm({...searchForm, motif: e.target.value})}
              />
            </div>

            {/* Champ Date */}
            <div>
              <label className="block text-sm font-bold text-[#374151] mb-3 uppercase tracking-wide">Date souhaitée</label>
              <input
                type="text"
                placeholder="JJ/MM/AAAA"
                className="w-full px-5 py-4 rounded-xl border border-[#E0E0E0] placeholder-[#9CA3AF] focus:ring-2 focus:ring-[#00A8B0]/20 focus:border-[#00A8B0] outline-none transition-all"
                value={searchForm.date}
                onChange={(e) => setSearchForm({...searchForm, date: e.target.value})}
              />
            </div>

            {/* Bouton Nouvelle Réservation */}
            <div className="pt-4">
              <button
                onClick={() => alert("Recherche lancée...")}
                className="bg-[#00A8B0] hover:bg-[#009299] text-white font-bold py-4 px-10 rounded-xl flex items-center gap-2 shadow-lg shadow-[#00A8B0]/20 transition-all uppercase text-sm"
              >
                <span>+</span> Nouvelle réservation
              </button>
            </div>
          </div>
        </section>

      </main>
    </div>
  );
}