package ra.edu.presentation.admin;

import ra.edu.presentation.technology.TechnologyMain;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdminMain {
    public static void run() {
        int choice;
        do {
            System.out.println("=== CHÀO MỪNG BẠN ĐẾN VỚI HỆ THỐNG QUẢN LÍ TUYỂN DỤNG ===");
            System.out.println("1. quản lí công nghệ tuyển dụng");
            System.out.println("2. quản lí ứng viên");
            System.out.println("3. quản lí vị trí tuyển dụng");
            System.out.println("4. quản lí đơn ứng tuyển");
            System.out.println("5. Đăng xuất");
            System.out.println("====================================================");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    TechnologyMain.run();
                    break;
                case 2:
                    System.out.println("Quản lý ứng viên");
                    // Gọi phương thức quản lý ứng viên
                    break;
                case 3:
                    System.out.println("Quản lý vị trí tuyển dụng");
                    // Gọi phương thức quản lý vị trí tuyển dụng
                    break;
                case 4:
                    System.out.println("Quản lý đơn ứng tuyển");
                    // Gọi phương thức quản lý đơn ứng tuyển
                    break;
                case 5:
                    System.out.println("Đăng xuất thành công");
                    try {
                        FileWriter writer = new FileWriter("login_token.txt");
                        writer.write("");
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Lỗi khi xóa token: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }while (choice != 5);
    }

}
