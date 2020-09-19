import React from 'react';
import Footer from './components/layout/footer/Footer'
import Header from './components/layout/header/Header';
import Search from './components/search/Search';
import Register from './components/register/Register';
import Login from './components/login/Login';
import Logout from './components/logout/Logout';
import EmployeeSearch from './components/employee search/EmployeeSearch';
import EmployeeLogin from './components/employee login/EmployeeLogin';
import { BrowserRouter as Router, Route } from "react-router-dom";
import './App.css';
import Dashboard from './components/Dashboard';
import CustomerProfile from './components/customerProfile/CustomerProfile';
import QueryTester from './components/backend-integration/QueryTester';
import BusinessProfile from './components/businessProfile/BusinessProfile';


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
        <Route exact path="/queryTester" component={QueryTester} />
        <Route exact path="/register" component={Register} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/logout" component={Logout} />
        <Route exact path="/employeeLogin" component={EmployeeLogin} />
        <Route exact path="/employeeSearch" component={EmployeeSearch} />
        
        <Route 
          exact path="/business/:id" 
          component={BusinessProfile}
        />

      </div>
    
    <Footer />
    </div>

    </Router>

  );
}

export default App;
