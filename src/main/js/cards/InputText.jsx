import React from "react";
import '../App.css';


function InputText(props) {
    return (
        <div className="display__input">
            <input type="text" name="" placeholder={props.text} />
        </div>
    );
}


export default InputText;