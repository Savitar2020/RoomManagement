package roommanagement.model;

import javax.ws.rs.FormParam;
import java.util.Date;

public class Reservation {

    @FormParam("reservationID")
    //@Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private int reservationID;

    @FormParam("start")
    //@Size(min=5, max=40)
    private String start;

    @FormParam("end")
    //@Size(min=3, max=40)
    private String end;

    @FormParam("Room_roomID")
    //@DecimalMin(value="0.05")
    //@DecimalMax(value="199.95")
    private int Room_roomID;

    private String reservation;
    private Room room;



    /**
     * default constructor
     */

    public Reservation() {

    }

    /**
     * @return the reservationID
     */
    public int getReservationID() {
        return reservationID;
    }


    /**
     * sets the reservationID if valid
     *
     * @param reservationID the reservationID to be set
     */
    public void setReservationID(int reservationID) {
        if (reservationID != 0) {
            if (idCheck(String.valueOf(reservationID))) {
                this.reservationID = reservationID;
            }
            reservationID = 0;
        }
        this.reservationID = reservationID;
    }

    /**
     * @return the end
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end
     *
     * @param end the value to set
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start
     *
     * @param start the value to set
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return the Room_roomID
     */
    public int getRoom_roomID() {
        return Room_roomID;
    }

    /**
     * Sets the room_roomID
     *
     * @param room_roomID the value to set
     */
    public void setRoom_roomID(int room_roomID) {
        Room_roomID = room_roomID;
    }


    public static boolean idCheck(String ID) {
        char[] idChar = ID.toCharArray();
        boolean firstHalf = false;
        boolean secHalf = false;
        for (int i = 0; i < 5; ++i) {//Check first half
            if ((idChar[i] > 47 && idChar[i] < 58)) {//Checks ascii vals to see if valid ID
                firstHalf = true;
            }
        }

        for (int i = 5; i < idChar.length; ++i) {//Check second half
            if ((idChar[i] > 47 && idChar[i] < 58)) {//Checks ascii vals to see if valid ID
                secHalf = true;
            }
        }

        //If all values are valid, returns true.
        if (firstHalf && secHalf && idChar[4] == '-' && ID.length() == 9) {
            return true;
        }

        return false;
    }

    /**
     * Gets the reservation
     *
     * @return value of reservation
     */
    public String getReservation() {
        return reservation;
    }

    /**
     * Sets the reservation
     *
     * @param reservation the value to set
     */

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


}
