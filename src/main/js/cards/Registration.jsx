import React from "react";
import '../App.css';
// import './step/step.css'
import { MainContext } from '../App';


import { Button } from '../cards'

function Registration() {
    const { onNextStep } = React.useContext(MainContext);
    const [inputValue, setInputValue] = React.useState('');
    const nextDisabled = !inputValue;

    const handleChangeInput = (event) => {
        setInputValue(event.target.value);
    }


    const onClickNextStep = () => {
        onNextStep();
    }

    return (
        <>
            <div className="content">
                <div>
                    <div>
                        <h1 className="zgl__login--card--reg">Регистрация</h1>
                        <div className="bl__registr">
                            <p>Заполните поля, чтобы создать аккаунт</p>
                        </div>
                    </div>
                </div>
                <div className="display__input--field">
                    <div className="input__regstr">
                        <input
                            placeholder="Имя"
                            className="field"
                        // onChange={handleChangeInput}
                        // value={inputValue}
                        />
                        <input
                            placeholder="Фамилия"
                            className="field"
                            onChange={handleChangeInput}
                            value={inputValue}
                        />

                        {/* <InputText text="Имя" /> */}
                        {/* <InputText text="Эл.почта" /> */}

                        {/* <InputPassword text="Пароль" />
                        <InputPassword text="Повторить пароль" />
                        <div className="src__password">
                            <p>Забыли пароль?</p>
                        </div> */}
                        <div>
                        </div>
                        <Button
                            disabled={nextDisabled}
                            onClick={onClickNextStep}
                        >Далее</Button>
                        {/* <button
                            disabled={nextDisabled}
                            onClick={onClickNextStep}
                        >Кнопка</button> */}
                    </div>
                    <div className="cont__bl--card--reg">
                        <p>Уже есть аккаунт? <span>Войдите</span></p>
                    </div>
                </div>
            </div>
        </>
    );
}


export default Registration;