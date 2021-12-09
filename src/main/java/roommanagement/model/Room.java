package roommanagement.model;

import javax.ws.rs.FormParam;

public class Room {

    @FormParam("roomID")
    //@Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private int roomID;

    @FormParam("name")
    //@Size(min=5, max=40)
    private String name;

    @FormParam("imageSrc")
    //@Size(min=5, max=40)
    private String imageSrc;

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @FormParam("description")
    //@Size(min=3, max=40)
    private String description;

    private String room;


    /**
     * @return the roomID
     */
    public int getRoomID() {
        return roomID;
    }


    /**
     * sets the roomID if valid
     *
     * @param roomID the roomID to be set
     */
    public void setRoomID(int roomID) {
        if (roomID != 0) {
            if (Reservation.idCheck(String.valueOf(roomID))) {
                this.roomID = roomID;
            }
            roomID = 0;
        }
        this.roomID = roomID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the end
     *
     * @param name the value to set
     */
    public void setName(String name) {
        this.name = name;
    }



    /**
     * Gets the room
     *
     * @return value of room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets the room
     *
     * @param room the value to set
     */

    public void setRoom(String room) {
        this.room = room;
    }

}
