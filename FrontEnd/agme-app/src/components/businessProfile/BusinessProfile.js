import React, { Component } from 'react';
import axios from 'axios';
import { Button, Table } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal'

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
            showModal: false,
            
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

    toTitleCase = (phrase) => {
        return phrase
          .toLowerCase()
          .split(' ')
          .map(word => word.charAt(0).toUpperCase() + word.slice(1))
          .join(' ');
      };

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

    renderEmployees = () => {
        
        const results = this.state.employees.payload;
                 

        if (results) {

            const rows = results.map(row => 
                <tr>
                    <td>{row.first_name} {row.last_name}</td>
                    <td>{row.phone_number}</td>
                    <td>{row.email}</td>
                    <td>{row.cheapest_cost}</td>
                    <td><Button onClick={ () => 
                        this.displayModal(row.employee_ID, row.first_name)
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

    updateSelectedEmployee(employee_ID, employee_name) {

        var emp = {...this.state.selectedEmployee}
        emp.id = employee_ID;
        emp.name = employee_name;

        const freeSessionQuery = `http://localhost:7000/api/employee/nextFreeSession?id=${employee_ID}`;

        axios
            .get(freeSessionQuery)
            .then((res) => {

                if ((res.data.status) === "success") {
                    emp.nextFreeDay = res.data.payload[0];
                    emp.nextFreeHour = res.data.payload[1];
                } else {
                    emp.nextFreeDay = -1;
                    emp.nextFreeHour = -1;
                }
                
                this.setState({ selectedEmployee: emp })
                emp.loading = false;

                console.log('state after recieving next free session data')
                console.log(this.state)
                
            })
            .catch((error) => {
                console.log(error)
            });
            
            
    }

    displayModal(employee_ID, employee_name) {

        var emp = {...this.state.selectedEmployee}
        emp.loading = true;
        this.setState({ selectedEmployee: emp })

        this.updateSelectedEmployee(employee_ID, employee_name)

        if (!this.state.selectedEmployee.loading) {
            this.setState({ showModal: true })
        }

    }

    closeModal() {
        
        // reset selected employee data
        var emp = this.state.selectedEmployee;
        emp.id = '';
        emp.name = '';
        emp.nextFreeDay = '';
        emp.nextFreeHour = '';
        
        this.setState({showModal: false, selectedEmployee: emp})
        
        console.log('state on exit of closeModal()')
        console.log(this.state)
    }

    processBooking() {

        const bookingRequest = `http://localhost:7000/api/employee/makeNextBooking`

        let account = JSON.parse(localStorage.getItem("account"))
        if (!account) {
            this.closeModal();
            return
        }

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

        this.closeModal();
    }

    renderModal() {

        const noBookingsAvailable = this.state.selectedEmployee.nextFreeDay === -1
        var message = ""

        if (this.state.selectedEmployee.loading) {
            message = "Loading data."
        } else {

            if (noBookingsAvailable) {
                message = "Sorry, this employee does not have any booking slots available."
            } else {
                message = `By clicking Make Booking, a booking will be reserved with ${this.state.selectedEmployee.name}
                            for ${this.state.selectedEmployee.nextFreeDay} at ${this.state.selectedEmployee.nextFreeHour}.`
            }
        }


        return(


            <div>
            <Modal
                show={this.state.showModal}
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
              
                    <Button onClick={() => this.closeModal()}>
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
            
            
            { /* Modal */ }
            {this.renderModal()}
        
        </div>
        )
    
    }

} export default BusinessProfile;