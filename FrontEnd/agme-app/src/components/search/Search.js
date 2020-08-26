import React, { Component } from 'react';
import axios from 'axios';
import Loader from './loader.gif'
import './Search.css'
import PageNavigation from '../pageNavigation/PageNavigation'

class Search extends Component {

    constructor( props ) {
		super( props );
		this.state = {
			query: '',
            results: {},
            loading: false,
            message: '',
            totalResults: 0,
    		totalPages: 0,
	    	currentPageNo: 0,
        };
        
        this.cancel = '';

    }

    /**
     * Fetch the search results and update the state with the result.
     *
     * @param {int} updatedPageNo Updated Page No.
     * @param {String} query Search Query.
     *
     */

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
                const total = res.data.total;
		        const totalPagesCount = this.getPagesCount( total, 20 );
                const resultNotFoundMsg = !res.data.hits.length
                    ? 'There are no more search results. Please try a new search.'
                    : '';
                this.setState({
                    results: res.data.hits,
                    totalResults: res.data.total,
			        currentPageNo: updatedPageNo,
			        totalPages: totalPagesCount,
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

        const { query, loading, message, currentPageNo, totalPages } = this.state;
        
        // showPrevLink will be false, when on the 1st page, hence Prev link be shown on 1st page.
        const showPrevLink = 1 < currentPageNo;
        
        // showNextLink will be false, when on the last page, hence Next link wont be shown last page.
        const showNextLink = totalPages > currentPageNo;

        return (

            <div className="container">
            
            { /* Heading */ }
            <h2 className="heading">AGME: Search Businesses</h2>
            
            { /* Search Input */ }
            <label className="search-label" htmlFor="search-input">
                <input
                    type="text"
                    value={query}
                    id="search-input"
                    placeholder="Search..."
                    onChange={this.handleOnInputChange}
                />
                <i className="fa fa-search search-icon"/>
            </label>

            {/*Error Message*/}
            { message && <p className="message">{message}</p> }
            
            {/*Loader*/}
            <img  src={Loader} className={`search-loading ${loading ? 'show' : 'hide' }`}  alt="loader"/>   

            {/*Navigation Top*/}
            <PageNavigation
            loading={loading}
            showPrevLink={showPrevLink}
            showNextLink={showNextLink}
            handlePrevClick={() => this.handlePageClick('prev')}
            handleNextClick={() => this.handlePageClick('next')}
            />
            
            {/*Result*/}
            { this.renderSearchResults() }

            {/*Navigation Bottom*/}
            <PageNavigation
            loading={loading}
            showPrevLink={showPrevLink}
            showNextLink={showNextLink}
            handlePrevClick={() => this.handlePageClick('prev')}
            handleNextClick={() => this.handlePageClick('next')}
            />
            
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
                <div className="results-container">
                    {results.map((result) => {
                        return (
                            <a key={result.id} href={result.previewURL} className="result-items">
                                <h6 className="image-username">{result.user}</h6>
                                <div className="image-wrapper">
                                    <img className="image" src={result.previewURL} alt={result.user}/>
                                </div>
                            </a>
                        );
                    })}
                </div>
            );
        }
    };

    /**
     * Fetch results according to the prev or next page requests.
     *
     * @param {String} type 'prev' or 'next'
     */
    handlePageClick = (type) => {
        
        const updatedPageNo =
                'prev' === type
                    ? this.state.currentPageNo - 1
                    : this.state.currentPageNo + 1;
        if (!this.state.loading) {
            this.setState({ loading: true, message: '' }, () => {
                // Fetch previous 20 Results
                this.fetchSearchResults(updatedPageNo, this.state.query);
            });
        }

        return false;
    };


    /**
     * Get the Total Pages count.
     *
     * @param total
     * @param denominator Count of results per page
     * @return {number}
     */
    getPagesCount = (total, denominator) => {
        const divisible = total % denominator === 0;
        const valueToBeAdded = divisible ? 0 : 1;
        return Math.floor(total / denominator) + valueToBeAdded;
    };

} export default Search;