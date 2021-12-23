import React from "react";
import '../App.css';
import classNames from 'classnames';

function Button(props) {
    return (
        <div>
            <button className={classNames('btn__burger', {
                'button--outline': props.outline,
            })}>
                {props.children}
            </button>
        </div>
    );
}


export default Button;

// npm install classnames скачать библиотеку //// https://www.npmjs.com/package/classnames
/* <Button classNames = {`btn__burger ${props.outline ? 'button--outline' : ''}`}>{props.children}</Button> */