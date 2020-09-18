import React, { Component } from 'react';
import axios from 'axios';

class BusinessProfile extends Component {

    constructor() {
        super()
		this.state = {
            business_id: 1,
            name: '',
            phone_number: '',
            email: '',
            cheapestCost: ''
        };
    }

    componentDidMount() {
        
        const request = `http://localhost:7000/api/business?id=1`;
        

        axios.get(request)
        .then((response) => {
            console.log(response.data)
            const business = response.data.payload;            
            console.log(business)

            this.setState({
                name: business.name,
                phone_number: business.phone_number,
                email: business.email,
                cheapestCost: business.cheapestCost
            })
        })
    }


    render() {

        return(
            <div>
                <div>This is a business, yo.</div>
                <div>Name: {this.state.name}</div>
            </div>
        )
    
    }

} export default BusinessProfile;