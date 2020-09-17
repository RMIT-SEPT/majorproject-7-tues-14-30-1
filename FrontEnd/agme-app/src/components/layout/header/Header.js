import React, { Component } from 'react'
import { Navbar, Nav, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Header.css';



class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {}
        switch(localStorage.getItem("type")){
            case "1": 
                this.state.type = "Customer" 
                break
            case "2": 
                this.state.type = "Employee"
                break
            case "3": 
                this.state.type = "Admin"
                break;
            default: 
                this.state.type = null
                break;
      }
    }

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
            

                {localStorage.getItem("email") == null ?
                <div style = {{display:"inherit"}}>    
                <div className="box">
                        <Button href="/register" variant="info">Register</Button>
                    </div>
            
                    <div className="box">
                        <Button href="/login" variant="info">Log In</Button>
                    </div>

                    <div className="box">
                        <Button href="/employeeLogin" variant="info">Employee Log In</Button>
                    </div>

                </div>
                    :
                    <div style = {{display:"inherit"}}>
                        <div className="box">
                            <Button href="/logout" variant="info">Log Out</Button>
                        </div>
                        
                        {this.state.type != null &&
                            <div className="box">
                                <Button variant="info">{this.state.type}</Button>
                            </div>
                        }
                    </div>
                }
            
            


            <div className="box">
                <Button href="/queryTester" variant="info">Query Tester</Button>
            </div>

            
            </Nav>
            
            </Navbar>
            
        )
    }
}

export default Header;