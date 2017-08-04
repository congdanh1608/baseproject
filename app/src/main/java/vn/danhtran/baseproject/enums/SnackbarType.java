package vn.danhtran.baseproject.enums;

/**
 * Created by danhtran on 09/03/2017.
 */
public enum SnackbarType {
    SUCCESS(0),
    ERROR(1),
    INFO(2),
    NORMAL(3),
    WARNING(4);

    private int value;

    private SnackbarType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SnackbarType fromValue(int value) {
        for (SnackbarType requestCode : SnackbarType.values())
            if (requestCode.getValue() == value)
                return requestCode;
        return null;
    }
}
