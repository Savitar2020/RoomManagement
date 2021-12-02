package roommanagement.service;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for categories
 * <p>
 * M151: Refproject
 *
 * @author Marcio Duarte
 */
@Path("room")
public class RoomService {

    /**
     * produces a list of all rooms
     *
     * @param token encrypted authorization token
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listRooms(
            @CookieParam("token") String token
    ) {

        Dao<Room, String> roomDao = new RoomDao();
        List<Room> roomList = roomDao.getAll();

        if (roomList.isEmpty()) {
            return Response
                    .status(404)
                    .entity("{\"error\":\"Kein Raum gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(200)
                    .entity(roomList)
                    .build();
        }
    }

    /**
     * reads a single room identified by the roomID
     *
     * @param roomID the roomID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readRooms(
            @QueryParam("uuid") int roomID,
            @CookieParam("token") String token
    ) {
        Dao<Room, String> roomDAO = new RoomDao();
        Room room = roomDAO.getEntity(roomID);
        if (room.getName() == null) {
             return Response
                    .status(404)
                    .entity("{\"error\":\"Kein Raum gefunden\"}")
                    .build();
        } else {
            return Response
                    .status(200)
                    .entity(room)
                    .build();
        }
    }

    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saveRoom(
            @FormParam("roomID") int roomID,
            @FormParam("name") String name,
            @FormParam("description") String description

    ) {
        int httpStatus;
        String message;
        Room room = new Room();
        room.setRoomID(roomID);
        room.setName(name);
        room.setDescription(description);


        Dao<Room, String> roomDao = new RoomDao();
        Result result = roomDao.save(room);
        if (result == Result.SUCCESS) {
            message = "Raum gespeichert";
            httpStatus = 200;
        }
        else {
            message = "Fehler beim Speichern des Raums";
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
    public Response deleteRoom(
            @QueryParam("id") int roomID
    ) {
        int httpStatus;
        String message;
        Dao<Room, String> roomDao = new RoomDao();
        Room result = roomDao.delete(roomID);
        if (result == Result.SUCCESS) {
            httpStatus = 200;
            message = "Raum gelöscht";
        } else if (result == Result.NOACTION) {
            httpStatus = 200;
            message = "Kein Raum gefunden";
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
