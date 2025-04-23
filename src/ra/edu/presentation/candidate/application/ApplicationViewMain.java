package ra.edu.presentation.candidate.application;

import ra.edu.business.model.application.ApplicationDetail;
import ra.edu.business.model.application.ApplicationProgress;
import ra.edu.business.model.application.ApplicationView;
import ra.edu.business.service.application.ApplicationSevice;
import ra.edu.business.service.application.ApplicationSeviceImp;

import java.util.List;
import java.util.Scanner;

import static ra.edu.presentation.candidate.profileCandidate.ProfileCandidateMain.candidateService;
import static ra.edu.presentation.candidate.profileCandidate.ProfileCandidateMain.readCandidateIdFromToken;

public class ApplicationViewMain {
    private static final ApplicationSevice ApplicationSevice = new ApplicationSeviceImp();
    static int account_id = readCandidateIdFromToken();
    static int candidateId = candidateService.getCandidateIdByAccountId(account_id);
    public static void run(){
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("========== QUẢN LÍ ĐƠN ĐÃ UNG TUYỂN ==========");
            System.out.println("1. Xem danh sách đơn đã ứng tuyển");
            System.out.println("2. Xem chi tiết đơn đã ứng tuyển");
            System.out.println("3. Quay lại");
            System.out.println("===============================================");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Danh sách đơn đã ứng tuyển: ");
                    List<ApplicationView> applications = ApplicationSevice.viewApplicationByCandidateId(candidateId);
                    if (applications == null || applications.isEmpty()) {
                        System.out.println("Bạn chưa ứng tuyển vào vị trí nào.");
                    } else {
                        for (ApplicationView application : applications) {
                            System.out.println("-------------------------------------------------------------");
                            System.out.println("ID đơn ứng tuyển: " + application.getId());
                            System.out.println("Tên vị trí tuyển dụng: " + application.getRecruitmentPositionName());
                            System.out.println("Trạng thái đơn ứng tuyển: " + application.getProgress());
                            System.out.println("CV URL: " + application.getCvUrl());
                            System.out.println("--------------------------------------------------------------");
                        }
                    }

                    break;
                case 2:
                    System.out.println("Nhập ID đơn ứng tuyển bạn muốn xem chi tiết: ");
                    int applicationId = scanner.nextInt();
                    scanner.nextLine(); // clear buffer

                    ApplicationDetail application = ApplicationSevice.getApplicationById(applicationId);

                    if (application != null) {
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Mã đơn ứng tuyển: " + application.getId());
                        System.out.println("CV: " + application.getCvUrl());
                        System.out.println("Vị trí tuyển dụng: " + application.getRecruitmentPositionName());
                        System.out.println("Mô tả: " + application.getRecruitmentPositionDescription());
                        System.out.println("Lương: " + application.getMinSalary() + " - " + application.getMaxSalary());
                        System.out.println("Kinh nghiệm yêu cầu: " + application.getMinExperience() + " năm");
                        System.out.println("Trạng thái: " + application.getProgress());
                        System.out.println("Ngày tạo đơn: " + application.getCreatedAt());
                        System.out.println("Ngày cập nhật: " + application.getUpdatedAt());

                        if (application.getInterviewRequestDate() != null) {
                            System.out.println("📅 Ngày yêu cầu phỏng vấn: " + application.getInterviewRequestDate());
                            System.out.println("📎 Link phỏng vấn: " + application.getInterviewLink());
                        }

                        // Nếu đang ở trạng thái INTERVIEWING và có lịch hẹn
                        if (application.getProgress() == ApplicationProgress.INTERVIEWING && application.getInterviewRequestDate() != null)
                        {

                            System.out.print("Bạn có muốn xác nhận tham gia phỏng vấn không? (Y/N) E để thoát: ");
                            String confirm = scanner.nextLine();

                            if (confirm.equalsIgnoreCase("Y")) {
                                boolean success = ApplicationSevice.confirmInterview(application.getId(), true);
                                System.out.println(success ? "✅ Đã xác nhận tham gia phỏng vấn." : "❌ Xác nhận thất bại.");
                            } else if (confirm.equalsIgnoreCase("N")) {
                                boolean success = ApplicationSevice.confirmInterview(application.getId(), false);
                                System.out.println(success ? "⚠️ Bạn đã từ chối tham gia phỏng vấn." : "❌ Thao tác thất bại.");
                            } else if (confirm.equalsIgnoreCase("E")) {
                                return;
                            } else {
                                System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng thử lại.");
                            }
                        }

                        System.out.println("--------------------------------------------------------------");
                    } else {
                        System.out.println("Không tìm thấy đơn ứng tuyển với ID này.");
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
