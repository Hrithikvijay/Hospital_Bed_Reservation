package Bed_reserve;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validation {
    public static boolean isdigit(String id) {
        Boolean isDigit = true;
        for (int i = 0; i < id.length(); i++) {
            char digit = id.charAt(i);
            if (!Character.isDigit(digit)) {
                isDigit = false;
                break;
            }
        }
        return isDigit;
    }

    
    public static Boolean validatedPassword(String password) {
        // password validation
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=!*])" + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        if (password.equals("")) {
            return false;
        }
        Matcher match = pattern.matcher(password);
        return match.matches();
    }
}
