'use strict';
// const {
//     colors,
//     CssBaseline,
//     ThemeProvider,
//     Typography,
//     Container,
//     makeStyles,
//     createMuiTheme,
//     Box,
//     SvgIcon,
//     Link,
//     TextField
// } = MaterialUI;
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
                    {/*    templates*/}
                    {/*</Link>*/}
                </div>
                <LoginControl key={'lctrl'}></LoginControl>
            </div>
        );
    }
};

class ListInput extends React.Component {
    constructor(props) {
        super(props);
        this.getListValues = this.getListValues.bind(this);
        this.selectListValue = this.selectListValue.bind(this);
        this.state =
            {
                selectedValue: "",
                listItems: []
            };
    }

    getListValues(event) {
        let listValues = this.props.attribute.listValues.map(lv => this.printListValueItem(lv));
        this.setState(
            {
                selectedValue: event.target.value,
                listItems: listValues
            });
        // console.log(listValues);
    }

    selectListValue(event) {
        this.setState(
            {
                selectedValue: event.target.innerText,
                listItems: []
            });
        this.props.onChange(event);
    }


    printListValueItem(listValue) {
        return <div key={listValue.id + listValue.value}
                    role="option"
                    onClick={this.selectListValue}
                    className="list_item">{listValue.value}</div>;
    }

    render() {
        return (
            <div>
                <input className="form__input"
                       onChange={this.selectListValue}
                       onClick={this.getListValues}
                       id={this.props.attribute.id}
                       value={this.state.selectedValue} type="text"/>
                <div className="list_items">
                    {this.state.listItems}
                </div>
                <label className="form__label" htmlFor={this.props.attribute.id}>
                    {this.props.attribute.name}</label>
            </div>
        );
    }
}

class AddressInput extends React.Component {
    constructor(props) {
        super(props);
        this.getAddresses = this.getAddresses.bind(this);
        this.selectAddress = this.selectAddress.bind(this);
        this.state =
            {
                addressValue: "",
                suggestionItems: []
            };
    }

    getAddresses(event) {
        console.log("addrInput")
        var url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";
        var token = "f019553965d0c4ba18ed2ddb215c3c11cfaf4878";
        var query = event.nativeEvent.target.value;

        var options = {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": "Token " + token
            },
            body: JSON.stringify({query: query})
        }

        fetch(url, options)
            .then(response => response.text())
            .then(result => {
                let suggestionItems = JSON.parse(result).suggestions.map((suggestion) => this.getSuggestionItem(suggestion));
                this.setState(
                    {
                        addressValue: query,
                        suggestionItems: suggestionItems
                    });
                // console.log(suggestionItems)
            })
            .catch(error => {
                console.log("error", error)
                console.log("11111")
            });
    }

    getSuggestionItem(suggestion) {
        return <div key={suggestion.value} role="option"
                    onClick={this.selectAddress}
                    className="address_item">{suggestion.value}</div>;
    }

    selectAddress(event) {
        this.setState(
            {
                addressValue: event.target.innerText,
                suggestionItems: []
            });
        this.props.onChange(event);
    }

    render() {
        return (
            <div>
                <input className="form__input"
                       onInput={this.getAddresses}
                       onChange={this.props.onChange}
                       id={this.props.attribute.id}
                       value={this.state.addressValue}
                       type="text"/>
                <div className="address_items">
                    {this.state.suggestionItems}
                </div>
                <label className="form__label" htmlFor={this.props.attribute.id}>
                    {this.props.attribute.name}</label>
            </div>
        );
    }
}

class PhoneNumberInput extends React.Component {
    phoneNumberFieldHandler(event) {
        console.log('phoneNumberFieldHandler start')
        let nativeEvent = event.nativeEvent;
        let phone = nativeEvent.path[0];
        let inputSymbol = nativeEvent.data;
        let mask = '+7 (___)-___-__-__'
        console.log(event)
        console.log(inputSymbol);
        if (nativeEvent.inputType == "deleteContentBackward") {
            return;
        }
        let currentValue = phone.value;
        let prevValue = currentValue.slice(0, -1)
        if (!inputSymbol.match(/^[0-9]*$/g) || currentValue.length > mask.length) {
            phone.value = prevValue;
            return;
        }
        let resValue = prevValue.length == 0 ?
            mask : prevValue + mask.substring(prevValue.length, mask.length);
        let valueSize = resValue.indexOf('_') + 1;
        phone.value = resValue.replace('_', inputSymbol)
            .substring(0, valueSize);
    }

    render() {
        return (<div>
                <input className="form__input phone_number__input"
                       id={this.props.attribute.id}
                       type="text"
                       onInput={this.phoneNumberFieldHandler}
                       onChange={this.props.onChange}
                       placeholder="+7 (999) 999 99 99"/>
                <label className="form__label" htmlFor={this.props.attribute.id}>
                    {this.props.attribute.name}</label>
            </div>
        );
    }
}

class UploadFileInput extends React.Component {
    constructor() {
        super();
        this.getPhoto = this.getPhoto.bind(this);
        // this.pressButton = this.pressButton.bind(this);
        this.state =
            {
                previewImages: []
            };
    }

    // pressButton(e) {
    //     e.preventDefault();
    //     //     TODO: do something with -> this.state.file
    //     console.log('handle uploading-', this.state.file);
    // }

    getPhoto(e) {
        e.preventDefault();
        let reader = new FileReader();
        let file = e.target.files[0];
        console.log(this.props.attribute)
        reader.onloadend = () => {
            var images = this.state.previewImages;
            images.push({f: file, u: reader.result})
            this.setState({
                previewImages: images
            });
        }
        reader.readAsDataURL(file);
        let parentOnChangeFunction = this.props.onChange;
        parentOnChangeFunction(e);
    }

    // getPhoto(e) {
    //     e.preventDefault();
    //     let reader = new FileReader();
    //     let file = e.target.files[0];
    //     console.log(this.props.attribute)
    //     reader.onloadend = () => {
    //         var images = this.state.previewImages;
    //         images.push({f: file, u: reader.result})
    //         this.setState({
    //             previewImages: images
    //         });
    //     }
    //     reader.readAsDataURL(file);
    // }

    render() {
        let {previewImages} = this.state;
        let images = null;
        if (previewImages) {
            // let previewImages = this.state.previewImages;
            images = previewImages.map((item) => (<img key={item.f.name}
                                                       className="previewUploadedFile"
                                                       src={item.u}/>));
            for (var i =0 ;i<previewImages.length;i++) {
                var item = previewImages[i];
                var data = new FormData();
                console.log(item.f);
                data.append('file', item.f)
                axios.post("/uploadForm/", data).then(res => {
                    console.log("File uploaded successfully.")
                });
            }
        }
        return (
            <div className="example-2">
                <div className="form-group">
                    <input type="file" name="file" id="file"
                           onChange={this.getPhoto}
                           className="input-file"/>
                    <label htmlFor="file" className="btn btn-tertiary js-labelFile">
                        <i className="icon fa fa-check"></i>
                        <span className="js-fileName">{this.props.attribute.name}</span>
                    </label>
                </div>
                {images}
            </div>
        );
    }
}

class OrderInput extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            orderInfo: props.orderInfo || {}
        }
    }

    render() {
        let attribute = this.props.attribute;
        let placeholder = "";
        let inputtype = "text"
        let handler;
        let inputElement;
        if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_TEXT)) {

        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_REFERENCE)) {

        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_DATE)) {
            inputElement =
                <div>
                    <div id={attribute.id}>
                        <input className="style__date" type="date" placeholder="мм/дд/гггг"/>
                        <input className="style__time" type="time" placeholder="--:-- --"/>
                    </div>
                    <label className="form__label" htmlFor={attribute.id}>{attribute.name}</label>
                </div>;
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_LIST)) {
            inputElement = (<ListInput key={'list_' + attribute.id}
                                       attribute={attribute}
                                       onChange={this.props.onChange}
            ></ListInput>)
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_DATA)) {
            //https://stackoverflow.com/questions/39695275/react-js-handling-file-upload
            inputElement = (<UploadFileInput key={'ui_' + attribute.id}
                                             attribute={attribute}
                                             onChange={this.props.onChange}
            ></UploadFileInput>);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_TIME)) {
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_ADDRESS)) {
            inputElement = (<AddressInput attribute={attribute} onChange={this.props.onChange}></AddressInput>);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_PHONE_NUMBER)) {
            inputElement = <PhoneNumberInput attribute={attribute} onChange={this.props.onChange}></PhoneNumberInput>;
        }
        inputElement = inputElement ? inputElement :
            <div>
                <input className="form__input " id={attribute.id} type={inputtype} onInput={handler}
                       placeholder={placeholder} onChange={this.props.onChange}/>
                <label className="form__label" htmlFor={attribute.id}>{attribute.name}</label>
            </div>;
        return (
            <div className="form__group" key={'form__group' + attribute.id}>
                {inputElement}
            </div>
        );
    }
}

class PageContent extends React.Component {
    constructor(props) {
        super(props);
        this.printOrderFields = this.printOrderFields.bind(this);
        this.sendOrder = this.sendOrder.bind(this);
        this.changeInputHandle = this.changeInputHandle.bind(this);
        this.state =
            {
                sendOrderProcess: 'new',
                orderFields: [],
                orderInfo: new Map(),
            }
    }

    componentDidMount() {
    }

    componentWillUnmount() {
    }

    getParameterValue(attribute, e) {
        console.log(e);
        let res;
        if (e.type == "change") {
            if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_REFERENCE)) {
            } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_DATE)) {
            } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_LIST)) {
            } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_DATA)) {
            } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_TIME)) {
            }
            res = res || e.target.value;
        } else if (e.type == "click") {
            res = e.target.innerText;
        }
        return res;
    }

    changeInputHandle(e, attr) {
        let attrMap = this.state.orderInfo;
        let parameterValue = this.getParameterValue(attr, e);
        attrMap.set(attr.id, parameterValue);
        this.setState({
            orderInfo: attrMap
        });
        console.log(attrMap);
    }

    printOrderFields() {
        axios.get('/fixer/api/attrs').then(
            response => {
                var attrs = response.data;
                let fields = attrs.map((attr) => <OrderInput key={'oi_' + attr.id}
                                                             attribute={attr}
                                                             orderInfo={this.state.orderInfo}
                                                             onChange={(e) => this.changeInputHandle(e, attr)}
                ></OrderInput>);
                this.setState(
                    {
                        sendOrderProcess: 'toSend',
                        orderFields: fields
                    });
            }
        );
    }

    sendOrder() {
        let {filesToUpload} = this.state;
        let {orderInfo} = this.state;
        let order = this.getOrderJson(orderInfo);
        console.log('filesToUpload')
        console.log(filesToUpload)
        console.log(JSON.stringify(order))
        var url = serverUrl + "/fixer/api/order/create";
        var options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
            },
            body: JSON.stringify(order)
        }

        fetch(url, options)
            .then(response => response.text())
            .then(result => {
                let res = JSON.parse(result);
                console.log(res);
            })
            .catch(error => {
                console.log("error", error)
                console.log("11111")
            });


        this.setState(
            {
                sendOrderProcess: 'sended',
                orderFields: []
            });
    }

    getOrderJson(orderInfo) {
        let client = {
            'name': orderInfo.get(12),
            'phoneNumber': orderInfo.get(13),
            'role': 'CLIENT'
        };
        let order = {};
        let params = [];
        for (let attrid of orderInfo.keys()) {
            let param = {};
            param['attribute'] = {'id': attrid};
            param['value'] = orderInfo.get(attrid);
            params.push(param);
        }
        order['parameters'] = params;
        order['client'] = client;
        return order;
    }

    render() {
        let handler;
        let buttonName = 'Вызвать мастера';
        if (this.state.sendOrderProcess == 'new') {
            handler = this.printOrderFields
        } else if (this.state.sendOrderProcess == 'toSend') {
            handler = this.sendOrder
        } else if (this.state.sendOrderProcess == 'sended') {
            buttonName = 'Вернуться на главную страницу';
            handler = () => {
                window.location.replace("/")
            };
        }

        return (
            <div className="buttom">
                <div className="block__title">
                    <div className="buttom__title" id="content">
                        <h1 className="form__title" id="page_title"></h1>
                        <div id="fields_group">
                            {this.state.orderFields}
                        </div>
                        {/*<TextField></TextField>*/}
                        <MainButton name={buttonName} handler={handler}></MainButton>
                    </div>
                </div>
            </div>
        );
    }
}

class MainButton extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (<button onClick={this.props.handler} className="form__buttom">{this.props.name}</button>);
    }
}

// class LoginButton extends React.Component {
//     constructor(props) {
//         super(props);
//     }
//     render(){
//         return <MainButton name={this.props.name} handler={this.props.handler} ></MainButton>;
//     }
// }

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.handleLoginClick = this.handleLoginClick.bind(this);
        this.handleLogoutClick = this.handleLogoutClick.bind(this);
        this.login= this.login.bind(this);
        this.logout= this.logout.bind(this);
        this.componentDidMount= this.componentDidMount.bind(this);
        this.state = {
            isLoggedIn: false,
            userName: ''
        };
    }

    handleLoginClick() {
        this.setState({isLoggedIn: true});
        this.login()
    }

    handleLogoutClick() {
        this.setState({isLoggedIn: false});
        this.logout()
    }

    componentDidMount() {
        // fetch the project name, once it retrieves resolve the promsie and update the state.
        axios.get('/fixer/api/user/currentUser').then(
            response => {
                var user = response.data;
                console.log(user);
                if (user) {
                    this.setState(
                        {
                            userName: user.name,
                            isLoggedIn: true
                        });

                }
            });
    }



    login() {
        window.location.replace("/auth/login");
        // axios.post('/auth/login').then(
        //     response => {
        //         console.log('/auth/login');
        //     });
    }

    logout() {
        window.location.replace("/auth/logout");
        // axios.post('/auth/logout').then(
        //     response => {
        //         console.log('/auth/logout');
        //     });
    }

    render() {
        let userName = this.state.userName;
        const isLoggedIn = this.state.isLoggedIn;
        let handler = isLoggedIn ? this.handleLogoutClick : this.handleLoginClick;
        let buttonName = isLoggedIn ? 'Выйти' : 'Войти';

        return (<div key={'btns'}>
                <MainButton name={userName} key={'btn1'}/>
                <MainButton name={buttonName} handler={handler} key={'btn2'}/>
            </div>);
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