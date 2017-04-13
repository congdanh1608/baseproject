package vn.danhtran.baseproject.enums;

/**
 * Created by SilverWolf on 09/04/2017.
 */

public enum Api {
    LOGIN("login"),
    EVENTS("events");

    private final String value;

    private Api(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
