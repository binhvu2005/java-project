package ra.edu.business.service.candidateManagement;

import ra.edu.business.dao.candidateManagement.CandidateManagementDao;
import ra.edu.business.dao.candidateManagement.CandidateManagementDaoImp;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;

import java.util.List;

public class CandidateManagementSeviceImp implements CandidateManagementSevice {
    private CandidateManagementDao dao;
    public CandidateManagementSeviceImp() {
        this.dao = new CandidateManagementDaoImp();
    }
    @Override
    public int getTotalPages(int limit) {
        return dao.getTotalPages(limit);
    }

    @Override
    public List<Candidate> getCandidates(int page, int limit) {
        return dao.getCandidates(page, limit);
    }

    @Override
    public boolean updateCandidateStatus(int candidateId, String status) {
        return dao.updateCandidateStatus(candidateId, status);
    }

    @Override
    public boolean resetCandidatePassword(int candidateId, String password) {
        return dao.resetCandidatePassword(candidateId, password);
    }

    @Override
    public List<Candidate> searchCandidatesByName(String name, int page, int limit) {
        return dao.searchCandidatesByName(name, page, limit);
    }

    @Override
    public int getSearchCandidateTotalPages(String name, int limit) {
        return dao.getSearchCandidateTotalPages(name, limit);
    }

    @Override
    public List<Candidate> filterByExperience(int minExp, int maxExp, int page, int limit) {
        return dao.filterByExperience(minExp, maxExp, page, limit);
    }

    @Override
    public int getExperienceFilterPages(int minExp, int maxExp, int limit) {
        return dao.getExperienceFilterPages(minExp, maxExp, limit);
    }

    @Override
    public List<Candidate> filterByGender(CandidateGender gender, int page, int limit) {
        return dao.filterByGender(gender, page, limit);
    }

    @Override
    public int getGenderFilterPages(CandidateGender gender, int limit) {
        return dao.getGenderFilterPages(gender, limit);
    }

    @Override
    public List<Candidate> filterByTechnology(int techId, int page, int limit) {
        return dao.filterByTechnology(techId, page, limit);
    }

    @Override
    public int getTechnologyFilterPages(int techId, int limit) {
        return dao.getTechnologyFilterPages(techId, limit);
    }

    @Override
    public List<Candidate> filterByAge(int minAge, int maxAge, int page, int limit) {
        return dao.filterByAge(minAge, maxAge, page, limit);
    }

    @Override
    public int getAgeFilterPages(int minAge, int maxAge, int limit) {
        return dao.getAgeFilterPages(minAge, maxAge, limit);
    }
    
}
