package ra.edu.business.service.application;

import ra.edu.business.dao.application.ApplicationDao;
import ra.edu.business.dao.application.ApplicationDaoImp;
import ra.edu.business.model.application.ApplicationDetail;
import ra.edu.business.model.application.ApplicationView;

import java.util.List;

public class ApplicationSeviceImp implements ApplicationSevice {
    private final ApplicationDao dao ;
    public ApplicationSeviceImp() {
        this.dao = new ApplicationDaoImp();
    }
    @Override
    public boolean createApplication(int userId, int recruitmentPositionId, String cvUrl) {
        return dao.createApplication(userId, recruitmentPositionId, cvUrl);
    }

    @Override
    public List<ApplicationView> viewApplicationByCandidateId(int candidateId) {
        return dao.viewApplicationByCandidateId(candidateId);
    }
    @Override
    public ApplicationDetail getApplicationById(int id) {
        return dao.getApplicationById(id);
    }

    @Override
    public boolean confirmInterview(int applicationId, boolean confirm) {
        return dao.confirmInterview(applicationId, confirm);
    }
}
