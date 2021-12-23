import logo from '../img/1.png';
import '../App.css';
import { Button } from '../cards'


function ComeRegComponent() {
    return (
        <div className="wrapper">
            <div className="content">
                <div>
                    <img alt="" src={logo} />
                    <div>
                        <h1 className="zgl__login">Войти</h1>
                        <div className="bl__registr">
                        </div>
                    </div>
                </div>
                <div className="input__regstr">
                    <div className="display__input">
                        <input type="text" name="" placeholder="ведите номер телефона или почту" />
                    </div>
                    <div className="display__input">
                        <input type="password" name="" placeholder="пароль" />
                    </div>
                    <div className="src__password">
                        <a href="">Забыли пароль?</a>
                    </div>
                    <div>
                        <Button butText='Проверка' />
                    </div>
                    <div className="cont__bl-reg">
                        <p>Нет аккаунта? <span>Зарегистрируйтесь</span></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ComeRegComponent;
