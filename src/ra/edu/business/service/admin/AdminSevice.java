package ra.edu.business.service.admin;

import ra.edu.business.service.AppSevice;

public interface AdminSevice extends AppSevice {
    boolean loginAdmin(String adminName, String password);
    void registerAdmin();
}
