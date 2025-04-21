package ra.edu.business.dao.RecruitmentPosition;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.recruitmentPosition.RecruitmentPosition;
import ra.edu.business.model.technology.Technology;

import java.util.Date;
import java.util.List;

public interface RecruitmentPositionDao extends AppDao {
    int getRecruitmentPositionPage(int in_limit);
    List<RecruitmentPosition> getAllRecruitmentPosition(int page, int in_limit);

    boolean addRecruitmentPosition(String name, String description, double minSalary, double maxSalary, int minExperience, Date expiredDate);
    boolean updateRecruitmentPositionByName(int id , String newName);
    boolean updateRecruitmentPositionByDescription(int id , String newDescription);
    boolean updateRecruitmentPositionBySalary(int id , double newMinSalary , double newMaxSalary);
    boolean updateRecruitmentPositionByExperience(int id , int newMinExperience);
    boolean updateRecruitmentPositionByExpiredDate(int id , Date newExpiredDate);
    boolean deleteRecruitmentPositionById(int id);
    RecruitmentPosition getRecruitmentPositionById(int id);
//lấy danh sách công nghệ theo id vị trí tuyển dụng
    List<Technology> getTechnologiesOfRecruitmentPosition(int id);
// -- thêm công nghệ cho vị trí tuyển dụng
    boolean addTechnologyToRecruitmentPosition(int recruitmentPositionId, int technologyId);
// -- xoá công nghệ đã gán cho vị trí tuyển dụng
    boolean removeTechnologyFromRecruitmentPosition(int recruitmentPositionId, int technologyId);

}
