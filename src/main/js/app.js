'use strict';
import {OrderList, CreateOrderDialog} from './orderList';
import axios from 'axios';
import Cookies from 'js-cookie';


const React = require('react');
const ReactDOM = require('react-dom');

async function createOrder(userId, order) {
    console.log("createOrderFunc")
    await axios.post(`/fixer/api/user/${userId}`, order, {
        headers: {
            Authorization: "Bearer " + Cookies.get('access_token'),
            'X-CSRF-TOKEN': Cookies.get('csrf_token')
        }
    }).then(response =>{
        console.log('response.data')
        console.log(response.data)
    })
}

class App extends React.Component {
    render() {
        return (<div>
                <CreateOrderDialog createFunc={createOrder}/>
                <OrderList />
            </div>
        )
    }
}


ReactDOM.render(
    <App />,
    document.getElementById('react')
)
