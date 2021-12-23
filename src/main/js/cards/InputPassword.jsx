import React from "react";
import '../App.css';


function InputPassword(props) {
    return (
        <div className="display__input">
            <input type="password" name="" placeholder={props.text} />
        </div>
    );
}


export default InputPassword;