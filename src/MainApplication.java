import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.service.admin.AdminSevice;
import ra.edu.business.service.admin.AdminSeviceImp;
import ra.edu.business.service.candidate.CandidateSevice;
import ra.edu.business.service.candidate.CandidateSeviceImp;
import ra.edu.presentation.admin.AdminMain;
import ra.edu.presentation.candidate.CandidateMain;
import ra.edu.utils.PasswordField;
import ra.edu.validate.Validator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainApplication {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static Scanner scanner = new Scanner(System.in);
    static final String TOKEN_FILE = "login_token.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static void main(String[] args) throws Exception {
        while (true) {
            String token = readToken();
            if (token != null) {
                String[] parts = token.split(":");
                if (parts.length == 2) {
                    String role = parts[0];
                    String identifier = parts[1];
                    if (role.equals("admin")) {
                        System.out.println("Đăng nhập với vai trò quản trị viên: " + identifier);
                        AdminMain.run();
                    } else if (role.equals("candidate")) {
                        System.out.println("Đăng nhập với vai trò ứng viên: " + identifier);
                        CandidateMain.run();
                    }
                }
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
            String token = "admin" + ":" + password;
            saveToken(token);
            System.out.println("Đăng nhập thành công với vai trò quản trị viên.");
            AdminMain.run();
        } else {
            System.out.println("Tên đăng nhập hoặc mật khẩu không đúng.");
        }

    }

    private static void loginAsCandidate() throws Exception {
        System.out.println("\n=== ĐĂNG NHẬP VỚI VAI TRÒ ỨNG VIÊN ===");
        System.out.print("Nhập Email đăng nhập: ");
        String email = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String candidatePassword = scanner.nextLine();
        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());
        CandidateSevice candidateService = new CandidateSeviceImp();
        boolean isLogin = candidateService.loginCandidate(email, candidatePassword);
        if (isLogin) {
            String token = "candidate" + ":" + email;
            saveToken(token);
            System.out.println("Đăng nhập thành công với vai trò ứng viên.");
            CandidateMain.run();
        } else {
            System.out.println("Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }

    private static void registerCandidate() throws Exception {
        System.out.println("\n=== ĐĂNG KÝ VỚI VAI TRÒ ỨNG VIÊN ===");
        String name, email, phone, genderStr, description, password, confirmPassword;
        Date dob;
        CandidateGender gender;

        // Nhập tên
        while (true) {
            System.out.print("Nhập tên của bạn: ");
            name = scanner.nextLine();
            if (Validator.isValidLength(name)) {
                break;
            }
            System.out.println("Tên phải có độ dài từ 10 đến 100 ký tự. Vui lòng thử lại.");
        }

        // Nhập email
        while (true) {
            System.out.print("Nhập địa chỉ email: ");
            email = scanner.nextLine();
            if (Validator.isValidEmail(email)) {
                break;
            }
            System.out.println("Email không hợp lệ. Vui lòng thử lại.");
        }

        // Nhập số điện thoại
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            phone = scanner.nextLine();
            if (Validator.isValidPhone(phone)) {
                break;
            }
            System.out.println("Số điện thoại không hợp lệ. Vui lòng thử lại.");
        }

        // Nhập giới tính
        while (true) {
            System.out.print("Nhập giới tính (Male/Female/Other): ");
            genderStr = scanner.nextLine().trim();
            if (Validator.isValidGender(genderStr)) {
                gender = CandidateGender.valueOf(genderStr.substring(0, 1).toUpperCase() + genderStr.substring(1).toLowerCase());
                break;
            }
            System.out.println("Giới tính không hợp lệ. Vui lòng nhập Male, Female hoặc Other.");
        }

        // Nhập ngày sinh
        while (true) {
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            String dobStr = scanner.nextLine();
            try {
                dob = sdf.parse(dobStr);
                if (Validator.isNotNullDate(dob)) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ngày sinh không hợp lệ.");
            }
            System.out.println("Vui lòng nhập đúng định dạng ngày (dd/MM/yyyy).");
        }

        // Nhập mô tả
        while (true) {
            System.out.print("Nhập mô tả bản thân: ");
            description = scanner.nextLine();
            if (Validator.isValidLength(description)) {
                break;
            }
            System.out.println("Mô tả phải có độ dài từ 10 đến 100 ký tự.");
        }

        // Nhập mật khẩu và xác nhận
        while (true) {
            System.out.print("Nhập mật khẩu (ít nhất 6 ký tự): ");
            password = scanner.nextLine();
            if (password.length() < 6) {
                System.out.println("Mật khẩu phải ít nhất 6 ký tự.");
                continue;
            }

            System.out.print("Xác nhận lại mật khẩu: ");
            confirmPassword = scanner.nextLine();
            if (!password.equals(confirmPassword)) {
                System.out.println("Mật khẩu xác nhận không khớp.");
                continue;
            }

            break;
        }
        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());
        CandidateSevice candidateService = new CandidateSeviceImp();
        candidateService.registerCandidate(name, email, phone, gender, dob, description, password);
        System.out.println("✅ Đăng ký thành công!");
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
