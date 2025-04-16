package ra.edu.presentation.technology;

import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.technology.TechnologySevice;
import ra.edu.business.service.technology.TechnologySeviceImp;

import java.util.List;
import java.util.Scanner;

public class TechnologyMain {
    private static final TechnologySevice technologySevice = new TechnologySeviceImp();
    public static void run (){
        int choice;


        do {
            System.out.println("=== Quản lý công nghệ ===");
            System.out.println("1. Thêm công nghệ");
            System.out.println("2. Cập nhật công nghệ");
            System.out.println("3. Xoá công nghệ");
            System.out.println("4. Hiển thị danh sách công nghệ");
            System.out.println("5. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("===Thêm công nghệ====");
                    System.out.print("Nhập tên công nghệ: ");
                    String name = scanner.next();
                    technologySevice.addTechnology(name);
                    break;
                case 2:
                    System.out.println("Cập nhật công nghệ");
                    System.out.print("Nhập ID công nghệ cần cập nhật: ");
                    int id = scanner.nextInt();
                    technologySevice.updateTechnology(id);
                    break;
                case 3:
                    System.out.println("Xoá công nghệ");
                    System.out.print("Nhập ID công nghệ cần xoá: ");
                    int deleteId = scanner.nextInt();
                    if (technologySevice.sp_get_technology_by_id(deleteId) != null) {
                        System.out.println("Công nghệ có ID " + deleteId + " đã được tìm thấy.");
                    } else {
                        break;
                    }
                    System.out.print("Bạn có chắc chắn muốn xoá công nghệ này không? (Y/N): ");
                    String confirm = scanner.next();
                    if (confirm.equalsIgnoreCase("Y")) {
                        technologySevice.deleteTechnology(deleteId);
                        System.out.println("Xoá công nghệ thành công");
                    } else {
                        System.out.println("Hủy thao tác xoá công nghệ");
                    }

                    break;
                case 4:
                    System.out.println("Hiển thị danh sách công nghệ");
                    int totalPage = technologySevice.getTechnologyPage(5);
                    System.out.println("Có " + totalPage + " trang công nghệ.");
                    do {
                        System.out.print("Nhập số trang bạn muốn xem (nhập 0 để thoát)(1-" + totalPage + "): ");
                        int page = scanner.nextInt();
                        if (page == 0) {
                           break;
                        }
                        if (totalPage ==0){
                            System.out.println("Chưa có công nghệ nào trong hệ thống.");
                            break;
                        }
                        if (page < 0 || page > totalPage) {
                            System.out.println("Số trang không hợp lệ. Vui lòng thử lại.");
                        } else {
                            List<Technology> technologyList = technologySevice.getAllTechnology(page, 5);
                            for (Technology technology : technologyList) {
                                System.out.println(technology);
                            }
                        }
                    }while (true);
                    break;
                case 5:
                    System.out.println("Quay lại");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 5);
    }
}
