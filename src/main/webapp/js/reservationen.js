/**
 * view-controller for bookshelf.html
 *
 * M151: BookDB
 *
 * @author  Gian Niemann
 * @since   2021-16-12
 * @version 1.0
 */

/**
 * loads all the reservations
 */
$(document).ready(function () {
    showMessage("info", "Reservationen werden geladen");
    loadReservations();
});

/**
 * loads the reservations from the webservice
 *
 */
function loadReservations() {
    $
        .ajax({
            url: "./resource/reservations/list",
            dataType: "json",
            type: "GET"
        })
        .done(showReservations())
        .fail(function (xhr) {
            if (xhr.status == 403) {
                location.href = "./login.html";
            } else if (xhr.status == 404) {
                showMessage("info", "Keine Reservationen gefunden");
            } else {
                showMessage("error", "Fehler beim Lesen der Reservationen");
            }
        })
}

/**
 * shows all reservations as a table
 *
 * @param reservationData all books as an array
 */
function showReservations(reservationData) {
    var tableData = "";
    $.each(reservationData, function (index, reservation) {
        tableData += "<tr>";
        tableData += "<td>" + reservation.start + "</td>";
        tableData += "<td>" + reservation.end + "</td>";
        tableData += "<td>" + reservation.tenantName + "</td>";
        tableData += "<td>" + reservation.tenantPhoneNumber + "</td>";
        tableData += "<td><a>Zurück</a></td>";
        tableData += "</tr>";
    });
    $("#reservationliste > tbody").html(tableData);
    showMessage("empty", " ");
}