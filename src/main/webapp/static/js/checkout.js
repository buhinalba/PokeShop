const RE = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
const LETTERS = /^[a-zA-Z]+$/;
const DIGITS = /\d/;
const MIN_LENGTH = 2;
const MIN_NAME_LENGTH = 5;
const CVV_LENGTH = 3;
const CARD_NUM_LENGTH = 16;


// TODO check valid date

function initCheckout() {
    let checkOutButton = document.querySelector("#checkout-button");
    checkOutButton.addEventListener("click", checkoutValidation)
}

function checkoutValidation() {
    if (!validBillingInformaion()) {
        window.alert("Invalid Billing Information!")
    } else if (!validPaymentInformation()){
        window.alert("Invalid Payment Information!")
    } else {
        let jsonObject = JSON.stringify(objectFromInput());
        console.log(jsonObject);
    }
}

function objectFromInput() {
    let inputObject = new Object();
    inputObject.billing_information = jsonObjectFromBillingInfo();
    inputObject.payment_information = jsonObjectFromPaymentInfo();

    return inputObject;
}

function jsonObjectFromBillingInfo() {
    let billingInfoObject = new Object();
    billingInfoObject.full_name = document.querySelector("#fname").value;
    billingInfoObject.email = document.querySelector("#email").value;
    billingInfoObject.address = document.querySelector("#adr").value;
    billingInfoObject.city = document.querySelector("#city").value;
    billingInfoObject.state = document.querySelector("#state").value;
    billingInfoObject.zip = document.querySelector("#zip").value;

    return billingInfoObject;
}

function jsonObjectFromPaymentInfo() {
    let paymentInfoObject = new Object();
    paymentInfoObject.card_name = document.querySelector("#ccname").value;
    paymentInfoObject.card_number = document.querySelector("#ccnum").value;
    paymentInfoObject.exp_date = document.querySelector("#expdate").value;
    paymentInfoObject.cvv = document.querySelector("#cvv").value;

    return paymentInfoObject;
}

function validBillingInformaion() {
    return validFullnameInput() && validEmailInput() && validAddressInput() && validCityInput() && validStateInput() && validZipInput();
}

function validFullnameInput() {
    let fullName = document.querySelector("#fname");
    fullName.classList.remove("error")
    if (fullName.value.length <= 4 || /\d/.test(fullName.value) || RE.test(fullName.value)) {
        fullName.classList.add("error");
        return false;
    }
    return true;
}

function validEmailInput() {
    let email = document.querySelector("#email");
    email.classList.remove("error");
    if (email.value.length < MIN_NAME_LENGTH || !email.value.match(RE)) {
        email.classList.add("error");
        return false;
    }
    return true
}

function validAddressInput() {
    let address = document.querySelector("#adr");
    address.classList.remove("error");
    if (address.value.length < MIN_NAME_LENGTH || RE.test(address.value)) {
        address.classList.add("error");
        return false;
    }
    return true;
}

function validCityInput() {
    let city = document.querySelector("#city");
    city.classList.remove("error");
    if (city.value.length < MIN_LENGTH || RE.test(city.value) || DIGITS.test(city.value)) {
        city.classList.add("error");
        return false;
    }
    return true;
}

function validStateInput() {
    let state = document.querySelector("#state");
    state.classList.remove("error");
    if (state.value.length < MIN_LENGTH || DIGITS.test(state.value)) {
        state.classList.add("error");
        return false;
    }
    return true;
}

function validZipInput() {
    let zipCode = document.querySelector("#zip");
    zipCode.classList.remove("error");
    if (zipCode.value.length < MIN_LENGTH || zipCode.value.match(LETTERS)) {
        zipCode.classList.add("error");
        return false;
    }
    return true;
}

function validPaymentInformation() {
    return validCardName() && validCardNumber() && validCvv();
}

function validCardName() {
    let cardName = document.querySelector("#ccname");
    cardName.classList.remove("error");
    if (cardName.value.length < MIN_NAME_LENGTH || DIGITS.test(cardName.value)) {
        cardName.classList.add("error");
        return false;
    }
    return true;
}

function validCardNumber() {
    let cardNumber = document.querySelector("#ccnum");
    cardNumber.classList.remove("error");
    if (cardNumber.value.length !== CARD_NUM_LENGTH || cardNumber.value.match(LETTERS)) {
        cardNumber.classList.add("error");
        return false;
    }
    return true;
}

function validCvv() {
    let cvv = document.querySelector("#cvv");
    cvv.classList.remove("error");
    if (cvv.value.length !== CVV_LENGTH || cvv.value.match(LETTERS)) {
        cvv.classList.add("error");
        return false;
    }
    return true;
}

initCheckout();