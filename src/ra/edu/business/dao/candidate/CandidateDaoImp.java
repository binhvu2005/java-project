package ra.edu.business.dao.candidate;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.candidate.CandidateGender;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CandidateDaoImp implements CandidateDao{

    @Override
    public boolean loginCandidate(String candidateName, String password) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_candidate_login(?,?)}")) {
            stmt.setString(1, candidateName);
            stmt.setString(2, password);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục đăng nhập với ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void registerCandidate(String id, String name, String email,String phone,CandidateGender gender, Date dod, String description, String password) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_candidate_register(?,?,?,?,?,?,?,?)}")) {
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender.toString());
            stmt.setDate(6, new java.sql.Date(dod.getTime()));
            stmt.setString(7, description);
            stmt.setString(8, password);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục đăng ký với ứng viên: " + e.getMessage());
        }
    }
}
