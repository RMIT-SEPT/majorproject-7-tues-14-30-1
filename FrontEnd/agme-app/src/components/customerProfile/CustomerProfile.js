import React, { Component } from 'react';
import { Table } from 'react-bootstrap'
import axios from 'axios';



class CustomerProfile extends Component {

    constructor( props ) {
        super( props );
        let customer = JSON.parse(localStorage.getItem("account"));
        this.state={}
		this.state = {
            "email": customer.email,
            "password": customer.password,
            "bookings":null
        };
        
        this.cancel = '';
        this.fetchCustomerHistory();
    }

    fetchCustomerHistory = (query ) => {

        const searchUrl = `http://localhost:7000/api/booking/getByCustomer`;
        
        if (this.cancel) {
            // Cancel the previous request before making a new request
            this.cancel.cancel();
        }

        // Create a new CancelToken
        this.cancel = axios.CancelToken.source();
        const formData = new FormData()
        const {email, password} = this.state;
        formData.append("password", password)
        formData.append("email", email)
        axios
            .post(searchUrl, formData)
    
            .then((res) => {

                console.log(res.data);

                const resultNotFoundMsg = !res.data.payload.length 
                    ? 'No results found. Please try a different query.'
                    : ''

                this.setState({
                    bookings: res.data.payload,
                    loading: false,
                    message: resultNotFoundMsg,
                })

            })
            .catch((error) => {
                if (axios.isCancel(error) || error) {
                    this.setState({
                        loading: false,
                        message: 'Failed to fetch results. Please check network',
                    });
                }
            });
    };

    render() {

        return(
    
        <div className="container">
            
            { /* Heading */ }
            <h1>Hannah's Profile:</h1>
            
            <h4 className="heading">Upcoming Appointments</h4>

            {this.renderBookings()}
        

            <h4 className="heading">Past Appointments</h4>

            <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>#</th>
                    <th>Location</th>
                    <th>Employee</th>
                    <th>Time</th>
                    </tr>
                </thead>

                <tbody>
                    <tr>
                    <td>1</td>
                    <td>Best Haircuts Studio</td>
                    <td>Greg</td>
                    <td>3:00pm, Tuesday</td>
                    </tr>
                    <tr>
                    <td>2</td>
                    <td>Curl Up & Dye</td>
                    <td>Samantha</td>
                    <td>9:00am, Wednesday</td>
                    </tr>
                    <tr>
                    <td>3</td>
                    <td>Shaving The Day</td>
                    <td>Boris</td>
                    <td>9:00am Monday</td>
                    </tr>
                </tbody>
            </Table>
        
        </div>
        )
    
    }

    renderBookings(){
        let {bookings} = this.state;
        let rows = bookings.map(row => 
            <tr>
                <td>{row.booking_id}</td>
                <td>{row.employee_name}</td>
                <td>{row.business_name}</td>
                <td>{row.dateTime}</td>                
            </tr>)

        return (
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>
                            #
                        </th>
                        <th>
                            Employee
                        </th>
                        <th>
                            Location
                        </th>
                        <th>
                            Time
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </Table>
        )
    }

} export default CustomerProfile;