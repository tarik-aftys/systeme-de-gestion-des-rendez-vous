import { useEffect, useMemo, useState } from 'react';
import AuthView from './components/AuthView';
import ClientsListView from './components/ClientsListView';
import CreateClientView from './components/CreateClientView';
import Navigation from './components/Navigation';

const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

const initialClientForm = {
  email: '',
  nom: '',
  password: '',
  telephone: '',
  adresse: '',
  dateNaissance: '',
};

export default function App() {
  const [activeView, setActiveView] = useState('auth');
  const [loginForm, setLoginForm] = useState({ username: 'admin', password: 'admin123' });
  const [token, setToken] = useState('');
  const [authMessage, setAuthMessage] = useState('');

  const [clients, setClients] = useState([]);
  const [loadingClients, setLoadingClients] = useState(false);
  const [clientForm, setClientForm] = useState(initialClientForm);
  const [clientMessage, setClientMessage] = useState('');

  const isAuthenticated = useMemo(() => token.trim().length > 0, [token]);

  const fetchClients = async () => {
    try {
      setLoadingClients(true);
      const response = await fetch(`${API_BASE}/clients`);
      if (!response.ok) {
        throw new Error(`Erreur API (${response.status})`);
      }
      const data = await response.json();
      setClients(Array.isArray(data) ? data : []);
    } catch (error) {
      setClientMessage(`Impossible de charger les clients: ${error.message}`);
    } finally {
      setLoadingClients(false);
    }
  };

  useEffect(() => {
    fetchClients();
  }, []);

  const handleLogin = async (event) => {
    event.preventDefault();
    setAuthMessage('Connexion en cours...');

    try {
      const response = await fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginForm),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || `Echec login (${response.status})`);
      }

      const data = await response.json();
      setToken(data.token);
      setAuthMessage(`Connecte en tant que ${data.username}`);
      setActiveView('create');
    } catch (error) {
      setToken('');
      setAuthMessage(`Login echoue: ${error.message}`);
    }
  };

  const handleCreateClient = async (event) => {
    event.preventDefault();

    if (!isAuthenticated) {
      setClientMessage('Vous devez vous connecter (JWT) avant de creer un client.');
      return;
    }

    setClientMessage('Creation du client en cours...');

    try {
      const response = await fetch(`${API_BASE}/clients`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(clientForm),
      });

      if (!response.ok) {
        const errorPayload = await response.text();
        throw new Error(errorPayload || `Echec creation (${response.status})`);
      }

      const createdClient = await response.json();
      setClientMessage(`Client cree avec succes (id=${createdClient.id}).`);
      setClientForm(initialClientForm);
      fetchClients();
      setActiveView('list');
    } catch (error) {
      setClientMessage(`Creation echouee: ${error.message}`);
    }
  };

  return (
    <main className="min-h-screen bg-[radial-gradient(circle_at_5%_15%,rgba(34,211,238,0.2),transparent_35%),radial-gradient(circle_at_95%_5%,rgba(16,185,129,0.18),transparent_30%),linear-gradient(120deg,#020617,#0f172a,#111827)] px-4 py-8 text-slate-100 sm:px-6">
      <div className="mx-auto flex max-w-7xl flex-col gap-6 lg:flex-row">
        <Navigation activeView={activeView} onChange={setActiveView} isAuthenticated={isAuthenticated} />

        <div className="flex-1 space-y-6">
          <section className="rounded-3xl border border-white/15 bg-white/5 p-6 shadow-2xl shadow-black/30 backdrop-blur sm:p-8">
            <p className="text-xs uppercase tracking-[0.2em] text-cyan-300">Sous-partie coherente</p>
            <h1 className="mt-3 text-3xl font-semibold leading-tight text-white sm:text-5xl">
              Interfaces separees pour la demo
            </h1>
            <p className="mt-3 max-w-3xl text-sm text-slate-300 sm:text-base">
              Le frontend est maintenant divise en vues distinctes: authentification, creation client, et liste clients.
              Cela rend la demonstration plus lisible et plus professionnelle.
            </p>
          </section>

          {activeView === 'auth' && (
            <AuthView
              loginForm={loginForm}
              setLoginForm={setLoginForm}
              onLogin={handleLogin}
              authMessage={authMessage}
              tokenPreview={isAuthenticated ? `${token.slice(0, 45)}...` : 'non connecte'}
            />
          )}

          {activeView === 'create' && (
            <CreateClientView
              clientForm={clientForm}
              setClientForm={setClientForm}
              onCreateClient={handleCreateClient}
              clientMessage={clientMessage}
              isAuthenticated={isAuthenticated}
            />
          )}

          {activeView === 'list' && (
            <ClientsListView clients={clients} loadingClients={loadingClients} onRefresh={fetchClients} />
          )}
        </div>
      </div>
    </main>
  );
}
