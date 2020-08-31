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
    fetchSearchResults = (updatedPageNo = '', query ) => {

        const pageNumber = updatedPageNo ? `&page=${updatedPageNo}` : '';        
        const searchUrl = `https://pixabay.com/api/?key=12413278-79b713c7e196c7a3defb5330e&q=${query}${pageNumber}`;
        
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
                const resultNotFoundMsg = !res.data.hits.length
                    ? 'There are no more search results. Please try a new search.'
                    : '';
                this.setState({
                    results: res.data.hits,
                    totalResults: res.data.total,
                    message: resultNotFoundMsg,
                    loading: false,
                });
            })
            .catch((error) => {
                if (axios.isCancel(error) || error) {
                    this.setState({
                        loading: false,
                        message: 'Failed to fetch results.Please check network',
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

            <div class="md-form active-pink active-pink-2 mb-3 mt-0">
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
            this.setState({ query, results: {}, totalResults: 0, totalPages: 0, currentPageNo: 0, message: '' } );
        } else {
            this.setState({ query, loading: true, message: '' }, () => {
                this.fetchSearchResults(1, query);
            });
        }
    };

    renderSearchResults = () => {
        const {results} = this.state;
        if (Object.keys(results).length && results.length) {
            return (

            <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>#</th>
                    <th>Business Name</th>
                    <th>Phone</th>
                    <th>Employees</th>
                    <th>Next Available Appointment</th>
                    </tr>
                </thead>

                <tbody>
                    <tr>
                    <td>1</td>
                    <td>Best Haircuts Studio</td>
                    <td>0476 342 563</td>
                    <td>4</td>
                    <td>3:00pm Tuesday</td>
                    </tr>
                    <tr>
                    <td>2</td>
                    <td>Curl Up & Dye</td>
                    <td>0433 555 096</td>
                    <td>2</td>
                    <td>9:00am, Wednesday</td>
                    </tr>
                    <tr>
                    <td>3</td>
                    <td>Shaving The Day</td>
                    <td>0457 398 287</td>
                    <td>9</td>
                    <td>9:00am Monday</td>
                    </tr>
                </tbody>
            </Table>

            );
        }
    };


} export default Search;