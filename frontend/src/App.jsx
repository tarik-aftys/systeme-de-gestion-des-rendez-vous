export default function App() {
  return (
    <main className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-slate-800 px-6 py-10 text-slate-100">
      <div className="mx-auto flex max-w-5xl flex-col gap-8">
        <section className="rounded-3xl border border-white/10 bg-white/5 p-8 shadow-2xl shadow-black/30 backdrop-blur">
          <p className="text-sm uppercase tracking-[0.3em] text-sky-300">Système de gestion de rendez-vous</p>
          <h1 className="mt-4 text-4xl font-semibold leading-tight sm:text-6xl">
            Plateforme de réservation claire, rapide et structurée.
          </h1>
          <p className="mt-4 max-w-2xl text-base text-slate-300 sm:text-lg">
            Base frontend React + Tailwind prête pour brancher l’authentification, la recherche de prestataires,
            la réservation et les tableaux de bord.
          </p>
        </section>

        <section className="grid gap-4 md:grid-cols-3">
          {[
            ['Authentification', 'Inscription, connexion et gestion de session.'],
            ['Réservation', 'Parcours en 3 étapes pour choisir, confirmer et suivre un rendez-vous.'],
            ['Tableaux de bord', 'Vues dédiées client, prestataire et administrateur.'],
          ].map(([title, description]) => (
            <article key={title} className="rounded-2xl border border-white/10 bg-slate-900/80 p-6">
              <h2 className="text-xl font-medium text-white">{title}</h2>
              <p className="mt-2 text-sm leading-6 text-slate-300">{description}</p>
            </article>
          ))}
        </section>
      </div>
    </main>
  );
}
