package ra.edu.business.service.candidate;

import ra.edu.business.dao.candidate.CandidateDao;
import ra.edu.business.dao.candidate.CandidateDaoImp;
import ra.edu.business.model.candidate.CandidateGender;

import java.util.Date;

public class CandidateSeviceImp implements CandidateSevice {
    private CandidateDao dao;
    public CandidateSeviceImp() {
        this.dao = new CandidateDaoImp();
    }
    @Override
    public boolean loginCandidate(String candidateName, String password) {
        return dao.loginCandidate(candidateName, password);
    }

    @Override
    public void registerCandidate(String name, String email, String phone, CandidateGender gender, Date dod, String description, String password) {
        dao.registerCandidate(name, email, phone, gender, dod, description, password);
    }
}
