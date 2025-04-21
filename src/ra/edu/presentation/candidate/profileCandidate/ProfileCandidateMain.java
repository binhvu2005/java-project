package ra.edu.presentation.candidate.profileCandidate;

import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.candidate.CandidateSeviceImp;
import ra.edu.business.service.technology.TechnologySevice;
import ra.edu.business.service.technology.TechnologySeviceImp;
import ra.edu.validate.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;


public class ProfileCandidateMain {
    private static final TechnologySevice technologySevice = new TechnologySeviceImp();
    private static final CandidateSeviceImp candidateService = new CandidateSeviceImp();
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        System.out.println("Chào mừng bạn đến với trang quản lý hồ sơ ứng viên!");

        int account_id = readCandidateIdFromToken();
        int candidateId = candidateService.getCandidateIdByAccountId(account_id);
        if (candidateId == -1) {
            System.out.println("Không tìm thấy thông tin đăng nhập. Vui lòng đăng nhập lại.");
            return;
        }

        while (true) {
            System.out.println("\n================== MENU QUẢN LÝ HỒ SƠ =======================");
            viewProfile(candidateId);
            System.out.println("1. Cập nhật thông tin cá nhân");
            System.out.println("2. Quản lý công nghệ");
            System.out.println("0. Thoát");
            System.out.println("===============================================================");
            System.out.print("Lựa chọn của bạn: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (choice) {
                case 1 -> {
                    updateCandidateInfo(candidateId);
                }
                case 2 -> {
                    manageTechnologies(candidateId);
                }
                case 0 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static int readCandidateIdFromToken() {
        try (BufferedReader reader = new BufferedReader(new FileReader("login_token.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Lỗi đọc file login_token.txt: " + e.getMessage());
        }
        return -1;
    }

    private static void viewProfile(int candidateId) {
        Candidate candidate = candidateService.getCandidateInfo(candidateId);
        if (candidate != null) {
            System.out.println("\n======= THÔNG TIN HỒ SƠ ỨNG VIÊN =======");
            System.out.println("ID: " + candidate.getId());
            System.out.println("Họ tên: " + candidate.getName());
            System.out.println("Email: " + candidate.getEmail());
            System.out.println("Số điện thoại: " + candidate.getPhone());
            System.out.println("Giới tính: " + candidate.getGender());
            System.out.println("Ngày sinh: " + candidate.getDob());
            System.out.println("Mô tả: " + candidate.getDescription());
            System.out.println("========================================");
        } else {
            System.out.println("Không tìm thấy thông tin ứng viên.");
        }
    }

    private static void updateCandidateInfo(int candidateId) {
        while (true) {
            System.out.println("\n--- CẬP NHẬT THÔNG TIN ---");
            System.out.println("1. Tên");
            System.out.println("2. Số điện thoại");
            System.out.println("3. Giới tính");
            System.out.println("4. Ngày sinh");
            System.out.println("5. Kinh nghiệm");
            System.out.println("6. Mô tả");
            System.out.println("7. Đổi mật khẩu");
            System.out.println("0. Quay lại");
            System.out.print("Chọn thông tin muốn cập nhật: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();
                    if (Validator.isValidLength(newName)) {
                        boolean success = candidateService.updateCandidateName(candidateId, newName);
                        System.out.println(success ? "✔️ Tên đã được cập nhật thành công." : "❌ Cập nhật tên không thành công.");
                    } else {
                        System.out.println("❌ Tên phải có độ dài từ 10 đến 100 ký tự.");
                    }
                }
                case 2 -> {
                    System.out.print("Nhập số điện thoại mới: ");
                    String newPhone = scanner.nextLine();
                    if (Validator.isValidPhone(newPhone)) {
                        boolean success = candidateService.updateCandidatePhone(candidateId, newPhone);
                        System.out.println(success ? "✔️ Số điện thoại đã được cập nhật thành công." : "❌ Cập nhật số điện thoại không thành công.");
                    } else {
                        System.out.println("❌ Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                    }
                }
                case 3 -> {
                    System.out.print("Nhập giới tính mới (NAM/NU): ");
                    String genderInput = scanner.nextLine().toUpperCase();
                    if (Validator.isValidGender(genderInput)) {
                        CandidateGender gender = CandidateGender.valueOf(genderInput);
                        boolean success = candidateService.updateCandidateGender(candidateId, gender);
                        System.out.println(success ? "✔️ Giới tính đã được cập nhật thành công." : "❌ Cập nhật giới tính không thành công.");
                    } else {
                        System.out.println("❌ Giới tính không hợp lệ. Vui lòng nhập NAM, NU.");
                    }
                }
                case 4 -> {
                    System.out.print("Nhập ngày sinh mới (yyyy-MM-dd): ");
                    String dobInput = scanner.nextLine();
                    try {
                        Date newDob = Date.valueOf(dobInput);
                        if (Validator.isNotNullDate(newDob)) {
                            boolean success = candidateService.updateCandidateDob(candidateId, newDob);
                            System.out.println(success ? "✔️ Ngày sinh đã được cập nhật thành công." : "❌ Cập nhật ngày sinh không thành công.");
                        } else {
                            System.out.println("❌ Định dạng ngày không hợp lệ.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Định dạng ngày không hợp lệ. Vui lòng nhập đúng định dạng yyyy-MM-dd.");
                    }
                }
                case 5 -> {
                    System.out.print("Nhập số năm kinh nghiệm mới: ");
                    int newExperience = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer
                    if (Validator.isValidExperience(newExperience)) {
                        boolean success = candidateService.updateCandidateExperience(candidateId, newExperience);
                        System.out.println(success ? "✔️ Kinh nghiệm đã được cập nhật thành công." : "❌ Cập nhật kinh nghiệm không thành công.");
                    } else {
                        System.out.println("❌ Kinh nghiệm không hợp lệ. Vui lòng nhập số từ 0 đến 100.");
                    }
                }
                case 6 -> {
                    System.out.print("Nhập mô tả mới: ");
                    String newDescription = scanner.nextLine();
                    if (Validator.isValidLength(newDescription)) {
                        boolean success = candidateService.updateCandidateDescription(candidateId, newDescription);
                        System.out.println(success ? "✔️ Mô tả đã được cập nhật thành công." : "❌ Cập nhật mô tả không thành công.");
                    } else {
                        System.out.println("❌ Mô tả phải có độ dài từ 10 đến 100 ký tự.");
                    }
                }
                case 7 -> {
                    System.out.print("Nhập mật khẩu cũ: ");
                    String oldPassword = scanner.nextLine();
                    System.out.print("Nhập mật khẩu mới: ");
                    String newPassword = scanner.nextLine();
                    int account_id = readCandidateIdFromToken();
                    boolean success = candidateService.changeCandidatePassword(account_id, oldPassword, newPassword);
                    System.out.println(success ? "✔️ Mật khẩu đã được thay đổi thành công." : "❌ Đổi mật khẩu không thành công.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void manageTechnologies(int candidateId) {
        while (true) {
            System.out.println("\n===================== QUẢN LÝ CÔNG NGHỆ ============================");
            viewTechnologies(candidateId);
            System.out.println("1. Thêm công nghệ");
            System.out.println("2. Xoá công nghệ");
            System.out.println("0. Quay lại");
            System.out.println("=====================================================================");
            System.out.print("Chọn thao tác: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> {
                    List<Technology> technologyList = technologySevice.getAllTechnology(1, 100);
                    // In header của bảng
                    System.out.println("\n+--------+----------------------+");
                    System.out.println("| ID     | Tên Công Nghệ        |");
                    System.out.println("+--------+----------------------+");

                    // In dữ liệu
                    for (Technology technology : technologyList) {
                        System.out.printf("| %-6d | %-20s |\n",
                                technology.getId(),
                                technology.getName());
                    }
                    System.out.println("+--------+----------------------+");
                    System.out.print("Nhập ID công nghệ cần thêm: ");
                    int techId = scanner.nextInt();
                    scanner.nextLine();
                    boolean success = candidateService.addTechnologyToCandidate(candidateId, techId);
                    System.out.println(success ? "✔️ Công nghệ đã được thêm thành công." : "❌ Thêm công nghệ không thành công.");
                }
                case 2 -> {
                    System.out.print("Nhập ID công nghệ cần xoá: ");
                    int techId = scanner.nextInt();
                    scanner.nextLine();
                    boolean success = candidateService.removeTechnologyFromCandidate(candidateId, techId);
                    System.out.println(success ? "✔️ Công nghệ đã được xoá thành công." : "❌ Xoá công nghệ không thành công.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
    private static void viewTechnologies(int candidateId) {
        List<Technology> technologies = candidateService.getTechnologiesOfCandidate(candidateId);
        if (technologies == null || technologies.isEmpty()) {
            System.out.println("❌ Không có công nghệ nào được gán.");
        } else {
            System.out.println("\n+-------+-----------------------+");
            System.out.println("| ID     | Tên Công Nghệ        |");
            System.out.println("+--------+----------------------+");

            // In dữ liệu
            for (Technology technology : technologies) {
                System.out.printf("| %-6d | %-20s |\n",
                        technology.getId(),
                        technology.getName());
            }
            System.out.println("+--------+----------------------+");

        }
    }
}
