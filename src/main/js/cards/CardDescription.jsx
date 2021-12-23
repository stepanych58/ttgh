import React from "react";
import '../App.css';
// import logos from '../img/2.png';
// import Button from '../Button.jsx'
// import InputTimeDate from "../InputTimeDate";
// import InputText from "../InputText";



// import Logo from "./Logo";
// import BtnBurger from './BtnBurger';
// import NavigationBottom from './NavigationBottom';
// import ButtonBack from './ButtonBack';

import { Logo, BtnBurger, NavigationBottom, ButtonBack } from '../cards'



function СardOrder() {
    return (
        <div className="wrapper">
            <div className="wrapper__content">
                <div className="header__container">
                    <div className="header__logo">
                        <Logo />
                    </div>
                    <ButtonBack />
                </div>
                <BtnBurger />
                <div className="content__discription-order">
                    <div className="content-p">
                        <p>техника:</p>
                        <p>адрес:</p>
                        <p>время:</p>
                        <p>дата:</p>
                    </div>
                    <div className="breakdowns">
                        <h4>
                            Описание:
                        </h4>
                    </div>
                </div>
            </div>
            <NavigationBottom />
        </div>
    );
}




export default СardOrder;