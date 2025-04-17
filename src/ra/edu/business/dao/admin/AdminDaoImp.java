package ra.edu.business.dao.admin;

import ra.edu.business.config.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImp implements AdminDao {

    @Override
    public boolean loginAdmin(String adminName, String password) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_admin_login(?,?)}")) {
            stmt.setString(1, adminName);
            stmt.setString(2, password);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục đăng nhập với admin: " + e.getMessage());
        }
        return false;
    }


    @Override
    public void registerAdmin() {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_admin_init()}")) {
             stmt.execute();
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục đăng ký với admin: " + e.getMessage());
        }
    }
}
