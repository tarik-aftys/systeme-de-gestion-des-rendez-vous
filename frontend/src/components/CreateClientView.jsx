export default function CreateClientView({ clientForm, setClientForm, onCreateClient, clientMessage, isAuthenticated }) {
  return (
    <section className="rounded-3xl border border-white/15 bg-slate-900/75 p-6 shadow-xl shadow-black/30">
      <p className="text-xs uppercase tracking-[0.18em] text-emerald-300">Etape 2</p>
      <h3 className="mt-2 text-2xl font-semibold text-white">Creation d un client</h3>
      <p className="mt-2 text-sm text-slate-300">
        Cette interface est separee pour montrer clairement la saisie metier et la validation des donnees.
      </p>

      {!isAuthenticated && (
        <p className="mt-4 rounded-lg border border-amber-700/50 bg-amber-500/10 px-3 py-2 text-sm text-amber-300">
          Connectez-vous d abord pour envoyer un POST securise.
        </p>
      )}

      <form className="mt-5 grid gap-3 md:grid-cols-2" onSubmit={onCreateClient}>
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100"
          placeholder="Email"
          type="email"
          value={clientForm.email}
          onChange={(event) => setClientForm((prev) => ({ ...prev, email: event.target.value }))}
          required
        />
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100"
          placeholder="Nom"
          value={clientForm.nom}
          onChange={(event) => setClientForm((prev) => ({ ...prev, nom: event.target.value }))}
          required
        />
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100"
          placeholder="Mot de passe"
          type="password"
          value={clientForm.password}
          onChange={(event) => setClientForm((prev) => ({ ...prev, password: event.target.value }))}
          required
        />
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100"
          placeholder="Telephone"
          value={clientForm.telephone}
          onChange={(event) => setClientForm((prev) => ({ ...prev, telephone: event.target.value }))}
          required
        />
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100 md:col-span-2"
          placeholder="Adresse"
          value={clientForm.adresse}
          onChange={(event) => setClientForm((prev) => ({ ...prev, adresse: event.target.value }))}
          required
        />
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100 md:col-span-2"
          type="date"
          value={clientForm.dateNaissance}
          onChange={(event) => setClientForm((prev) => ({ ...prev, dateNaissance: event.target.value }))}
          required
        />
        <button
          className="rounded-xl bg-emerald-400 px-4 py-2 font-medium text-slate-950 transition hover:bg-emerald-300 md:col-span-2"
          type="submit"
        >
          Creer client
        </button>
      </form>

      <p className="mt-3 text-sm text-slate-200">{clientMessage}</p>
    </section>
  );
}
