package ra.edu.business.service.login;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.service.AppSevice;

import java.sql.Date;

public interface LoginSevice extends AppSevice {
    Account login(String email, String password);
    void registerAdmin();
    void registerCandidate(String name, String email, String phone, CandidateGender gender, Date dob,int experience,String description, String password);
    AccountRole getRole(int id);
}
