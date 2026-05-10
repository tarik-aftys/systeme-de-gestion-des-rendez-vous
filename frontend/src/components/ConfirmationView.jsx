import React from 'react';

export default function ConfirmationView({ booking, onReturn }) {
  // Optionnel : un petit formatage de la date si l'objet booking contient la date (pour l'instant on garde ça simple)
  const formattedDate = booking?.date ? new Date(booking.date).toLocaleDateString('fr-FR', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute:'2-digit' }).replace(':', 'h') : "Jeudi 7 Mai 2026 à 14:30";
  const serviceName = booking?.serviceName || "Consultation Génie Logiciel";

  return (
    <div className="min-h-screen bg-[#F7F9FA] flex items-center justify-center p-6 relative overflow-hidden">

      {/* Le SVG de fond que tu as fourni
        On le place en absolute, au centre, en dessous du contenu (z-0)
      */}
      <div className="absolute inset-0 z-0 flex items-center justify-center pointer-events-none">
        <svg width="1440" height="1024" viewBox="0 0 1440 1024" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-full h-full object-cover">
          {/* ... (Je n'ai pas recopié l'intégralité du code SVG pour ne pas surcharger, mais TU METS TON LONG SVG ICI) ... */}
           {/* (Exemple des premières lignes de ton SVG) */}
           <path d="M1440 0H0V80H1440V0Z" fill="white"/>
           <path d="M0 80H1440" stroke="#E0E0E0"/>
           {/* ... etc ... */}
        </svg>
      </div>

      {/* La Carte Centrale (z-10 pour être au-dessus du fond) */}
      <div className="bg-white rounded-[32px] p-12 shadow-[0_8px_30px_rgb(0,0,0,0.04)] border border-gray-50 w-full max-w-lg text-center relative z-10">

        {/* Le Cercle Vert avec le Check */}
        <div className="w-24 h-24 bg-[#00A8B0] rounded-full flex items-center justify-center mx-auto mb-8 shadow-lg shadow-[#00A8B0]/30">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="white" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round">
            <polyline points="20 6 9 17 4 12"></polyline>
          </svg>
        </div>

        <h1 className="text-[32px] font-bold text-[#00A8B0] mb-10">Réservation confirmée !</h1>

        {/* Le bloc d'informations structuré comme sur ton image */}
        <div className="space-y-6 text-left mb-12">

            {/* Champ Motif */}
            <div>
                <p className="text-sm font-bold text-[#374151] mb-2">Motif de consultation</p>
                <div className="w-full px-5 py-4 rounded-xl border border-[#E0E0E0] bg-gray-50/50 text-[#374151]">
                    {serviceName}
                </div>
            </div>

            {/* Champ Date et Heure */}
            <div>
                <p className="text-sm font-bold text-[#374151] mb-2">Date et heure</p>
                <div className="w-full px-5 py-4 rounded-xl border border-[#E0E0E0] bg-gray-50/50 text-[#374151]">
                    {formattedDate}
                </div>
            </div>

        </div>

        {/* Le Bouton de retour */}
        <button
          onClick={onReturn}
          className="w-full bg-[#00A8B0] hover:bg-[#009299] text-white font-bold py-4 rounded-xl shadow-lg shadow-[#00A8B0]/20 transition-all uppercase text-sm"
        >
          Retourner à l'accueil
        </button>

      </div>
    </div>
  );
}