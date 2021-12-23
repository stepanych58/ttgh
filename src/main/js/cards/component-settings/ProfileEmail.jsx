import React from "react";
import './settings.css'
import { Button, InputText, ButtonBack } from '../../cards'

function ProfileEmail() {
    return (
        <>
            <a href='./4'>
                <ButtonBack />
            </a>
            <div className="content__profile">
                <div className="">
                    <div className="input__regstr">
                        <h3>Поменять адрес почты</h3>
                        <InputText text="введите email" />
                    </div>
                    <div>
                        <Button>изменить</Button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ProfileEmail;