package roommanagement.service;


import roommanagement.data.Dao;
import roommanagement.data.RoomDao;
import roommanagement.model.Room;
import roommanagement.util.Result;

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
     * @param roomId the roomID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readRooms(
            @QueryParam("Id") int roomId,
            @CookieParam("token") String token
    ) {
        Dao<Room, String> roomDAO = new RoomDao();
        Room room = roomDAO.getEntity(roomId);
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
            @FormParam("roomId") int roomId,
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("imageSrc") String imageSrc,
            @FormParam("price") long price

    ) {
        int httpStatus;
        String message;
        Room room = new Room();
        room.setRoomId(roomId);
        room.setName(name);
        room.setDescription(description);
        room.setImageSrc(imageSrc);
        room.setPrice(price);

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
            @QueryParam("Id") int roomId
    ) {
        int httpStatus;
        String message;
        Dao<Room, String> roomDao = new RoomDao();
        Result result = roomDao.delete(roomId);
        if (result == Result.SUCCESS) {
            httpStatus = 200;
            message = "Raum gel??scht";
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
