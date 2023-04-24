
let backendUrl = "http://localhost:8080/currencies"

window.addEventListener('load', () =>{
    let exchangeForm = document.getElementsByClassName('exchangeRateForm')[0];
    exchangeForm.addEventListener('submit', event => getExchangeRate(event));

    let minMaxExchangeForm = document.getElementsByClassName('minMaxExchangeRateForm')[0];
    minMaxExchangeForm.addEventListener('submit', event => getMinMaxExchangeRate(event));

    let majorDifferenceForm = document.getElementsByClassName('majorDifferenceForm')[0];
    majorDifferenceForm.addEventListener('submit', event => getMajorDifference(event));
})


function getExchangeRate(event){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayResult(this.responseText);
        }else if (this.readyState === 4 && (this.status === 404 || this.status === 400)){
            alert("Something went wrong");
        }
    };
    event.preventDefault();

    let currencyCode = document.getElementById('currencyCode_exchangeRateForm').value;
    let date = document.getElementById('quotationDate_exchangeRateForm').value;

    xhttp.open("GET", backendUrl + '/' + currencyCode + '/'+ date, true);
    xhttp.send();

    document.getElementById('currencyCode_exchangeRateForm').value = "";
    document.getElementById('quotationDate_exchangeRateForm').value = "";
}

function getMinMaxExchangeRate(event){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayResult(this.responseText);
        }else if (this.readyState === 4 && (this.status === 404 || this.status === 400)){
            alert("Something went wrong");
        }
    };
    event.preventDefault();

    let currencyCode = document.getElementById('currencyCode_minMaxExchangeRateForm').value;
    let numberOfQuotations = document.getElementById('quotationDate_minMaxExchangeRateForm').value;

    xhttp.open("GET", backendUrl + '/' + currencyCode + '/minAndMax/'+ numberOfQuotations, true);
    xhttp.send();

    document.getElementById('currencyCode_minMaxExchangeRateForm').value = "";
    document.getElementById('quotationDate_minMaxExchangeRateForm').value = "";
}

function getMajorDifference(event){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayResult(this.responseText);
            
        }else if (this.readyState === 4 && (this.status === 404 || this.status === 400)){
            alert("Something went wrong");
        }
    };
    event.preventDefault();

    let currencyCode = document.getElementById('currencyCode_majorDifferenceForm').value;
    let numberOfQuotations = document.getElementById('quotationNumber_majorDifferenceForm').value;

    xhttp.open("GET", backendUrl + '/' + currencyCode + '/maxDifference/'+ numberOfQuotations, true);
    xhttp.send();

    document.getElementById('currencyCode_majorDifferenceForm').value = "";
    document.getElementById('quotationNumber_majorDifferenceForm').value = "";
}

function displayResult(jsonString){
    let jsonObject = JSON.parse(jsonString);
    let keys = Object.keys(jsonObject);

    // create the string to display
    var displayString = "";
    for(let i = 0; i< keys.length; i++){

        displayString += keys[i] + " - " + jsonObject[keys[i]] +"\n"; 
    }

    alert(displayString);

}