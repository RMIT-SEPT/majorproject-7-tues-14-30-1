import React, { Component } from 'react';
import { Table } from 'react-bootstrap'



class CustomerProfile extends Component {


    render() {

        return(
    
        <div className="container">
            
            { /* Heading */ }
            <h1>Hannah's Profile:</h1>
            
            <h4 className="heading">Upcoming Appointments</h4>

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

} export default CustomerProfile;