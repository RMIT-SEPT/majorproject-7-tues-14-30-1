import React, { Component } from 'react';
import {Button} from 'react-bootstrap';

class HeaderButton extends Component {

    render() {
        return(
            <div className="box">
                <Button {...this.props} variant="info">{this.props.label}</Button>
            </div>
        )
    }


} export default HeaderButton;