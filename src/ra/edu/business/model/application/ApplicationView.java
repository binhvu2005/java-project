package ra.edu.business.model.application;

public class ApplicationView {
    private int id;
    private String cvUrl;
    private String progress;
    private String recruitmentPositionName;


    public ApplicationView() {
    }
    public ApplicationView(int id, String cvUrl, String progress, String recruitmentPositionName) {
        this.id = id;
        this.cvUrl = cvUrl;
        this.progress = progress;
        this.recruitmentPositionName = recruitmentPositionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRecruitmentPositionName() {
        return recruitmentPositionName;
    }

    public void setRecruitmentPositionName(String recruitmentPositionName) {
        this.recruitmentPositionName = recruitmentPositionName;
    }
}
