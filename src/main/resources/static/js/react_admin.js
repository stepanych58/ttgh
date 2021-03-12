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
            <div>
                <a href={'stbe'}> orders table link</a>
                <a href={'stbe'}> orders table link1</a>
                <a href={'stbe'}> orders table link2</a>
            </div>
        );
    }
    // render() {
    //     return (
    //         <div>
    //             <p>Orders table</p>
    //             {/*<table border={2}>*/}
    //             {/*    <tr>*/}
    //             {/*        <th>Name</th>*/}
    //             {/*        <th>Phone Number</th>*/}
    //             {/*        <th>What broke</th>*/}
    //             {/*    </tr>*/}
    //             {/*</table>*/}
    //             <table cellSpacing="0">
    //                 <tr>
    //                     <th>Picture</th>
    //                     <th>Name</th>
    //                     <th>Email</th>
    //                     <th>Phone</th>
    //                     <th width="230">Comments</th>
    //                 </tr>
    //
    //                 <tr>
    //                     <td><img
    //                         src="https://habrastorage.org/getpro/moikrug/uploads/education_platform/000/000/037/logo/medium_3869053716ff0b787b38590f7ae5ed62.jpg"
    //                         alt=""/></td>
    //                     <td>Jane Doe</td>
    //                     <td>jane.doe@foo.com</td>
    //                     <td>01 800 2000</td>
    //                     <td>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</td>
    //                 </tr>
    //
    //                 <tr>
    //                     <td><img
    //                         src="https://habrastorage.org/getpro/moikrug/uploads/education_platform/000/000/037/logo/medium_3869053716ff0b787b38590f7ae5ed62.jpg"
    //                         alt=""/></td>
    //                     <td>John Doe</td>
    //                     <td>john.doe@foo.com</td>
    //                     <td>01 800 2000</td>
    //                     <td>Blanditiis, aliquid numquam iure voluptatibus ut maiores explicabo ducimus neque, nesciunt
    //                         rerum perferendis, inventore.
    //                     </td>
    //                 </tr>
    //
    //                 <tr>
    //                     <td><img src="https://i.picsum.photos/id/64/100/100.jpg" alt=""/></td>
    //                     <td>Jane Smith</td>
    //                     <td>jane.smith@foo.com</td>
    //                     <td>01 800 2000</td>
    //                     <td> Culpa praesentium unde pariatur fugit eos recusandae voluptas.</td>
    //                 </tr>
    //
    //                 <tr>
    //                     <td><img src="https://i.picsum.photos/id/1025/100/100.jpg" alt=""/></td>
    //                     <td>John Smith</td>
    //                     <td>john.smith@foo.com</td>
    //                     <td>01 800 2000</td>
    //                     <td>Aut voluptatum accusantium, eveniet, sapiente quaerat adipisci consequatur maxime temporibus
    //                         quas, dolorem impedit.
    //                     </td>
    //                 </tr>
    //             </table>
    //
    //         </div>
    //     );
    // }
}

class EngineersTable extends React.Component {
    render() {
        return (
            <div className={"midletable"}>
                <p>Engeeners table</p>
                <table border={2}>
                    <tr>
                        <th>Name</th>
                        <th>Master</th>
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