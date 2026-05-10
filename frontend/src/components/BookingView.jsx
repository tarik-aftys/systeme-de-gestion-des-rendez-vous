import React, { useState } from 'react';

export default function BookingView({ token, currentUser, onSuccess, onCancel }) {
  // 1. Nouveaux états : on gère la date dynamiquement (par défaut aujourd'hui)
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);
  const [selectedSlot, setSelectedSlot] = useState("10:00");
  const [loading, setLoading] = useState(false);

  const handleConfirm = async () => {
    setLoading(true);
    try {
      // 2. On combine la date choisie dans le calendrier et l'heure
      const dateISO = `${selectedDate}T${selectedSlot}:00`;

      // 3. Sécurité : si currentUser.userId est indéfini suite à notre rollback, on force un ID (ex: 3) pour que la BDD ne plante pas
      const clientIdToSend = currentUser?.userId || 3;

      const payload = {
        clientId: clientIdToSend,
        prestataireId: 4, // ⚠️ Assure-toi que ce prestataire existe en BDD !
        serviceId: 1,     // ⚠️ Assure-toi que ce service existe en BDD !
        creneauId: 1,
        date: dateISO
      };

      const response = await fetch('http://localhost:8080/api/rendez-vous', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        const data = await response.json();
        onSuccess(data); // Redirige vers la vue Confirmation
      } else {
        const errorMsg = await response.text();
        console.error("Détails de l'erreur:", errorMsg);
        alert("Erreur 500 : Le serveur a refusé la création. Regardez votre terminal Spring Boot pour la cause exacte.");
      }
    } catch (e) {
      console.error(e);
      alert("Erreur de connexion au serveur.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#F7F9FA] p-8 font-sans">
      <div className="max-w-4xl mx-auto space-y-8">
        <button onClick={onCancel} className="text-[#00A8B0] font-bold flex items-center gap-2 hover:underline">
          ← Retour
        </button>

        <h1 className="text-[28px] font-bold text-[#00A8B0]">Sélectionnez une date et un créneau</h1>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">

            {/* Colonne Gauche : Calendrier + Heures */}
            <div className="bg-white p-8 rounded-[24px] shadow-sm border border-gray-100 flex flex-col gap-6">

                {/* LE CALENDRIER */}
                <div>
                  <h3 className="font-bold mb-4 uppercase text-xs text-gray-400 tracking-wider">Date souhaitée</h3>
                  <input
                    type="date"
                    value={selectedDate}
                    onChange={(e) => setSelectedDate(e.target.value)}
                    className="w-full p-4 border border-gray-200 rounded-xl text-lg font-bold text-[#374151] focus:ring-2 focus:ring-[#00A8B0] outline-none"
                  />
                </div>

                <div className="w-full h-[1px] bg-gray-100"></div>

                {/* LES HEURES */}
                <div>
                  <h3 className="font-bold mb-4 uppercase text-xs text-gray-400 tracking-wider">Heures disponibles</h3>
                  <div className="grid grid-cols-3 gap-3">
                      {["09:00", "10:00", "11:00", "14:00", "15:00", "16:00"].map(slot => (
                          <button
                              key={slot}
                              onClick={() => setSelectedSlot(slot)}
                              className={`p-3 rounded-xl border-2 font-bold transition-all ${
                                  selectedSlot === slot
                                  ? 'bg-[#00A8B0] text-white border-[#00A8B0] shadow-md shadow-[#00A8B0]/20'
                                  : 'bg-white text-gray-400 border-gray-100 hover:border-[#00A8B0]/30 hover:text-[#00A8B0]'
                              }`}
                          >
                              {slot}
                          </button>
                      ))}
                  </div>
                </div>
            </div>

            {/* Colonne Droite : Récapitulatif et Confirmation */}
            <div className="bg-white p-8 rounded-[24px] shadow-sm border border-gray-100 flex flex-col justify-between">
                <div>
                    <h3 className="font-bold mb-6 uppercase text-xs text-gray-400 tracking-wider">Récapitulatif</h3>

                    <div className="space-y-4 bg-gray-50 p-6 rounded-xl border border-gray-100">
                        <div className="flex justify-between items-center pb-4 border-b border-gray-200">
                            <span className="text-gray-500">Service</span>
                            <span className="font-bold text-[#374151]">Consultation DSE</span>
                        </div>
                        <div className="flex justify-between items-center pb-4 border-b border-gray-200">
                            <span className="text-gray-500">Date</span>
                            <span className="font-bold text-[#374151]">
                              {new Date(selectedDate).toLocaleDateString('fr-FR', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' })}
                            </span>
                        </div>
                        <div className="flex justify-between items-center">
                            <span className="text-gray-500">Heure</span>
                            <span className="font-bold text-[#00A8B0] text-lg">{selectedSlot}</span>
                        </div>
                    </div>
                </div>

                <button
                    onClick={handleConfirm}
                    disabled={loading}
                    className="w-full bg-[#00A8B0] hover:bg-[#009299] text-white font-bold py-4 rounded-xl mt-8 shadow-lg shadow-[#00A8B0]/20 transition-all uppercase text-sm disabled:opacity-50"
                >
                    {loading ? "Traitement en cours..." : "CONFIRMER LA RÉSERVATION"}
                </button>
            </div>
        </div>
      </div>
    </div>
  );
}