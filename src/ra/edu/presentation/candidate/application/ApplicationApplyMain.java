package ra.edu.presentation.candidate.application;

import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.application.ApplicationSevice;
import ra.edu.business.service.application.ApplicationSeviceImp;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionSevice;
import ra.edu.business.service.recruitmentPosition.RecruitmentPositionSeviceImp;

import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.admin.RecruitmentPositionManagement.RecruitmentPositionManagementMain.showAllRecruitmentPositions;
import static ra.edu.presentation.admin.RecruitmentPositionManagement.RecruitmentPositionManagementMain.showRecruitmentPositionById;
import static ra.edu.presentation.candidate.profileCandidate.ProfileCandidateMain.candidateService;
import static ra.edu.presentation.candidate.profileCandidate.ProfileCandidateMain.readCandidateIdFromToken;
import static ra.edu.validate.Validator.isValidUrl;

public class ApplicationApplyMain {
    private static final RecruitmentPositionSevice RecruitmentPositionSevice = new RecruitmentPositionSeviceImp();
    private static final ApplicationSevice ApplicationSevice = new ApplicationSeviceImp();
    static int account_id = readCandidateIdFromToken();
    static int candidateId = candidateService.getCandidateIdByAccountId(account_id);
    public static void run() {
      int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("====== MENU XEM VÀ NỘP ĐƠN ỨNG TUYỂN======");
            System.out.println("1. Xem danh sách vị trí tuyển dụng");
            System.out.println("2. Xem chi tiết vị trí tuyển dụng và nộp đơn ứng tuyển");
            System.out.println("3. Quay lại");
            System.out.println("===========================================");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showAllRecruitmentPositions();
                    break;
                case 2:
                    System.out.println("Nhập ID vị trí tuyển dụng bạn muốn xem chi tiết: ");
                    int recruitmentPositionId = scanner.nextInt();
                    showRecruitmentPositionById(recruitmentPositionId);
                    System.out.println("Công nghệ yêu cầu: ");
                    List<Technology> technologies = RecruitmentPositionSevice.getTechnologiesOfRecruitmentPosition(recruitmentPositionId);
                    for (Technology technology : technologies) {
                        System.out.println(technology.getName());
                    }
                    System.out.println("Bạn có muốn nộp đơn ứng tuyển không? (Y/N): ");
                    String confirm = scanner.next();
                    if (confirm.equalsIgnoreCase("Y")) {
                        while (true){
                            System.out.println("Nhập CV (url )của bạn: ");
                            String cvUrl = scanner.next();
                            if (isValidUrl(cvUrl)) {
                                boolean isSuccess = ApplicationSevice.createApplication(candidateId,recruitmentPositionId, cvUrl);
                                if (isSuccess) {
                                    System.out.println("Nộp đơn ứng tuyển thành công.");
                                    break;
                                } else {
                                    System.out.println("Nộp đơn ứng tuyển thất bại. Vui lòng thử lại sau.");
                                    break;
                                }
                            } else {
                                System.out.println("CV không hợp lệ. Vui lòng nhập lại.");
                            }
                        }
                    } else {
                        System.out.println("Bạn đã hủy thao tác nộp đơn ứng tuyển.");
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Quay lại");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");

            }
        } while (choice != 3);
    }
}
