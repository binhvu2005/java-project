package ra.edu.business.dao.candidateManagement;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.account.AccountStatus;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;

import java.util.ArrayList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CandidateManagementDaoImp implements CandidateManagementDao {
    @Override
    public int getTotalPages(int limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_get_candidate_page(?)}")) {
            cs.setInt(1, limit);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;

    }

    @Override
    public List<Candidate> getCandidates(int page, int limit) {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_get_candidate(?,?)}")) {
            cs.setInt(1, page);
            cs.setInt(2, limit);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender").toUpperCase()));

                candidate.setDob(rs.getDate("dob"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setDescription(rs.getString("description"));
                candidate.setStatus(AccountStatus.valueOf(rs.getString("status").toUpperCase()));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public boolean updateCandidateStatus(int candidateId, String status) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_unlock_candidate(?,?)}")) {
            cs.setInt(1, candidateId);
            cs.setString(2, status);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean resetCandidatePassword(int candidateId , String password) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_reset_candidate_password(?,?)}")) {
            cs.setInt(1, candidateId);
            cs.setString(2, password);
            cs.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Candidate> searchCandidatesByName(String name, int page, int limit) {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_search_candidate(?,?,?)}")) {
            cs.setString(1, name);
            cs.setInt(2, page);
            cs.setInt(3, limit);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender").toUpperCase()));
                candidate.setDob(rs.getDate("dob"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setDescription(rs.getString("description"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public int getSearchCandidateTotalPages(String name, int limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_search_candidate_page(?,?)}")) {
            cs.setString(1, name);
            cs.setInt(2, limit);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    @Override
    public List<Candidate> filterByExperience(int minExp, int maxExp, int page, int limit) {
        List <Candidate> candidates = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_experience(?,?,?,?)}")) {
            cs.setInt(1, minExp);
            cs.setInt(2, maxExp);
            cs.setInt(3, page);
            cs.setInt(4, limit);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender").toUpperCase()));
                candidate.setDob(rs.getDate("dob"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setDescription(rs.getString("description"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public int getExperienceFilterPages(int minExp, int maxExp, int limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_experience_page(?,?,?)}")) {
            cs.setInt(1, minExp);
            cs.setInt(2, maxExp);
            cs.setInt(3, limit);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    @Override
    public List<Candidate> filterByGender(CandidateGender gender, int page, int limit) {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_gender(?,?,?)}")) {

            cs.setString(1, gender.toString());
            cs.setInt(2, page);
            cs.setInt(3, limit);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender").toUpperCase()));
                candidate.setDob(rs.getDate("dob"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setDescription(rs.getString("description"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public int getGenderFilterPages(CandidateGender gender, int limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_gender_page(?,?)}")) {
            cs.setString(1, gender.toString());
            cs.setInt(2, limit);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    @Override
    public List<Candidate> filterByTechnology(int techId, int page, int limit) {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_technology(?,?,?)}")) {
            cs.setInt(1, techId);
            cs.setInt(2, page);
            cs.setInt(3, limit);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender").toUpperCase()));
                candidate.setDob(rs.getDate("dob"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setDescription(rs.getString("description"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public int getTechnologyFilterPages(int techId, int limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_technology_page(?,?)}")) {
            cs.setInt(1, techId);
            cs.setInt(2, limit);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;
    }

    @Override
    public List<Candidate> filterByAge(int minAge, int maxAge, int page, int limit) {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_age(?,?,?,?)}")) {
            cs.setInt(1, minAge);
            cs.setInt(2, maxAge);
            cs.setInt(3, page);
            cs.setInt(4, limit);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender").toUpperCase()));
                candidate.setDob(rs.getDate("dob"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setDescription(rs.getString("description"));
                candidates.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public int getAgeFilterPages(int minAge, int maxAge, int limit) {
        int totalPage = 0;
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_filter_candidate_by_age_page(?,?,?)}")) {
            cs.setInt(1, minAge);
            cs.setInt(2, maxAge);
            cs.setInt(3, limit);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPage;
    }
}