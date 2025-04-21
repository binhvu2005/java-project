package ra.edu.business.service.candidateManagement;

import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.service.AppSevice;

import java.util.List;

public interface CandidateManagementSevice extends AppSevice {
    int getTotalPages(int limit);

    // 2. Lấy danh sách ứng viên theo trang
    List<Candidate> getCandidates(int page, int limit);

    // 3. Mở/Khoá tài khoản ứng viên
    boolean updateCandidateStatus(int candidateId, String status); // active / inactive

    // 4. Reset mật khẩu ứng viên
    boolean resetCandidatePassword(int candidateId , String password);

    // 5. Tìm kiếm ứng viên theo tên + phân trang
    List<Candidate> searchCandidatesByName(String name, int page, int limit);
    int getSearchCandidateTotalPages(String name, int limit);

    // 6. Lọc theo kinh nghiệm
    List<Candidate> filterByExperience(int minExp, int maxExp, int page, int limit);
    int getExperienceFilterPages(int minExp, int maxExp, int limit);

    // 7. Lọc theo giới tính
    List<Candidate> filterByGender(CandidateGender gender, int page, int limit);
    int getGenderFilterPages(CandidateGender gender, int limit);

    // 8. Lọc theo công nghệ
    List<Candidate> filterByTechnology(int techId, int page, int limit);
    int getTechnologyFilterPages(int techId, int limit);

    // 9. Lọc theo độ tuổi
    List<Candidate> filterByAge(int minAge, int maxAge, int page, int limit);
    int getAgeFilterPages(int minAge, int maxAge, int limit);
}
