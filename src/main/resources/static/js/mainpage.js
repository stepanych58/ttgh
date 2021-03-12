var AttrTypes = {
    ATTR_TYPE_TEXT: "TEXT",
    ATTR_TYPE_REFERENCE: "REFERENCE",
    ATTR_TYPE_DATE: "DATE",
    ATTR_TYPE_LIST: "LIST",
    ATTR_TYPE_DATA: "DATA",
    ATTR_TYPE_TIME: "TIME",
    ATTR_TYPE_PHONE_NUMBER: "PHONE_NUMBER",
    ATTR_TYPE_ADDRESS: "ADDRESS"
}

function reg() {
    var pt = document.getElementById("page_title");
    pt.innerText = 'Регистрация'
}

function log() {
    var pt = document.getElementById("page_title");
    pt.innerText = 'Войти';
}

function createElem(tagName, dclass, innerText, id) {
    let elem = document.createElement(tagName);
    elem.setAttribute('class', dclass);
    elem.innerText = innerText || "";
    elem.id = id;
    return elem;
}

function div(dclass) {
    return createElem('div', dclass);
}

function lbl(lclass, innerText) {
    return createElem('label', lclass, innerText);
}

function tinput(id, type, plholder, pclass) {
    let inputElement = createElem('input', pclass, '', id);
    inputElement.type = type || "";
    inputElement.placeholder = plholder || "";
    return inputElement;
}

function textInput(attribute) {
    return tinput(attribute.id, 'text', ' ', 'form__input');
}

function phoneInput(attribute) {
    let resElem = tinput(attribute.id, 'text', '+7 (999) 999 99 99', 'form__input');
    resElem.addEventListener('input', function (event) {
        let phone = event.currentTarget;
        let mask = '+7 (___)-___-__-__'
        let inputSymbol = String(event.data);
        if (event.inputType == "deleteContentBackward") {
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
    });
    return resElem;
}

function wrapInput(attr, field) {
    let inpDiv = div('form__group');
    inpDiv.appendChild(field(attr))
    inpDiv.appendChild(lbl('form__label', attr.name))
    return inpDiv;

}

function addElement(parentId, element) {
    let parent = document.getElementById(parentId);
    if (parent) parent.appendChild(element);
}

function referenceField(attribute) {
    return undefined;
}

function dateField(attribute) {
    let wrapper = div('form__group');
    let divWrapper = div('form__group');
    let dateInput = tinput(attribute.id, 'date', 'мм/дд/гггг', 'style__date');
    let timeInput = timeField(attribute);
    divWrapper.appendChild(dateInput);
    divWrapper.appendChild(timeInput);
    // divWrapper.appendChild(lbl('form__label', attribute.name));
    wrapper.appendChild(divWrapper);
    wrapper.appendChild(lbl('form__label', attribute.name));
    return wrapper;
}

function timeField(attribute) {
    return tinput(attribute.id, 'time', '--:-- --', 'style__time');
}

function selectField(attribute) {
    let select = document.createElement('select');
    for (let lv of attribute.listValues) {
        let htmlOptionElement = document.createElement('option');
        htmlOptionElement.innerText = lv.value;
        select.appendChild(htmlOptionElement);
    }
    select.setAttribute('class', 'form__input');
    return select;
}

function dataField(attribute) {
    return undefined;
}



class ClientOrder {
    init() {
        let sendOrderProcess = document.getElementById("sendOrderProcess").value;
        console.log("sendOrderProcess: " + sendOrderProcess);


        // if (sendOrderProcess == "sended") {
        //     this.setHeader('Спасибо за заказ!');
        //     this.setBodyText('Мы свяжевся с вами в ближайшее Время!');
        //     this.setButton('Вернуться на главную страницу');
        //     this.absentProcessField();
        // } else
        if (sendOrderProcess == "new") {
            this.setHeader('Заполните поля');
            this.printOrderFields();
            this.setButton('Вызвать мастера!');
            this.setProcessField('toSend');
        } else if (sendOrderProcess == "toSend") {
            console.log("else sendProcess");
            let isValidForm = this.validateInputForm();
            if (isValidForm) {
                this.setHeader('Спасибо за заказ!');
                this.absentProcessField();
                this.absentFields();
                this.setBodyText('Мы свяжевся с вами в ближайшее Время!');
                this.setButton('Вернуться на главную страницу');
            } else {
                console.log('form not valid please check')
            }
        } else if (sendOrderProcess == "") {
            this.returnToMainPage();
        }
    }

    setHeader(headerText) {
        document.getElementById("page_title").innerText = headerText;
    }

    validateInputForm() {
        console.log('validate Form start');
        return true;
    }

    printOrderFields() {
        axios.get('/fixer/api/attrs').then(
            response => {
                var attrs = response.data;
                for (let attribute of attrs) {
                    console.log(attribute);
                    let field = this.fieldRenderer(attribute)
                    if (field) {
                        addElement('fields_group', field)
                    }
                }
                console.log(attrs);
            }
        );
    }

    fieldRenderer(attribute) {
        let resultField;
        if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_TEXT)) {
            resultField = wrapInput(attribute, textInput);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_REFERENCE)) {
            resultField = referenceField(attribute);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_DATE)) {
            resultField = dateField(attribute)
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_LIST)) {
            resultField = wrapInput(attribute, selectField);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_DATA)) {
            // resultField = dataField(attribute);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_TIME)) {
            resultField =  wrapInput(attribute, timeField);
        } else if (isEqualAttrType(attribute, AttrTypes.ATTR_TYPE_PHONE_NUMBER)) {
            resultField = wrapInput(attribute, phoneInput);
        }
        return resultField;
    }

    setButton(buttonName) {
        let button = document.getElementById("mainClicker");
        button.innerText = buttonName;
    }

    setBodyText(bodyText) {
        let message = document.createElement('p');
        message.innerText = bodyText;
        addElement('fields_group', message);
    }

    absentProcessField() {
        this.setProcessField("");
    }

    setProcessField(value) {
        document.getElementById("sendOrderProcess").value = value;
    }

    absentFields() {
        document.getElementById('fields_group').replaceChildren();
    }

    returnToMainPage() {
        window.location.replace("/");
    }
}

function isEqualAttrType(attribute, attrType) {
    console.log("attr:" + JSON.stringify(attribute));
    console.log("[TYPE] from attr :" + attribute.type + ", const attrType:" + attrType);
    return attribute.type == attrType;
}

function printOrderFields() {
    ///fixer/api/user/2/order/8
    axios.get('/fixer/api/user/2/order/8').then(
        response => {
            var order = response.data;
            for (param of order.parameters) {
                console.log(param);
                let field = textInput(1, param.attribute.name);
                addElement('fields_group', field)
            }
            console.log(order);
        }
    );
}

async function createUser() {
    var attr = {
        "name": "Date221",
        "type": "DATE"
    }
    let res = await axios.post('/fixer/api/attrs/create', attr);
    console.log(res);
}

function addrTest() {
    var url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";
    var token = "f019553965d0c4ba18ed2ddb215c3c11cfaf4878";
    var query = "москва хабар";

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
            console.log(result)
            console.log("123")
        })
        .catch(error => {
            console.log("error", error)
            console.log("11111")
        });
}
//read about axios await
