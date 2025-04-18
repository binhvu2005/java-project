package ra.edu.business.dao.candidate;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.candidate.CandidateGender;

import java.util.Date;

public interface CandidateDao extends AppDao {
    boolean loginCandidate(String candidateName, String password);
    void registerCandidate(String id, String name, String email,String phone,CandidateGender gender, Date dod, String description, String password);
}
