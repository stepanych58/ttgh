import React from "react";
import '../App.css';


function InputTimeDate(props) {
    return (
        <div className="input__time-date">
            <input type="text" name="" placeholder={props.text} />
        </div>
    );
}


export default InputTimeDate;