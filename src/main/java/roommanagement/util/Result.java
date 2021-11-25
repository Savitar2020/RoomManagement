
package roommanagement.util;

/**
 * resultcodes for data layer
 * <p>
 * M426: RoomManagementDb
 *
 * @author Julia Peric
 * @version 1.0
 * @since 2021-11-25
 */
public enum Result {
    SUCCESS(0), // command was successfully executed
    NOACTION(1), // nothing to be done
    ERROR(9); // there was an error => throw exception

    private int code;

    Result(int code) {
        this.setCode(code);
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

}
