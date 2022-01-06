package roommanagement.model;

import javax.ws.rs.FormParam;

public class Room {

    @FormParam("roomId")
    private int roomId;

    @FormParam("name")
    //@Size(min=5, max=45)
    private String name;

    @FormParam("imageSrc")
    //@Size(min=5, max=45)
    private String imageSrc;

    @FormParam("description")
    //@Size(min=3, max=40)
    private String description;

    private String room;

    @FormParam("price")
    private long price;

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    /**
     * @return the roomID
     */
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

}
