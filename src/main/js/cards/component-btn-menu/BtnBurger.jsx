import React from "react";
// import '../App.css';
import './btn-burger.css'
import { BtnMenu } from '../../cards'

function BtnBurger(props) {
    const items = ['Войти',]
    const [onClickBtn, setOnClickBtn] = React.useState(false)


    return (
        <>
            <div className={onClickBtn ? 'center active' : 'center'} onClick={() => setOnClickBtn(!onClickBtn)}>
                <span />
            </div>

            <BtnMenu items={items} active={onClickBtn} setActive={setOnClickBtn} />
        </>
    );
}


export default BtnBurger;