import React from "react";
import './settings.css'
import { Button, InputText, ButtonBack } from '..'

function ProfileName() {
    return (
        <>
            <a href='./4'>
                <ButtonBack />
            </a>
            <div className="content__profile">
                <div className="">
                    <div className="input__regstr">
                        <h3>Изменить Имя / Фамилию</h3>
                        <InputText text="Имя" />
                        <InputText text="Фамилия" />
                    </div>
                    <div>
                        <Button>Изменить</Button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ProfileName;