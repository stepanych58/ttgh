'use strict';
var serverUrl = "http://localhost:8000";
class HeaderItem extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (<div className="header__item headerbuttom"><a href="#">{this.props.name}</a></div>);
    }
}

class PageHeader extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="header">
                <div className="header__section">
                    <div className="header__item headerlogo">Твой Техник</div>
                    <HeaderItem name='Пользователям'></HeaderItem>
                    <HeaderItem name='Что Чиним?'></HeaderItem>
                    <HeaderItem name='О нас'></HeaderItem>
                    <HeaderItem name='Партнерам'></HeaderItem>
                    {/*<TextField ></TextField>*/}
                    {/*<Link href="https://material-ui.com/getting-started/templates/">*/}
                    {/*templates*/}
                    {/*</Link>*/}
                </div>
                {/*<LoginControl></LoginControl>*/}
            </div>
        );
    }
};

class OrdersTable extends React.Component {
    render() {
        return (
            <div className={"midletable"}>
                <p>Orders table</p>
                <table border={2}>
                    <tr>
                        <th>Name</th>
                        <th>Master</th>
                        <th>Rate</th>
                        <th>Count of orders</th>
                    </tr>
                </table>
            </div>
        );
    }
}

class EngineersTable extends React.Component {
    render() {
        return (
            <div className={"midletable"}>
                <table border={2}>
                    <tr>
                        <th>Name</th>
                        <th>Master</th>
                        <th>Rate</th>
                        <th>Count of orders</th>
                    </tr>
                </table>
            </div>
        );
    }
}
//https://www.youtube.com/watch?v=930JPFaKg-s use it (drag and drop lesson)
class PageContent extends React.Component {
    render() {
        return (
            <div className={'tables'}>
                <OrdersTable></OrdersTable>
                <EngineersTable></EngineersTable>
            </div>
        );
    }
}

class WelcomePage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const e = (
            <div className="wrapper">
                <PageHeader></PageHeader>
                {/*<OrdersTable></OrdersTable>*/}
                {/*<EngineersTable></EngineersTable>*/}
                <PageContent></PageContent>
            </div>
        );
        return e;
    }
}

ReactDOM.render(
    <WelcomePage/>,
    document.getElementById('root')
);