import React from "react";
import './settings.css'
import { Button, InputText, ButtonBack } from '../../cards'

function ProfileAdress() {
    return (
        <>
            <a href='./4'>
                <ButtonBack />
            </a>
            <div className="content__profile">
                <div className="">
                    <div className="input__regstr">
                        <h3>Изменить адрес "авто заполнение"</h3>
                        <InputText text="введите адрес" />
                    </div>
                    <div>
                        <Button>изменить</Button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ProfileAdress;