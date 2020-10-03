import React, { Component } from 'react';
import { Button, Table } from 'react-bootstrap';
import './AppointmentTable.css'

class BusinessProfile extends Component {

    constructor(props) {
        super(props);

        this.state = {
            employee_ID: '1',
            availabilities: {

                sun: [],
                mon: [],
                tue: [],
                wed: [],
                thu: [],
                fri: [],
                sat: []

            }

        }
    }

    getAvailabilities() {

        // send request to API
        // check that data was successfully returned
        // parse boolean array into state arrays

    }

    timeConverter(time){
        let pmam = "";
        if (time>12){
            pmam="PM"
            time-=12;
        }
        else{
            pmam="AM"
        }
        return time + ":00 " + pmam
    }

    renderTable = () => {

        // ensure availability data exists 
        // create table s.t. (x, y) = (time, day)
        // populate table with availability data

        let days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]
        const columnHeaders = days.map(day => <th>{day}</th> )

        let times = [...Array(24).keys()];
        const rows = times.map(time => 
                <tr>
                    <td>
                        <b>{this.timeConverter(time)}</b>
                    </td>

                    <td>
                        Available
                    </td>
                    <td>
                        Not Available
                    </td>
                    <td>
                        Booked
                    </td>
                    <td>
                        Available
                    </td>
                    <td>
                        Booked
                    </td>
                    <td>
                        Not Available
                    </td>
                    <td>
                        Not Available
                    </td>
                    
                </tr>
            )
                 
        if (true) {

            return(

                <Table striped bordered hover>
                    <thead>
                        <tr>
                        <th>Time</th>
                        {columnHeaders}
                        </tr>
                    </thead>

                    <tbody>{rows}</tbody>

                </Table>
            )
        } 

    }


    render() {

        return(

            <div>
                {this.renderTable()}
            </div>

        )

    }

} export default BusinessProfile;