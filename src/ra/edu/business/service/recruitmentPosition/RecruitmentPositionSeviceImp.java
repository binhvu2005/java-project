package ra.edu.business.service.recruitmentPosition;

import ra.edu.business.dao.RecruitmentPosition.RecruitmentPositionDao;
import ra.edu.business.dao.RecruitmentPosition.RecruitmentPositionDaoImp;
import ra.edu.business.model.recruitmentPosition.RecruitmentPosition;
import ra.edu.business.model.technology.Technology;

import java.util.Date;
import java.util.List;

public class RecruitmentPositionSeviceImp implements RecruitmentPositionSevice {
    private RecruitmentPositionDao dao;
    public RecruitmentPositionSeviceImp() {
        this.dao = new RecruitmentPositionDaoImp();
    }
    @Override
    public int getRecruitmentPositionPage(int in_limit) {
        return dao.getRecruitmentPositionPage(in_limit);
    }

    @Override
    public List<RecruitmentPosition> getAllRecruitmentPosition(int page, int in_limit) {
        return dao.getAllRecruitmentPosition(page, in_limit);
    }

    @Override
    public boolean addRecruitmentPosition(String name, String description, double minSalary, double maxSalary, int minExperience, Date expiredDate) {
    	return dao.addRecruitmentPosition(name, description, minSalary, maxSalary, minExperience, expiredDate);
    }

    @Override
    public boolean updateRecruitmentPositionByName(int id, String newName) {
        return dao.updateRecruitmentPositionByName(id, newName);
    }

    @Override
    public boolean updateRecruitmentPositionByDescription(int id, String newDescription) {
        return dao.updateRecruitmentPositionByDescription(id, newDescription);
    }

    @Override
    public boolean updateRecruitmentPositionBySalary(int id, double newMinSalary, double newMaxSalary) {
        return dao.updateRecruitmentPositionBySalary(id, newMinSalary, newMaxSalary);
    }

    @Override
    public boolean updateRecruitmentPositionByExperience(int id, int newMinExperience) {
        return dao.updateRecruitmentPositionByExperience(id, newMinExperience);
    }

    @Override
    public boolean updateRecruitmentPositionByExpiredDate(int id, Date newExpiredDate) {
        return dao.updateRecruitmentPositionByExpiredDate(id, newExpiredDate);
    }

    @Override
    public boolean deleteRecruitmentPositionById(int id) {
        return dao.deleteRecruitmentPositionById(id);
    }

    @Override
    public RecruitmentPosition getRecruitmentPositionById(int id) {
        return dao.getRecruitmentPositionById(id);
    }

    @Override
    public List<Technology> getTechnologiesOfRecruitmentPosition(int id) {
        return dao.getTechnologiesOfRecruitmentPosition(id);
    }

    @Override
    public boolean addTechnologyToRecruitmentPosition(int recruitmentPositionId, int technologyId) {
        return dao.addTechnologyToRecruitmentPosition(recruitmentPositionId, technologyId);
    }

    @Override
    public boolean removeTechnologyFromRecruitmentPosition(int recruitmentPositionId, int technologyId) {
        return dao.removeTechnologyFromRecruitmentPosition(recruitmentPositionId, technologyId);
    }
}
