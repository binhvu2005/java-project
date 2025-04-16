package ra.edu.utils;

import java.io.IOException;

public class PasswordField {
    public static String readPassword(String prompt) {
        System.out.print(prompt);

        StringBuilder password = new StringBuilder();
        EraserThread eraser = new EraserThread();
        Thread eraserThread = new Thread(eraser);
        eraserThread.start();

        try {
            while (true) {
                char c = (char) System.in.read();
                // ENTER (Windows: \r\n or Unix: \n)
                if (c == '\n' || c == '\r') {
                    break;
                }


                if (c == 8 || c == 46) { // 8 là '\b', 46 là DEL
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b");
                    }
                } else {
                    password.append(c);
                    System.out.print("*");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        eraser.stopMasking(); // Dừng thread khi nhập xong
        try {
            eraserThread.join(); // Đảm bảo thread đã dừng trước khi tiếp tục
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(); // xuống dòng sau khi nhập xong
        return password.toString();
    }
}
