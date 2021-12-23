import React from "react";
import '../App.css';
import logos from '../img/2.png';
import { FaInfoCircle } from "react-icons/fa"; //вопрос
// import Button from '../Button.jsx'
// import InputTimeDate from "../InputTimeDate";
// import InputText from "../InputText";
// import Logo from "../Logo";
// import BtnBurger from '../BtnBurger';

import { Button, InputTimeDate, InputText, } from '../cards'
import { SortPopup, QuestionBlock } from "./components";

function Repair() {
    return (

        // <div className="content__repair">
        <div className="content__repair">
            <div>
                <img className="repair__page-img" alt="" src={logos} />
            </div>
            <div className="input__regstr">
                <InputText text="Адрес" />
                <SortPopup items={['холодильник', 'стиральная машинка', 'телевизор',]} />
                <div className="block__time-date">
                    <InputTimeDate text="время" />
                    <QuestionBlock
                        outline
                        icons={[<FaInfoCircle />]}
                        items={['Напишите любое для Вас удобное время. Пример "с 16:00 до 18:00"']}
                    />

                    <InputTimeDate text="дата" />
                    <QuestionBlock
                        icons={[<FaInfoCircle />]}
                        items={['Напишите любую для Вас удобную дату. Пример "22.02.2022"']}
                    />
                </div>
                <textarea placeholder="напишите что случилось"></textarea>
                <div>
                    <Button>отправить</Button>
                    {/* <Button outline>отправить</Button> */}
                </div>

            </div>
            <div className="bl_heitgh--crOrder"></div>
        </div>
        // </div>
    );
}




export default Repair;