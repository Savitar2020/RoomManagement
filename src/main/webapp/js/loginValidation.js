function validation() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (username === "Hauswart" && password === "Password") {
        window.open("eventliste.html")
    } else if (username === "Admin" && password === "Password") {
        window.open("reservationsliste.html")
    } else {
        alert("Benutername oder Passwort falsch");
    }
}