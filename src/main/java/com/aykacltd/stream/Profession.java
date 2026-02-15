package com.aykacltd.stream;

/**
 * Represents the professional details of a {@link Person}.
 *
 * <p>Captures the job title, department, company name, years of
 * experience, current salary, and employment status. Used in Stream
 * exercises for salary analytics, department-level grouping, and
 * experience-based filtering.</p>
 *
 * @param title           the job title (e.g. "Senior Developer")
 * @param department      the organisational department (e.g. "Engineering")
 * @param company         the employer name
 * @param yearsExperience total years of professional experience
 * @param salary          current annual salary in GBP
 * @param status          the employment status
 */
public record Profession(
    String title,
    String department,
    String company,
    int yearsExperience,
    double salary,
    EmploymentStatus status
) {

    /**
     * Indicates the current employment status of a person.
     */
    public enum EmploymentStatus {
        FULL_TIME, PART_TIME, CONTRACT, FREELANCE, UNEMPLOYED
    }
}
