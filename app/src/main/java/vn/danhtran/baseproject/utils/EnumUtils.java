package vn.danhtran.baseproject.utils;

/**
 * Created by danhtran on 29/11/2016.
 */
public class EnumUtils {
    public static <T extends Enum<T>> T valueOf(final Class<T> enumType, final String value, final boolean caseSensitive) {
        final T[] enumConstants = enumType.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }
        for (final T enumConstant : enumConstants) {
            if (caseSensitive) {
                if (enumConstant.toString().equals(value)) {
                    return enumConstant;
                }
            } else {
                if (enumConstant.toString().equalsIgnoreCase(value)) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

    public static <T extends Enum<T>> T valueOf(final Class<T> enumType, final String value) {
        final T[] enumConstants = enumType.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }
        for (final T enumConstant : enumConstants) {
            if (enumConstant.toString().equals(value)) {
                return enumConstant;
            }
        }
        return null;
    }
}
