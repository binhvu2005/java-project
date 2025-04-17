package ra.edu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PasswordField {
    public static String readPassword(String prompt) {
        MaskingThread maskingThread = new MaskingThread(prompt);
        Thread thread = new Thread(maskingThread);
        thread.start();

        // Đọc mật khẩu từ input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";
        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Khi đã đọc xong mật khẩu thì dừng thread mask
        maskingThread.stopMasking();
        return password;
    }
}