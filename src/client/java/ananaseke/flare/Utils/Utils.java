package ananaseke.flare.Utils;

public class Utils {
    public static String removeColorTags(String string) {
        return string.replaceAll("(§[0-9a-fklmnor])", "");
    }
}
