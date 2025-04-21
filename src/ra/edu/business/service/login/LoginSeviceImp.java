package ra.edu.business.service.login;

import ra.edu.business.dao.login.LoginDao;
import ra.edu.business.dao.login.LoginDaoImp;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.candidate.CandidateGender;

import java.sql.Date;

public class LoginSeviceImp implements LoginSevice {
    private LoginDao dao;
    public LoginSeviceImp() {
        this.dao = new LoginDaoImp();
    }

    @Override
    public Account login(String email, String password) {
        return dao.login(email, password);
    }

    @Override
    public void registerAdmin() {
        dao.registerAdmin();
    }

    @Override
    public void registerCandidate(String name, String email, String phone, CandidateGender gender, Date dob,int experience, String description, String password) {
        dao.registerCandidate(name, email, phone, gender, dob,experience, description, password);
    }

    @Override
    public AccountRole getRole(int id) {
        return dao.getRole(id);
    }
}
