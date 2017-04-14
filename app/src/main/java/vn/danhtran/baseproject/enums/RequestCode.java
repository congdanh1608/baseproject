package vn.danhtran.baseproject.enums;

/**
 * Created by danhtran on 09/03/2017.
 */
public enum RequestCode {
    RC_GG_SIGN_IN(9989),
    MY_PERMISSIONS_REQUEST_LOCATION(9995),
    MY_PERMISSIONS_REQUEST_READ_CONTACTS(9996),
    MY_UPDATE_FEED_FINALIZE(9991),
    BOTTOM_BAR_SELECTED(9992),
    SEARCH_FILTER_CODE(9993),
    MY_PERMISSIONS_REQUEST_CALENDAR(9994),
    CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE(100),
    GALLERY_ACTIVITY_REQUEST_CODE(101),
    GALLERY_INTENT_CALLED(102),
    ALARM_REQUEST_CODE(103),
    CALENDAR_RESULT_CODE(104),
    RATING_RESULT_CODE(105),
    NOTIFICATION_ID(106),                       // id to handle the notification in the notification tray
    NOTIFICATION_ID_BIG_IMAGE(107);

    private int value;

    private RequestCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RequestCode fromValue(int value) {
        for (RequestCode requestCode : RequestCode.values())
            if (requestCode.getValue() == value)
                return requestCode;
        return null;
    }
}
