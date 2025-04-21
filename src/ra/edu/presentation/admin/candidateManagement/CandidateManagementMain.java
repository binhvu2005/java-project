package ra.edu.presentation.admin.candidateManagement;

import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.service.candidateManagement.CandidateManagementSevice;
import ra.edu.business.service.candidateManagement.CandidateManagementSeviceImp;

import java.util.Random;

import java.util.List;
import java.util.Scanner;


public class CandidateManagementMain {
	private static final CandidateManagementSevice candidateManagementSevice = new CandidateManagementSeviceImp();

    public static void run() {
        int choice;
        do {
            System.out.println("============ Quản lý ứng viên =============");
            System.out.println("1. Hiển thị danh sách ứng viên");
            System.out.println("2. Khóa/mở tài khoản ứng viên");
            System.out.println("3. Reset mật khẩu ứng viên(hiển thị mật khẩu random)");
            System.out.println("4. Tim kiếm ứng viên theo tên");
            System.out.println("5. Lọc theo Kinh nghiệm , tuổi , giới tính , công nghệ");
            System.out.println("6. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewCandidate();
                    break;
                case 2:
                    lockCandidate();
                    break;
                case 3:
                    System.out.println("Nhập ID ứng viên cần reset mật khẩu: ");
                    int resetId = scanner.nextInt();
                    resetPass(resetId);
                    break;
                case 4:
                    searchCandidateByName();
                    break;
                case 5:
                    FilterCandidate.run();
                    break;
                case 6:
                    System.out.println("Quay lại");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 6);
    }
    public static void viewCandidate() {
        int limit = 5;
        int totalPage = candidateManagementSevice.getTotalPages(limit);
        if (totalPage == 0) {
            System.out.println("Không có ứng viên nào trong hệ thống");
            return;
        }
        System.out.print("Nhập số trang bạn muốn xem (nhập 0 để thoát)(1-" + totalPage + "): ");
        Scanner scanner = new Scanner(System.in);
        int page = scanner.nextInt();

        
        List<Candidate> candidates = candidateManagementSevice.getCandidates(page, limit);

        System.out.println("\n+--------+----------------------+------------------+-------------+----------+------------+-------------+----------------------+");
        System.out.println("| ID     | Tên                  | Email            | Điện thoại  | Giới tính| Ngày sinh  | Kinh nghiệm | Mô tả                |");
        System.out.println("+--------+----------------------+------------------+-------------+----------+------------+-------------+----------------------+");

        for (Candidate candidate : candidates) {
            System.out.printf("| %-6d | %-20s | %-16s | %-11s | %-8s | %-10s | %-11d | %-20s |\n",
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getEmail(),
                    candidate.getPhone(),
                    candidate.getGender(),
                    candidate.getDob(),
                    candidate.getExperience(),
                    candidate.getDescription());
        }

        System.out.println("+--------+----------------------+------------------+-------------+----------+------------+-------------+----------------------+");
        System.out.println("Trang " + page + "/" + totalPage);
    }
    public static void lockCandidate() {
        int choice;
        do {
        System.out.println("Nhập ID ứng viên cần khóa/mở: ");
        Scanner scanner = new Scanner(System.in);
        int candidateId = scanner.nextInt();
        System.out.println("1. Mở tài khoản");
        System.out.println("2. Khóa tài khoản");
        System.out.println("0. Quay lại");
        System.out.print("Nhập lựa chọn của bạn: ");
        choice = scanner.nextInt();

        String status;
        switch (choice) {
            case 1:
                status = "active";
                break;
            case 2:
                status = "inactive";
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                return;
        }
        boolean result = candidateManagementSevice.updateCandidateStatus(candidateId, status);
        if (result) {
            System.out.println("Cập nhật trạng thái ứng viên thành công");
        } else {
            System.out.println("Cập nhật trạng thái ứng viên thất bại");
        }
    }while (choice != 3);
    }
public static void resetPass(int candidateId) {
    String password = generateRandomPassword();
    boolean result = candidateManagementSevice.resetCandidatePassword(candidateId, password);
    if (result) {
        System.out.println("Reset mật khẩu ứng viên thành công");
        System.out.println("Mật khẩu mới: " + password);
    } else {
        System.out.println("Reset mật khẩu ứng viên thất bại");
    }
}

    public static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static void searchCandidateByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên ứng viên cần tìm: ");
        String name = scanner.nextLine();

        System.out.println("Nhập số trang bạn muốn xem: ");
        int page = scanner.nextInt();
        int limit = 5;

        int totalPage = candidateManagementSevice.getSearchCandidateTotalPages(name, limit);
        if (page > totalPage) {
            System.out.println("Số trang không hợp lệ");
            return;
        }

        List<Candidate> candidates = candidateManagementSevice.searchCandidatesByName(name, page, limit);

        System.out.println("\n+--------+----------------------+------------------+-------------+----------+------------+-------------+----------------------+");
        System.out.println("| ID     | Tên                  | Email            | Điện thoại  | Giới tính| Ngày sinh  | Kinh nghiệm | Mô tả                |");
        System.out.println("+--------+----------------------+------------------+-------------+----------+------------+-------------+----------------------+");

        for (Candidate candidate : candidates) {
            System.out.printf("| %-6d | %-20s | %-16s | %-11s | %-8s | %-10s | %-11d | %-20s |\n",
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getEmail(),
                    candidate.getPhone(),
                    candidate.getGender(),
                    candidate.getDob(),
                    candidate.getExperience(),
                    candidate.getDescription());
        }

        System.out.println("+--------+----------------------+------------------+-------------+----------+------------+-------------+----------------------+");
        System.out.println("Trang " + page + "/" + totalPage);
    }
}