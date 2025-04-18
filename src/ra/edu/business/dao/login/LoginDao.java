package ra.edu.business.dao.login;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.candidate.CandidateGender;

import java.sql.Date;

public interface LoginDao extends AppDao {
    Account login(String email, String password);
    void registerAdmin();
    void registerCandidate(String name, String email, String phone, CandidateGender gender, Date dob, String description, String password);
    AccountRole getRole(String email);

}
