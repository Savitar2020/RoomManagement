package roommanagement.data;


import roommanagement.model.Reservation;
import roommanagement.model.Room;
import roommanagement.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
                "SELECT reservationId ,start, end, roomName, tenantPhoneNumber, tenantName, status, eventId" +
                        " FROM Reservation"+
                " WHERE DATEDIFF(start, CURDATE()) < 8 AND DATEDIFF(start, CURDATE()) > -1";
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
     * reads all reservations in the table "Reservation"
     *
     * @return list of Reservation
     */
    @Override
    public List<Reservation> getAllByFilter(String room, String start, String mieter) {
        ResultSet resultSet;
        List<Reservation> reservationList = new ArrayList<>();
        String filter = "";
        if(room != null){
            if(!filter.isEmpty()){
                filter += " AND ";
            }
            filter += "roomName = '";
            filter+=room;
            filter +=  "'";
        }
        if(start  != null){
            if(!filter.isEmpty()){
                filter += " AND ";
            }
            filter += "start = DATE ('";
            filter+= start;
            filter +=  "')";
        }
        if(mieter  != null){
            if(!filter.isEmpty()){
                filter += " AND ";
            }
            filter += "tenantName = '";
            filter += mieter;
            filter +=  "'";
        }
        String sqlQuery =
                "SELECT reservationId ,start, end, roomName, tenantPhoneNumber, tenantName, status, eventId" +
                        " FROM Reservation" +
                        " WHERE DATEDIFF(start, CURDATE()) > -1 AND " + filter;
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
     * @param reservationId the primary key
     * @return room object
     */
    @Override
    public Reservation getEntity(int reservationId) {
        ResultSet resultSet;
        Reservation reservation = new Reservation();
        HashMap<Integer, String> hashMap = new HashMap<>();

        String sqlQuery =
                "SELECT reservationId,start, end, roomName, tenantPhoneNumber,tenantName, status, eventId" +
                        " FROM Reservation" +
                        " WHERE reservationId=?";
        try {
            hashMap.put(1, String.valueOf(reservationId));
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
        Map<Integer, String> values = new HashMap<>();
        String sqlQuery;
        if (reservation.getReservationId() == 0) {
            reservation.setReservationId(2000);
            sqlQuery = "INSERT INTO Reservation";
        } else {
            sqlQuery = "REPLACE Reservation";
        }
        sqlQuery += " SET reservationId=?," +
                " start=?," +
                " end=?," +
                " roomName=?," +
                " tenantName=?," +
                " tenantPhoneNumber=?" +
                " status=?," +
                " eventId=?";

        values.put(1, String.valueOf(reservation.getReservationId()));
        values.put(2, reservation.getStart());
        values.put(3, reservation.getEnd());
        values.put(4, reservation.getRoomName());
        values.put(5, reservation.getTenantName());
        values.put(6, reservation.getTenantPhoneNumber());
        values.put(7, reservation.getStatus());
        values.put(8, reservation.getEventId());

        try {
            return MySqlDB.sqlUpdate(sqlQuery, values);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            System.out.println(sqlEx.getSQLState());
            if(sqlEx.getSQLState().equals("23000")){
                return Result.SUCCESS;
            }
            throw new RuntimeException();
        }

    }

    /**
     * deletes a reservation in the table "Reservation" identified by the reservationID
     *
     * @param reservationId the primary key
     * @return Result code
     */
    @Override
    public Result delete(int reservationId){
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "DELETE FROM Reservation" +
                        " WHERE reservationId=?";
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
        reservation.setReservationId(resultSet.getInt("reservationId"));
        reservation.setStart(resultSet.getString("start"));
        reservation.setEnd(resultSet.getString("end"));
        reservation.setRoomName(resultSet.getString("roomName"));
        reservation.setTenantPhoneNumber(resultSet.getString("tenantPhoneNumber"));
        reservation.setTenantName(resultSet.getString("tenantName"));
        reservation.setStatus(resultSet.getString("status"));
        reservation.setEventId(resultSet.getString("eventId"));
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
                    "SELECT COUNT(reservationId) " +
                            " FROM Reservation";
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
