package roommanagement.service;


import roommanagement.data.Dao;
import roommanagement.data.ReservationDao;
import roommanagement.model.Reservation;
import roommanagement.model.Room;
import roommanagement.util.Result;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for the projects
 * <p>
 * 426: Scrum-Project
 *
 * @author Marcio Duarte
 */
@Path("reservations")
public class ReservationService {

    /**
     * produces a list of all reservations
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listReservations(
            @CookieParam("token") String token
    ) {

        int httpStatus = 200;
        Dao<Reservation, String> reservationDao = new ReservationDao();
        List<Reservation> reservationList = reservationDao.getAll();
        if (reservationList.isEmpty())
            httpStatus = 404;

        if (reservationList.isEmpty()) {
            return Response
                    .status(404)
                    .entity("{\"error\":\"Keine Reservation gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(httpStatus)
                    .entity(reservationList)
                    .build();
        }
    }

    /**
     * reads a single project identified by the reservationID
     *
     * @param reservationID the reservationId in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readReservation(
            @QueryParam("id") int reservationID,
            @CookieParam("token") String token
    ) {
        Dao<Reservation, String> reservationDao = new ReservationDao();
        Reservation reservation = reservationDao.getEntity(reservationID);
        if (reservation.getRoomID() == 0) {
            return Response
                    .status(404)
                    .entity("{\"error\":\"Kein Reservation gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(200)
                    .entity(reservation)
                    .build();
        }
    }

    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveReservation(
            @FormParam("reservationID") int reservationID,
            @FormParam("start") String start,
            @FormParam("end") String end,
            @FormParam("Room_roomId") int roomID

    ) {
        int httpStatus;
        String message;
        Reservation reservation = new Reservation();
        reservation.setReservationID(reservationID);
        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setRoom(new Room());
        reservation.getRoom().setRoomID(roomID);

        Dao<Reservation, String> reservationDao = new ReservationDao();
        Result result = reservationDao.save(reservation);
        if (result == Result.SUCCESS) {
            message = "Reservation gespeichert";
            httpStatus = 200;
        }
        else {
            message = "Fehler beim Speichern der Reservation";
            httpStatus = 500;
        }
        return Response
                .status(httpStatus)
                .entity(message)
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteReservation(
            @QueryParam("id") int reservationID
    ) {
        int httpStatus;
        String message;
        Dao<Reservation, String> reservationDao = new ReservationDao();
        Result result = reservationDao.delete(reservationID);
        if (result == Result.SUCCESS) {
            httpStatus = 200;
            message = "Reservation gelöscht";
        } else if (result == Result.NOACTION) {
            httpStatus = 200;
            message = "Kein Reservation gefunden";
        } else {
            httpStatus = 500;
            message = "Fehler";
        }
        return Response
                .status(httpStatus)
                .entity(message)
                .build();
    }
}
