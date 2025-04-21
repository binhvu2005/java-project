package ra.edu.business.service.candidate;

import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.AppSevice;

import java.util.Date;
import java.util.List;

public interface CandidateSevice extends AppSevice {

    Candidate getCandidateInfo(int candidateId);
    int getCandidateIdByAccountId(int accountId);
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
