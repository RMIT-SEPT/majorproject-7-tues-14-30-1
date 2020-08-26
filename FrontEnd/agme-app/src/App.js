import React from 'react';
import Footer from './components/layout/footer/Footer'
import TopNav from './components/layout/topNav/TopNav';
import Search from './components/search/Search';
import './App.css';

function App() {
  return (
    <div>
      <TopNav />

      <div>
      
      <Search />
      
      </div>

      <Footer />
    </div>
  );
}

export default App;
