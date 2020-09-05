import React, { Component } from 'react';
import axios from 'axios';

class QueryBusinesses extends Component {


    // constructor() {
        
    //     super();

    //     this.state= {
    //         data: "",
    //         status: "",
    //         statusText: "",
    //         headers: "",
    //         config: ""
    //     }; 

    //     this.fetchBusiness.bind(this);

    //     // this.state= {
    //     //     name: "",
    //     //     phone_number: "",
    //     //     email: "",
    //     //     business_id: "",
    //     //     cheapest_cost: ""
    //     // }; 

    //     // this.onChange = this.onChange.bind(this);
    //     // this.onSubmit = this.onSubmit.bind(this);
    
    // }

    // onChange(e){
    //     this.setState({[e.target.name]: e.target.value});
    // }
    // onSubmit(e){
    //     e.preventDefault();
    //     const foundBusiness = {
    //         name: this.state.name,
    //         phone_number: this.state.phone_number,
    //         email: this.state.email,
    //         business_id:this.state.business_id,
    //         cheapest_cost: this.state.cheapest_cost  
    //     }

    //     console.log(foundBusiness);
    // }


    fetchBusiness = (id, name) => {
    
        const testQuery = "http://localhost:7000/api/business?id=1";

        axios.get(testQuery)
            .then((response) => {
            console.log(response.data);
            console.log(response.status);
            console.log(response.statusText);
            console.log(response.headers);
            console.log(response.config);
            })
    
    }



} export default QueryBusinesses;