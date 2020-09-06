import React, { Component } from 'react';
import axios from 'axios';
import { Table } from 'react-bootstrap'




class QueryTester extends Component {

    state = {
        business: []
    }
    

    componentDidMount() {
        

        const testQuery = "http://localhost:7000/api/business?id=1";

        axios.get(testQuery)
        .then((response) => {
            console.log(response.data)
            const business = response.data;
            this.setState({business})
        })
    }

    render() {

        return(
    
        <div className="container">
            
            { /* Heading */ }
            <h1>Hannah's Profile:</h1>
            
            <h4 className="heading">Upcoming Appointments</h4>

            <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>Name</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>ID</th>
                    <th>Cheapest Cost</th>
                    </tr>
                </thead>

                <tbody>
                    <tr>
                    <td>{ this.state.business.name }</td>
                    <td>{ this.state.business.phone_number }</td>
                    <td>{ this.state.business.email }</td>
                    <td>{ this.state.business.business_id }</td>
                    <td>{ this.state.business.cheapestCost }</td>
                    </tr>
                </tbody>
            </Table>
            
        </div>
        )
    
    }

} export default QueryTester;