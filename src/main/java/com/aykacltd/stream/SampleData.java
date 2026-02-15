package com.aykacltd.stream;

import com.aykacltd.stream.Address.AddressType;
import com.aykacltd.stream.Profession.EmploymentStatus;
import com.aykacltd.stream.Skill.ProficiencyLevel;
import com.aykacltd.stream.Person.Gender;

import java.time.LocalDate;
import java.util.List;

/**
 * Factory class that produces a realistic dataset of {@link Person} objects
 * for use in Stream API exercises.
 *
 * <p>The dataset is carefully designed to cover many edge cases and
 * grouping scenarios:</p>
 * <ul>
 *   <li>Multiple cities and countries for geographic grouping</li>
 *   <li>Varied departments, companies, and salary ranges</li>
 *   <li>Different employment statuses and experience levels</li>
 *   <li>Overlapping and unique skills for flatMap exercises</li>
 *   <li>Mixed genders and age ranges for demographic analysis</li>
 * </ul>
 */
public final class SampleData {

    private SampleData() {
        // utility class — no instantiation
    }

    /**
     * Returns an immutable list of 12 diverse {@link Person} records.
     *
     * @return sample dataset
     */
    public static List<Person> getPeople() {
        return List.of(
                Person.builder()
                        .firstName("Alice").lastName("Morgan")
                        .dateOfBirth(LocalDate.of(1990, 3, 15))
                        .gender(Gender.FEMALE)
                        .email("alice.morgan@techcorp.com")
                        .addresses(List.of(
                                new Address("12 Oak Lane", "London", "UK", "SW1A 1AA", AddressType.HOME),
                                new Address("100 Tech Park", "London", "UK", "EC2A 1NT", AddressType.WORK)
                        ))
                        .profession(new Profession("Senior Developer", "Engineering", "TechCorp", 8, 85000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Java", ProficiencyLevel.EXPERT, "Programming"),
                                new Skill("Spring Boot", ProficiencyLevel.ADVANCED, "Framework"),
                                new Skill("SQL", ProficiencyLevel.ADVANCED, "Database"),
                                new Skill("Docker", ProficiencyLevel.INTERMEDIATE, "DevOps")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Bob").lastName("Chen")
                        .dateOfBirth(LocalDate.of(1985, 7, 22))
                        .gender(Gender.MALE)
                        .email("bob.chen@financeplus.com")
                        .addresses(List.of(
                                new Address("45 High Street", "Manchester", "UK", "M1 1AD", AddressType.HOME)
                        ))
                        .profession(new Profession("Data Analyst", "Analytics", "FinancePlus", 12, 72000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Python", ProficiencyLevel.EXPERT, "Programming"),
                                new Skill("SQL", ProficiencyLevel.EXPERT, "Database"),
                                new Skill("Tableau", ProficiencyLevel.ADVANCED, "Visualisation"),
                                new Skill("Machine Learning", ProficiencyLevel.INTERMEDIATE, "AI")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Clara").lastName("Dubois")
                        .dateOfBirth(LocalDate.of(1995, 11, 3))
                        .gender(Gender.FEMALE)
                        .email("clara.dubois@designhub.fr")
                        .addresses(List.of(
                                new Address("8 Rue de Rivoli", "Paris", "France", "75001", AddressType.HOME),
                                new Address("22 Avenue Montaigne", "Paris", "France", "75008", AddressType.WORK)
                        ))
                        .profession(new Profession("UX Designer", "Design", "DesignHub", 4, 55000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Figma", ProficiencyLevel.EXPERT, "Design"),
                                new Skill("CSS", ProficiencyLevel.ADVANCED, "Programming"),
                                new Skill("User Research", ProficiencyLevel.ADVANCED, "Design"),
                                new Skill("JavaScript", ProficiencyLevel.INTERMEDIATE, "Programming")
                        ))
                        .build(),

                Person.builder()
                        .firstName("David").lastName("Okafor")
                        .dateOfBirth(LocalDate.of(1988, 1, 30))
                        .gender(Gender.MALE)
                        .email("david.okafor@techcorp.com")
                        .addresses(List.of(
                                new Address("7 Castle Road", "Birmingham", "UK", "B1 1BB", AddressType.HOME),
                                new Address("100 Tech Park", "London", "UK", "EC2A 1NT", AddressType.WORK)
                        ))
                        .profession(new Profession("Tech Lead", "Engineering", "TechCorp", 14, 105000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Java", ProficiencyLevel.EXPERT, "Programming"),
                                new Skill("Kubernetes", ProficiencyLevel.EXPERT, "DevOps"),
                                new Skill("System Design", ProficiencyLevel.ADVANCED, "Architecture"),
                                new Skill("Mentoring", ProficiencyLevel.ADVANCED, "Leadership")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Emma").lastName("Schmidt")
                        .dateOfBirth(LocalDate.of(1992, 6, 18))
                        .gender(Gender.FEMALE)
                        .email("emma.schmidt@freelance.de")
                        .addresses(List.of(
                                new Address("15 Berliner Str.", "Berlin", "Germany", "10115", AddressType.HOME)
                        ))
                        .profession(new Profession("Freelance Consultant", "Consulting", "Self-Employed", 6, 78000, EmploymentStatus.FREELANCE))
                        .skills(List.of(
                                new Skill("Agile", ProficiencyLevel.EXPERT, "Methodology"),
                                new Skill("Java", ProficiencyLevel.ADVANCED, "Programming"),
                                new Skill("Public Speaking", ProficiencyLevel.ADVANCED, "Leadership"),
                                new Skill("Spring Boot", ProficiencyLevel.INTERMEDIATE, "Framework")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Frank").lastName("Williams")
                        .dateOfBirth(LocalDate.of(1978, 9, 5))
                        .gender(Gender.MALE)
                        .email("frank.williams@govuk.gov.uk")
                        .addresses(List.of(
                                new Address("3 Downing Crescent", "London", "UK", "WC2N 5DU", AddressType.HOME),
                                new Address("1 Whitehall", "London", "UK", "SW1A 2AS", AddressType.WORK)
                        ))
                        .profession(new Profession("Programme Manager", "Digital Services", "HMRC", 20, 92000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Stakeholder Management", ProficiencyLevel.EXPERT, "Leadership"),
                                new Skill("Risk Assessment", ProficiencyLevel.EXPERT, "Management"),
                                new Skill("SQL", ProficiencyLevel.INTERMEDIATE, "Database"),
                                new Skill("Agile", ProficiencyLevel.ADVANCED, "Methodology")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Grace").lastName("Kim")
                        .dateOfBirth(LocalDate.of(1998, 4, 12))
                        .gender(Gender.FEMALE)
                        .email("grace.kim@techcorp.com")
                        .addresses(List.of(
                                new Address("20 Park Avenue", "Manchester", "UK", "M2 3HG", AddressType.HOME),
                                new Address("55 Northern Quarter", "Manchester", "UK", "M4 1LQ", AddressType.WORK)
                        ))
                        .profession(new Profession("Junior Developer", "Engineering", "TechCorp", 2, 38000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Java", ProficiencyLevel.INTERMEDIATE, "Programming"),
                                new Skill("React", ProficiencyLevel.INTERMEDIATE, "Framework"),
                                new Skill("Git", ProficiencyLevel.ADVANCED, "DevOps"),
                                new Skill("Python", ProficiencyLevel.BEGINNER, "Programming")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Hassan").lastName("Ali")
                        .dateOfBirth(LocalDate.of(1983, 12, 1))
                        .gender(Gender.MALE)
                        .email("hassan.ali@financeplus.com")
                        .addresses(List.of(
                                new Address("9 Victoria Rd", "London", "UK", "E1 6AN", AddressType.HOME)
                        ))
                        .profession(new Profession("VP of Analytics", "Analytics", "FinancePlus", 18, 130000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Python", ProficiencyLevel.EXPERT, "Programming"),
                                new Skill("Machine Learning", ProficiencyLevel.EXPERT, "AI"),
                                new Skill("Leadership", ProficiencyLevel.EXPERT, "Leadership"),
                                new Skill("SQL", ProficiencyLevel.ADVANCED, "Database"),
                                new Skill("Tableau", ProficiencyLevel.INTERMEDIATE, "Visualisation")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Isabella").lastName("Rossi")
                        .dateOfBirth(LocalDate.of(2000, 8, 25))
                        .gender(Gender.FEMALE)
                        .email("isabella.rossi@contractor.it")
                        .addresses(List.of(
                                new Address("Via Roma 14", "Milan", "Italy", "20121", AddressType.HOME),
                                new Address("10 Soho Square", "London", "UK", "W1D 3QA", AddressType.SECONDARY)
                        ))
                        .profession(new Profession("QA Engineer", "Engineering", "TechCorp", 1, 34000, EmploymentStatus.CONTRACT))
                        .skills(List.of(
                                new Skill("Selenium", ProficiencyLevel.ADVANCED, "Testing"),
                                new Skill("Java", ProficiencyLevel.INTERMEDIATE, "Programming"),
                                new Skill("Cypress", ProficiencyLevel.INTERMEDIATE, "Testing"),
                                new Skill("CI/CD", ProficiencyLevel.BEGINNER, "DevOps")
                        ))
                        .build(),

                Person.builder()
                        .firstName("James").lastName("Patel")
                        .dateOfBirth(LocalDate.of(1993, 2, 14))
                        .gender(Gender.MALE)
                        .email("james.patel@designhub.fr")
                        .addresses(List.of(
                                new Address("30 Quai Voltaire", "Paris", "France", "75007", AddressType.HOME)
                        ))
                        .profession(new Profession("Product Manager", "Product", "DesignHub", 7, 88000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Roadmapping", ProficiencyLevel.EXPERT, "Management"),
                                new Skill("SQL", ProficiencyLevel.INTERMEDIATE, "Database"),
                                new Skill("Agile", ProficiencyLevel.ADVANCED, "Methodology"),
                                new Skill("User Research", ProficiencyLevel.ADVANCED, "Design")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Karen").lastName("Nowak")
                        .dateOfBirth(LocalDate.of(1987, 5, 9))
                        .gender(Gender.FEMALE)
                        .email("karen.nowak@techcorp.com")
                        .addresses(List.of(
                                new Address("5 Queen's Gate", "London", "UK", "SW7 5HR", AddressType.HOME),
                                new Address("100 Tech Park", "London", "UK", "EC2A 1NT", AddressType.WORK)
                        ))
                        .profession(new Profession("DevOps Engineer", "Engineering", "TechCorp", 10, 95000, EmploymentStatus.FULL_TIME))
                        .skills(List.of(
                                new Skill("Kubernetes", ProficiencyLevel.EXPERT, "DevOps"),
                                new Skill("Docker", ProficiencyLevel.EXPERT, "DevOps"),
                                new Skill("Terraform", ProficiencyLevel.ADVANCED, "DevOps"),
                                new Skill("Java", ProficiencyLevel.INTERMEDIATE, "Programming"),
                                new Skill("Python", ProficiencyLevel.INTERMEDIATE, "Programming")
                        ))
                        .build(),

                Person.builder()
                        .firstName("Liam").lastName("O'Brien")
                        .dateOfBirth(LocalDate.of(1996, 10, 20))
                        .gender(Gender.MALE)
                        .email("liam.obrien@unemployed.ie")
                        .addresses(List.of(
                                new Address("18 Grafton St", "Dublin", "Ireland", "D02 VF65", AddressType.HOME)
                        ))
                        .profession(new Profession("Software Developer", "N/A", "N/A", 3, 0, EmploymentStatus.UNEMPLOYED))
                        .skills(List.of(
                                new Skill("Java", ProficiencyLevel.ADVANCED, "Programming"),
                                new Skill("Spring Boot", ProficiencyLevel.INTERMEDIATE, "Framework"),
                                new Skill("React", ProficiencyLevel.INTERMEDIATE, "Framework"),
                                new Skill("Docker", ProficiencyLevel.BEGINNER, "DevOps")
                        ))
                        .build()
        );
    }
}
