package ra.edu.business.dao.login;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.account.AccountStatus;
import ra.edu.business.model.candidate.CandidateGender;

import java.sql.*;

public class LoginDaoImp implements LoginDao {

    @Override
    public Account login(String email, String password) {
        Account account = null;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_login(?, ?)}")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setEmail(rs.getString("email"));
                account.setPassword(rs.getString("password"));
                account.setRole(AccountRole.valueOf(rs.getString("role")));
                account.setStatus(AccountStatus.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục đăng nhập: " + e.getMessage());
        }
        return account;
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

    @Override
    public void registerCandidate(String name, String email, String phone, CandidateGender gender, Date dob,int experience,String description, String password) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_candidate_register(?, ?, ?, ?, ?, ?, ?, ?)}")) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, gender.name());
            stmt.setDate(5, dob);
            stmt.setInt(6, experience);
            stmt.setString(7, description);
            stmt.setString(8, password);

            stmt.execute();
            System.out.println("✅ Đăng ký thành công!");
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi đăng ký ứng viên: " + e.getMessage());
        }
    }

    @Override
    public AccountRole getRole(int id) {
        AccountRole role = null;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_role(?)}")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = AccountRole.valueOf(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi lấy vai trò: " + e.getMessage());
        }
        return role;
    }


}
