package ra.edu.presentation.admin.candidateManagement;

import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.service.candidateManagement.CandidateManagementSevice;
import ra.edu.business.service.candidateManagement.CandidateManagementSeviceImp;


import java.util.List;
import java.util.Scanner;

public class FilterCandidate {
    private static final CandidateManagementSevice candidateManagementSevice = new CandidateManagementSeviceImp();


    public static void run() {
        int choice;
        do {
            System.out.println("============ Lọc ứng viên =============");
            System.out.println("1. Lọc theo giới tính");
            System.out.println("2. Lọc theo kinh nghiệm");
            System.out.println("3. Lọc theo độ tuổi");
            System.out.println("4. Lọc theo công nghệ");
            System.out.println("5. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    filterByGender();
                    break;
                case 2:
                    filterByExperience();
                    break;
                case 3:
                    filterByAge();
                    break;
                case 4:
                    filterByTechnology();
                    break;
                case 5:
                    System.out.println("Quay lại");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 5);
    }

    public static void filterByGender() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. MALE");
        System.out.println("2. FEMALE");
        System.out.println("3. OTHER");
        System.out.print("Chọn giới tính (1-3): ");
        int genderChoice = scanner.nextInt();

        CandidateGender gender;
        switch (genderChoice) {
            case 1:
                gender = CandidateGender.MALE;
                break;
            case 2:
                gender = CandidateGender.FEMALE;
                break;
            case 3:
                gender = CandidateGender.OTHER;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        int totalPage = candidateManagementSevice.getGenderFilterPages(gender, 5);
        System.out.println("Có " + totalPage + " trang ứng viên.");

        do {
            System.out.print("Nhập số trang bạn muốn xem (nhập 0 để thoát)(1-" + totalPage + "): ");
            int page = scanner.nextInt();
            if (page == 0) {
                break;
            }
            if (totalPage == 0) {
                System.out.println("Không có ứng viên nào.");
                break;
            }
            if (page < 0 || page > totalPage) {
                System.out.println("Số trang không hợp lệ. Vui lòng thử lại.");
            } else {
                List<Candidate> candidates = candidateManagementSevice.filterByGender(gender, page, 5);
                displayCandidates(candidates, page, totalPage);
            }
        } while (true);
    }

    public static void filterByExperience() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số năm kinh nghiệm tối thiểu: ");
        int minExp = scanner.nextInt();
        System.out.print("Nhập số năm kinh nghiệm tối đa: ");
        int maxExp = scanner.nextInt();

        int totalPage = candidateManagementSevice.getExperienceFilterPages(minExp,maxExp, 5);
        System.out.println("Có " + totalPage + " trang ứng viên.");

        do {
            System.out.print("Nhập số trang bạn muốn xem (nhập 0 để thoát)(1-" + totalPage + "): ");
            int page = scanner.nextInt();
            if (page == 0) {
                break;
            }
            if (totalPage == 0) {
                System.out.println("Không có ứng viên nào.");
                break;
            }
            if (page < 0 || page > totalPage) {
                System.out.println("Số trang không hợp lệ. Vui lòng thử lại.");
            } else {
                List<Candidate> candidates = candidateManagementSevice.filterByExperience(minExp,maxExp, page, 5);
                displayCandidates(candidates, page, totalPage);
            }
        } while (true);
    }

    public static void filterByAge() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập độ tuổi tối thiểu: ");
        int minAge = scanner.nextInt();
        System.out.print("Nhập độ tuổi tối đa: ");
        int maxAge = scanner.nextInt();

        int totalPage = candidateManagementSevice.getAgeFilterPages(minAge, maxAge, 5);
        System.out.println("Có " + totalPage + " trang ứng viên.");

        do {
            System.out.print("Nhập số trang bạn muốn xem (nhập 0 để thoát)(1-" + totalPage + "): ");
            int page = scanner.nextInt();
            if (page == 0) {
                break;
            }
            if (totalPage == 0) {
                System.out.println("Không có ứng viên nào.");
                break;
            }
            if (page < 0 || page > totalPage) {
                System.out.println("Số trang không hợp lệ. Vui lòng thử lại.");
            } else {
                List<Candidate> candidates = candidateManagementSevice.filterByAge(minAge, maxAge, page, 5);
                displayCandidates(candidates, page, totalPage);
            }
        } while (true);
    }

    public static void filterByTechnology() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ID công nghệ: ");
        int techId = scanner.nextInt();

        int totalPage = candidateManagementSevice.getTechnologyFilterPages(techId, 5);
        System.out.println("Có " + totalPage + " trang ứng viên.");

        do {
            System.out.print("Nhập số trang bạn muốn xem (nhập 0 để thoát)(1-" + totalPage + "): ");
            int page = scanner.nextInt();
            if (page == 0) {
                break;
            }
            if (totalPage == 0) {
                System.out.println("Không có ứng viên nào.");
                break;
            }
            if (page < 0 || page > totalPage) {
                System.out.println("Số trang không hợp lệ. Vui lòng thử lại.");
            } else {
                List<Candidate> candidates = candidateManagementSevice.filterByTechnology(techId, page, 5);
                displayCandidates(candidates, page, totalPage);
            }
        } while (true);
    }

    private static void displayCandidates(List<Candidate> candidates, int currentPage, int totalPage) {
        System.out.println("\n+--------+----------------------+------------+----------------------+");
        System.out.println("| ID     | Tên                  | Kinh Nghiệm| Email               |");
        System.out.println("+--------+----------------------+------------+----------------------+");

        for (Candidate candidate : candidates) {
            System.out.printf("| %-6d | %-20s | %-10d | %-20s |\n",
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getExperience(),
                    candidate.getEmail());
        }

        System.out.println("+--------+----------------------+------------+----------------------+");
        System.out.println("Trang " + currentPage + "/" + totalPage);
    }
}
