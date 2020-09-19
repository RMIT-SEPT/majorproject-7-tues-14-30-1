import React, { Component } from 'react';
import axios from 'axios';
import { Table } from 'react-bootstrap';

class BusinessProfile extends Component {

    constructor(props) {
        super(props)
		this.state = {
            business_id: this.props.match.params.id,
            name: '',
            phone_number: '',
            email: '',
            cheapestCost: '',
            employees: {}
        };

        this.cancel = '';
        console.log(this.state)
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
            console.log('response.data:')
            console.log(response.data)
            const business = response.data.payload;            
            console.log('business:')
            console.log(business)

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
            console.log('response.data:')
            console.log(response.data)
            this.setState({employees: response.data})
        }).catch((error) => {
            console.log('error:')
            console.log(error)
        });
    
    }

    renderEmployees = () => {
        
        const results = this.state.employees.payload;
        console.log('results:');       
        console.log(results);   

        if (results) {
            console.log('results query test'); 
            console.log(results[0]); 

            const rows = results.map(row => 
                <tr>
                    <td>{row.first_name} {row.last_name}</td>
                    <td>{row.phone_number}</td>
                    <td>{row.email}</td>
                    <td>{row.cheapest_cost}</td>
                    <td>Book Appt</td>
                </tr>)

            return(
                
                <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>Employee</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>Cheapest Cost</th>
                    <th>Book</th>
                    </tr>
                </thead>
                <tbody>{rows}</tbody>

                </Table>
            )
        }

 
        

    }


    render() {

        return(

            <div className="container">
            
            { /* Heading: Business Name */ }
            <h1>{this.toTitleCase(this.state.name)}</h1>
            {console.log("this.state.name")}
            {console.log(this.state.name)}
            
            
            <h4 className="heading">Employees</h4>

            { /* List of Employees */ }
            {this.renderEmployees()}
        
        
        </div>
        )
    
    }

} export default BusinessProfile;