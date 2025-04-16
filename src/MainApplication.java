import ra.edu.business.service.admin.AdminSevice;
import ra.edu.business.service.admin.AdminSeviceImp;
import ra.edu.presentation.admin.AdminMain;
import ra.edu.utils.PasswordField;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainApplication {
    static Scanner scanner = new Scanner(System.in);
    static final String TOKEN_FILE = "login_token.txt";

    public static void main(String[] args) throws Exception {
        while (true) {
            String token = readToken();
            if (token != null) {
                System.out.println("\nBạn đã đăng nhập với token: " + token);
                System.out.println("Đang xử lý...");
                TimeUnit.SECONDS.sleep(randomDelay());
                AdminMain.run();
            }

            System.out.println("\n============= CHỌN VAI TRÒ ===============");
            System.out.println("1. Đăng nhập với vai trò quản trị viên");
            System.out.println("2. Đăng nhập với vai trò ứng viên");
            System.out.println("3. Đăng ký với vai trò ứng viên");
            System.out.println("4. Thoát");
            System.out.println("============================================");
            System.out.print("Lựa chọn (1-4): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số từ 1 đến 4.");
                continue;
            }

            switch (choice) {
                case 1:
                    loginAsAdmin();
                    break;
                case 2:
                    loginAsCandidate();
                    break;
                case 3:
                    registerCandidate();
                    break;
                case 4:
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void loginAsAdmin() throws Exception {
        System.out.println("\n=== ĐĂNG NHẬP VỚI VAI TRÒ QUẢN TRỊ VIÊN ===");
        System.out.print("Nhập tên đăng nhập: ");
        String adminName = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());

        AdminSevice adminService = new AdminSeviceImp();
        adminService.registerAdmin();
        boolean isLogin = adminService.loginAdmin(adminName, password);
        if (isLogin) {
            String token = adminName + ":" + password;
            saveToken(token);
            System.out.println("Đăng nhập thành công với vai trò quản trị viên.");
            AdminMain.run();
        } else {
            System.out.println("Tên đăng nhập hoặc mật khẩu không đúng.");
        }

    }

    private static void loginAsCandidate() throws Exception {
        System.out.println("\n=== ĐĂNG NHẬP VỚI VAI TRÒ ỨNG VIÊN ===");
        System.out.print("Nhập tên đăng nhập: ");
        String candidateName = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String candidatePassword = scanner.nextLine();
        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());
        // TODO: Gọi phương thức đăng nhập cho ứng viên
    }

    private static void registerCandidate() throws Exception {
        System.out.println("\n=== ĐĂNG KÝ VỚI VAI TRÒ ỨNG VIÊN ===");
        System.out.print("Nhập tên đăng nhập: ");
        String registerName = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String registerPassword = scanner.nextLine();
        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());
        // TODO: Gọi phương thức đăng ký cho ứng viên
    }


    static void saveToken(String token) {
        try (FileWriter writer = new FileWriter(TOKEN_FILE)) {
            writer.write(token);
        } catch (IOException e) {
            System.out.println("Không thể lưu token.");
        }
    }

    static String readToken() {
        try (BufferedReader br = new BufferedReader(new FileReader(TOKEN_FILE))) {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    static int randomDelay() {
        return new java.util.Random().nextInt(2) + 1; // 1–2 giây
    }


}
