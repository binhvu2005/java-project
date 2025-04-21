package ra.edu.validate;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.Date;

public class Validator {

    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0[3|5|7|8|9])[0-9]{8}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^https?://.*");
    private static final Pattern ID_PATTERN = Pattern.compile("^C\\d{4}$");

    // Length check
    public static boolean isValidCandidateId(String id) {
        return id != null && ID_PATTERN.matcher(id).matches();
    }
    public static boolean isValidLength(String input) {
        return input != null && input.length() >= 10 && input.length() <= 100;
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

    // URL check (cvUrl, interviewLink)
    public static boolean isValidUrl(String url) {
        return url == null || URL_PATTERN.matcher(url).matches(); // NULL is allowed
    }

    // Enum validation
    public static boolean isValidGender(String gender) {
        if (gender == null) return false;
        String g = gender.trim().toUpperCase();
        return g.equals("MALE") || g.equals("FEMALE") || g.equals("OTHER");
    }


    public static boolean isValidStatus(String status) {
        return status == null || (status.equals("active") || status.equals("inactive"));
    }

    public static boolean isValidProgress(String progress) {
        return progress != null && (
                progress.equals("applied") || progress.equals("interviewing") ||
                        progress.equals("offer") || progress.equals("rejected") || progress.equals("withdrawn")
        );
    }

    public static boolean isValidInterviewResult(String result) {
        return result == null || result.equals("pass") || result.equals("fail") || result.equals("pending");
    }

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

    public static boolean isValidExperience(int newExperience) {
        return newExperience >= 0 && newExperience <= 100;
    }
}
