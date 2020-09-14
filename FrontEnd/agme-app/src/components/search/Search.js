import React, { Component } from 'react';
import axios from 'axios';
import Loader from './loader.gif'
import './Search.css'
import { Table } from 'react-bootstrap'



class Search extends Component {

    constructor( props ) {
		super( props );
		this.state = {
			query: '',
            results: {},
            loading: false,
            message: '',
        };
        
        this.cancel = '';

    }

    // Fetch the search results and update the state with the result.
    fetchSearchResults = (query ) => {

        const searchUrl = `http://localhost:7000/api/business/searchBusiness?q=${query}`;
        
        if (this.cancel) {
            // Cancel the previous request before making a new request
            this.cancel.cancel();
        }

        // Create a new CancelToken
        this.cancel = axios.CancelToken.source();
        
        axios
            .get(searchUrl, {
                cancelToken: this.cancel.token,
            })
    
            .then((res) => {

                console.log(res.data);

                const resultNotFoundMsg = !res.data.payload.length 
                    ? 'No results found. Please try a different query.'
                    : ''

                this.setState({
                    results: res.data.payload,
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

        const { query, loading, message } = this.state;
        
        return (

            <div className="container">
            
            { /* Heading */ }
            <h2 className="heading">AGME: Search Businesses</h2>
            
            { /* Search Input */ }

            <div className="md-form active-pink active-pink-2 mb-3 mt-0">
            <input
                    type="text"
                    value={query}
                    id="search-input"
                    placeholder="Search..."
                    onChange={this.handleOnInputChange}
                />
            </div>

            {/*Error Message*/}
            { message && <p className="message">{message}</p> }
            
            {/*Loader*/}
            <img  src={Loader} className={`search-loading ${loading ? 'show' : 'hide' }`}  alt="loader"/>
            
            {/*Result*/}
            { this.renderSearchResults() }
            
            
        </div>
        )

    }

    handleOnInputChange = (event) => {
        const query = event.target.value;
        
        if ( ! query ) {
            this.setState({ query, results: {}, message: '' } );
        } else {
            this.setState({ query, loading: true, message: '' }, () => {
                this.fetchSearchResults(query);
            });
        }
    };

    renderSearchResults = () => {
        
        const {results} = this.state;
        console.log(results);       

        
        if (Object.keys(results).length && results.length) {

            
            const rows = results.map(row => 
                <tr>
                    <td>{row.name}</td>
                    <td>{row.phone_number}</td>
                    <td>{row.email}</td>
                    <td>{row.business_id}</td>
                    <td>{row.cheapest_cost}</td>
                    
                </tr>)

            return (
                
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
                <tbody>{rows}</tbody>

            </Table>

            );
        }
    };


} export default Search;
