package Bed_reserve;

import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class SendSms {
    public static void sendSms(String message, String contact) {
        // Here i have used Fast2sms to send sms...
        try {
            String sendId = "BED_RESERVATION_APPLICATION";
            String route = "v3";
            String apiKey = "2krxTyjFlWGc6uNZAEiDvVliziEtYHrWXVjxtfTp3uvixoGZdipTddNLZhRq";
            message = URLEncoder.encode(message, "UTF-8");

            String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey + "&sender_id=" + sendId
                    + "&message=" + message + "&route=" + route + "&numbers=" + contact;
            URL url = new URL(myUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-store");
            con.getResponseCode();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
