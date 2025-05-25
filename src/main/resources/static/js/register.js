function matchPassword() {
    const password = document.getElementById("inputPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        document.getElementById("confirmPassword").setCustomValidity("Passwords do not match");
    } else {
        document.getElementById("confirmPassword").setCustomValidity("");
    }
}

