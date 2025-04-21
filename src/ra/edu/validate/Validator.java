package ra.edu.validate;

import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0[3|5|7|8|9])[0-9]{8}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^https?://.*");

    // Length check (general)
    public static boolean isValidLength(String input) {
        return input != null && input.length() >= 10 && input.length() <= 100;
    }

    // Recruitment position name length check (e.g., 5–100)
    public static boolean isValidRecruitmentPositionNameLength(String name) {
        return name != null && name.trim().length() >= 5 && name.length() <= 100;
    }

    // Email check
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Phone check (Vietnam)
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    // Salary check
    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }

    // Salary range: max > min
    public static boolean isValidSalaryRange(double minSalary, double maxSalary) {
        return minSalary > 0 && maxSalary > minSalary;
    }

    // Experience check
    public static boolean isValidExperience(int experience) {
        return experience >= 0 && experience <= 100;
    }

    // URL check (cvUrl, interviewLink)
    public static boolean isValidUrl(String url) {
        return url == null || URL_PATTERN.matcher(url).matches(); // NULL is allowed
    }

    // Gender ENUM
    public static boolean isValidGender(String gender) {
        if (gender == null) return false;
        String g = gender.trim().toUpperCase();
        return g.equals("MALE") || g.equals("FEMALE") || g.equals("OTHER");
    }

    // Status ENUM (candidate)
    public static boolean isValidStatus(String status) {
        return status == null || (status.equals("active") || status.equals("inactive"));
    }

    // Recruitment position status ENUM
    public static boolean isValidRecruitmentPositionStatus(String status) {
        return status != null && (status.equalsIgnoreCase("ACTIVE") || status.equalsIgnoreCase("INACTIVE"));
    }

    // Progress ENUM (candidate process)
    public static boolean isValidProgress(String progress) {
        return progress != null && (
                progress.equals("applied") || progress.equals("interviewing") ||
                        progress.equals("offer") || progress.equals("rejected") || progress.equals("withdrawn")
        );
    }

    // Interview result
    public static boolean isValidInterviewResult(String result) {
        return result == null || result.equals("pass") || result.equals("fail") || result.equals("pending");
    }

    // Interview request result
    public static boolean isValidInterviewRequestResult(String result) {
        return result == null || result.equals("accepted") || result.equals("rejected") || result.equals("pending");
    }

    // destroyAt logic: if set, destroyReason must be set
    public static boolean isValidDestroyFields(Date destroyAt, String destroyReason) {
        return destroyAt == null || (destroyAt != null && destroyReason != null && !destroyReason.isEmpty());
    }

    // Date not null check
    public static boolean isNotNullDate(Date date) {
        return date != null;
    }

    // ExpiredDate must be after CreatedDate
    public static boolean isValidExpiredDateAfterCreatedDate(Date createdDate, Date expiredDate) {
        return createdDate != null && expiredDate != null && expiredDate.after(createdDate);
    }
}
