import React from 'react';
import { Link } from 'react-router-dom';

const EmployeeSearchButton=() => {
    return (

        <React.Fragment>

        <Link to="/employeeSearch" className="btn btn-lg btn-info">Employee Search</Link>
        
        </React.Fragment>

    )
}

export default SearchButton;