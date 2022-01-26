/**
 * view-controller for detailpage_Reservation.html
 *
 * Roommanagement
 *
 * @author  Julia Peric
 * @since   2022-01-25
 * @version 1.0
 */

/**
 * load the reservation data
 */
$(document).ready(function () {
    showMessage("empty", " ");
    loadEvents().done(loadReservation());

    /**
     * listener for submitting the form
     */
    $("#reservationEditForm").submit(saveReservation());

    /**
     * listener for button [abbrechen], redirects to Reservationslioste
     */
    $("#cancel").click(function () {
        window.location.href = "./reservationsliste.html";
    });

    /**
     * listener for button [Speichern], redirects to Reservationsliste
     */
    $("#save").click(function () {
        window.location.href = "./reservationsliste.html";
    });


});

/**
 * loads all events
 */
function loadEvents() {
    var waiting = $.Deferred();
    $
        .ajax({
            url: "./resource/event/list",
            dataType: "json",
            type: "GET"
        })
        .done(addEvents)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                showMessage("error","Kein Anlass gefunden");
            } else {
                window.location.href = "./reservationsliste.html";
            }
        })

    return waiting;
}

/**
 *  loads the data of this reservation
 *
 */
function loadReservation() {
    var reservationID = $.urlParam('ID');
    if (reservationID !== null && reservationID != -1) {
        $
            .ajax({
                url: "./resource/reservations/read?id=" + reservationID,
                dataType: "json",
                type: "GET"
            })
            .done(showReservation)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 404) {
                    showMessage("error", "Keine Reservation gefunden");
                } else {
                    window.location.href = "./reservationsliste.html";
                }
            })
    }
}

/**
 * adds all events to dropdown
 * @param eventList
 */
function addEvents(eventList) {
    $.each(eventList, function (index, event) {
        $("#eventID").append(new Option(event.event, event.id));
    });
}

/**
 * shows the data of this reservation
 * @param  reservation  the reservation data to be shown
 */
function showReservation(reservation) {
    $("#message").empty();
    $("#raumID").val(reservation.roomName);
    $("#start").val(reservation.start);
    $("#end").val(reservation.end);
    $("#name").val(reservation.tenantName);
    $("#telNr").val(reservation.tenantPhoneNumber);
    $("#status").val(reservation.status);
    var sel = document.getElementById('status');
    var opt = document.createElement('option');
    opt.appendChild( document.createTextNode(reservation.status) );
    opt.value = reservation.status;
    sel.appendChild(opt);
    $("#eventID")
        .val(reservation.event.eventID)
        .change();
}

/**
 * sends the reservation data to the webservice
 * @param form the form being submitted
 */
function saveReservation(form) {
    form.preventDefault();
    var id="";
    if ($.urlParam('id') !== null) {
        id = "?id=" + $.urlParam('id');
    }
    $
        .ajax({
            url: "./resource/reservation/save" + id,
            dataType: "text",
            type: "POST",
            data: $("#reservationEditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./reservationsliste.html";
        })
        .fail(function (xhr, status, errorThrown) {
            showMessage("error", xhr.responseText);
        })
}