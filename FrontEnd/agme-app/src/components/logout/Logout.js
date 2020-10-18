import { Component } from 'react';
import "./Logout.css";


class Logout extends Component {
  
  constructor(props) {
    super(props);
    console.log("ERRORRR");
    localStorage.clear();  //clears in the logged in account and logs out
    console.log(localStorage.getItem("email"));
    window.location = "/dashboard"; //directs to the home page
  }
  
} export default Logout