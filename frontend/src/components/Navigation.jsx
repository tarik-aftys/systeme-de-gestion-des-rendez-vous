const links = [
  { key: 'auth', label: 'Authentification' },
  { key: 'create', label: 'Creer Client' },
  { key: 'list', label: 'Liste Clients' },
];

export default function Navigation({ activeView, onChange, isAuthenticated }) {
  return (
    <aside className="w-full rounded-3xl border border-white/15 bg-slate-900/70 p-5 shadow-xl shadow-black/30 lg:w-72">
      <p className="text-xs uppercase tracking-[0.22em] text-cyan-300">Demo Academique</p>
      <h2 className="mt-2 text-2xl font-semibold text-white">Gestion Clients</h2>
      <p className="mt-2 text-sm text-slate-300">Une sous-partie coherente frontend + backend.</p>

      <div className="mt-5 space-y-2">
        {links.map((link) => {
          const active = activeView === link.key;
          return (
            <button
              key={link.key}
              type="button"
              onClick={() => onChange(link.key)}
              className={`w-full rounded-xl px-4 py-3 text-left text-sm transition ${
                active
                  ? 'bg-cyan-400 text-slate-950 shadow-md shadow-cyan-500/30'
                  : 'bg-slate-800/90 text-slate-200 hover:bg-slate-700'
              }`}
            >
              {link.label}
            </button>
          );
        })}
      </div>

      <div className="mt-6 rounded-xl border border-slate-700 bg-slate-950/60 p-3 text-sm text-slate-300">
        <p className="font-medium text-slate-100">Etat session</p>
        <p className="mt-1">{isAuthenticated ? 'Connecte (JWT actif)' : 'Non connecte'}</p>
      </div>
    </aside>
  );
}
