package ra.edu.business.model.candidate;

import ra.edu.business.model.account.AccountStatus;

import java.util.Date;

public class Candidate {
    private int id;
    private String Name;
    private String Email;
    private String Phone;
    private CandidateGender Gender;
    private Date Dob;
    private int experience;
    private String description;
    private String password;
    private AccountStatus status;

    public Candidate() {
    }

    public Candidate(int id, String name, String email, String phone, CandidateGender gender, Date dob,int experience, String description, String password, AccountStatus status) {
        this.id = id;
        Name = name;
        Email = email;
        Phone = phone;
        Gender = gender;
        Dob = dob;
        this.experience = experience;
        this.description = description;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public CandidateGender getGender() {
        return Gender;
    }

    public void setGender(CandidateGender gender) {
        Gender = gender;
    }

    public Date getDob() {
        return Dob;
    }

    public void setDob(Date dob) {
        Dob = dob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
