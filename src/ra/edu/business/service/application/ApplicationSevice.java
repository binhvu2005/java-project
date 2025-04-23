package ra.edu.business.service.application;

import ra.edu.business.model.application.ApplicationDetail;
import ra.edu.business.model.application.ApplicationView;
import ra.edu.business.service.AppSevice;

import java.util.List;

public interface ApplicationSevice extends AppSevice {
    boolean createApplication(int userId, int recruitmentPositionId, String cvUrl);

    List<ApplicationView> viewApplicationByCandidateId(int candidateId);

    ApplicationDetail getApplicationById(int id);
    boolean confirmInterview(int applicationId, boolean confirm);
}
