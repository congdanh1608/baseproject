package vn.danhtran.baseproject.enums;

/**
 * Created by SilverWolf on 09/04/2017.
 */

public enum Sub {
    NONE(""),
    JOIN_EVENT_DAY("checkin");

    private final String value;

    private Sub(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
