package roommanagement.data;


import roommanagement.model.Reservation;
import roommanagement.model.Room;
import roommanagement.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReservationDao implements Dao<Reservation, String> {

    /**
     * reads all reservations in the table "Reservation"
     *
     * @return list of Reservation
     */
    @Override
    public List<Reservation> getAll() {
        ResultSet resultSet;
        List<Reservation> reservationList = new ArrayList<>();
        String sqlQuery =
                "SELECT re.reservationID,re.start, re.end, ro.roomID" +
                        " FROM Reservation AS re JOIN Room AS ro USING (Room_roomId)";
        try {
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                setValues(resultSet, reservation);
                reservationList.add(reservation);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }
        return reservationList;

    }

    /**
     * reads a room from the table "Room" identified by the roomID
     *
     * @param roomID the primary key
     * @return room object
     */
    @Override
    public Reservation getEntity(int roomID) {
        ResultSet resultSet;
        Reservation reservation = new Reservation();
        HashMap<Integer, String> hashMap = new HashMap<>();

        String sqlQuery =
                "SELECT roomId,name, description" +
                        " FROM Room" +
                        "WHERE roomID=?";
        try {
            hashMap.put(1, sqlQuery);
            resultSet = MySqlDB.sqlSelect(sqlQuery, hashMap);
            if (resultSet.next()) {
                setValues(resultSet, reservation);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return reservation;

    }

    /**
     * saves a room in the table "Room"
     *
     * @param reservation the room object
     * @return Result code
     */
    @Override
    public Result save(Reservation reservation) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "REPLACE Room" +
                        " SET reservation='" + reservation.getReservationID() + "'," +
                        " start='" + reservation.getStart() + "'," +
                        " end='" + reservation.getEnd() + "'," +
                        " Room_roomID='" + reservation.getRoom().getRoomID() + "'," +
                        " reservation=" + reservation.getReservation() + "," +
                        " room='" + reservation.getRoom() + "'";
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
     * deletes a reservation in the table "Reservation" identified by the reservationID
     *
     * @param reservationID the primary key
     * @return Result code
     */
    @Override
    public Result delete(int reservationID) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "DELETE FROM Reservation" +
                        " WHERE reservationID=?";
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
     * @param resultSet   the resultSet with an entity
     * @param reservation a reservation object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Reservation reservation) throws SQLException {
        reservation.setReservationID(resultSet.getInt("reservationID"));
        reservation.setStart(resultSet.getString("start"));
        reservation.setEnd(resultSet.getString("end"));
        reservation.setEnd(resultSet.getString("Room_roomID"));
        reservation.setRoom(new Room());
        reservation.getRoom().setRoomID(resultSet.getInt("roomID"));
        reservation.getRoom().setRoom(resultSet.getString("room"));
    }

    /**
     * count all reservations in table Reservation
     *
     * @return number of reservations
     */

    @Override
    public Integer count() {

        ResultSet resultSet = null;
        String sqlQuery;
        int reservationCount = 0;
        try {
            sqlQuery =
                    "SELECT COUNT(reservationID) " +
                            " FROM Room";
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            if (resultSet.next()) {
                reservationCount = resultSet.getInt(1);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return reservationCount;
    }

}
