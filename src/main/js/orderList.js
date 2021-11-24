import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

const ordersUrl = '/fixer/api/user/2/orders'

// async function getOrders (id) {
//     const url = `/fixer/api/user/${id}/orders`;
//
//     const response = await axios.get(url, {
//         headers: {
//             Authorization: `Bearer ${Cookies.get('access_token')}`,
//             'X-CSRF-TOKEN': Cookies.get('csrf_token')
//         }
//     });
//     return response.data;
// };

async function getUserOrders(id) {
    const url = `/fixer/api/user/${id}/orders`;
    const res = await axios.get(url, {
        headers: {
            Authorization: `Bearer ${Cookies.get('access_token')}`,
            'X-CSRF-TOKEN': Cookies.get('csrf_token')
        }
    });
    return res.data;
};

async function getOrders() {
    const url = `/fixer/api/order/all`;
    const res = await axios.get(url, {
        headers: {
            Authorization: `Bearer ${Cookies.get('access_token')}`,
            'X-CSRF-TOKEN': Cookies.get('csrf_token')
        }
    });
    return res.data;
};

async function getOrderAttrs() {
    const url = `/fixer/api/attrs`;
    const res = await axios.get(url, {
        headers: {
            Authorization: `Bearer ${Cookies.get('access_token')}`,
            'X-CSRF-TOKEN': Cookies.get('csrf_token')
        }
    });
    return res.data;
};

const OrderList = (props) => {

    async function updateOrder(userId, order) {
        await axios.patch(`/fixer/api/user/${userId}`, order, {
            headers: {
                Authorization: "Bearer " + Cookies.get('access_token'),
                'X-CSRF-TOKEN': Cookies.get('csrf_token')
            }
        });
        console.log("editOrderFunc")
    }

    async function deleteOrder(orderId) {
        console.log("deleteOrderFunc")
        await axios.delete(`fixer/api/order/delete/${orderId}`, {
            headers: {
                Authorization: "Bearer " + Cookies.get('access_token'),
                'X-CSRF-TOKEN': Cookies.get('csrf_token')
            }
        });
        loadFromServer();
    }

    const [emploees, setEmploees] = useState([])

    function loadFromServer() {
        getOrders().then(response => {
            var res = response.map(order => {
                return <Order key={order.id} orderObj={order}
                              deleteFunc={deleteOrder}
                              updateFunc={updateOrder}/>
            })
            setEmploees(res)
        })
    }

    useEffect(() => {
        loadFromServer()
    }, [setEmploees]);
    console.log('OrderList start');

    console.log('JSON.stringify(emploees)')
    console.log(JSON.stringify(emploees))
    return <div>
        <br/>
        Page number:<input/>
        <br/>
        Count orders on the page:<input/>
        <table>
            <tbody>
            <tr>
                <th>Order ID</th>
                <th>Status</th>
                <th>Client</th>
                <th>Engineer</th>
                <th>Delete</th>
                <th>Edit</th>
                {/*<th>Check</th>*/}
            </tr>
            {emploees}
            </tbody>
        </table>
        <div>Navigation Links</div>
    </div>
}

const Order = (props) => {
    const {
        orderObj,
        deleteFunc,
        updateFunc
    } = props;

    function handleDelete() {
        deleteFunc(orderObj.id)
    }

    let engeneer = orderObj.engineer ? orderObj.engineer.name : "-";
    return <tr>
        <td>{orderObj.id}</td>
        <td>{orderObj.status}</td>
        <td>{orderObj.client.name}</td>
        <td>{engeneer}</td>
        <td>
            <button onClick={handleDelete}>Delete</button>
        </td>
        <td>
            <EditOrderDialog order={orderObj} updateFunc={updateFunc}/>
        </td>
    </tr>
}

const CreateOrderDialog = (props) => {
    const {createFunc} = props

    function handleChangeInput(e) {
        parameterValues.set(e.target.id, e.target.value)
        setParameterValues(parameterValues)
    }

    function handleClick() {
        const newOrder = {}
        newOrder["status"] = "OPEN"
        newOrder["parameters"] = []
        const userId = 2
        for (let attrid of parameterValues.keys()) {
            let param = {}
            param["attribute"] = {"id": attrid};
            param["value"] = parameterValues.get(attrid);
            newOrder.parameters.push(param)
        }
        createFunc(userId, newOrder);
    }

    function getInputs(attrs) {
        let res = attrs.map(
            attr => {
                let key = attr.id + "_" + attr.name;
                return <p key={key}>
                    {attr.name} : <input type="text" className="field" id={attr.id}
                                         name={attr.name} onChange={handleChangeInput}/>
                </p>
            }
        )
        return res;
    }

    function loadAttributeInputs() {
        getOrderAttrs().then(attibutes => {
            setOrderAttributes(attibutes)
        });
    }

    const [orderAttributes, setOrderAttributes] = useState([]);
    const [parameterValues, setParameterValues] = useState(new Map());

    useEffect(() => {
        loadAttributeInputs()
    }, [setOrderAttributes])
    var inputs = getInputs(orderAttributes);
    return <div>
        <form action="#createOrder">
            <button type="submit">Create Order</button>
        </form>
        <div id="createOrder" className="modalDialog">
            <div>
                <a href="#" title="Close" className="close">X</a>
                <h2>Create new order</h2>
                <form>
                    {inputs}
                    <button onClick={handleClick}>Create</button>
                </form>
            </div>
        </div>
    </div>
}

const EditOrderDialog = (props) => {
    const {order, updateFunc} = props;

    function handleChangeInput(e) {
        // parameterValues.set(e.target.id, e.target.value)
        // orderParam
        console.log("handleChangeInput")
        for (let param of parameterValues) {
            if (param.attribute.id+ "_" + order.id == e.target.id) {
                param.value = e.target.value
                //return
            }
        }
        setParameterValues(parameterValues)
    }

    function handleClick() {
        order.parameters = parameterValues
        updateFunc(order.client.id, order);
    }

    function getInputs(params) {
        let res = params.map(param => {
            let attr = param.attribute;
            let key = attr.id + "_" + attr.name + "_" + order.id;
            return <p key={key}>
                {attr.name} : <input type="text" className="field" id={attr.id + "_" + order.id}
                                     defaultValue={param.value}
                                     name={attr.name + "_" + order.id} onChange={handleChangeInput}/>
            </p>
        })
        return res;
    }

    const [parameterValues, setParameterValues] = useState([])
    // setParameterValues(order.parameters);
    useEffect(() => {
        if (parameterValues.length == 0) {
            setParameterValues(order.parameters)
        }
    }, [setParameterValues])
    let inputs = getInputs(parameterValues)
    console.log("editorder")
    console.log(order.id)
    let formId = "editOrder" + order.id
    let action = "#" + formId;
    return <div>
        <form action={action}>
            <button type="submit">Edit</button>
        </form>
        <div id={formId} className="modalDialog">
            <div>
                <a href="#" title="Close" className="close">X</a>
                <h2>Edit order ID: {order.id}</h2>
                <form>
                    {inputs}
                    <button onClick={handleClick}>Update</button>
                </form>
            </div>
        </div>
    </div>
}

export {OrderList, CreateOrderDialog};//{OrderList, CreateOrderDialog};
