package ra.edu.presentation.admin;

import ra.edu.presentation.admin.RecruitmentPositionManagement.RecruitmentPositionManagementMain;
import ra.edu.presentation.admin.candidateManagement.CandidateManagementMain;
import ra.edu.presentation.admin.technology.TechnologyMain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static ra.edu.utils.clearConsole.clearConsoles;

public class AdminMain {
    public static void run() {
        int choice;
        do {
            clearConsoles();
            System.out.println("=== CHÀO MỪNG BẠN ĐẾN VỚI HỆ THỐNG QUẢN LÍ TUYỂN DỤNG ===");
            System.out.println("1. quản lí công nghệ tuyển dụng");
            System.out.println("2. quản lí ứng viên");
            System.out.println("3. quản lí vị trí tuyển dụng");
            System.out.println("4. quản lí đơn ứng tuyển");
            System.out.println("5. Đăng xuất");
            System.out.println("6. Thoát");
            System.out.println("====================================================");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    TechnologyMain.run();
                    break;
                case 2:
                    CandidateManagementMain.run();
                    break;
                case 3:
                    RecruitmentPositionManagementMain.run();
                    break;
                case 4:
                    System.out.println("Quản lý đơn ứng tuyển");
                    // Gọi phương thức quản lý đơn ứng tuyển
                    break;
                case 5:
                    System.out.println("Đăng xuất thành công ✅");
                    try (FileWriter writer = new FileWriter("login_token.txt")) {
                        writer.write("0");
                    } catch (IOException e) {
                        System.out.println("⚠️ Lỗi khi đăng xuất: không thể ghi file token.");
                    }
                    return;
                case 6:
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }while (choice != 6);
    }

}
