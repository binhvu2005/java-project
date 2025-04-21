package ra.edu.presentation.candidate;

import ra.edu.presentation.candidate.profileCandidate.ProfileCandidateMain;

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
                    ProfileCandidateMain.run();
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
                    System.out.println("Đăng xuất thành công ✅");
                    try (FileWriter writer = new FileWriter("login_token.txt")) {
                        writer.write("0"); // Ghi lại token bằng 0 để "đăng xuất"
                    } catch (IOException e) {
                        System.out.println("⚠️ Lỗi khi đăng xuất: không thể ghi file token.");
                    }
                    return;
                case 5:
                    System.out.println("Thoát chương trình");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }while (choice != 5);
    }
}
