package roommanagement.model;

import javax.ws.rs.FormParam;


public class Event {


    @FormParam("eventID")
    //@Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private int eventID;


    private Reservation reservation;

    @FormParam("description")
    //@Size(min=3, max=40)
    private String description;

    @FormParam("organiser")
    //@DecimalMin(value="0.05")
    //@DecimalMax(value="199.95")
    private String organiser;

    @FormParam("title")
    //@Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private String title;


    /**
     * default constructor
     */

    public Event() {

    }

    /**
     * @return the eventID
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * sets the eventID if valid
     *
     * @param eventID the eventID to be set
     */
    public void setEventID(int eventID) {
        if (eventID != 0) {
            if (idCheck(String.valueOf(eventID))) {
                this.eventID = eventID;
            }
            eventID = 0;
        }
        this.eventID = eventID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }


    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
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
        if (firstHalf == true && secHalf == true && idChar[4] == '-' && ID.length() == 9) {
            return true;
        }

        return false;
    }
}