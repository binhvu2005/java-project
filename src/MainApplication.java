
import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.account.AccountStatus;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.service.login.LoginSevice;
import ra.edu.business.service.login.LoginSeviceImp;
import ra.edu.presentation.admin.AdminMain;
import ra.edu.presentation.candidate.CandidateMain;
import ra.edu.validate.Validator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainApplication {
    static Scanner scanner = new Scanner(System.in);
    static final String TOKEN_FILE = "login_token.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {
        // kiểm tr xem đã đang nhập hay chưa
        File tokenFile = new File(TOKEN_FILE);
        if (tokenFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(tokenFile))) {
                String tokenLine = reader.readLine();
                System.out.println("Token: " + tokenLine);
                if (tokenLine != null && !tokenLine.isBlank()) {
                   if (tokenLine.equals("0")) {
                        System.out.println("⚠️ Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục.");
                    } else {
                        int token = Integer.parseInt(tokenLine);
                        LoginSevice loginService = new LoginSeviceImp();
                        AccountRole role = loginService.getRole(token);
                        if (role == AccountRole.ADMIN) {
                            System.out.println("✅ Đăng nhập thành công với quyền Admin!");
                            AdminMain.run();

                        } else if (role == AccountRole.CANDIDATE) {
                            System.out.println("✅ Đăng nhập thành công với quyền Ứng viên!");
                            CandidateMain.run();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("⚠️ Không thể đọc file token: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Token không hợp lệ, cần là số nguyên.");
            }
        }


        while (true) {
            System.out.println("\n============= MENU =============");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký (Ứng viên)");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng (1-3): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Lựa chọn không hợp lệ. Vui lòng nhập số từ 1 đến 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    registerCandidate();
                    break;
                case 3:
                    System.out.println("👋 Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("⚠️ Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void login() throws Exception {
        System.out.println("\n=== ĐĂNG NHẬP HỆ THỐNG ===");
        System.out.print("Nhập Email đăng nhập: ");
        String email = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());
        LoginSevice loginService = new LoginSeviceImp();
        loginService.registerAdmin();
        Account account = loginService.login(email, password);
        if (account != null) {
            if (account.getStatus()== AccountStatus.INACTIVE) {
                System.out.println("⚠️ Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.");
            }else {
                System.out.println("✅ Đăng nhập thành công!");
                saveToken(account.getId());
                if (account.getRole() == AccountRole.ADMIN) {
                    AdminMain.run();
                } else if (account.getRole() == AccountRole.CANDIDATE) {
                    CandidateMain.run();
                }
            }
        } else {
            System.out.println("❌ Email hoặc mật khẩu không đúng.");
        }
    }

    private static void registerCandidate() throws Exception {
        System.out.println("\n=== ĐĂNG KÝ ỨNG VIÊN ===");

        String name, email, phone, genderStr, description, password, confirmPassword;
        Date dob;
        int experience;
        CandidateGender gender;

        // Tên
        while (true) {
            System.out.print("Nhập tên: ");
            name = scanner.nextLine();
            if (Validator.isValidLength(name)) break;
            System.out.println("⚠️ Tên phải có từ 10 đến 100 ký tự.");
        }

        // Email
        while (true) {
            System.out.print("Nhập email: ");
            email = scanner.nextLine();
            if (Validator.isValidEmail(email)) break;
            System.out.println("⚠️ Email không hợp lệ.");
        }

        // SĐT
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            phone = scanner.nextLine();
            if (Validator.isValidPhone(phone)) break;
            System.out.println("⚠️ Số điện thoại không hợp lệ.");
        }

        // Giới tính
        while (true) {
            System.out.print("Nhập giới tính (Male/Female/Other): ");
            genderStr = scanner.nextLine().trim().toUpperCase();
            if (Validator.isValidGender(genderStr)) {
                gender = CandidateGender.valueOf(genderStr);
                break;
            }
            System.out.println("⚠️ Giới tính không hợp lệ.");
        }

        // Ngày sinh
        while (true) {
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            String dobStr = scanner.nextLine();
            try {
                dob = sdf.parse(dobStr);
                if (Validator.isNotNullDate(dob)) break;
            } catch (Exception e) {
                System.out.println("⚠️ Ngày sinh không hợp lệ.");
            }
        }
        // Kinh nghiệm
        while (true) {
            System.out.print("Nhập số năm kinh nghiệm (0-100): ");
            String experienceStr = scanner.nextLine();
            try {
                experience = Integer.parseInt(experienceStr);
                if (Validator.isValidExperience(experience)) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Kinh nghiệm không hợp lệ.");
            }
        }

        // Mô tả
        while (true) {
            System.out.print("Nhập mô tả bản thân: ");
            description = scanner.nextLine();
            if (Validator.isValidLength(description)) break;
            System.out.println("⚠️ Mô tả phải từ 10 đến 100 ký tự.");
        }

        // Mật khẩu
        while (true) {
            System.out.print("Nhập mật khẩu (≥ 6 ký tự): ");
            password = scanner.nextLine();
            if (password.length() < 6) {
                System.out.println("⚠️ Mật khẩu quá ngắn.");
                continue;
            }

            System.out.print("Xác nhận mật khẩu: ");
            confirmPassword = scanner.nextLine();
            if (!password.equals(confirmPassword)) {
                System.out.println("⚠️ Mật khẩu xác nhận không khớp.");
                continue;
            }
            break;
        }

        System.out.println("\nĐang xử lý...");
        TimeUnit.SECONDS.sleep(randomDelay());

        LoginSevice service = new LoginSeviceImp();
        service.registerCandidate(name, email, phone, gender, new java.sql.Date(dob.getTime()),experience, description, password);

    }

    static void saveToken(Integer id) {
        if (id == null || id <= 0) {
            System.out.println("⚠️ ID không hợp lệ, không thể lưu token.");
            return;
        }

        try (FileWriter writer = new FileWriter(TOKEN_FILE)) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            System.out.println("⚠️ Không thể lưu token đăng nhập.");
        }
    }


    static int randomDelay() {
        return new java.util.Random().nextInt(2) + 1;
    }
}
