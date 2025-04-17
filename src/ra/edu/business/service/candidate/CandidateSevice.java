package ra.edu.business.service.candidate;

import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.service.AppSevice;

import java.util.Date;

public interface CandidateSevice extends AppSevice {
    boolean loginCandidate(String candidateName, String password);
    void registerCandidate(String name, String email, String phone, CandidateGender gender, Date dod, String description, String password);
}
