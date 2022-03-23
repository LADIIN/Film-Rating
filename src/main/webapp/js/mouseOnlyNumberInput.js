window.onload = () => {
    const mouseOnlyNumberInputField = document.getElementById("number-input");
    mouseOnlyNumberInputField.addEventListener("keypress", (event) => {
        event.preventDefault();
    });
}
