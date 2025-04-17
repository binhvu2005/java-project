package ra.edu.business.service.admin;

import ra.edu.business.dao.admin.AdminDao;
import ra.edu.business.dao.admin.AdminDaoImp;

public class AdminSeviceImp implements AdminSevice {
    private AdminDao dao;
    public AdminSeviceImp() {
        this.dao = new AdminDaoImp();
    }
    @Override
    public boolean loginAdmin(String adminName, String password) {
		return dao.loginAdmin(adminName, password);
    }

    @Override
    public void registerAdmin() {
        dao.registerAdmin();
    }

}
