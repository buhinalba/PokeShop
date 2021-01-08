import {dataHandler} from "./data_handler.js";



function initValidator() {
    let registerButton = document.querySelector("#register-button");
    registerButton.addEventListener("click", validateLogin);
}

function printValidationSuccess(success) {
    console.log(success)

    if (success) {
        alert("Login successful!");
    } else {
        alert("Email or Password incorrect")
    }
}

function validateLogin(event) {
    let email = document.querySelector(".registration.form #email");
    let password = document.querySelector(".registration.form #password");
    dataHandler.validateUser(email.value, password.value, printValidationSuccess);
}

initValidator();