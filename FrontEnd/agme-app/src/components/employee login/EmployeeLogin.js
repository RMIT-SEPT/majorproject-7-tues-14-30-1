import React, { Component } from 'react';
import "./EmployeeLogin.css";
import axios from 'axios';

const emailRegex = RegExp(
  /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
);

const formValid = ({ formErrors, ...rest }) => {
  let valid = true;

  // validate form errors being empty
  Object.values(formErrors).forEach(val => {
    val.length > 0 && (valid = false);
  });

  // validate the form was filled out
  Object.values(rest).forEach(val => {
    val === null && (valid = false);
  });

  return valid;
};



class EmployeeLogin extends Component {

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
  
  handleSubmit = e => {
    e.preventDefault();


    if (formValid(this.state)) {

      const formData = new FormData()
      const {email, password} = this.state;
      formData.append("password", password)
      formData.append("email", email)
    
      axios
        .post(
          "http://localhost:7000/api/employee/login",
          formData
        )
        .then((res) =>{
          console.log(res.data.payload);
          if ((res.data.status) === "success") {

              localStorage.setItem("account", JSON.stringify(res.data.payload))
              window.location = "/dashboard";
          }
          else{
            let formErrors = { ...this.state.formErrors }
            formErrors.password = "Wrong email or password";
            console.log("Wrong email or password")
            this.setState({formErrors});
          }

        });

      console.log(`
        --SUBMITTING--
        Email: ${email}
        Password: ${password}
      `);
    } else {
      console.error("FORM INVALID - DISPLAY ERROR MESSAGE");
    }
  };

  handleChange = e => {
    e.preventDefault();
    const { name, value } = e.target;
    let formErrors = { ...this.state.formErrors };

    switch (name) {
      case "email":
        formErrors.email = emailRegex.test(value)
          ? ""
          : "Invalid email address";
        break;
      case "password":
        formErrors.password =
          value.length < 6 ? "Minimum 6 characaters required" : "";
        break;
      default:
        break;
    }

    this.setState({ formErrors, [name]: value }, () => console.log(this.state));
  };

  render() {
    const { formErrors } = this.state;

    return (
      <div className="wrapper">
        <div className="form-wrapper">
          <h1>Employee Login</h1>
          <form onSubmit={this.handleSubmit} noValidate>
            <div className="email">
              <label htmlFor="email">Email</label>
              <input
                className={formErrors.email.length > 0 ? "error" : null}
                placeholder="Email"
                type="email"
                name="email"
                noValidate
                onChange={this.handleChange}
              />
              {formErrors.email.length > 0 && (
                <span className="errorMessage">{formErrors.email}</span>
              )}
            </div>
            <div className="password">
              <label htmlFor="password">Password</label>
              <input
                className={formErrors.password.length > 0 ? "error" : null}
                placeholder="Password"
                type="password"
                name="password"
                noValidate
                onChange={this.handleChange}
              />
              {formErrors.password.length > 0 && (
                <span className="errorMessage">{formErrors.password}</span>
              )}
            </div>
            <div className="login">
              <button type="submit">Login</button>
              <small>Sign Up?</small>
            </div>
          </form>
        </div>
      </div>
    );
  }
  
} export default EmployeeLogin