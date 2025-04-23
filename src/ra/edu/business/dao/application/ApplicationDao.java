    package ra.edu.business.dao.application;

    import ra.edu.business.dao.AppDao;
    import ra.edu.business.model.application.Application;
    import ra.edu.business.model.application.ApplicationDetail;
    import ra.edu.business.model.application.ApplicationView;

    import java.util.List;

    public interface ApplicationDao extends AppDao {
        boolean createApplication(int candidateId, int recruitmentPositionId ,String cvUrl);

        List<ApplicationView> viewApplicationByCandidateId(int candidateId);
        ApplicationDetail getApplicationById(int id);
        boolean confirmInterview(int applicationId, boolean confirm);

    }
