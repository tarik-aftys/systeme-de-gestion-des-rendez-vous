import React, { useState } from 'react';

// ✅ Mapping heure → creneauId
// Chaque créneau horaire correspond à un ID unique en base.
// Adaptez ces valeurs si vos IDs en BDD sont différents.
const SLOT_TO_CRENEAU_ID = {
  "09:00": 1,
  "10:00": 2,
  "11:00": 3,
  "14:00": 4,
  "15:00": 5,
  "16:00": 6,
};

export default function BookingView({ token, currentUser, onSuccess, onCancel }) {
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);
  const [selectedSlot, setSelectedSlot] = useState("10:00");
  const [loading, setLoading] = useState(false);

  const handleConfirm = async () => {
    setLoading(true);
    try {
      const dateISO = `${selectedDate}T${selectedSlot}:00`;
      const clientIdToSend = currentUser?.userId || 3;

      // ✅ CORRECTION : creneauId est maintenant dynamique selon le créneau sélectionné
      // Plus de hardcode à 1 → plus de Duplicate entry en BDD
      const creneauId = SLOT_TO_CRENEAU_ID[selectedSlot];
      if (!creneauId) {
        alert("Créneau invalide. Veuillez en sélectionner un autre.");
        setLoading(false);
        return;
      }

      const payload = {
        clientId:      clientIdToSend,
        prestataireId: 4, // ⚠️ Assure-toi que ce prestataire existe en BDD
        serviceId:     1, // ⚠️ Assure-toi que ce service existe en BDD
        creneauId:     creneauId, // ✅ Dynamique
        date:          dateISO
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
        onSuccess(data);
      } else if (response.status === 409) {
        // ✅ NOUVEAU : le backend renvoie 409 si le créneau est déjà pris
        alert(`Ce créneau (${selectedSlot}) est déjà réservé. Veuillez en choisir un autre.`);
      } else {
        const errorMsg = await response.text();
        console.error("Détails de l'erreur:", errorMsg);
        alert(`Erreur ${response.status} : Le serveur a refusé la création. Consultez les logs Spring Boot.`);
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

            <div>
              <h3 className="font-bold mb-4 uppercase text-xs text-gray-400 tracking-wider">Heures disponibles</h3>
              <div className="grid grid-cols-3 gap-3">
                {Object.keys(SLOT_TO_CRENEAU_ID).map(slot => (
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
                    {new Date(selectedDate).toLocaleDateString('fr-FR', {
                      weekday: 'long', day: 'numeric', month: 'long', year: 'numeric'
                    })}
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