import React, { Component } from 'react';
import {Button} from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal'

class NotificationModal extends Component {

    render() {
        return(
            <Modal {...this.props} >
                
            <Modal.Header closeButton>
                <Modal.Title>{this.props.title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{this.props.message}</Modal.Body>
            
            <Modal.Footer>
                <Button variant="secondary" onClick={this.props.onClose}>
                Close
                </Button>
            </Modal.Footer>
            </Modal>
        )
    }

} export default NotificationModal;