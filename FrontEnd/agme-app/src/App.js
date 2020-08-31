import React from 'react';
import Footer from './components/layout/footer/Footer'
import Header from './components/layout/header/Header';
import Search from './components/search/Search';
import { BrowserRouter as Router, Route } from "react-router-dom";
import './App.css';
import Dashboard from './components/Dashboard';

function App() {
  return (

    <Router>

    <div>
      <Header />

      <div>
      
      <Route exact path="/dashboard" component={Dashboard} />
      <Route exact path="/search" component={Search} />
      
      </div>

      <Footer />
    </div>

    </Router>

  );
}

export default App;
