package Bed_reserve;

public class SecurityUtils {
    public static String decryption(String pass) {
        String decrypt = "";
        for (int i = 0; i < pass.length(); i++) {
            decrypt += (char) (pass.charAt(i) - 1);
        }
        return decrypt;
    }

    public static String encryption(String pass) {
        String encrypt = "";
        for (int i = 0; i < pass.length(); i++) {
            encrypt += (char) (pass.charAt(i) + 1);
        }
        return encrypt;
    }
}
