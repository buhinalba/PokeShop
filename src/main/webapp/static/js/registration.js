import {dataHandler} from "./data_handler.js";


let MIN_PASSWORD_LENGTH = 8;

function initValidator() {
    let registerButton = document.querySelector("#register-button");
    registerButton.addEventListener("click", validateRegistration);
}

function printValidationSuccess(success) {
    console.log(success)
    let pw = document.querySelector(".registration.form #password");
    let passwordLength= pw.value.length;
    console.log(passwordLength);
    if (success && passwordLength >= MIN_PASSWORD_LENGTH) {
        alert("Registration successful!");
    } else if (passwordLength >= MIN_PASSWORD_LENGTH) {
        alert("Email already in use!")
    }
}

function validateRegistration(event) {
    let email = document.querySelector(".registration.form #email");
    console.log(email);
    return !dataHandler.checkIfUserExists(email.value, printValidationSuccess);
}

initValidator();