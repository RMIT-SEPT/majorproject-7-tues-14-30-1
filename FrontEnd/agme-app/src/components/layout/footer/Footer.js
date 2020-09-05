import React, { Component } from 'react'
import './Footer.css'
import styled from 'styled-components'

class Footer extends Component {
    render() {
        return (
            <FooterContainer className="main-footer">

                <div className='footer-middle'>
            
                    <div className='container'>
                        
                        <div className='row'>
                    
                        
                            <div className='col-md-3 col-sm-6'>     
                            
                                <h4> Dev Team </h4>
                            
                                <ul className='list-unstyled'>
                                    <li>Erik Olivesjoe</li>
                                    <li>Matt Meskell</li>
                                    <li>Prerak Soni</li>
                                    <li>Ryan Wilson</li>
                                </ul>
                            </div>

                            <div className='col-md-3 col-sm-6'>     
                            
                                <h4> Site Navigation </h4>
                            
                                <ul className='list-unstyled'>
                                    <li><a href="/dashboard">Dashboard</a></li>
                                    <li><a href="/customerProfile">My Appointments</a></li>
                                    <li><a href="/search">Search Businesses</a></li>
                                </ul>
                            </div>

                            <div className='col-md-3 col-sm-6'>     
                            
                                <h4> Project Links </h4>
                            
                                <ul className='list-unstyled'>
                                    <li><a href="https://github.com/RMIT-SEPT/majorproject-7-tues-14-30-1">GitHub</a></li>
                                    <li><a href="https://www.notion.so/055f39ad9b6d4ae299c504284898af0e?v=741980e0b8ad48ccaa6d15ffc5b4814a">Notion</a></li>
                                    <li><a href="https://teams.microsoft.com/l/channel/19%3aeccec53a4be34a9e854d23aa197b6d7b%40thread.tacv2/General?groupId=c34e6ea2-9f50-4637-8fef-86c7d42e188f&tenantId=d1323671-cdbe-4417-b4d4-bdb24b51316b">MS Teams</a></li>
                                </ul>
                            </div>

                            <div className='col-md-3 col-sm-6'>     
                            
                                <h4> Other </h4>
                            
                                <ul className='list-unstyled'>
                                    <li><a href="https://www.rmit.edu.au/">RMIT</a></li>
                                    <li><a href="https://youtu.be/fhmeYoJZeOw">Punny</a></li>
                                </ul>
                            </div>


                            
                        </div>

                        
                    
                        <div className='footer-bottom'>
                            <p className='text-xs-center'> &copy;{new Date().getFullYear()} AGME App </p>
                        </div>

                    </div>

                </div>
            
            </FooterContainer>
        )
    }
}

export default Footer;

const FooterContainer = styled.footer`

    text-align: center;
    justify-content: center;
    

    .footer-middle {
        background: var(--mainDark);
        padding-top: 3rem;
        color: var(--mainWhite);
    }

    .footer-bottom {
        padding-top: 3rem;
        padding-bottom: 2rem;
    }

    ul li a {
        color: var(--mainGrey);
    }

    ul li a:hover {
        color: var(--mainLightGrey);
    }

`;