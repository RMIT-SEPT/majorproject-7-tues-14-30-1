import React, { Component } from 'react';
import { Table, Button } from 'react-bootstrap'
import axios from 'axios';
import Popup from 'react-popup';

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

                let bookings=res.data.payload;
                let oldBookings=[]
                let newBookings=[]
                for (var i=0; i<bookings.length;i++){
                    if (this.isOld(bookings[i])){
                        oldBookings.push(bookings[i]);
                    }
                    else{
                        newBookings.push(bookings[i]);
                    }
                }
                this.setState({
                    bookings: {oldBookings: oldBookings, newBookings: newBookings},
                    loading: false,
                })

            })
            .catch((error) => {
                if (axios.isCancel(error) || error) {
                    this.setState({
                        loading: false,
                        message: 'Failed to fetch bookings. Please check network',
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
            let {bookings} = this.state;
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
    handleCancelButton = (e) =>{
        let booking_id = e.target.id
        this.cancelBooking(booking_id)
    };

    cancelBooking(booking_id){
        let {account} = this.state;
        console.log(account)
        let {email, password} = account
        const formData = new FormData()
        formData.append("email",email);
        formData.append("password",password);
        formData.append("booking_id",booking_id)
        const cancelURL = "http://localhost:7000/api/booking/cancel"
        axios
            .post(cancelURL, formData)
    
            .then((res) => {
                if (res.data.status==="success"){
                    this.fetchCustomerHistory();
                }
                else{
                    Popup.alert(res.data.message)
                }
                console.log(res.data)
                
            })
            .catch((error) => {
                if (axios.isCancel(error) || error) {
                    this.setState({
                        loading: false,
                        message: 'Failed to fetch bookings. Please check network',
                    });
                }
            });
    }

    renderNewBookings(){
        if (this.state.bookings){
            let {bookings} = this.state;
            let newRows = bookings.newBookings.map(row => 
                <tr>
                    <td>{row.booking_id}</td>
                    <td>{row.employee_name}</td>
                    <td>{row.business_name}</td>
                    <td>{new Date(row.dateTime).toString()}</td>
                    <td><Button variant="danger" onClick={this.handleCancelButton} id={row.booking_id}>Cancel</Button></td>            
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