package ra.edu.business.model.application;

import java.time.LocalDateTime;

public class Application {
    private int id;
    private int candidateId;
    private int recruitmentPositionId;
    private String cvUrl;
    private ApplicationProgress progress;
    private LocalDateTime interviewRequestDate;
    private ApplicationInterviewRequestResult interviewRequestResult;
    private String interviewLink;
    private LocalDateTime interviewTime;
    private ApplicationInterviewResult interviewResult;
    private String interviewResultNote;
    private LocalDateTime destroyAt;
    private String destroyReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Application() {
    }
    public Application(int id, int candidateId, int recruitmentPositionId, String cvUrl, ApplicationProgress progress,
            LocalDateTime interviewRequestDate, ApplicationInterviewRequestResult interviewRequestResult,
            String interviewLink, LocalDateTime interviewTime, ApplicationInterviewResult interviewResult,
            String interviewResultNote, LocalDateTime destroyAt, String destroyReason, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.candidateId = candidateId;
        this.recruitmentPositionId = recruitmentPositionId;
        this.cvUrl = cvUrl;
        this.progress = progress;
        this.interviewRequestDate = interviewRequestDate;
        this.interviewRequestResult = interviewRequestResult;
        this.interviewLink = interviewLink;
        this.interviewTime = interviewTime;
        this.interviewResult = interviewResult;
        this.interviewResultNote = interviewResultNote;
        this.destroyAt = destroyAt;
        this.destroyReason = destroyReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getRecruitmentPositionId() {
        return recruitmentPositionId;
    }

    public void setRecruitmentPositionId(int recruitmentPositionId) {
        this.recruitmentPositionId = recruitmentPositionId;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public ApplicationProgress getProgress() {
        return progress;
    }

    public void setProgress(ApplicationProgress progress) {
        this.progress = progress;
    }

    public LocalDateTime getInterviewRequestDate() {
        return interviewRequestDate;
    }

    public void setInterviewRequestDate(LocalDateTime interviewRequestDate) {
        this.interviewRequestDate = interviewRequestDate;
    }

    public ApplicationInterviewRequestResult getInterviewRequestResult() {
        return interviewRequestResult;
    }

    public void setInterviewRequestResult(ApplicationInterviewRequestResult interviewRequestResult) {
        this.interviewRequestResult = interviewRequestResult;
    }

    public String getInterviewLink() {
        return interviewLink;
    }

    public void setInterviewLink(String interviewLink) {
        this.interviewLink = interviewLink;
    }

    public LocalDateTime getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(LocalDateTime interviewTime) {
        this.interviewTime = interviewTime;
    }

    public ApplicationInterviewResult getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(ApplicationInterviewResult interviewResult) {
        this.interviewResult = interviewResult;
    }

    public String getInterviewResultNote() {
        return interviewResultNote;
    }

    public void setInterviewResultNote(String interviewResultNote) {
        this.interviewResultNote = interviewResultNote;
    }

    public LocalDateTime getDestroyAt() {
        return destroyAt;
    }

    public void setDestroyAt(LocalDateTime destroyAt) {
        this.destroyAt = destroyAt;
    }

    public String getDestroyReason() {
        return destroyReason;
    }

    public void setDestroyReason(String destroyReason) {
        this.destroyReason = destroyReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
