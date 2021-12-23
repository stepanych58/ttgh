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
                        <InputText text="Имя" />
                        <InputText text="Фамилия" />
                    </div>
                    <div>
                        <Button>Создать</Button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ProfileName;