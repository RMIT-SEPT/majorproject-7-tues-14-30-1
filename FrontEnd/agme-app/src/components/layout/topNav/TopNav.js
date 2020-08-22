import React, { Component } from 'react'
import { Navbar, Nav, NavDropdown, Form, FormControl, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './TopNav.css';

class TopNav extends Component {
    render() {
        return (
            
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="#home">AGME</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />

                <Navbar.Collapse id="basic-navbar-nav">
                    
                    <Nav className="mr-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                        <Nav.Link href="#link">Book</Nav.Link>
                        <NavDropdown title="More" id="basic-nav-dropdown">
                            <NavDropdown.Item href="#action/3.1">Here</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">Are</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.3">More!</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">And even more.</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    
                    <Form inline>
                        <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                        <Button variant="outline-info">Search</Button>
                    </Form>

                </Navbar.Collapse>
            </Navbar>
            
        )
    }
}

export default TopNav;