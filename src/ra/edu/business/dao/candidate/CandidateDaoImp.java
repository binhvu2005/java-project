package ra.edu.business.dao.candidate;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.technology.Technology;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CandidateDaoImp implements CandidateDao{


    @Override
    public int getCandidateIdByAccountId(int accountId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_candidate_id_by_account_id(?)}")) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("candidateId");
            } else {
                System.out.println("Không tìm thấy ứng viên với id tài khoản: " + accountId);
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục lấy id ứng viên theo id tài khoản: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Candidate getCandidateInfo(int candidateId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_candidate_by_id(?)}")) {
            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                String genderStr = rs.getString("gender").toUpperCase();
                candidate.setGender(CandidateGender.valueOf(genderStr));
                candidate.setDob(rs.getDate("dob"));
                candidate.setDescription(rs.getString("description"));
                return candidate;
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục lấy thông tin ứng viên: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void registerCandidate(int id, String name, String email,String phone,CandidateGender gender, Date dod,int experience, String description, String password) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_candidate_register(?,?,?,?,?,?,?,?,?)}")) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender.toString());
            stmt.setDate(6, new java.sql.Date(dod.getTime()));
            stmt.setInt(7, experience);
            stmt.setString(8, description);
            stmt.setString(9, password);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục đăng ký với ứng viên: " + e.getMessage());
        }
    }

    @Override
    public boolean changeCandidatePassword(int accountId, String oldPassword, String newPassword) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_change_candidate_password(?,?,?)}")) {
            stmt.setInt(1, accountId);
            stmt.setString(2, oldPassword);
            stmt.setString(3, newPassword);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục thay đổi mật khẩu với ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCandidateName(int candidateId, String newName) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_candidate_name(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setString(2, newName);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục cập nhật tên ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCandidatePhone(int candidateId, String newPhone) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_candidate_phone(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setString(2, newPhone);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục cập nhật số điện thoại ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCandidateGender(int candidateId, CandidateGender newGender) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_candidate_gender(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setString(2, newGender.toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục cập nhật giới tính ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCandidateDob(int candidateId, Date newDob) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_candidate_dob(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setDate(2, new java.sql.Date(newDob.getTime()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục cập nhật ngày sinh ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCandidateDescription(int candidateId, String newDescription) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_candidate_description(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setString(2, newDescription);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục cập nhật mô tả ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCandidateExperience(int candidateId, int newExperience) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_candidate_experience(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setInt(2, newExperience);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục cập nhật kinh nghiệm ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addTechnologyToCandidate(int candidateId, int technologyId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_add_candidate_technology(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setInt(2, technologyId);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục thêm công nghệ vào ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeTechnologyFromCandidate(int candidateId, int technologyId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_remove_candidate_technology(?,?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setInt(2, technologyId);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục xoá công nghệ khỏi ứng viên: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Technology> getTechnologiesOfCandidate(int candidateId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_candidate_technologies(?)}")) {
            stmt.setInt(1, candidateId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            List<Technology> technologies = new ArrayList<>();
            while (rs.next()) {
                Technology technology = new Technology();
                technology.setId(rs.getInt("id"));
                technology.setName(rs.getString("name"));
                technologies.add(technology);
            }
            return technologies;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi thực hiện thủ tục lấy danh sách công nghệ của ứng viên: " + e.getMessage());
        }
        return null;
    }
}
