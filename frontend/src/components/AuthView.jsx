export default function AuthView({ loginForm, setLoginForm, onLogin, authMessage, tokenPreview }) {
  return (
    <section className="rounded-3xl border border-white/15 bg-slate-900/75 p-6 shadow-xl shadow-black/30">
      <p className="text-xs uppercase tracking-[0.18em] text-cyan-300">Etape 1</p>
      <h3 className="mt-2 text-2xl font-semibold text-white">Connexion JWT</h3>
      <p className="mt-2 text-sm text-slate-300">
        Cette interface isole la partie securite: l admin se connecte et recupere un token JWT.
      </p>

      <form className="mt-5 grid gap-3" onSubmit={onLogin}>
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100"
          placeholder="Username"
          value={loginForm.username}
          onChange={(event) => setLoginForm((prev) => ({ ...prev, username: event.target.value }))}
          required
        />
        <input
          className="rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-slate-100"
          type="password"
          placeholder="Password"
          value={loginForm.password}
          onChange={(event) => setLoginForm((prev) => ({ ...prev, password: event.target.value }))}
          required
        />
        <button
          className="rounded-xl bg-cyan-400 px-4 py-2 font-medium text-slate-950 transition hover:bg-cyan-300"
          type="submit"
        >
          Se connecter
        </button>
      </form>

      <p className="mt-3 text-sm text-slate-200">{authMessage}</p>
      <p className="mt-2 break-all rounded-lg border border-slate-700 bg-slate-950/80 px-3 py-2 text-xs text-slate-400">
        JWT: {tokenPreview}
      </p>
    </section>
  );
}
