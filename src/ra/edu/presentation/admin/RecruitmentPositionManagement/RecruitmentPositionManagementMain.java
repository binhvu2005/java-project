package ra.edu.presentation.admin.RecruitmentPositionManagement;

import ra.edu.business.model.recruitmentPosition.RecruitmentPosition;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionSevice;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionSeviceImp;
import ra.edu.validate.Validator;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class RecruitmentPositionManagementMain {
    private static final RecruitmentPositionSevice service = new RecruitmentPositionSeviceImp();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void run() {
        int choice;
        do {
            System.out.println("\n============== Quản lý vị trí tuyển dụng ============");
            System.out.println("1. Thêm vị trí tuyển dụng");
            System.out.println("2. Cập nhật vị trí tuyển dụng");
            System.out.println("3. Xóa vị trí tuyển dụng");
            System.out.println("4. Hiển thị danh sách vị trí tuyển dụng");
            System.out.println("5. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addRecruitmentPosition();
                case 2 -> updateRecruitmentPosition();
                case 3 -> deleteRecruitmentPosition();
                case 4 -> showAllRecruitmentPositions();
                case 5 -> System.out.println("Quay lại...");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 5);
    }

    private static void addRecruitmentPosition() {
        System.out.println("=== Thêm vị trí tuyển dụng ===");
        String name;
        do {
            System.out.print("Nhập tên vị trí: ");
            name = scanner.nextLine();
        } while (!Validator.isValidLength(name));

        System.out.print("Nhập mô tả: ");
        String description = scanner.nextLine();

        double minSalary;
        do {
            System.out.print("Nhập lương tối thiểu (> 0): ");
            minSalary = Double.parseDouble(scanner.nextLine());
        } while (!Validator.isValidSalary(minSalary));

        double maxSalary;
        do {
            System.out.print("Nhập lương tối đa (> lương tối thiểu): ");
            maxSalary = Double.parseDouble(scanner.nextLine());
        } while (maxSalary <= minSalary);

        int minExp;
        do {
            System.out.print("Nhập kinh nghiệm tối thiểu (>= 0): ");
            minExp = Integer.parseInt(scanner.nextLine());
        } while (!Validator.isValidExperience(minExp));

        Date expiredDate;
        while (true) {
            System.out.print("Nhập ngày hết hạn (dd/MM/yyyy): ");
            String expiredDateStr = scanner.nextLine();
            try {
                expiredDate = new Date(dateFormat.parse(expiredDateStr).getTime());
                if (expiredDate.after(new java.util.Date())) break;
                System.out.println("Ngày hết hạn phải sau ngày hiện tại.");
            } catch (ParseException e) {
                System.out.println("Định dạng ngày không hợp lệ.");
            }
        }

        boolean success = service.addRecruitmentPosition(name, description, minSalary, maxSalary, minExp, expiredDate);
        System.out.println(success ? "Thêm thành công!" : "Thêm thất bại!");
    }

    private static void updateRecruitmentPosition() {
        System.out.print("Nhập ID vị trí cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());

        int choice;
        do {
            showRecruitmentPositionById(id);
            System.out.println("\n=== Cập nhật vị trí tuyển dụng ===");

            System.out.println("1. Cập nhật tên");
            System.out.println("2. Cập nhật mô tả");
            System.out.println("3. Cập nhật lương");
            System.out.println("4. Cập nhật kinh nghiệm tối thiểu");
            System.out.println("5. Cập nhật ngày hết hạn");
            System.out.println("6. Quay lại");
            System.out.print("Chọn mục muốn cập nhật: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();
                    boolean ok = service.updateRecruitmentPositionByName(id, newName);
                    System.out.println(ok ? "Cập nhật thành công!" : "Thất bại!");
                }
                case 2 -> {
                    System.out.print("Nhập mô tả mới: ");
                    String desc = scanner.nextLine();
                    boolean ok = service.updateRecruitmentPositionByDescription(id, desc);
                    System.out.println(ok ? "Cập nhật thành công!" : "Thất bại!");
                }
                case 3 -> {
                    System.out.print("Nhập lương tối thiểu mới: ");
                    double min = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nhập lương tối đa mới: ");
                    double max = Double.parseDouble(scanner.nextLine());
                    boolean ok = service.updateRecruitmentPositionBySalary(id, min, max);
                    System.out.println(ok ? "Cập nhật thành công!" : "Thất bại!");
                }
                case 4 -> {
                    System.out.print("Nhập kinh nghiệm tối thiểu mới: ");
                    int exp = Integer.parseInt(scanner.nextLine());
                    boolean ok = service.updateRecruitmentPositionByExperience(id, exp);
                    System.out.println(ok ? "Cập nhật thành công!" : "Thất bại!");
                }
                case 5 -> {
                    System.out.print("Nhập ngày hết hạn mới (dd/MM/yyyy): ");
                    java.util.Date date = null;
                    while (true) {
                        String dateStr = scanner.nextLine();
                        try {
                            date = dateFormat.parse(dateStr);
                            if (date.after(new java.util.Date())) break;
                            System.out.println("Ngày hết hạn phải sau ngày hiện tại.");
                        } catch (ParseException e) {
                            System.out.println("Định dạng ngày không hợp lệ.");
                        }
                    }
                    boolean ok = service.updateRecruitmentPositionByExpiredDate(id, date);
                    System.out.println(ok ? "Cập nhật thành công!" : "Thất bại!");
                }
                case 6 -> System.out.println("Quay lại...");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 6);
    }

    private static void deleteRecruitmentPosition() {
        System.out.print("Nhập ID vị trí cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean success = service.deleteRecruitmentPositionById(id);
        System.out.println(success ? "Xóa thành công!" : "Xóa thất bại!");
    }
	private static void showRecruitmentPositionById(int id) {
        RecruitmentPosition position = service.getRecruitmentPositionById(id);
        if (position != null) {
            System.out.println("+------+----------------------+----------------------+--------------+--------------+-------------+------------------+");
            System.out.println("| ID   | Tên vị trí           | Mô tả                | Lương Min    | Lương Max    | Kinh nghiệm | Ngày hết hạn     |");
            System.out.println("+------+----------------------+----------------------+--------------+--------------+-------------+------------------+");
            System.out.printf("| %-4d | %-20s | %-20s | %-12.2f | %-12.2f | %-11d | %-16s |\n",
                    position.getId(),
                    position.getName(),
                    position.getDescription(),
                    position.getMinSalary(),
                    position.getMaxSalary(),
                    position.getMinExperience(),
                    dateFormat.format(position.getExpiredDate()));
            System.out.println("+------+----------------------+----------------------+--------------+--------------+-------------+------------------+");
        } else {
            System.out.println("Không tìm thấy vị trí tuyển dụng với ID: " + id);
        }
    }
    private static void showAllRecruitmentPositions() {
        int limit = 5;
        int page = 1;
        int totalPage = service.getRecruitmentPositionPage(limit);

        while (true) {
            if (totalPage == 0) {
                System.out.println("Không có vị trí tuyển dụng nào trong hệ thống.");
                break;
            }
            System.out.printf("\n--- Danh sách trang %d/%d ---\n", page, totalPage);
            System.out.println("+------+----------------------+----------------------+--------------+--------------+-------------+------------------+");
            System.out.println("| ID   | Tên vị trí           | Mô tả                | Lương Min    | Lương Max    | Kinh nghiệm | Ngày hết hạn     |");
            System.out.println("+------+----------------------+----------------------+--------------+--------------+-------------+------------------+");
            service.getAllRecruitmentPosition(page, limit).forEach(position -> {
                System.out.printf("| %-4d | %-20s | %-20s | %-12.2f | %-12.2f | %-11d | %-16s |\n",
                        position.getId(),
                        position.getName(),
                        position.getDescription(),
                        position.getMinSalary(),
                        position.getMaxSalary(),
                        position.getMinExperience(),
                        dateFormat.format(position.getExpiredDate()));
            });
            System.out.println("+------+----------------------+----------------------+--------------+--------------+-------------+------------------+");


            System.out.println("[N]ext | [P]revious | [E]xit");
            System.out.print("Lựa chọn: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("n") && page < totalPage) page++;
            else if (input.equals("p") && page > 1) page--;
            else if (input.equals("e")) break;
            else System.out.println("Không hợp lệ!");
        }
    }
}
