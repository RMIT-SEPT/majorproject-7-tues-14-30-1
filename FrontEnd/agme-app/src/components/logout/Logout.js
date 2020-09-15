import React, { Component } from 'react';
import "./Logout.css";


class Logout extends Component {
  
  constructor(props) {
    super(props);
    this.state = {
      email: null,
      password: null,
      formErrors: {
        email: "",
        password: ""
      }
    };
  }

  render() {
    console.log("ERRORRR");
    localStorage.clear();
    console.log(localStorage.getItem("email"))
    return ("hello")
  }
  
} export default Logout