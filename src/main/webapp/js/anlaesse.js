/**
 * view-controller for bookshelf.html
 *
 * M426: RoomManagement
 *
 * @author  Julia Peric
 * @since   2022-20-01
 * @version 1.0
 */


/**
 * loads all the events
 */
$(document).ready(function () {
    console.log("Erfolgreich JS gestartet");
    showMessage("info", "Events werden geladen");
    loadEvents();
    console.log("Service aufruf fertig");
});

/**
 * loads the events from the webservice
 *
 */
function loadEvents() {
    $
        .ajax({
            url: "./resource/events/list",
            dataType: "json",
            type: "GET"
        })
        .done(showEvents)
        .fail(function (xhr) {
            if (xhr.status == 403) {
                location.href = "./login.html";
            } else if (xhr.status == 404) {
                showMessage("info", "Keine Events gefunden");
            } else {
                showMessage("error", "Fehler beim Lesen der Events");
            }
        })
}

/**
 * shows all events as a table
 *
 * @param eventData all events as an array
 */
function showEvents(eventData) {
    var selectData = "";
    $.each(eventData, function (index, event) {
        selectData += "<select>";
        selectData += "<option>" + "dhdh" + "</option>";
        selectData += "</select>";
    });
    $("#eventliste > tbody").html(selectData);
    showMessage("empty", " ");
}