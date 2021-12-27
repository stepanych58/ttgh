import React from "react";
import NumberFormat from "react-number-format";
import '../App.css';
import './step/step.css'



import { Button } from '.'

function FormatNamber() {
    const [inputValue, setInputValue] = React.useState({})

    const nextDisabled = !inputValue.formattedValue || inputValue.formattedValue.includes('_')

    return (
        <>
            <div className="content">
                <div>
                    <div>
                        <h1 className="zgl__login--card--reg">Введите номер</h1>

                    </div>
                </div>
                <div className="display__input--field">
                    <div className="input__regstr">
                        <NumberFormat
                            className="field"
                            format='+# (###) ###-##-##'
                            mask='_'
                            placeholder="+7 (999) 333-22-11"
                            value={inputValue.value}
                            onValueChange={(values) => setInputValue(values)}
                        />
                        <div>
                        </div>
                        <Button disabled={nextDisabled}>
                            Далее
                        </Button>
                    </div>
                </div>
            </div>
        </>
    );
}


export default FormatNamber;