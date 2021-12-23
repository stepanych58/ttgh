import React from "react";
import './settings.css'
import { Button, InputText, ButtonBack } from '../../cards'

function ProfileContact() {
    return (
        <>
            <a href='./4'>
                <ButtonBack />
            </a>
            <div className="content__profile">
                <div className="">
                    <div className="input__regstr">
                        <h3>Изменить номер телефона</h3>
                        <InputText text="+7 (___)-__-__" />
                    </div>
                    <div>
                        <Button>изменить</Button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ProfileContact;