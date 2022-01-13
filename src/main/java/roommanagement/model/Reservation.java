package roommanagement.model;

import javax.ws.rs.FormParam;
import java.util.Date;

public class Reservation {

    @FormParam("reservationId")
    private int reservationId;

    @FormParam("start")
    private String start;

    @FormParam("end")
    private String end;

    @FormParam("roomId")
    private int roomId;

    private String reservation;

    @FormParam("tenantPhoneNumber")
    private String tenantPhoneNumber;

    @FormParam("tenantName")
    private String tenantName;



    /**
     * default constructor
     */

    public Reservation() {

    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public String getTenantPhoneNumber() {
        return tenantPhoneNumber;
    }

    public void setTenantPhoneNumber(String tenantPhoneNumber) {
        this.tenantPhoneNumber = tenantPhoneNumber;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
