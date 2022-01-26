package roommanagement.model;

import javax.ws.rs.FormParam;
import java.util.Date;

public class Reservation {

    @FormParam("reservationId")
    private int reservationId;

    @FormParam("eventId")
    private int eventId;

    @FormParam("start")
    private String start;

    @FormParam("end")
    private String end;

    @FormParam("roomName")
    private String roomName;

    private String reservation;

    @FormParam("tenantPhoneNumber")
    private String tenantPhoneNumber;

    @FormParam("tenantName")
    private String tenantName;

    @FormParam("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getReservation() {
        return reservation;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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
