package ra.edu.business.model.recruitmentPosition;

import java.util.Date;

public class RecruitmentPosition {
    private int id;
	private String name;
    private String description;
    private Double minSalary;
    private Double maxSalary;
    private int minExperience;
    private RecruitmentPositionStatus status;
    private Date createdDate;
    private Date expiredDate;

    public RecruitmentPosition() {
    }
    public RecruitmentPosition(int id, String name, String description, Double minSalary, Double maxSalary, int minExperience, RecruitmentPositionStatus status, Date createdDate, Date expiredDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.minExperience = minExperience;
        this.status = status;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(int minExperience) {
        this.minExperience = minExperience;
    }

    public RecruitmentPositionStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitmentPositionStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
