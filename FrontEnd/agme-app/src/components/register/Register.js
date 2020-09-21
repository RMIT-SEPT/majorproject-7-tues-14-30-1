import React, { Component } from 'react';
import axios from 'axios';
import "./Register.css";

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

class Register extends Component {

  constructor(props) {
    super(props);

    this.state = {
      firstName: null,
      lastName: null,
      phoneNumber: null,
      email: null,
      password: null,
      password_confirmation: null,
      formErrors: {
        firstName: "",
        lastName: "",
        phoneNumber: "",
        email: "",
        password: "",
        password_confirmation: ""
      }
    };
  }
  
  

  handleSubmit = e => {
    e.preventDefault();
    const { firstName, lastName, phoneNumber, email, password} = this.state;

    
    if (formValid(this.state)) {

      const formData = new FormData()
      formData.append("first_name", firstName)
      formData.append("last_name", lastName)
      formData.append("password", password)
      formData.append("phone", phoneNumber)
      formData.append("email", email)
      
      axios
        .put(
          "http://localhost:7000/api/customer",
          formData
        )
        .then((res) =>{
          console.log(res.data);
          if ((res.data.status) === "success") {
              console.log("registered")
              localStorage.setItem("email", email)
              localStorage.setItem("password", password)
              window.location = "/dashboard";
          }
          else{
            let formErrors = { ...this.state.formErrors }
            formErrors.email = "Email is already in use";
            this.setState({formErrors});
          }

        });
      console.log(`
        --SUBMITTING--
        First Name: ${this.state.firstName}
        Last Name: ${this.state.lastName}
        Phone number: ${this.state.phoneNumber}
        Email: ${this.state.email}
        Password: ${this.state.password}
        Password Confirmation: ${this.state.password_confirmation}
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
      case "firstName":
        formErrors.firstName =
          value.length < 3 ? "Minimum 3 characaters required" : "";
        break;
      case "lastName":
        formErrors.lastName =
          value.length < 3 ? "Minimum 3 characaters required" : "";
        break;
      case "phoneNumber":
        formErrors.phoneNumber =
          value.length < 10 ? "Minimum 10 characaters required" : "";
        break;
      case "email":
        formErrors.email = emailRegex.test(value)
          ? ""
          : "Invalid email address";
        break;
      case "password":
        formErrors.password =
          value.length < 6 ? "Minimum 6 characaters required" : "";
        break;
      case "password_confirmation":
        if (value !== this.state.password)
          formErrors.password_confirmation = "Password much match";
        else
          formErrors.password_confirmation = ""
        break;
      default:
        break;
    }

    this.setState({formErrors, [name]: value}, () => console.log(this.state));
  };

  render() {
    const { formErrors } = this.state;
    return (
      <div className="wrapper">
        <div className="form-wrapper">
          <h1>Create Account</h1>
          <form onSubmit={this.handleSubmit} noValidate>
            <div className="firstName">
              <label htmlFor="firstName">First Name</label>
              <input
                className={formErrors.firstName.length > 0 ? "error" : null}
                placeholder="First Name"
                type="text"
                name="firstName"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.firstName.length > 0 && (
                <span className="errorMessage">{formErrors.firstName}</span>
              )}
            </div>
            <div className="lastName">
              <label htmlFor="lastName">Last Name</label>
              <input
                className={formErrors.lastName.length > 0 ? "error" : null}
                placeholder="Last Name"
                type="text"
                name="lastName"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.lastName.length > 0 && (
                <span className="errorMessage">{formErrors.lastName}</span>
              )}
            </div>
            <div className="phoneNumber">
              <label htmlFor="phoneNumber">Phone Number</label>
              <input
                className={formErrors.phoneNumber.length > 10 ? "error" : null}
                placeholder="Phone Number"
                type="text"
                name="phoneNumber"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.phoneNumber.length > 0 && (
                <span className="errorMessage">{formErrors.phoneNumber}</span>
              )}
            </div>
            <div className="email">
              <label htmlFor="email">Email</label>
              <input
                className={formErrors.email.length > 0 ? "error" : null}
                placeholder="Email"
                type="email"
                name="email"
                noValidate
                onChange={event => {this.handleChange(event);}}
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
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.password.length > 0 && (
                <span className="errorMessage">{formErrors.password}</span>
              )}
            </div>
            <div className="password_confirmation">
              <label htmlFor="password_confirmation">Password Confirmation</label>
              <input
                className={formErrors.password_confirmation.length > 0 ? "error" : null}
                placeholder="Password_confirmation"
                type="password"
                name="password_confirmation"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.password_confirmation.length > 0 && (
                <span className="errorMessage">{formErrors.password_confirmation}</span>
              )}
            </div>
            <div className="createAccount">
              <button type="submit">Create Account</button>
              <small>Already Have an Account?</small>
              <a href="/login">Click here to login</a>
            </div>
          </form>
        </div>
      </div>
    );
  }
  
} export default Register