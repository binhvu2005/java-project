package ra.edu.business.dao.application;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.application.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImp implements ApplicationDao {

    @Override
    public boolean createApplication(int candidateId, int recruitmentPositionId, String cvUrl) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_create_application(?, ?, ?)}")) {
            stmt.setInt(1, candidateId);
            stmt.setInt(2, recruitmentPositionId);
            stmt.setString(3, cvUrl);
            stmt.execute();
            return true;
        }catch (Exception e){
            System.out.println("lỗi khi tạo đơn ứng tuyển:" + e.getMessage());
        }
        return false;
    }

    @Override
    public List<ApplicationView> viewApplicationByCandidateId(int candidateId) {
        List<ApplicationView> applicationViews = new ArrayList<>();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_application_by_candidate_id(?)}")) {
            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ApplicationView applicationView = new ApplicationView();
                applicationView.setId(rs.getInt("id"));
                applicationView.setCvUrl(rs.getString("cvUrl"));
                applicationView.setRecruitmentPositionName(rs.getString("recruitmentPositionName"));
                applicationView.setProgress(rs.getString("progress"));
                applicationViews.add(applicationView);
            }
        } catch (Exception e) {
            System.out.println("lỗi khi xem đơn ứng tuyển:" + e.getMessage());
        }
        return applicationViews;
    }

    @Override
    public ApplicationDetail getApplicationById(int id) {
        ApplicationDetail application = new ApplicationDetail();
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_get_application_by_id(?)}")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                application.setId(rs.getInt("id"));
                application.setCvUrl(rs.getString("cvUrl"));
                String progress = rs.getString("progress");
                application.setProgress(ApplicationProgress.valueOf(progress.toUpperCase()));

                application.setRecruitmentPositionName(rs.getString("recruitmentPositionName"));
                application.setRecruitmentPositionDescription(rs.getString("recruitmentPositionDescription"));
                application.setMinSalary(rs.getInt("minSalary"));
                application.setMaxSalary(rs.getInt("maxSalary"));
                application.setMinExperience(rs.getInt("minExperience"));

                Timestamp interviewRequestDate = rs.getTimestamp("interviewRequestDate");
                if (interviewRequestDate != null)
                    application.setInterviewRequestDate(interviewRequestDate.toLocalDateTime());

                String interviewRequestResultStr = rs.getString("interviewRequestResult");
                application.setInterviewRequestResult(ApplicationInterviewRequestResult.valueOf(interviewRequestResultStr.toUpperCase()));
                application.setInterviewLink(rs.getString("interviewLink"));

                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null)
                    application.setInterviewTime(interviewTime.toLocalDateTime());

                String interviewResult = rs.getString("interviewResult");
                application.setInterviewResult(ApplicationInterviewResult.valueOf(interviewResult.toUpperCase()));
                application.setInterviewResultNote(rs.getString("interviewResultNote"));

                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null)
                    application.setDestroyAt(destroyAt.toLocalDateTime());

                application.setDestroyReason(rs.getString("destroyReason"));

                Timestamp createdAt = rs.getTimestamp("createdAt");
                if (createdAt != null)
                    application.setCreatedAt(createdAt.toLocalDateTime());

                Timestamp updatedAt = rs.getTimestamp("updatedAt");
                if (updatedAt != null)
                    application.setUpdatedAt(updatedAt.toLocalDateTime());

                return application;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin đơn ứng tuyển: " + e.getMessage());
        }
        return application ;
    }

    @Override
    public boolean confirmInterview(int applicationId, boolean confirm) {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall("{CALL sp_confirm_interview(?, ?)}")) {
            stmt.setInt(1, applicationId);
            stmt.setBoolean(2, confirm);
            stmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi khi xác nhận phỏng vấn: " + e.getMessage());
        }
        return false;
    }

}
