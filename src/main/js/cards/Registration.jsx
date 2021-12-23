import React from "react";
import '../App.css';
// import logo from '../img/1.png';
// import Button from '../Button.jsx'
// import InputPassword from '../InputPassword.jsx'
// import InputText from "../InputText";
// import Logo from "../Logo";
// import BtnBurger from '../BtnBurger';


import { BtnBurger, Logo, Button, InputPassword, InputText, } from '../cards'

function Registration() {
    return (
        <div className="wrapper">
            <div className="wrapper__content">
                <div className="header__container">
                    <div className="header__logo">
                        <Logo />
                    </div>
                </div>
                <BtnBurger />
                <div className="content">
                    <div>
                        <div>
                            <h1 className="zgl__login--card--reg">Регистрация</h1>
                            <div className="bl__registr">
                                <p>Заполните все поля, чтобы создать аккаунт</p>
                            </div>
                        </div>
                    </div>
                    <div className="input__regstr">
                        <InputText text="Фамилия" />
                        <InputText text="Имя" />
                        <InputText text="Телефон" />
                        <InputText text="Эл.почта" />

                        <InputPassword text="Пароль" />
                        <InputPassword text="Повторить пароль" />
                        <div className="src__password">
                            <p>Забыли пароль?</p>
                        </div>
                        <div>
                            <Button butName="Создать" />
                        </div>
                        <div className="cont__bl--card--reg">
                            <p>Уже есть аккаунт? <span>Войдите</span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}


export default Registration;