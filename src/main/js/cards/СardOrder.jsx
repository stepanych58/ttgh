import React, {useState} from "react";
import '../App.css';
import axios from "axios";
import Cookies from "js-cookie";

async function getOrders() {
    const url = `/fixer/api/order/all`;
    const res = await axios.get(url, {
        headers: {
            Authorization: `Bearer ${Cookies.get('access_token')}`,
            'X-CSRF-TOKEN': Cookies.get('csrf_token')
        }
    });
    return res.data;
}

function СardOrder() {
    const [orderList, setOrderList] = useState([])
    getOrders().then(response => {
        var res = response.map(order => {
            return <div className="order__content">
                <p>{order.parameters[0].value}</p>
                <div className="status__order">
                    <p>{order.status}</p>
                </div>
            </div>
        })
        setOrderList(res)
    })

    return (
        <div className="wrapper">
            <div className="wrapper__content">
                <div className="header__container">
                    <div className="header__logo">
                    </div>
                </div>
                <div className="content">
                    <div>
                        <div>
                            <h1 className="zgl__login--card--reg">Мои заказы</h1>
                            <div className="bl__registr">
                                <p>текущие заказы и статус заказа</p>
                            </div>
                        </div>
                        <div className="order__block">
                            {orderList}
                        </div>
                    </div>
                    <div className="bl_heitgh--crOrder"></div>
                </div>
            </div>
        </div>
    );
}

export default СardOrder;
