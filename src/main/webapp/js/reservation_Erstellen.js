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
    var start="";
    var end="";
    var tenantName="";
    var tenantPhoneNumber="";
    var eventId="";
    var roomName="";

    if ($.urlParam('id') !== null) {
        id = "?id=" + $.urlParam('id');
        start = "&start=" + $.urlParam('start');
        end = "&end=" + $.urlParam('end');
        roomName = "&roomName=" + $.urlParam('roomName');
        tenantName = "&tenantName=" + $.urlParam('tenantName');
        tenantPhoneNumber = "&tenantPhoneNumber=" + $.urlParam('tenantPhoneNumber');
        eventId = "&eventId=" + $.urlParam('eventId');
//?roomName=asdfasdf&start=2022-01-29+00%3A00%3A00&end=2022-01-29+00%3A00%3A00&eventId=test&status=test&tenantName=sadsd&tenantPhoneNumber=0980980809
    }

    $
        .ajax({
            url: "./resource/reservation/save" + id + start + end + roomName + tenantName + tenantPhoneNumber + eventId,
            dataType: "text",
            type: "GET"
            //data: $("#reservationEditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./reservationsliste.html";
        })
        .fail(function (xhr, status, errorThrown) {
            showMessage("error", xhr.responseText);
        })
}