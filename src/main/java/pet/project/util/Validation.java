package pet.project.util;

public class Validation {
    public static boolean isNonNegative(Integer parameter) {
        return parameter != null && parameter >= 0;
    }
    public static boolean isPositive(Integer parameter) {
        return parameter != null && parameter > 0;
    }
}
