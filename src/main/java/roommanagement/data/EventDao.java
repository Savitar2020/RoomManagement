package roommanagement.data;

import roommanagement.model.Event;
import roommanagement.model.Reservation;
import roommanagement.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventDao implements Dao<Event, String> {


    /**
     * reads all events in the table "Event"
     *
     * @return list of events
     */
    @Override
    public List<Event> getAll() {
        ResultSet resultSet;
        List<Event> bookList = new ArrayList<>();
        String sqlQuery =
                "SELECT eventId,name, description" +
                        " FROM Room";
        try {
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            while (resultSet.next()) {
                Event event = new Event();
                setValues(resultSet, event);
                bookList.add(event);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {

            MySqlDB.sqlClose();
        }
        return bookList;

    }

    /**
     * reads a event from the table "Event" identified by the eventID
     *
     * @param eventID the primary key
     * @return event object
     */
    @Override
    public Event getEntity(int eventID) {
        ResultSet resultSet;
        Event event = new Event();
        HashMap<Integer, String> hashMap = new HashMap<>();

        String sqlQuery =
                "SELECT roomId,name, description" +
                        " FROM Room" +
                        "WHERE roomID=?";
        try {
            hashMap.put(1, sqlQuery);
            resultSet = MySqlDB.sqlSelect(sqlQuery, hashMap);
            if (resultSet.next()) {
                setValues(resultSet, event);
            }

        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return event;

    }

    /**
     * saves a event in the table "Event"
     *
     * @param event the event object
     * @return Result code
     */
    @Override
    public Result save(Event event) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "REPLACE Event" +
                        " SET eventID='" + event.getEventID() + "'," +
                        " title='" + event.getTitle() + "'," +
                        " description='" + event.getDescription() + "'," +
                        " reservationID='" + event.getReservation().getReservationID() + "'," +
                        " organiser=" + event.getOrganiser() + ",";
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
     * deletes a event in the table "Event" identified by the eventID
     *
     * @param eventID the primary key
     * @return Result code
     */
    @Override
    public Result delete(int eventID) {
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery =
                "DELETE FROM Event" +
                        " WHERE eventID=?";
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
     * @param event     a event object
     * @throws SQLException
     */
    private void setValues(ResultSet resultSet, Event event) throws SQLException {
        event.setEventID(resultSet.getInt("eventID"));
        event.setDescription(resultSet.getString("description"));
        event.setOrganiser(resultSet.getString("organiser"));
        event.setReservation(new Reservation());
        event.getReservation().setReservationID(resultSet.getInt("reservationID"));
        event.getReservation().setReservation(resultSet.getString("reservation"));
        event.setTitle(resultSet.getString("title"));
    }

    /**
     * count all event in table Event
     *
     * @return number of events
     */

    @Override
    public Integer count() {

        ResultSet resultSet = null;
        String sqlQuery;
        int eventCount = 0;
        try {
            sqlQuery =
                    "SELECT COUNT(eventID) " +
                            " FROM Event";
            resultSet = MySqlDB.sqlSelect(sqlQuery);
            if (resultSet.next()) {
                eventCount = resultSet.getInt(1);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.sqlClose();
        }
        return eventCount;
    }
}
