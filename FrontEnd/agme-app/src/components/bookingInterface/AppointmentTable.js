import React, { Component } from 'react';
import { Button, Table } from 'react-bootstrap';
import './AppointmentTable.css';
import axios from 'axios';

class BusinessProfile extends Component {

    constructor(props) {
        super(props);

        this.state = {
            employee_ID: '1',
            employee_fetched: false,
            employee: {},
            
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

    componentDidMount() {
        if (!this.state.employee_fetched) {
            this.fetchEmployeeData();
        }
    }

    fetchEmployeeData() {

        // const employeeRequest = `http://localhost:7000/api/employee/get?id=${this.state.employee_ID}`
        const employeeRequest = `http://localhost:7000/api/employee/get?id=1`

        axios.post(employeeRequest)
        .then((response) => {
            
            const emp = response.data.payload;
            console.log(emp);
            this.setState({
                employee: emp,
                employee_fetched: true
            })

            this.checkAvailability(0, 0);

        }).catch((error) => {
            
            console.log('error:');
            console.log(error);

            this.setState({
                employee_fetched: false
            })

        });

        // send request to API
        // check that data was successfully returned
        // parse boolean array into state arrays

    }

    timeConverter(time) {
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

    checkAvailability(day, time) {

        var availability = '';

        const emp = this.state.employee;
        const availabilities = emp.working;
        const status = availabilities[day][time];

        console.log("status:");
        console.log(availabilities[day][time]);

        // var arr = []
        // for (var key in availabilities) {
        //     arr.push(key)
        // }

        // console.log("converted");
        // console.log(arr);


        if (status === 2) {

            availability = 'Available';

        } else if (status === 1) {

            availability = 'Booked';

        } else if (status === 0) {
            availability = 'Not Available';
        } else {
            availability = 'Error'
        }

        return availability

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

                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(0, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(1, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(2, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(3, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(4, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(5, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                        {this.state.employee_fetched ? 
                            <td>
                                {this.checkAvailability(6, time)}
                            </td>
                        :
                            <td>
                                Loading Data
                            </td>
                        }
                                            
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