function validate() {
    var password = document.getElementById("password");
    var confirmPassword = document.getElementById("confirmPassword");
    if (password.value !== confirmPassword.value) {
        document.getElementById("confirm-error").style.visibility = 'visible';
        password.style.borderColor = '#F73A4C';
        password.style.borderWidth = '3px';
        confirmPassword.style.borderColor = '#F73A4C';
        confirmPassword.style.borderWidth = '3px';
        return false;
    }
    return true;
}