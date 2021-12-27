import React from "react";
// import '../App.css';
import './btn-burger.css'
import { Logo2 } from '../../cards';

function BtnMenu({ items, active, setActive }) {

    return (

        <div className={active ? 'menu active__menu' : 'menu'}>


            <div className={active ? 'Y active' : 'Y'} onClick={() => setActive(!active)}>
                <span />
            </div>
            <Logo2 />
            <hr className="hr__mune" />
            <div>
                {
                    items.map((z) =>
                        <a href=''>{z}</a>
                    )}
            </div>
        </div>

    );
}


export default BtnMenu;