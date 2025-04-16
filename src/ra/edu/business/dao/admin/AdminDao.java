package ra.edu.business.dao.admin;

import ra.edu.business.dao.AppDao;

public interface AdminDao extends AppDao {
    boolean loginAdmin(String adminName, String password);
    void registerAdmin();

}
