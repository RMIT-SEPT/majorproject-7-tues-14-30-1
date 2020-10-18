import React, { Component } from 'react';
import axios from 'axios';
import "./EmployeeManager.css";

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

class EmployeeManager extends Component {

  constructor(props) {
    super(props);

    let account = JSON.parse(localStorage.getItem("account"))

    this.state = {
      business_id: null,
      employee_id: null,
      type: null,
      firstName: null,
      lastName: null,
      phoneNumber: null,
      email: null,
      password: null,
      account: account,  //pulling from the account that is logged in, instead of having the user input admin details again.
      formErrors: {
        business_id: "",
        employee_id: "",
        type: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        email: "",
        password: "",
      }
    };
  }
  
  

  handleSubmit = e => {
    e.preventDefault();
    const {business_id, employee_id, type, firstName, lastName, phoneNumber, email, password} = this.state;

    
    if (formValid(this.state)) {

      const formData = new FormData()
      formData.append("business_id", business_id)
      formData.append("employee_id", employee_id)
      formData.append("type", type)
      formData.append("first_name", firstName)
      formData.append("last_name", lastName)
      formData.append("password", password)
      formData.append("phone", phoneNumber)
      formData.append("email", email)
      formData.append("loginemail", this.state.account.email)
      formData.append("loginpassword", this.state.account.password)
      
      axios
        .put(
          "http://localhost:7000/api/employee/",   // calling the API
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

      //for error checking
      console.log(`     
        --SUBMITTING--  
        Business_id: ${this.state.business_id}
        Employee_id: ${this.state.employee_id}
        Type: ${this.state.type}
        First Name: ${this.state.firstName}
        Last Name: ${this.state.lastName}
        Phone number: ${this.state.phoneNumber}
        Email: ${this.state.email}
        Password: ${this.state.password}
      `);
    } else {
      console.error("FORM INVALID - DISPLAY ERROR MESSAGE");
    }
  };

  //handleChange to check for valid data inputs in rego form
  handleChange = e => {
    e.preventDefault();
    const { name, value } = e.target;
    let formErrors = { ...this.state.formErrors };

    switch (name) {
      case "business_id":
        formErrors.business_id =
          value.length < 1 ? "Minimum 1 characater required" : "";
        break;
      case "employee_id":
        formErrors.employee_id =
          value.length < 1 ? "Minimum 1 characater required" : "";
        break;
      case "type":
        formErrors.type =
          value.length < 1 ? "Minimum 1 characater required" : "";
        break;
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
          <h1>Add Employee</h1>
          <form onSubmit={this.handleSubmit} noValidate>
            
            <div className="business_id">
              <label htmlFor="business_id">Business ID</label>
              <input
                className={formErrors.business_id.length > 0 ? "error" : null}
                placeholder="Business ID"
                type="text"
                name="business_id"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.business_id.length > 0 && (
                <span className="errorMessage">{formErrors.business_id}</span>
              )}
            </div>
            <div className="employee_id">
              <label htmlFor="employee_id">Employee ID</label>
              <input
                className={formErrors.employee_id.length > 0 ? "error" : null}
                placeholder="Employee ID"
                type="text"
                name="employee_id"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.employee_id.length > 0 && (
                <span className="errorMessage">{formErrors.employee_id}</span>
              )}
            </div>
            <div className="type">
              <label htmlFor="type">Type</label>
              <input
                className={formErrors.employee_id.length > 0 ? "error" : null}
                placeholder="Type"
                type="text"
                name="type"
                noValidate
                onChange={event => {this.handleChange(event);}}
              />
              {formErrors.type.length > 0 && (
                <span className="errorMessage">{formErrors.type}</span>
              )}
            </div>
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
            
            <div className="addEmployee">
              <button type="submit">Add Employee</button>
              <small>Want to login?</small>
              <a href="/login">Click here to login</a>
            </div>
          </form>
        </div>
      </div>
    );
  }
  
} export default EmployeeManager