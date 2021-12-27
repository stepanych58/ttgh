import React from "react";
import './settings.css'
import { Button, InputText, ButtonBack } from '../../cards'
import Tree from './Tree';






function ProfileAdress() {

    const link = [
        { text: 'Мясные', },
        { text: 'Саленные', },
        { text: 'Маленые', },
        { text: 'Крутые', },
        { text: 'Малые', },
        { text: 'Слепые', },
        { text: 'Топлые', },
    ]

    return (
        <>
            <div className="container">
                {/* <Tree _class='items'>
                    {
                        link.map((nema, i) => {
                            return (
                                <div key={i} className="item" >
                                    <p>{nema.text}</p>
                                </div>
                            )
                        })
                    }

                </Tree> */}
            </div>
            <a href='./4'>
                <ButtonBack />
            </a>
            <div className="content__profile">
                <div className="">
                    <div className="input__regstr">
                        <h3>Изменить адрес "авто заполнение"</h3>
                        <InputText text="введите адрес" />
                    </div>
                    <div>
                        <Button>изменить</Button>
                    </div>
                </div>
            </div>
        </>
    );
}


// function Proplem(props) {
//     let ref = React.useRef

// React.useEffect(() => {
//     const el = ref.current
//     if (el) {
//         const onWeil = e => {
//             e.preventDefault()
//             el.scrollTo({
//                 left: el.scrollLeft + e.deltaY * 4,
//                 behavior: 'smooth'
//             })
//         }
//         el.addEventListener('wheel', onWeil)
//         return () => el.removeEventListener('wheel', onWeil)
//     }
// }, [])

// return (
//     <div
//         // ref={ref}
//         className={props._class} >
//         {
//             React.Children.map(props.children, child => React.Children.only(child))
//         }
//     </div>
// );
// }

export default ProfileAdress;


