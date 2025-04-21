package ra.edu.business.service.candidate;

import ra.edu.business.dao.candidate.CandidateDao;
import ra.edu.business.dao.candidate.CandidateDaoImp;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.technology.Technology;

import java.util.Date;
import java.util.List;

public class CandidateSeviceImp implements CandidateSevice {
    private CandidateDao dao;
    public CandidateSeviceImp() {
        this.dao = new CandidateDaoImp();
    }


    @Override
    public Candidate getCandidateInfo(int candidateId) {
        return dao.getCandidateInfo(candidateId);
    }

    @Override
    public int getCandidateIdByAccountId(int accountId) {
        return dao.getCandidateIdByAccountId(accountId);
    }



    @Override
    public boolean changeCandidatePassword(int accountId, String oldPassword, String newPassword) {
        return dao.changeCandidatePassword(accountId, oldPassword, newPassword);
    }

    @Override
    public boolean updateCandidateName(int candidateId, String newName) {
            return dao.updateCandidateName(candidateId, newName);
    }

    @Override
    public boolean updateCandidatePhone(int candidateId, String newPhone) {
        return dao.updateCandidatePhone(candidateId, newPhone);
    }

    @Override
    public boolean updateCandidateGender(int candidateId, CandidateGender newGender) {
        return dao.updateCandidateGender(candidateId, newGender);
    }

    @Override
    public boolean updateCandidateDob(int candidateId, Date newDob) {
        return dao.updateCandidateDob(candidateId, newDob);
    }

    @Override
    public boolean updateCandidateDescription(int candidateId, String newDescription) {
        return dao.updateCandidateDescription(candidateId, newDescription);
    }

    @Override
    public boolean updateCandidateExperience(int candidateId, int newExperience) {
        return dao.updateCandidateExperience(candidateId, newExperience);
    }

    @Override
    public boolean addTechnologyToCandidate(int candidateId, int technologyId) {
        return dao.addTechnologyToCandidate(candidateId, technologyId);
    }

    @Override
    public boolean removeTechnologyFromCandidate(int candidateId, int technologyId) {
        return dao.removeTechnologyFromCandidate(candidateId, technologyId);
    }

    @Override
    public List<Technology> getTechnologiesOfCandidate(int candidateId) {
        return dao.getTechnologiesOfCandidate(candidateId);
    }
}
