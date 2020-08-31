import React from 'react';
import { Link } from 'react-router-dom';

const SearchButton=() => {
    return (

        <React.Fragment>

        <Link to="/search" className="btn btn-lg btn-info">Search</Link>
        
        </React.Fragment>

    )
}

export default SearchButton;