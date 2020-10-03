import React, { Component } from 'react'
import { Navbar, Nav } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Header.css';
import HeaderButton from './HeaderButton';

class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {type:null};
        let type = this.state.type
        let account = JSON.parse(localStorage.getItem("account"))
        if (account){
            type = account.type
            switch(type){
                case 1: 
                    type="Customer"
                    break
                case 2: 
                    type="Employee"
                    break
                case 3: 
                    type="Admin"
                    break;
                default: 
                    type=null
                    break;
            }
      }
      this.state.type=type;
    }

    render() {
        return (
            
            <Navbar bg="dark" variant="dark">
            <Nav>

                <HeaderButton href="/dashboard" label={"Home"}></HeaderButton>
                <HeaderButton href="/search" label={"Search"}></HeaderButton>
                <HeaderButton href="/customerProfile" label={"Profile"}></HeaderButton>            
                <HeaderButton href="/bookAppointment" label={"Book Appointment"}></HeaderButton>            

                {localStorage.getItem("account") == null ?
                
                    <div style = {{display:"inherit"}}>    
                    
                        <HeaderButton href="/register" label={"Register"}></HeaderButton>
                        <HeaderButton href="/login" label={"Log In"}></HeaderButton>
                        <HeaderButton href="/employeeLogin" label={"Employee Log In"}></HeaderButton>

                    </div>

                    :

                    <div style = {{display:"inherit"}}>
                        
                        <HeaderButton href="/logout" label={"Log Out"}></HeaderButton>
                        
                        {this.state.type !== "customer" && this.state.type!==null &&
                            <div style = {{display:"inherit"}}>
                                
                                {this.state.type==="Admin"&&
                                    <HeaderButton href="/employeeSearch" label={"Employee Search"}></HeaderButton>
                                }

                                <HeaderButton label={this.state.type}></HeaderButton>
                              
                            </div>
                        }
                    </div>
                }
                        
            </Nav>
            </Navbar>
        )
    }
}

export default Header;