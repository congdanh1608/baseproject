package vn.danhtran.baseproject.enums;

import vn.danhtran.baseproject.MyApplication;

/**
 * Created by SilverWolf on 09/04/2017.
 */

public enum SharePref {
    SESSION_LOGIN("SESSION_LOGIN"),
    TOKEN_FIREBASE("TOKEN_FIREBASE"),
    PUSH_ID_FIREBASE("PUSH_ID_FIREBASE"),
    OFF_NOTIFY("OFF_NOTIFY"),
    LANGUAGE("LANGUAGE");

    public static final String PREFIX = MyApplication.Instance().getPackageName();

    private final String name;

    private SharePref(String s) {
        name = s;
    }

    public String toString() {
        return PREFIX + this.name;
    }
}
