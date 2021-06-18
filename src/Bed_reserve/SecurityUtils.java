package Bed_reserve;

public class SecurityUtils {
    public static String decryption(String password) {
        String decrypt = "";
        for (int i = 0; i < password.length(); i++) {
            decrypt += (char) (password.charAt(i) - 1);
        }
        return decrypt;
    }

    public static String encryption(String password) {
        String encrypt = "";
        for (int i = 0; i < password.length(); i++) {
            encrypt += (char) (password.charAt(i) + 1);
        }
        return encrypt;
    }
}
