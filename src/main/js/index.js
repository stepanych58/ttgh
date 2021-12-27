import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
// import Registration from './cards/Registration'
// import Repair from './cards/Repair';
// import СardOrder from './cards/СardOrder';
// import CardDescription from './cards/CardDescription';
// import HistoryMap from './cards/HistoryMap';
// import Settings from './cards/Settings';
// import { BrowserRouter, Route } from 'react-router-dom';


// import { Registration, Repair, СardOrder, CardDescription, } from './cards';
// import { NavigationBottom } from './cards';
ReactDOM.render(
  <React.StrictMode>

    <BrowserRouter>
      {/* <NavigationBottom /> */}
      <App />
      {/* < Route path="/2"><Registration /></Route>
      < Route path="/3"><Repair /></Route>
      < Route path="/4"><СardOrder /></Route>
      < Route path="/5"><CardDescription /></Route> */}


      {/* < Route exact path="/qwe" component={Registration} /> */}
      {/* <Registration /> */}
      {/* <Repair /> */}
      {/* <СardOrder /> */}
      {/* <App /> */}
      {/* < Route exact path="/2q" component={CardDescription} /> */}
      {/* <CardDescription /> */}


      {/* <HistoryMap /> */}
      {/* <Settings /> */}

    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('react')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
