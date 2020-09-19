import React, { Component } from 'react';
import axios from 'axios';

class BusinessProfile extends Component {

    constructor(props) {
        super(props)
		this.state = {
            business_id: this.props.match.params.id,
            name: '',
            phone_number: '',
            email: '',
            cheapestCost: ''
        };

        console.log(this.state)
    }

    componentDidMount() {
        
        const request = `http://localhost:7000/api/business?id=${this.state.business_id}`;
        

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
        }).catch((error) => {
                this.setState({
                    name: 'ERROR: Business does not exist :(',
                    phone_number: '',
                    email: '',
                    cheapestCost: ''
            })
        });
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