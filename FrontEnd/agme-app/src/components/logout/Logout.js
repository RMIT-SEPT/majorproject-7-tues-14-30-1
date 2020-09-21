import { Component } from 'react';
import "./Logout.css";


class Logout extends Component {
  
  constructor(props) {
    super(props);
    console.log("ERRORRR");
    localStorage.clear();
    console.log(localStorage.getItem("email"));
    window.location = "/dashboard";
  }
  
} export default Logout