package ra.edu.business.dao.RecruitmentPosition;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.recruitmentPosition.RecruitmentPosition;
import ra.edu.business.model.technology.Technology;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecruitmentPositionDaoImp implements RecruitmentPositionDao {
    @Override
    public int getRecruitmentPositionPage(int in_limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_recruitment_position_page(?)}")) {
            stmt.setInt(1, in_limit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (Exception e) {
            System.out.println("lỗi khi lấy tổng số trang vị trí tuyển dụng: " + e.getMessage());
        }
        return totalPage;
    }

    @Override
    public List<RecruitmentPosition> getAllRecruitmentPosition(int page, int in_limit) {
        List<RecruitmentPosition> recruitmentPositions = new ArrayList<>();  // ✅ khởi tạo list
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_recruitment_position(?, ?)}")) {
            stmt.setInt(1, page);
            stmt.setInt(2, in_limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RecruitmentPosition recruitmentPosition = new RecruitmentPosition();
                recruitmentPosition.setId(rs.getInt("id"));
                recruitmentPosition.setName(rs.getString("name"));
                recruitmentPosition.setDescription(rs.getString("description"));
                recruitmentPosition.setMinSalary(rs.getDouble("minSalary"));
                recruitmentPosition.setMaxSalary(rs.getDouble("maxSalary"));
                recruitmentPosition.setMinExperience(rs.getInt("minExperience"));
                recruitmentPosition.setExpiredDate(rs.getDate("expiredDate")); // Dùng rs.getDate là gọn nhất
                recruitmentPositions.add(recruitmentPosition);
            }
        } catch (Exception e) {
            System.out.println("lỗi khi lấy tất cả vị trí tuyển dụng: " + e.getMessage());
        }
        return recruitmentPositions;
    }


    @Override
    public boolean addRecruitmentPosition(String name, String description, double minSalary, double maxSalary, int minExperience, Date expiredDate) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_add_recruitment_position(?, ?, ?, ?, ?, ?)}")) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, minSalary);
            stmt.setDouble(4, maxSalary);
            stmt.setInt(5, minExperience);
            stmt.setDate(6, new java.sql.Date(expiredDate.getTime()));
            stmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi thêm vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRecruitmentPositionByName(int id, String newName) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_recruitment_position_name(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setString(2, newName);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi cập nhật tên vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRecruitmentPositionByDescription(int id, String newDescription) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_recruitment_position_description(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setString(2, newDescription);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi cập nhật mô tả vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRecruitmentPositionBySalary(int id, double newMinSalary, double newMaxSalary) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_recruitment_position_salary(?, ?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setDouble(2, newMinSalary);
            stmt.setDouble(3, newMaxSalary);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi cập nhật lương vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRecruitmentPositionByExperience(int id, int newMinExperience) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_recruitment_position_experience(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setInt(2, newMinExperience);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi cập nhật kinh nghiệm vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateRecruitmentPositionByExpiredDate(int id, Date newExpiredDate) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_recruitment_position_expired_date(?, ?)}")) {
            stmt.setInt(1, id);
            stmt.setDate(2, new java.sql.Date(newExpiredDate.getTime()));
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi cập nhật ngày hết hạn vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteRecruitmentPositionById(int id) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_delete_recruitment_position(?)}")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi xóa vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public RecruitmentPosition getRecruitmentPositionById(int id) {
        RecruitmentPosition recruitmentPosition = null;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_recruitment_position_by_id(?)}")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                recruitmentPosition = new RecruitmentPosition();
                recruitmentPosition.setId(rs.getInt("id"));
                recruitmentPosition.setName(rs.getString("name"));
                recruitmentPosition.setDescription(rs.getString("description"));
                recruitmentPosition.setMinSalary(rs.getDouble("minSalary"));
                recruitmentPosition.setMaxSalary(rs.getDouble("maxSalary"));
                recruitmentPosition.setMinExperience(rs.getInt("minExperience"));
                recruitmentPosition.setExpiredDate(rs.getDate("expiredDate"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm vị trí theo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return recruitmentPosition;
    }


    @Override
    public List<Technology> getTechnologiesOfRecruitmentPosition(int id) {
        List<Technology> technologies = new ArrayList<>(); // ✅ FIXED

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_technology_by_recruitment_position_id(?)}")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Technology technology = new Technology();
                technology.setId(rs.getInt("id"));
                technology.setName(rs.getString("name"));
                technologies.add(technology);
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy công nghệ của vị trí tuyển dụng: " + e.getMessage());
        }

        return technologies;
    }


    @Override
    public boolean addTechnologyToRecruitmentPosition(int recruitmentPositionId, int technologyId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_add_technology_to_recruitment_position(?, ?)}")) {
            stmt.setInt(1, recruitmentPositionId);
            stmt.setInt(2, technologyId);
            stmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi thêm công nghệ vào vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeTechnologyFromRecruitmentPosition(int recruitmentPositionId, int technologyId) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_remove_technology_from_recruitment_position(?, ?)}")) {
            stmt.setInt(1, recruitmentPositionId);
            stmt.setInt(2, technologyId);
            stmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("lỗi khi xoá công nghệ khỏi vị trí tuyển dụng: " + e.getMessage());
        }
        return false;
    }
}
