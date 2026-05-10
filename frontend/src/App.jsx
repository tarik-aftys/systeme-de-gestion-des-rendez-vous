import { useEffect, useMemo, useState } from 'react';
import AuthView from './components/AuthView';
import ClientsListView from './components/ClientsListView';
import CreateClientView from './components/CreateClientView';
import ClientLoginView from './components/ClientLoginView';
import ClientHomeView from './components/ClientHomeView';
import BookingView from './components/BookingView';
import ConfirmationView from './components/ConfirmationView';

const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

const initialClientForm = {
  email: '', nom: '', password: '', telephone: '', adresse: '', dateNaissance: '', role: 'CLIENT'
};

export default function App() {
  const [activeView, setActiveView] = useState('client-login');
  const [token, setToken] = useState('');
  const [currentUser, setCurrentUser] = useState(null);
  const [authMessage, setAuthMessage] = useState('');

  // États pour la liste des clients (Admin)
  const [clients, setClients] = useState([]);
  const [loadingClients, setLoadingClients] = useState(false);
  const [clientForm, setClientForm] = useState(initialClientForm);
  const [clientMessage, setClientMessage] = useState('');

  // États pour les rendez-vous (Client)
  const [appointments, setAppointments] = useState([]);
  const [lastBooking, setLastBooking] = useState(null);

  const [loginForm, setLoginForm] = useState({ username: 'admin', password: 'admin123' });
  const [clientLoginForm, setClientLoginForm] = useState({ username: '', password: '' });

  const isAuthenticated = useMemo(() => token.trim().length > 0, [token]);

  // --- FONCTIONS DE CHARGEMENT ---

  const fetchClients = async () => {
    try {
      setLoadingClients(true);
      const response = await fetch(`${API_BASE}/clients`);
      if (!response.ok) throw new Error(`Erreur API (${response.status})`);
      const data = await response.json();
      setClients(Array.isArray(data) ? data : []);
    } catch (error) {
      setClientMessage(`Impossible de charger les clients: ${error.message}`);
    } finally {
      setLoadingClients(false);
    }
  };

  const fetchClientAppointments = async (userId, userToken) => {
    try {
      const response = await fetch(`${API_BASE}/rendez-vous/client/${userId}`, {
        headers: { 'Authorization': `Bearer ${userToken}` }
      });
      if (response.ok) {
        const data = await response.json();
        setAppointments(data);
      }
    } catch (error) {
      console.error("Erreur chargement RDV:", error);
    }
  };

  useEffect(() => {
    fetchClients();
  }, []);

  // --- HANDLERS ---

  const handleLogin = async (event, formData) => {
    event.preventDefault();
    setAuthMessage('Connexion en cours...');
    try {
      const response = await fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (!response.ok) throw new Error('Identifiants incorrects ou erreur serveur');

      const data = await response.json();
      setToken(data.token);
      setCurrentUser(data);
      setAuthMessage(`Bienvenue ${data.nom}`);

      if (formData.username === 'admin') {
        setActiveView('list');
      } else {
        await fetchClientAppointments(data.userId, data.token);
        setActiveView('client-home');
      }
    } catch (error) {
      setToken('');
      setAuthMessage(`Échec : ${error.message}`);
    }
  };

  const handleCreateClient = async (event) => {
    event.preventDefault();
    setClientMessage('Création du compte en cours...');
    const payload = { ...clientForm, adresse: clientForm.adresse || 'À compléter', telephone: clientForm.telephone || '00000000', dateNaissance: clientForm.dateNaissance || '2000-01-01' };
    try {
      const response = await fetch(`${API_BASE}/clients`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });
      if (!response.ok) throw new Error('Échec création');
      setClientMessage(`Succès !`);
      setClientForm(initialClientForm);
      setTimeout(() => { setClientMessage(''); setActiveView('client-login'); }, 2000);
    } catch (error) {
      setClientMessage(`Erreur : ${error.message}`);
    }
  };

  return (
    <div className="relative min-h-screen bg-[#F7F9FA]">
      {activeView === 'auth' && <AuthView loginForm={loginForm} setLoginForm={setLoginForm} onLogin={(e) => handleLogin(e, loginForm)} authMessage={authMessage} tokenPreview={token} />}
      {activeView === 'create' && <CreateClientView clientForm={clientForm} setClientForm={setClientForm} onCreateClient={handleCreateClient} clientMessage={clientMessage} onSwitchToLogin={() => setActiveView('client-login')} />}
      {activeView === 'client-login' && <ClientLoginView loginForm={clientLoginForm} setLoginForm={setClientLoginForm} onLogin={(e) => handleLogin(e, clientLoginForm)} authMessage={authMessage} onSwitchToSignup={() => setActiveView('create')} />}

      {activeView === 'client-home' && (
        <ClientHomeView
          userName={currentUser?.nom}
          appointments={appointments}
          onLogout={() => { setToken(''); setCurrentUser(null); setActiveView('client-login'); }}
          onNavigate={setActiveView}
        />
      )}

      {activeView === 'booking' && (
        <BookingView
          token={token}
          currentUser={currentUser}
          onSuccess={(newRDV) => {
            setLastBooking(newRDV);
            fetchClientAppointments(currentUser.userId, token);
            setActiveView('confirmation');
          }}
          onCancel={() => setActiveView('client-home')}
        />
      )}

      {activeView === 'confirmation' && <ConfirmationView booking={lastBooking} onReturn={() => setActiveView('client-home')} />}

      {activeView === 'list' && <div className="p-8"><ClientsListView clients={clients} loadingClients={loadingClients} onRefresh={fetchClients} /></div>}

      {/* ================= MENU FLOTTANT RESTAURÉ ================= */}
      <div className="fixed bottom-6 right-6 z-50 flex flex-col gap-2 bg-white p-4 rounded-2xl shadow-[0_8px_30px_rgb(0,0,0,0.12)] border border-gray-100">
        <span className="text-[10px] uppercase font-bold text-gray-400 text-center tracking-widest mb-1">Menu Démo</span>

        <div className="flex flex-col gap-1.5">
          <button onClick={() => setActiveView('create')} className={`px-4 py-2 text-xs font-bold rounded-lg transition-all ${activeView === 'create' ? 'bg-[#00A8B0] text-white' : 'bg-gray-50 text-gray-600 hover:bg-gray-100'}`}>Inscription Client</button>
          <button onClick={() => setActiveView('client-login')} className={`px-4 py-2 text-xs font-bold rounded-lg transition-all ${activeView === 'client-login' ? 'bg-[#00A8B0] text-white' : 'bg-gray-50 text-gray-600 hover:bg-gray-100'}`}>Connexion Client</button>
        </div>

        <div className="w-full h-[1px] bg-gray-100 my-1"></div>

        <div className="flex gap-2">
          <button onClick={() => setActiveView('auth')} className={`flex-1 px-3 py-2 text-xs font-bold rounded-lg transition-all ${activeView === 'auth' ? 'bg-gray-800 text-white' : 'bg-gray-50 text-gray-600 hover:bg-gray-100'}`}>Auth Admin</button>
          <button onClick={() => { setActiveView('list'); fetchClients(); }} className={`flex-1 px-3 py-2 text-xs font-bold rounded-lg transition-all ${activeView === 'list' ? 'bg-gray-800 text-white' : 'bg-gray-50 text-gray-600 hover:bg-gray-100'}`}>Liste</button>
        </div>
      </div>
    </div>
  );
}