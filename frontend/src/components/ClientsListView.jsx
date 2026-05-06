export default function ClientsListView({ clients, loadingClients, onRefresh }) {
  return (
    <section className="rounded-3xl border border-white/15 bg-slate-900/75 p-6 shadow-xl shadow-black/30">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <p className="text-xs uppercase tracking-[0.18em] text-violet-300">Etape 3</p>
          <h3 className="mt-1 text-2xl font-semibold text-white">Liste des clients</h3>
        </div>
        <button
          className="rounded-xl border border-slate-600 bg-slate-800 px-4 py-2 text-sm text-slate-100 transition hover:bg-slate-700"
          type="button"
          onClick={onRefresh}
        >
          Rafraichir
        </button>
      </div>

      {loadingClients ? (
        <p className="mt-4 text-slate-300">Chargement...</p>
      ) : clients.length === 0 ? (
        <p className="mt-4 text-slate-300">Aucun client enregistre.</p>
      ) : (
        <div className="mt-4 overflow-x-auto rounded-xl border border-slate-800">
          <table className="min-w-full text-left text-sm">
            <thead className="bg-slate-800/90 text-slate-200">
              <tr>
                <th className="px-3 py-2">ID</th>
                <th className="px-3 py-2">Nom</th>
                <th className="px-3 py-2">Email</th>
                <th className="px-3 py-2">Telephone</th>
              </tr>
            </thead>
            <tbody>
              {clients.map((client) => (
                <tr key={client.id} className="border-t border-slate-800 text-slate-100 hover:bg-slate-900/80">
                  <td className="px-3 py-2">{client.id}</td>
                  <td className="px-3 py-2">{client.nom}</td>
                  <td className="px-3 py-2">{client.email}</td>
                  <td className="px-3 py-2">{client.telephone}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}
