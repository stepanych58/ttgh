import React from "react";
import './step.css';
import { Button } from '../../cards'

function HomeRepair(props) {
    return (
        <div className="wr__homepage">
            <h1>Ремонт бытовой техники</h1>
            <p>Вам нужна помощь в ремонте бытовой техники? Великолепно!
                Вы нашли нужное место, оставайтесь с нами, и мы обещаем,
                что не пожалеете!
            </p>
            <Button homerepair>Оставить заявку</Button>
        </div>
    );
}


export default HomeRepair;