package roommanagement.service;


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
@Path("events")
public class EventService {

    /**
     * produces a list of all events
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listEvents(
            @CookieParam("token") String token
    ) {

        int httpStatus = 200;
        Dao<Event, String> eventDao = new EventDao();
        List<Event> eventList = eventDao.getAll();
        if (eventList.isEmpty())
            httpStatus = 404;

        if (eventList.isEmpty()) {
            return Response
                    .status(404)
                    .entity("{\"error\":\"Keine Events gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(httpStatus)
                    .entity(eventList)
                    .build();
        }
    }

    /**
     * reads a single project identified by the eventId
     *
     * @param eventID the eventID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readEvent(
            @QueryParam("id") int eventID,
            @CookieParam("token") String token
    ) {
        Dao<Event, String> eventDAO = new EventDao();
        Event event = eventDAO.getEntity(eventID);
        if (event.getTitle() == null) {
            return Response
                    .status(404)
                    .entity("{\"error\":\"Kein Projekt gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(200)
                    .entity(event)
                    .build();
        }
    }

    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveEvent(
            @FormParam("eventID") int eventID,
            @FormParam("title") String title,
            @FormParam("reservationID") int reservationID,
            @FormParam("description") String description,
            @FormParam("organiser") String organiser,
            @FormParam("roomID") int roomID

    ) {
        int httpStatus;
        String message;
        Event event = new Event();
        event.setEventID(eventID);
        event.setTitle(title);
        event.setDescription(description);
        event.setOrganiser(organiser);
        event.setRoom(new Room());
        event.getRoom().setRoomID(roomID);
        event.setReservation(new Reservation());
        event.getReservation().setReservationId(reservationID);

        Dao<Event, String> eventDao = new EventDao();
        Result result = eventDao.save(event);
        if (result == Result.SUCCESS) {
            message = "Event gespeichert";
            httpStatus = 200;
        }
        else {
            message = "Fehler beim Speichern des Events";
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
    public Response deleteEvent(
            @QueryParam("id") int eventID
    ) {
        int httpStatus;
        String message;
        Dao<Event, String> eventDao = new EventDao();
        Event result = eventDao.delete(eventID);
        if (result == Result.SUCCESS) {
            httpStatus = 200;
            message = "Event gelöscht";
        } else if (result == Result.NOACTION) {
            httpStatus = 200;
            message = "Kein Event gefunden";
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
