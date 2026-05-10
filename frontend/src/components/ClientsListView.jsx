import React, { useState } from 'react';

export default function ClientsListView({ clients, loadingClients, onRefresh, onUpdate, onDelete }) {
  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({});
  const [saving, setSaving] = useState(false); // ✅ Empêche les doubles clics pendant la requête

  const startEdit = (client) => {
    setEditingId(client.id);
    setEditForm({ ...client });
  };

  // ✅ CORRECTION : On attend la résolution de la promesse avant de fermer l'édition
  // Si onUpdate retourne false (erreur API), on garde le formulaire ouvert
  const handleConfirm = async () => {
    setSaving(true);
    const success = await onUpdate(editingId, editForm);
    setSaving(false);
    if (success) {
      setEditingId(null); // On ferme l'édition seulement en cas de succès
    }
    // En cas d'échec, l'alerte a déjà été affichée par App.jsx et l'édition reste ouverte
  };

  return (
    <div className="min-h-screen bg-[#F7F9FA] font-sans text-[#374151]">
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-[28px] font-bold text-[#00A8B0]">Gestion des Clients</h1>
          <p className="text-gray-500 text-sm mt-1">Utilisez les icônes pour modifier ou supprimer.</p>
        </div>
        <button
          onClick={onRefresh}
          className="bg-white border border-gray-200 text-[#374151] px-5 py-2.5 rounded-xl font-bold text-sm shadow-sm hover:bg-gray-50 transition-all"
        >
          Actualiser
        </button>
      </div>

      <div className="bg-white rounded-[24px] shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-gray-50/50 border-b border-gray-100 text-[11px] uppercase text-gray-400 tracking-widest">
              <th className="px-8 py-5">Nom</th>
              <th className="px-8 py-5">Email</th>
              <th className="px-8 py-5">Téléphone</th>
              <th className="px-8 py-5 text-right">Actions</th>
            </tr>
          </thead>
          <tbody>
            {clients.map((client) => (
              <tr key={client.id} className="border-b border-gray-50 hover:bg-gray-50/30 transition-colors">

                <td className="px-8 py-5">
                  {editingId === client.id ? (
                    <input
                      className="border border-[#00A8B0] rounded-md px-2 py-1 w-full outline-none"
                      value={editForm.nom}
                      onChange={e => setEditForm({ ...editForm, nom: e.target.value })}
                    />
                  ) : (
                    <p className={`font-bold ${client.estSupprime ? 'text-gray-300 line-through' : 'text-[#374151]'}`}>
                      {client.nom}
                    </p>
                  )}
                </td>

                <td className="px-8 py-5">
                  {editingId === client.id ? (
                    <input
                      className="border border-[#00A8B0] rounded-md px-2 py-1 w-full outline-none"
                      value={editForm.email}
                      onChange={e => setEditForm({ ...editForm, email: e.target.value })}
                    />
                  ) : (
                    <p className="text-sm text-gray-500">{client.email}</p>
                  )}
                </td>

                <td className="px-8 py-5 text-sm text-gray-600">
                  {editingId === client.id ? (
                    <input
                      className="border border-[#00A8B0] rounded-md px-2 py-1 w-full outline-none"
                      value={editForm.telephone}
                      onChange={e => setEditForm({ ...editForm, telephone: e.target.value })}
                    />
                  ) : (
                    client.telephone
                  )}
                </td>

                <td className="px-8 py-5 text-right">
                  <div className="flex justify-end gap-2">
                    {editingId === client.id ? (
                      <>
                        {/* ✅ CORRECTION : async/await + feedback visuel pendant la sauvegarde */}
                        <button
                          onClick={handleConfirm}
                          disabled={saving}
                          className="text-green-600 font-bold text-xs p-2 disabled:opacity-50"
                        >
                          {saving ? '...' : 'Confirmer'}
                        </button>
                        <button
                          onClick={() => setEditingId(null)}
                          disabled={saving}
                          className="text-gray-400 font-bold text-xs p-2 disabled:opacity-50"
                        >
                          Annuler
                        </button>
                      </>
                    ) : (
                      <>
                        {/* BOUTON MODIFIER (ICÔNE) — INCHANGÉ */}
                        <button
                          onClick={() => startEdit(client)}
                          className="p-2 text-blue-500 hover:bg-blue-50 rounded-lg transition-all"
                        >
                          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                          </svg>
                        </button>
                        {/* BOUTON SUPPRIMER (ICÔNE) — INCHANGÉ */}
                        <button
                          onClick={() => onDelete(client.id)}
                          className="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-all"
                        >
                          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                            <polyline points="3 6 5 6 21 6"></polyline>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                          </svg>
                        </button>
                      </>
                    )}
                  </div>
                </td>

              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}