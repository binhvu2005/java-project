package ra.edu.business.dao.candidate;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.technology.Technology;

import java.util.Date;
import java.util.List;

public interface CandidateDao extends AppDao {

    int getCandidateIdByAccountId(int accountId);
    Candidate getCandidateInfo(int candidateId);
    void registerCandidate(int id, String name, String email, String phone, CandidateGender gender, Date dob,int experience, String description, String password);

    // Thay đổi mật khẩu ứng viên
    boolean changeCandidatePassword(int accountId, String oldPassword, String newPassword);

    // Cập nhật từng thông tin cá nhân ứng viên (theo từng trường)
    boolean updateCandidateName(int candidateId, String newName);
    boolean updateCandidatePhone(int candidateId, String newPhone);
    boolean updateCandidateGender(int candidateId, CandidateGender newGender);
    boolean updateCandidateDob(int candidateId, Date newDob);
    boolean updateCandidateDescription(int candidateId, String newDescription);
    boolean updateCandidateExperience(int candidateId, int newExperience);
    // Quản lý công nghệ ứng viên
    boolean addTechnologyToCandidate(int candidateId, int technologyId);  // thêm công nghệ (chỉ khi công nghệ active)
    boolean removeTechnologyFromCandidate(int candidateId, int technologyId);  // xoá công nghệ đã gán
    List<Technology> getTechnologiesOfCandidate(int candidateId);  // lấy danh sách công nghệ của ứng viên


}
