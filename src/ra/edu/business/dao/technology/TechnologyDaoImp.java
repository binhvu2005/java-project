package ra.edu.business.dao.technology;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.technology.Technology;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TechnologyDaoImp implements TechnologyDao {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int getTechnologyPage(int in_limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_technology_page(?)}")) {

            stmt.setInt(1, in_limit);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy số trang công nghệ: " + e.getMessage());
        }
        return totalPage;
    }

    @Override
    public List<Technology> getAllTechnology(int page, int in_limit) {
        List<Technology> technologies = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_technology(?,?)}")) {

            stmt.setInt(1, page);
            stmt.setInt(2, in_limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Technology technology = new Technology();
                technology.setId(rs.getInt("id"));
                technology.setName(rs.getString("name"));
                technologies.add(technology);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách công nghệ: " + e.getMessage());
        }
        return technologies;
    }

    @Override
    public Technology sp_get_technology_by_id(int id) {
        Technology technologies = null;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_technology_by_id(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                technologies = new Technology();
                technologies.setId(rs.getInt("id"));
                technologies.setName(rs.getString("name"));
            } else {
                System.out.println("Công nghệ không tồn tại.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy công nghệ theo ID: " + e.getMessage());
        }
        return technologies;
    }

    @Override
    public void addTechnology(String name) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_add_technology(?)}")) {

            stmt.setString(1, name);
            stmt.executeUpdate();

            System.out.println("✅ Thêm công nghệ thành công!");

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm công nghệ: " + e.getMessage());
        }
    }

    @Override
    public void updateTechnology(int id) {
        System.out.print("Nhập tên công nghệ mới: ");
        String name = scanner.nextLine();

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_update_technology(?,?)}")) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.executeUpdate();

            System.out.println("✅ Cập nhật công nghệ thành công!");

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật công nghệ: " + e.getMessage());
        }
    }

    @Override
    public void deleteTechnology(int id) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_delete_technology(?)}")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Xoá công nghệ thành công");

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xoá công nghệ: " + e.getMessage());
        }
    }

    @Override
    public void logout() {
        System.out.println("🔒 Đã đăng xuất.");
    }
}
