import React, {useEffect, useState} from "react";
import '../App.css';
import logos from '../images/2.png';
import {FaInfoCircle} from "react-icons/fa";
import {Button, InputTimeDate, InputText,} from '../cards'
import {SortPopup, QuestionBlock} from "./components";
import axios from "axios";
import Cookies from "js-cookie";

async function getTechnicalTypes() {
    const url = `/fixer/api/attrs/4`;
    const res = await axios.get(url, {
        headers: {
            Authorization: `Bearer ${Cookies.get('access_token')}`,
            'X-CSRF-TOKEN': Cookies.get('csrf_token')
        }
    });
    return res.data;
}

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

function CreateOrder() {

    const [technicalTypes, setTechnicalTypes] = useState([])

    const [address, setAddress] = useState([])
    const [technicType, setTechnicType] = useState([])
    const [dateAndTime, setDateAndTime] = useState([])
    const [comment, setComment] = useState([])

    function create() {
        const newOrder = {}
        newOrder["status"] = "OPEN"
        newOrder["parameters"] = []
        const userId = 2

        setOrderAddress(newOrder)
        setOrderTechnicalType(newOrder)
        setOrderDateAndTime(newOrder)
        setOrderComment(newOrder)

        createOrder(userId, newOrder);
    }
    useEffect(() => {
        getTechnicalTypes().then(response => {
                console.log("technicalTypes: " + response);
                setTechnicalTypes(response.listValues)
            }
        )
    }, [setTechnicalTypes]);

    function setOrderAddress(order){
        console.log("order setAddress :" + order)
    }

    function setOrderTechnicalType(order){
        console.log("order setTechnicalType :" + order)
    }

    function setOrderDateAndTime(order){
        console.log("order setDateAndTime :" + order)
    }

    function setOrderComment(order){
        console.log("order setComment :" + order)
    }


    return (
        <div className="content__repair">
            <div>
                <img className="repair__page-img" alt="" src={logos}/>
            </div>
            <div className="input__regstr">
                <InputText setAddress={setAddress} text="Адрес"/>
                <SortPopup items={technicalTypes}/>
                <div className="block__time-date">
                    <InputTimeDate text="время"/>
                    <QuestionBlock
                        outline
                        icons={[<FaInfoCircle/>]}
                        items={['Напишите любое для Вас удобное время. Пример "с 16:00 до 18:00"']}
                    />

                    <InputTimeDate text="дата"/>
                    <QuestionBlock
                        icons={[<FaInfoCircle/>]}
                        items={['Напишите любую для Вас удобную дату. Пример "22.02.2022"']}
                    />
                </div>
                <textarea placeholder="напишите что случилось"></textarea>
                <div>
                    <Button>отправить</Button>
                </div>
            </div>
            <div className="bl_heitgh--crOrder"></div>
        </div>
    );
}

export default CreateOrder;
