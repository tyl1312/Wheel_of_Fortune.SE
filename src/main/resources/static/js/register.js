function matchPassword() {
    const password = document.getElementById("inputPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const registerBtn = document.getElementById("btnRegister");

    if (password !== confirmPassword) {
        document.getElementById("confirmPassword").setCustomValidity("Passwords do not match");
        registerBtn.disabled = true;
    } else {
        document.getElementById("confirmPassword").setCustomValidity("");
        registerBtn.disabled = false;
    }
}

