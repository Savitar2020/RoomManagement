package roommanagement.data;

import roommanagement.model.Room;
import roommanagement.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomDao implements Dao<Room, String>{


    /**
     * reads all Room in the table "Room"
     * @return list of Room
     */
    @Override
    public List<Room> getAll() {
        ResultSet resultSet;
        List<Room> roomList = new ArrayList<>();
        String sqlQuery =
                "SELECT roomId, name, description, imageSrc" +
                " FROM Room";
        try {
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Room room = new Room();
                setValues(resultSet, room);
                roomList.add(room);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }
        return roomList;

    }

    /**
     * reads a room from the table "Room" identified by the roomID
     * @param roomID the primary key
     * @return room object
     */
    @Override
    public Room getEntity(int roomID) {
        ResultSet resultSet;
        Room room = new Room();
        HashMap<Integer, String> hashMap = new HashMap<>();

        String sqlQuery =
                "SELECT roomId,name, description, imageSrc" +
                " FROM Room"+
                "WHERE roomID=?";
        try {
            hashMap.put(1, sqlQuery);
            resultSet = MySqlDB.sqlSelect(sqlQuery, hashMap);
            if (resultSet.next()) {
                setValues(resultSet, room);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return room;

    }

    /**
     * saves a room in the table "Room"
     * @param room the room object
     * @return Result code
     */
    @Override
    public Result save(Room room) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "REPLACE Room" +
                        " SET roomID='" + room.getRoomID() + "'," +
                        " name='" + room.getName() + "'," +
                        " descripton='" + room.getDescription() + "'," +
                        " imageSrc='" + room.getImageSrc() + "'," +
                        " room=" + room.getRoom() + ",";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            int affectedRows = prepStmt.executeUpdate();
            if (affectedRows <= 2) {
                return Result.SUCCESS;
            } else if (affectedRows == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * deletes a room in the table "Room" identified by the roomID
     * @param roomID the primary key
     * @return Result code
     */
    @Override
    public Result delete(int roomID) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "DELETE FROM Room" +
                        " WHERE roomID=?";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            int affectedRows = prepStmt.executeUpdate();
            if (affectedRows == 1) {
                return Result.SUCCESS;
            } else if (affectedRows == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * sets the values of the attributes from the resultset
     *
     * @param resultSet the resultSet with an entity
     * @param room      a room object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Room room) throws SQLException {
        room.setRoomID(resultSet.getInt("roomID"));
        room.setName(resultSet.getString("name"));
        room.setDescription(resultSet.getString("description"));
        room.setImageSrc(resultSet.getString("imageSrc"));
    }

    /**
     * count all room in table Room
     *
     * @return number of rooms
     */

    @Override
    public Integer count() {

        ResultSet resultSet = null;
        String sqlQuery;
        int roomCount = 0;
        try {
            sqlQuery =
                    "SELECT COUNT(roomID) " +
                            " FROM Room";
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            if (resultSet.next()) {
                roomCount = resultSet.getInt(1);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return roomCount;
    }
}
