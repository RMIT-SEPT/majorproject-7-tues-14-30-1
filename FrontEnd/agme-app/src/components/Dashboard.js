import React, { Component } from 'react'
import SearchButton from './search/SearchButton'

class Dashboard extends Component {
    render() {
        return (

            
            <div className="container">
                <div className="row">
                    <div className="col-md-12">
                        <h3 className="display-4 text-center">AGME: Appointments Made Easy</h3>
                        <br />
                        <SearchButton />
                        <br />
                        <hr />
                    </div>
                </div>
            </div>
        


        )
    }
}

export default Dashboard;