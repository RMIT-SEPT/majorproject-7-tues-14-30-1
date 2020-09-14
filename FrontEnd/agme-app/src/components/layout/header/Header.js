import React, { Component } from 'react'
import { Navbar, Nav, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Header.css';

class Header extends Component {
    render() {
        return (
            
            <Navbar bg="dark" variant="dark">

            <Nav>
            <div className="box">
                <Button href="/dashboard" variant="info">Dashboard</Button>
            </div>
            
            <div className="box">
                <Button href="/search" variant="info">Search</Button>
            </div>

            
            <div className="box">
            <Button href="/customerProfile" variant="info">Profile</Button>
            </div>
            
            <div className="box">
                <Button href="/register" variant="info">Register</Button>
            </div>

            <div className="box">
                <Button href="/login" variant="info">Log In</Button>
            </div>

            <div className="box">
                <Button href="/queryTester" variant="info">Query Tester</Button>
            </div>

            
            </Nav>
            
            </Navbar>
            
        )
    }
}

export default Header;