import React from "react";
import '../App.css';


function NavigationBottom({ items, }) {

    return (
        <>
            <div className="conteiner__navigation">
                <div className="conteiner__navigation__block">
                    {items && items.map((items, index, name) =>
                        <a href={items.href} className="block__navigation" key={`${name}_${index}`}>
                            <span className="">{items.icon}</span>
                            <a >{items.value}</a>
                        </a>

                    )}
                </div>
            </div>
        </>
    );
}


export default NavigationBottom;