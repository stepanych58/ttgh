import './App.css';

import { FaPlusCircle } from "react-icons/fa"; // создать
import { FaBorderAll } from "react-icons/fa"; // заказы
import { FaLayerGroup } from "react-icons/fa"; // история
import { FaRegSun } from "react-icons/fa"; // настройки

import { PasswordForma, FormatNamber, Registration, HistoryMap, Repair, Settings, СardOrder, Logo, NavigationBottom, BtnBurger, HomeRepair, } from './cards'
import { ProfileContact, ProfileEmail, ProfileAdress, ProfileName } from './cards/component-settings';
import { Routes, Route } from 'react-router-dom';
import React from 'react';


const stepsComponents = {
  0: HomeRepair,
  1: Registration,
  2: FormatNamber,
  3: PasswordForma,
};

export const MainContext = React.createContext({});

function App() {

  const items = [
    { value: 'создать', href: './1', icon: <FaPlusCircle />, },
    { value: 'заказы', href: './2', icon: <FaBorderAll />, },
    { value: 'история', href: './3', icon: <FaLayerGroup />, },
    { value: 'настройки', href: './4', icon: <FaRegSun />, },
  ];

  // const link = ['Мясные', 'Лесные', 'Саленные', 'Маленые', 'Крутые', 'Малые', 'Слепые', 'Топлые',]
  const [visibleRepir, setVisibleRepair] = React.useState(false);
  const repair = visibleRepir


  const onNextStep = () => {
    setStep((prev) => prev + 1)
  }

  const [step, setStep] = React.useState(1)
  const Step = stepsComponents[step];


  return (
    <>
      <div className="wrapper">
        <div className="wrapper__content">
          <div className="header__container">
            <div className="header__logo">
              <Logo />
            </div>
          </div>
          <BtnBurger />
          <div className="content__repair">
            <Routes>
              {/* <Route path="/" element={<HomeRepair />} /> */}
              <Route path="/1" element={<Repair />} />
              <Route path="/2" element={<СardOrder />} />
              <Route path="/3" element={<HistoryMap />} />
              <Route path="/4" element={<Settings />} />
              <Route path="/2-2" element={< ProfileName />} />
              <Route path="/3-3" element={< ProfileContact />} />
              <Route path="/4-4" element={< ProfileEmail />} />
              {/* <Route path="/5-5" element={< ProfileAdress link={link} />} /> */}
              <Route path="/5-5" element={< ProfileAdress />} />
            </Routes>
          </div>
        </div>

        {
          // <Step />
          // {repair === <HomeRepair /> ? <NavigationBottom items={items} /> : ''}
        }
        <MainContext.Provider value={{ step, onNextStep }}>
          <Step />
        </MainContext.Provider>

      </div>
    </>
  );
}

export default App;
