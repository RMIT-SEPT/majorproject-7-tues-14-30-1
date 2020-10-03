import React, { Component } from 'react';
import axios from 'axios';
import { Button, Table } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal'
import NotificationModal from './NotificationModal'

class BusinessProfile extends Component {

    constructor(props) {
        super(props)
		this.state = {
            business_id: this.props.match.params.id,
            name: '',
            phone_number: '',
            email: '',
            cheapestCost: '',
            employees: {},

            showBookingModal: false,
            showErrorModal: false,
            showBookingConfirmation: false,
            
            selectedEmployee: {
                id: '',
                name: '',
                nextFreeDay: '',
                nextFreeHour: '',
                loading: false
            } 
        
        };

        this.cancel = '';

    }
    
    componentDidMount() {
        this.fetchBusinessData();
        this.fetchEmployeeData();
    }

    // helper method for correctly capitalising business names
    toTitleCase = (phrase) => {
        return phrase
          .toLowerCase()
          .split(' ')
          .map(word => word.charAt(0).toUpperCase() + word.slice(1))
          .join(' ');
      };

    // updates state with business information based on URL of current page
    fetchBusinessData = () => {

        const businessRequest = `http://localhost:7000/api/business?id=${this.state.business_id}`;

        axios.get(businessRequest)
        .then((response) => {
            
            const business = response.data.payload;            
            
            this.setState({
                name: business.name,
                phone_number: business.phone_number,
                email: business.email,
                cheapestCost: business.cheapestCost
            })
        }).catch((error) => {
            console.log('error:')
            console.log(error)
                this.setState({
                    name: 'ERROR: Business does not exist :(',
                    phone_number: '',
                    email: '',
                    cheapestCost: ''
            })
        });

    }

    // uses current business ID to fetch a list of employees and load them into the state.
    fetchEmployeeData = () => {
        
        const employeeRequest = `http://localhost:7000/api/business/getEmployees?business_id=${this.state.business_id}`

        axios.post(employeeRequest)
        .then((response) => {
            this.setState({employees: response.data})
        }).catch((error) => {
            console.log('error fetching employee data:')
            console.log(error)
        });
    
    }

    // renders a table of employees
    renderEmployees = () => {
        
        const results = this.state.employees.payload;
        console.log(results)
                 
        if (results) {

            // map each employee to a table row
            const rows = results.map(row => 
                <tr>
                    <td>{row.first_name} {row.last_name}</td>
                    <td>{row.phone_number}</td>
                    <td>{row.email}</td>
                    <td>{row.cheapest_cost}</td>
                    <td><Button onClick={ () => 
                        this.displayBookingModal(row.employee_ID, row.first_name)
                    }>Book</Button></td>
                </tr>)

            return(

                <Table striped bordered hover>
                    <thead>
                        <tr>
                        <th>Employee</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Cheapest Cost</th>
                        <th>Next Appointment</th>
                        </tr>
                    </thead>

                    <tbody>{rows}</tbody>

                </Table>
            )
        } 

    }

    // updates state when book button is clicked, so we can retrieve the appropriate next booking
    updateSelectedEmployee(employee_ID, employee_name) {

        // store employee information, so we can update the state with it later
        var emp = {...this.state.selectedEmployee}
        emp.id = employee_ID;
        emp.name = employee_name;

        const freeSessionQuery = `http://localhost:7000/api/employee/nextFreeSession?id=${employee_ID}`;

        axios
            .get(freeSessionQuery)
            .then((res) => {

                // store details of next available booking, or set to -1 if no bookings are available
                if ((res.data.status) === "success") {
                    emp.nextFreeDay = res.data.payload[0];
                    emp.nextFreeHour = res.data.payload[1];
                } else {
                    emp.nextFreeDay = -1;
                    emp.nextFreeHour = -1;
                }
                
                // update state with new employee information
                this.setState({ selectedEmployee: emp })
                emp.loading = false;

                console.log('state after recieving next free session data')
                console.log(this.state)
                
            })
            .catch((error) => {
                console.log(error)
            });
            
            
    }

    displayBookingModal(employee_ID, employee_name) {

        let account = JSON.parse(localStorage.getItem("account"))

        if (!account) {
            
            // if user is not logged in, show error message
            this.setState({ showErrorModal: true })
            
        } else {

            // make sure modal does not display until employee has been loaded
            var emp = {...this.state.selectedEmployee}
            emp.loading = true;
            this.setState({ selectedEmployee: emp })
    
            this.updateSelectedEmployee(employee_ID, employee_name)
    
            if (!this.state.selectedEmployee.loading) {
                this.setState({ showBookingModal: true })
            }
        }


    }

    closeBookingModal() {
        
        // reset selected employee data
        var emp = this.state.selectedEmployee;
        emp.id = '';
        emp.name = '';
        emp.nextFreeDay = '';
        emp.nextFreeHour = '';
        
        // update state reset employee data and stop displaying modal
        this.setState({showBookingModal: false, selectedEmployee: emp})
        
        console.log('state on exit of closeModal()')
        console.log(this.state)
    }

    processBooking() {

        const bookingRequest = `http://localhost:7000/api/employee/makeNextBooking`

        let account = JSON.parse(localStorage.getItem("account"))

        // create form using currently logged in user, and selected employee
        const formData = new FormData();

        formData.append("email", account.email);
        formData.append("password", account.password);
        formData.append("employee_id", this.state.selectedEmployee.id);
        formData.append("day", this.state.selectedEmployee.nextFreeDay);
        formData.append("hour", this.state.selectedEmployee.nextFreeHour);

        axios
            .post(bookingRequest,
            formData
            )
            .then((res) =>{
                console.log(res.data);
                if ((res.data.status) === "success") {
                    console.log("booking was successful")
                }
                else {
                    console.log("booking failed")
                }
              })
              .catch((error) => {          
                  
              });
            ;

        this.closeBookingModal();
    }

    renderError() {

        const title = `Error`
        const message = `Sorry, you need to be logged in to make a booking.`

        return(
            <NotificationModal 
                show={this.state.showErrorModal} 
                onClose={() => this.setState({showErrorModal: false})}
                title={title}
                message={message}>
            </NotificationModal>
        )
    }

    renderBookingConfirmation() {
        const title = `Success`
        const message = `Your booking has been processed successfully.`

        return(
            <NotificationModal 
                show={this.state.showBookingConfirmation} 
                onClose={() => this.setState({showBookingConfirmation: false})}
                title={title}
                message={message}>
            </NotificationModal>
        )
    }

    numberToDOTW(day){
        let days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saterday"]
        return days[day];
    }

    timeConverter(time){
        let pmam = "";
        if (time==12){
            pmam="PM"
        }
        else if (time<12){
            pmam="AM"
        }
        else{
            pmam="PM"
            time-=12;
        }
        return time + ":00 " + pmam
    }

    renderBookingModal() {

        const noBookingsAvailable = this.state.selectedEmployee.nextFreeDay === -1
        var message = ""

        if (this.state.selectedEmployee.loading) {
            message = "Loading data."
        } else {

            if (noBookingsAvailable) {
                message = "Sorry, this employee does not have any booking slots available."
            } else {
                message = `By clicking Make Booking, a booking will be reserved with ${this.state.selectedEmployee.name}
                            for ${this.numberToDOTW(this.state.selectedEmployee.nextFreeDay)} at ${this.timeConverter(this.state.selectedEmployee.nextFreeHour)}.`
            }
        }


        return(


            <div>
            <Modal
                show={this.state.showBookingModal}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
              <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                  Confirm Booking
                </Modal.Title>
              </Modal.Header>
              <Modal.Body>
                <h4>Review your appointment details</h4>
                <p>
                {message}
                </p>
              </Modal.Body>
              <Modal.Footer>
              
                    <Button onClick={() => this.closeBookingModal()}>
                        Cancel
                    </Button>
                    {!noBookingsAvailable && !this.state.selectedEmployee.loading &&
                    <Button onClick={() => this.processBooking()}>
                        Make Booking
                    </Button>
                    }
                   
              </Modal.Footer>
            </Modal>
            
            </div>


        )
    }


    render() {

        return(

            <div className="container">
            
            { /* Heading: Business Name */ }
            <h1>{this.toTitleCase(this.state.name)}</h1>
                        
            <h4 className="heading">Employees</h4>

            { /* List of Employees */ }
            {this.renderEmployees()}
            
            { /* Modals */ }
            {this.renderBookingModal()}
            {this.renderError()}
            {this.renderBookingConfirmation()}
        
        </div>
        )
    
    }

} export default BusinessProfile;