/**
 * view-controller for reservation_Erstellen.html
 *
 * Roommanagement
 *
 * @author  Julia Peric
 * @since   2022-01-25
 * @version 1.0
 */

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