import React, { Component } from 'react';
import { Table, Button } from 'react-bootstrap'
import axios from 'axios';



class CustomerProfile extends Component {

    componentDidMount(){
        this.fetchCustomerHistory();
    }

    constructor( props ) {
        super( props );
        let customer = JSON.parse(localStorage.getItem("account"));
		this.state = {
            "email": customer.email,
            "password": customer.password,
            "account": customer,
            "bookings":null
        };
        
    }

    fetchCustomerHistory = (query ) => {

        const searchUrl = `http://localhost:7000/api/booking/getByCustomer`;
        console.log("HERE");
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
                let bookings=res.data.payload;
                let oldBookings=[]
                let newBookings=[]
                console.log(bookings.length)
                for (var i=0; i<bookings.length;i++){
                    if (this.isOld(bookings[i])){
                        oldBookings.push(bookings[i]);
                    }
                    else{
                        newBookings.push(bookings[i]);
                    }
                }
                console.log("Hello")
                console.log(newBookings)
                console.log(oldBookings)
                this.setState({
                    bookings: {oldBookings: oldBookings, newBookings: newBookings},
                    loading: false,
                })
                console.log(this.state);

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
            <h1>{this.state.account.fName}'s Profile:</h1>
            
            <h4 className="heading">Upcoming Appointments</h4>

            {this.renderNewBookings()}

            <h4 className="heading">Past Appointments</h4>
            
            {this.renderOldBookings()}
            
        
        </div>
        )
    
    }

    renderOldBookings(){
        if (this.state.bookings){
            console.log(this.state)
            let {bookings} = this.state;
            console.log(bookings)
            let oldRows = bookings.oldBookings.map(row => 
                <tr>
                    <td>{row.booking_id}</td>
                    <td>{row.employee_name}</td>
                    <td>{row.business_name}</td>
                    <td>{new Date(row.dateTime).toString()}</td>                
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
                        {oldRows}
                    </tbody>
                </Table>
            )
        }
        
    }
    renderNewBookings(){
        if (this.state.bookings){
            console.log(this.state)
            let {bookings} = this.state;
            let newRows = bookings.newBookings.map(row => 
                <tr>
                    <td>{row.booking_id}</td>
                    <td>{row.employee_name}</td>
                    <td>{row.business_name}</td>
                    <td>{new Date(row.dateTime).toString()}</td>
                    <td><Button variant="danger">Cancel</Button></td>            
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
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {newRows}
                    </tbody>
                </Table>
            )
        }
        
    }

    isOld(booking){
        if (booking.dateTime<new Date()){
            return true; 
        }
        else{
            return false;
        }
    }

} export default CustomerProfile;