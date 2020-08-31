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
                                    <li><a href="/">My Appointments</a></li>
                                    <li><a href="/">Search Businesses</a></li>
                                    <li><a href="/">Link 3</a></li>
                                    <li><a href="/">Link 4</a></li>
                                </ul>
                            </div>

                            <div className='col-md-3 col-sm-6'>     
                            
                                <h4> Project Links </h4>
                            
                                <ul className='list-unstyled'>
                                    <li><a href="/">GitHub</a></li>
                                    <li><a href="/">Notion</a></li>
                                    <li><a href="/">MS Teams</a></li>
                                </ul>
                            </div>

                            <div className='col-md-3 col-sm-6'>     
                            
                                <h4> Other </h4>
                            
                                <ul className='list-unstyled'>
                                    <li><a href="/">Whales</a></li>
                                    <li><a href="/">The Moon</a></li>
                                    <li><a href="/">Egg</a></li>
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