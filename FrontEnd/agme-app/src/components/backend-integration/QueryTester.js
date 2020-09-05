import React, { Component } from 'react';
import axios from 'axios';




class QueryTester extends Component {

    state = {
        business: []
    }
    

    componentDidMount() {
        

        const testQuery = "http://192.168.0.72:7000/api/employee/";

    //     axios.get(`https://jsonplaceholder.typicode.com/users`)
    //   .then(res => {
    //     const persons = res.data;
    //     this.setState({ persons });
    //   })

        axios.get(testQuery)
        .then((response) => {
            const business = response.data;
            this.setState({business})
        })
    }

    render() {

        console.log('This is a log')

        // this.componentDidMount();

        return(

            // <h1> TEST </h1>

            <ul>
                hi
            </ul>
            
    
        // <div className="container">
            
        //     { /* Heading */ }
        //     <h1>Hannah's Profile:</h1>
            
        //     <h4 className="heading">Upcoming Appointments</h4>

        //     <Table striped bordered hover>
        //         <thead>
        //             <tr>
        //             <th>Name</th>
        //             <th>Phone Number</th>
        //             <th>Email</th>
        //             <th>ID</th>
        //             <th>Cheapest Cost</th>
        //             </tr>
        //         </thead>

        //         <tbody>
        //             <tr>
        //             <td>name</td>
        //             <td>phone</td>
        //             <td>email</td>
        //             <td>id</td>
        //             <td>cost</td>
        //             </tr>
        //         </tbody>
        //     </Table>
            
        // </div>
        )
    
    }

} export default QueryTester;