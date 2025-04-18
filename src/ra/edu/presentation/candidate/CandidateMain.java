package ra.edu.presentation.candidate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CandidateMain {
    public static void run(){
        int choice;
        do {
            System.out.println("====== MENU ỨNG VIÊN ======");
            System.out.println("1. Quản lý thông tin cá nhân");
            System.out.println("2. Xem và nộp đơn ứng tuyển");
            System.out.println("3. Xem đơn đã ứng tuyển");
            System.out.println("4. Đăng xuất");
            System.out.println("5. Thoát");
            System.out.println("===========================");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Quản lý thông tin cá nhân");
                    // Gọi phương thức quản lý thông tin cá nhân
                    break;
                case 2:
                    System.out.println("Xem và nộp đơn ứng tuyển");
                    // Gọi phương thức xem và nộp đơn ứng tuyển
                    break;
                case 3:
                    System.out.println("Xem đơn đã ứng tuyển");
                    // Gọi phương thức xem đơn đã ứng tuyển
                    break;
                case 4:
                    System.out.println("Đăng xuất thành công");
                    try {
                        FileWriter writer = new FileWriter("login_token.txt");
                        writer.write("");
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("Lỗi khi xóa token: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Thoát chương trình");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }while (choice != 5);
    }
}
