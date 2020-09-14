import React from 'react';
import Footer from './components/layout/footer/Footer'
import Header from './components/layout/header/Header';
import Search from './components/search/Search';
import Register from './components/register/Register';
import Login from './components/login/Login';
import { BrowserRouter as Router, Route } from "react-router-dom";
import './App.css';
import Dashboard from './components/Dashboard';
import CustomerProfile from './components/customerProfile/CustomerProfile';

function App() {
  return (

    <Router>

    <div>
      <Header />

      <div>
      <Route exact path="/" component={Dashboard} />

      <Route exact path="/dashboard" component={Dashboard} />
      <Route exact path="/search" component={Search} />
      <Route exact path="/customerProfile" component={CustomerProfile} />
      <Route exact path="/register" component={Register} />
      <Route exact path="/login" component={Login} />

      </div>

      <Footer />
    </div>

    </Router>

  );
}

export default App;
