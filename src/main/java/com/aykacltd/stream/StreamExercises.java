package com.aykacltd.stream;

import com.aykacltd.stream.Address.AddressType;
import com.aykacltd.stream.Profession.EmploymentStatus;
import com.aykacltd.stream.Skill.ProficiencyLevel;
import com.aykacltd.stream.Person.Gender;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

/**
 * <h1>Java Stream API — Comprehensive Exercise Suite</h1>
 *
 * <p>This class contains <strong>40+</strong> self-contained methods that
 * demonstrate the full breadth of the Stream API, from basic filtering
 * and mapping through advanced multi-level grouping, custom collectors,
 * and parallel stream techniques.</p>
 *
 * <h2>Domain Model</h2>
 * <pre>
 *   Person ──┬── Address    (one-to-many)
 *            ├── Profession (one-to-one)
 *            └── Skill      (one-to-many)
 * </pre>
 *
 * <h2>Table of Contents</h2>
 * <ol>
 *   <li>Section 1  – Basic Filtering &amp; Mapping</li>
 *   <li>Section 2  – Sorting &amp; Comparators</li>
 *   <li>Section 3  – Reduction &amp; Aggregation</li>
 *   <li>Section 4  – FlatMap &amp; Nested Collections</li>
 *   <li>Section 5  – Collectors.groupingBy (single &amp; multi-level)</li>
 *   <li>Section 6  – Collectors.partitioningBy</li>
 *   <li>Section 7  – Collectors.toMap &amp; toUnmodifiableMap</li>
 *   <li>Section 8  – Custom Collectors &amp; Collector.of</li>
 *   <li>Section 9  – Advanced Collector Compositions</li>
 *   <li>Section 10 – Parallel Streams &amp; Thread Safety</li>
 *   <li>Section 11 – Stream.teeing, mapMulti &amp; Gatherers (Java 22+)</li>
 *   <li>Section 12 – Real-World Pipeline Patterns</li>
 * </ol>
 *
 * @author Stream API Exercise Suite
 * @see com.aykacltd.stream.Person
 * @see com.aykacltd.stream.SampleData
 */
public class StreamExercises {

    /** Shared dataset used by every exercise. */
    private static final List<Person> PEOPLE = SampleData.getPeople();

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  JAVA STREAM API — COMPREHENSIVE EXERCISES");
        System.out.println("═══════════════════════════════════════════════════\n");

        // ─── Section 1: Basic Filtering & Mapping ───
        printSection("1 — BASIC FILTERING & MAPPING");
        filterByAge();
        mapToFullNames();
        filterAndMapChained();
        distinctCities();
        findFirstHighEarner();
        anyMatchAllMatchNoneMatch();

        // ─── Section 2: Sorting & Comparators ───
        printSection("2 — SORTING & COMPARATORS");
        sortBySalaryDescending();
        sortByMultipleFields();
        topNBySalary(3);

        // ─── Section 3: Reduction & Aggregation ───
        printSection("3 — REDUCTION & AGGREGATION");
        totalSalaryReduce();
        averageSalaryWithCollector();
        summarizingSalaryStatistics();
        highestPaidPerson();
        concatenateNamesWithJoining();

        // ─── Section 4: FlatMap & Nested Collections ───
        printSection("4 — FLATMAP & NESTED COLLECTIONS");
        allUniqueSkillNames();
        peopleBySkillName("Java");
        flatMapAddressesToCities();

        // ─── Section 5: Collectors.groupingBy ───
        printSection("5 — COLLECTORS.GROUPINGBY");
        groupByGender();
        groupByCompany();
        groupByDepartmentCounting();
        groupByCountryAverageSalary();
        groupByCityAndCollectNames();
        multiLevelGroupByCountryThenDepartment();
        multiLevelGroupByCompanyThenGender();
        groupByAgeRange();
        groupBySkillCategory();

        // ─── Section 6: Collectors.partitioningBy ───
        printSection("6 — COLLECTORS.PARTITIONINGBY");
        partitionBySalaryThreshold(75000);
        partitionByEmploymentStatusWithCount();

        // ─── Section 7: Collectors.toMap ───
        printSection("7 — COLLECTORS.TOMAP");
        emailToPersonMap();
        companyToHighestSalaryMap();
        skillNameToPersonCountMap();

        // ─── Section 8: Custom Collectors ───
        printSection("8 — CUSTOM COLLECTORS & COLLECTOR.OF");
        customMedianSalaryCollector();
        customSalaryRangeCollector();

        // ─── Section 9: Advanced Collector Compositions ───
        printSection("9 — ADVANCED COLLECTOR COMPOSITIONS");
        groupByCompanyWithSalarySummary();
        collectingAndThenExample();
        filteringCollectorExample();
        flatMappingCollectorExample();
        groupByDepartmentThenPartitionBySenior();

        // ─── Section 10: Parallel Streams ───
        printSection("10 — PARALLEL STREAMS");
        parallelSalarySum();
        parallelGrouping();

        // ─── Section 11: teeing & mapMulti ───
        printSection("11 — TEEING & MAPMULTI");
        teeingMinMaxSalary();
        teeingAverageAndCount();
        mapMultiExpandSkills();

        // ─── Section 12: Real-World Pipeline Patterns ───
        printSection("12 — REAL-WORLD PIPELINE PATTERNS");
        employeeDashboardReport();
        skillGapAnalysis();
        salaryBandDistribution();

        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  ALL EXERCISES COMPLETED SUCCESSFULLY");
        System.out.println("═══════════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 1 — BASIC FILTERING & MAPPING
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Filters people who are older than 30.
     *
     * <p><strong>Concepts:</strong> {@code filter()}, {@code Predicate},
     * method reference {@code Person::getAge}.</p>
     *
     * <p><strong>Key point:</strong> {@code filter} is a lazy intermediate
     * operation — it does not execute until a terminal operation triggers
     * the pipeline.</p>
     */
    static void filterByAge() {
        List<Person> olderThan30 = PEOPLE.stream()
                .filter(p -> p.getAge() > 30)
                .toList();

        printExercise("1.1 People older than 30",
                olderThan30.stream().map(Person::getFullName).toList());
    }

    /**
     * Maps every person to their full name string.
     *
     * <p><strong>Concepts:</strong> {@code map()}, method reference as a
     * mapper function, collecting to an unmodifiable list via
     * {@code toList()} (Java 16+).</p>
     */
    static void mapToFullNames() {
        List<String> names = PEOPLE.stream()
                .map(Person::getFullName)
                .toList();

        printExercise("1.2 All full names", names);
    }

    /**
     * Chains {@code filter} then {@code map} to get the emails of
     * all full-time employees.
     *
     * <p><strong>Concepts:</strong> pipeline chaining, navigating into
     * nested objects ({@code Person → Profession → status}).</p>
     */
    static void filterAndMapChained() {
        List<String> fullTimeEmails = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() == EmploymentStatus.FULL_TIME)
                .map(Person::getEmail)
                .toList();

        printExercise("1.3 Emails of full-time employees", fullTimeEmails);
    }

    /**
     * Extracts a distinct, sorted list of all home cities.
     *
     * <p><strong>Concepts:</strong> {@code map()}, {@code distinct()},
     * {@code sorted()} — all intermediate operations.</p>
     */
    static void distinctCities() {
        List<String> cities = PEOPLE.stream()
                .map(Person::getHomeCity)
                .filter(c -> !"Unknown".equals(c))
                .distinct()
                .sorted()
                .toList();

        printExercise("1.4 Distinct home cities (sorted)", cities);
    }

    /**
     * Finds the first person earning more than £100,000 using
     * short-circuit terminal operation {@code findFirst()}.
     *
     * <p><strong>Concepts:</strong> {@code Optional}, {@code findFirst()},
     * short-circuiting — the stream stops as soon as one match is found.</p>
     */
    static void findFirstHighEarner() {
        Optional<Person> highEarner = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 100_000)
                .findFirst();

        printExercise("1.5 First person earning > £100k",
                highEarner.map(Person::getFullName).orElse("Nobody found"));
    }

    /**
     * Demonstrates the three matching terminal operations:
     * {@code anyMatch}, {@code allMatch}, and {@code noneMatch}.
     *
     * <p>All three are short-circuit operations that return a boolean.</p>
     */
    static void anyMatchAllMatchNoneMatch() {
        boolean anyExpert = PEOPLE.stream()
                .flatMap(p -> p.getSkills().stream())
                .anyMatch(s -> s.level() == ProficiencyLevel.EXPERT);

        boolean allEmployed = PEOPLE.stream()
                .allMatch(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED);

        boolean noneUnder18 = PEOPLE.stream()
                .noneMatch(p -> p.getAge() < 18);

        printExercise("1.6 anyMatch(EXPERT skill)", anyExpert);
        printExercise("    allMatch(employed)", allEmployed);
        printExercise("    noneMatch(under 18)", noneUnder18);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 2 — SORTING & COMPARATORS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Sorts people by salary in descending order.
     *
     * <p><strong>Concepts:</strong> {@code sorted(Comparator)},
     * {@code Comparator.comparingDouble}, {@code reversed()}.
     * The comparator navigates into the nested {@link Profession} object.</p>
     */
    static void sortBySalaryDescending() {
        List<String> sorted = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .sorted(Comparator.comparingDouble(
                        (Person p) -> p.getProfession().salary()).reversed())
                .map(p -> "%s — £%,.0f".formatted(
                        p.getFullName(), p.getProfession().salary()))
                .toList();

        printExercise("2.1 Sorted by salary (desc)", sorted);
    }

    /**
     * Sorts by company name (ascending), then by salary (descending)
     * within each company.
     *
     * <p><strong>Concepts:</strong> {@code Comparator.comparing().thenComparing()},
     * composite comparators, null-safe comparisons.</p>
     */
    static void sortByMultipleFields() {
        Comparator<Person> byCompanyThenSalary = Comparator
                .comparing((Person p) -> p.getProfession().company())
                .thenComparing(
                        (Person p) -> p.getProfession().salary(),
                        Comparator.reverseOrder());

        List<String> sorted = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .sorted(byCompanyThenSalary)
                .map(p -> "[%s] %s — £%,.0f".formatted(
                        p.getProfession().company(),
                        p.getFullName(),
                        p.getProfession().salary()))
                .toList();

        printExercise("2.2 Sorted by company, then salary desc", sorted);
    }

    /**
     * Retrieves the top N earners using {@code limit()}.
     *
     * <p><strong>Concepts:</strong> {@code sorted()}, {@code limit(n)} —
     * a short-circuit stateful intermediate operation.</p>
     *
     * @param n the number of top earners to retrieve
     */
    static void topNBySalary(int n) {
        List<String> topN = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .sorted(Comparator.comparingDouble(
                        (Person p) -> p.getProfession().salary()).reversed())
                .limit(n)
                .map(p -> "%s — £%,.0f".formatted(
                        p.getFullName(), p.getProfession().salary()))
                .toList();

        printExercise("2.3 Top %d earners".formatted(n), topN);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 3 — REDUCTION & AGGREGATION
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Calculates total salary expenditure using the three-argument
     * {@code reduce(identity, accumulator, combiner)}.
     *
     * <p><strong>Concepts:</strong> {@code reduce} with identity value,
     * accumulator, and combiner (required for parallel safety).
     * The combiner is used when the stream is processed in parallel
     * to merge partial results.</p>
     */
    static void totalSalaryReduce() {
        double totalSalary = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .reduce(
                        0.0,                                     // identity
                        (sum, p) -> sum + p.getProfession().salary(), // accumulator
                        Double::sum                              // combiner
                );

        printExercise("3.1 Total salary (reduce)", "£%,.2f".formatted(totalSalary));
    }

    /**
     * Computes average salary using {@code Collectors.averagingDouble}.
     *
     * <p><strong>Concepts:</strong> specialised numeric collectors,
     * navigating into nested objects for the averaging function.</p>
     */
    static void averageSalaryWithCollector() {
        double avgSalary = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .collect(Collectors.averagingDouble(
                        p -> p.getProfession().salary()));

        printExercise("3.2 Average salary (Collectors.averagingDouble)",
                "£%,.2f".formatted(avgSalary));
    }

    /**
     * Produces a full {@link DoubleSummaryStatistics} for salaries,
     * giving count, sum, min, max, and average in one pass.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.summarizingDouble},
     * summary statistics — an efficient single-pass aggregation.</p>
     */
    static void summarizingSalaryStatistics() {
        DoubleSummaryStatistics stats = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .collect(Collectors.summarizingDouble(
                        p -> p.getProfession().salary()));

        printExercise("3.3 Salary statistics",
                "count=%d, min=£%,.0f, max=£%,.0f, avg=£%,.0f, sum=£%,.0f"
                        .formatted(stats.getCount(), stats.getMin(),
                                stats.getMax(), stats.getAverage(),
                                stats.getSum()));
    }

    /**
     * Finds the person with the highest salary using
     * {@code Collectors.maxBy(Comparator)}.
     *
     * <p><strong>Concepts:</strong> {@code maxBy}, wrapping the result
     * in {@link Optional} for null-safety, comparator extraction.</p>
     */
    static void highestPaidPerson() {
        Optional<Person> highest = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .collect(Collectors.maxBy(
                        Comparator.comparingDouble(
                                p -> p.getProfession().salary())));

        printExercise("3.4 Highest paid person",
                highest.map(p -> "%s — £%,.0f".formatted(
                        p.getFullName(), p.getProfession().salary())).orElse("N/A"));
    }

    /**
     * Concatenates all names into a comma-separated string
     * using {@code Collectors.joining}.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.joining(delimiter, prefix, suffix)},
     * a collector purpose-built for string concatenation.</p>
     */
    static void concatenateNamesWithJoining() {
        String joined = PEOPLE.stream()
                .map(Person::getFullName)
                .collect(Collectors.joining(", ", "[", "]"));

        printExercise("3.5 All names joined", joined);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 4 — FLATMAP & NESTED COLLECTIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Extracts every unique skill name across all people
     * using {@code flatMap} to "flatten" nested skill lists.
     *
     * <p><strong>Concepts:</strong> {@code flatMap()} transforms each
     * element into a stream, then merges (flattens) all those streams
     * into a single stream. Essential for one-to-many relationships.</p>
     */
    static void allUniqueSkillNames() {
        List<String> uniqueSkills = PEOPLE.stream()
                .flatMap(p -> p.getSkills().stream())
                .map(Skill::name)
                .distinct()
                .sorted()
                .toList();

        printExercise("4.1 All unique skills", uniqueSkills);
    }

    /**
     * Finds all people who possess a given skill, regardless of
     * proficiency level.
     *
     * <p><strong>Concepts:</strong> filtering on a nested collection
     * using {@code anyMatch} inside the predicate — no flatMap needed
     * because we want to keep the Person as the stream element.</p>
     *
     * @param skillName the skill to search for
     */
    static void peopleBySkillName(String skillName) {
        List<String> holders = PEOPLE.stream()
                .filter(p -> p.getSkills().stream()
                        .anyMatch(s -> s.name().equalsIgnoreCase(skillName)))
                .map(p -> "%s (%s)".formatted(
                        p.getFullName(),
                        p.getSkills().stream()
                                .filter(s -> s.name().equalsIgnoreCase(skillName))
                                .map(s -> s.level().name())
                                .findFirst().orElse("?")))
                .toList();

        printExercise("4.2 People with '%s' skill".formatted(skillName), holders);
    }

    /**
     * FlatMaps all addresses to get every city a person is associated
     * with (home, work, secondary), then counts occurrences.
     *
     * <p><strong>Concepts:</strong> {@code flatMap} over addresses,
     * grouping by city, downstream {@code counting()}.</p>
     */
    static void flatMapAddressesToCities() {
        Map<String, Long> cityCounts = PEOPLE.stream()
                .flatMap(p -> p.getAddresses().stream())
                .collect(Collectors.groupingBy(
                        Address::city,
                        Collectors.counting()));

        printExercise("4.3 Address city frequency", cityCounts);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 5 — COLLECTORS.GROUPINGBY
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Groups people by gender using {@code Collectors.groupingBy}.
     *
     * <p><strong>Concepts:</strong> the simplest form of
     * {@code groupingBy(classifier)} which collects values into
     * a {@code Map<K, List<T>>}.</p>
     */
    static void groupByGender() {
        Map<Gender, List<Person>> byGender = PEOPLE.stream()
                .collect(Collectors.groupingBy(Person::getGender));

        printExercise("5.1 Group by gender",
                formatGroupMap(byGender, Person::getFullName));
    }

    /**
     * Groups people by their company name.
     *
     * <p><strong>Concepts:</strong> groupingBy with a classifier that
     * navigates into a nested object ({@code Person → Profession → company}).</p>
     */
    static void groupByCompany() {
        Map<String, List<Person>> byCompany = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().company()));

        printExercise("5.2 Group by company",
                formatGroupMap(byCompany, Person::getFullName));
    }

    /**
     * Groups by department and counts the number of people in each
     * using the downstream collector {@code Collectors.counting()}.
     *
     * <p><strong>Concepts:</strong> {@code groupingBy(classifier, downstream)},
     * downstream collectors — the second argument to groupingBy
     * specifies how to reduce the grouped elements.</p>
     */
    static void groupByDepartmentCounting() {
        Map<String, Long> deptCounts = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().department(),
                        Collectors.counting()));

        printExercise("5.3 Count by department", deptCounts);
    }

    /**
     * Groups by home country and calculates the average salary
     * within each country.
     *
     * <p><strong>Concepts:</strong> {@code groupingBy} with
     * {@code Collectors.averagingDouble} as the downstream —
     * combining geographic grouping with numeric aggregation.</p>
     */
    static void groupByCountryAverageSalary() {
        Map<String, Double> avgByCountry = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .collect(Collectors.groupingBy(
                        Person::getHomeCountry,
                        Collectors.averagingDouble(
                                p -> p.getProfession().salary())));

        // Format nicely
        Map<String, String> formatted = new LinkedHashMap<>();
        avgByCountry.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> formatted.put(e.getKey(),
                        "£%,.0f".formatted(e.getValue())));

        printExercise("5.4 Average salary by country", formatted);
    }

    /**
     * Groups by home city and collects just the full names of people
     * using {@code Collectors.mapping} as a downstream collector.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.mapping(mapper, downstream)}
     * — transforms elements before they reach the final downstream
     * collector. Here it maps Person → String before collecting to List.</p>
     */
    static void groupByCityAndCollectNames() {
        Map<String, List<String>> namesByCity = PEOPLE.stream()
                .collect(Collectors.groupingBy(
                        Person::getHomeCity,
                        Collectors.mapping(
                                Person::getFullName,
                                Collectors.toList())));

        printExercise("5.5 Names grouped by city", namesByCity);
    }

    /**
     * Two-level grouping: first by home country, then by department.
     *
     * <p><strong>Concepts:</strong> nested {@code groupingBy} — the
     * downstream of the outer groupingBy is itself another groupingBy.
     * This produces a {@code Map<String, Map<String, List<Person>>>}.</p>
     *
     * <p>Multi-level grouping is one of the most powerful features of
     * the Collectors API, enabling hierarchical data structures in a
     * single pipeline pass.</p>
     */
    static void multiLevelGroupByCountryThenDepartment() {
        Map<String, Map<String, List<String>>> result = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .collect(Collectors.groupingBy(
                        Person::getHomeCountry,                          // level 1
                        Collectors.groupingBy(                           // level 2
                                p -> p.getProfession().department(),
                                Collectors.mapping(
                                        Person::getFullName,
                                        Collectors.toList()))));

        printExercise("5.6 Group by country → department", result);
    }

    /**
     * Two-level grouping: company → gender, with count as the
     * innermost aggregation.
     *
     * <p><strong>Concepts:</strong> nesting groupingBy three levels deep
     * (classifier → classifier → counting), demonstrating that
     * downstream collectors can be arbitrarily composed.</p>
     */
    static void multiLevelGroupByCompanyThenGender() {
        Map<String, Map<Gender, Long>> result = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().company(),
                        Collectors.groupingBy(
                                Person::getGender,
                                Collectors.counting())));

        printExercise("5.7 Group by company → gender (count)", result);
    }

    /**
     * Groups people into custom age ranges (20s, 30s, 40s, etc.)
     * by applying a computed classifier function.
     *
     * <p><strong>Concepts:</strong> using a lambda as classifier that
     * computes a derived grouping key (age decade), proving that
     * the classifier need not be a simple getter.</p>
     */
    static void groupByAgeRange() {
        Map<String, List<String>> byAgeRange = PEOPLE.stream()
                .collect(Collectors.groupingBy(
                        p -> {
                            int decade = (p.getAge() / 10) * 10;
                            return "%d–%d".formatted(decade, decade + 9);
                        },
                        TreeMap::new,  // sorted map factory
                        Collectors.mapping(Person::getFullName,
                                Collectors.toList())));

        printExercise("5.8 Group by age range", byAgeRange);
    }

    /**
     * Groups all skills across all people by skill category, collecting
     * distinct skill names per category.
     *
     * <p><strong>Concepts:</strong> {@code flatMap} before {@code groupingBy},
     * {@code Collectors.mapping} with {@code Collectors.toSet()} to
     * ensure uniqueness within each group.</p>
     */
    static void groupBySkillCategory() {
        Map<String, Set<String>> skillsByCategory = PEOPLE.stream()
                .flatMap(p -> p.getSkills().stream())
                .collect(Collectors.groupingBy(
                        Skill::category,
                        Collectors.mapping(
                                Skill::name,
                                Collectors.toSet())));

        printExercise("5.9 Unique skills by category", skillsByCategory);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 6 — COLLECTORS.PARTITIONINGBY
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Partitions people into two groups: those earning above and
     * those earning below a given salary threshold.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.partitioningBy(predicate)}
     * — a special case of groupingBy that always produces exactly two
     * groups keyed by {@code true} and {@code false}.</p>
     *
     * @param threshold the salary dividing line
     */
    static void partitionBySalaryThreshold(double threshold) {
        Map<Boolean, List<String>> partitioned = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .collect(Collectors.partitioningBy(
                        p -> p.getProfession().salary() >= threshold,
                        Collectors.mapping(
                                p -> "%s (£%,.0f)".formatted(
                                        p.getFullName(),
                                        p.getProfession().salary()),
                                Collectors.toList())));

        printExercise("6.1 Partition by salary ≥ £%,.0f".formatted(threshold),
                "Above: " + partitioned.get(true));
        printExercise("    ",
                "Below: " + partitioned.get(false));
    }

    /**
     * Partitions people by whether they are currently employed
     * (any status except UNEMPLOYED), with counting in each partition.
     *
     * <p><strong>Concepts:</strong> {@code partitioningBy} with
     * {@code counting()} downstream — combining partition with aggregation.</p>
     */
    static void partitionByEmploymentStatusWithCount() {
        Map<Boolean, Long> partition = PEOPLE.stream()
                .collect(Collectors.partitioningBy(
                        p -> p.getProfession() != null
                                && p.getProfession().status() != EmploymentStatus.UNEMPLOYED,
                        Collectors.counting()));

        printExercise("6.2 Employed vs unemployed count",
                "Employed: %d, Unemployed: %d"
                        .formatted(partition.get(true), partition.get(false)));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 7 — COLLECTORS.TOMAP
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Creates a lookup map from email address to Person.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.toMap(keyMapper, valueMapper)}
     * — produces a {@code Map<K,V>} from stream elements. Throws
     * {@link IllegalStateException} on duplicate keys unless a merge
     * function is provided.</p>
     */
    static void emailToPersonMap() {
        Map<String, String> emailMap = PEOPLE.stream()
                .filter(p -> p.getEmail() != null)
                .collect(Collectors.toMap(
                        Person::getEmail,
                        Person::getFullName));

        printExercise("7.1 Email → Name lookup (first 5)",
                emailMap.entrySet().stream().limit(5).toList());
    }

    /**
     * Creates a map of company → highest salary, using a merge function
     * to resolve duplicate keys by keeping the higher salary.
     *
     * <p><strong>Concepts:</strong> {@code toMap(keyMapper, valueMapper, mergeFunction)}
     * — the merge function {@code BinaryOperator.maxBy()} resolves
     * collisions when multiple people share the same company.</p>
     */
    static void companyToHighestSalaryMap() {
        Map<String, Double> companyMaxSalary = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .collect(Collectors.toMap(
                        p -> p.getProfession().company(),
                        p -> p.getProfession().salary(),
                        BinaryOperator.maxBy(Double::compareTo)));

        // Format for display
        Map<String, String> formatted = new LinkedHashMap<>();
        companyMaxSalary.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> formatted.put(e.getKey(),
                        "£%,.0f".formatted(e.getValue())));

        printExercise("7.2 Company → highest salary", formatted);
    }

    /**
     * Creates a map of skill name → count of people who have that skill.
     *
     * <p><strong>Concepts:</strong> {@code flatMap} to reach skills,
     * then {@code toMap} with a merge function that sums counts
     * when the same skill appears for different people.</p>
     */
    static void skillNameToPersonCountMap() {
        Map<String, Long> skillCounts = PEOPLE.stream()
                .flatMap(p -> p.getSkills().stream())
                .collect(Collectors.toMap(
                        Skill::name,
                        s -> 1L,
                        Long::sum,
                        TreeMap::new));  // sorted by skill name

        printExercise("7.3 Skill → number of people", skillCounts);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 8 — CUSTOM COLLECTORS & COLLECTOR.OF
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Computes the median salary using a custom collector built with
     * {@code Collector.of(supplier, accumulator, combiner, finisher)}.
     *
     * <p><strong>Concepts:</strong> {@code Collector.of} — the four-function
     * factory for building collectors from scratch:</p>
     * <ol>
     *   <li><strong>Supplier</strong> – creates a new mutable container</li>
     *   <li><strong>Accumulator</strong> – folds an element into the container</li>
     *   <li><strong>Combiner</strong> – merges two containers (for parallel)</li>
     *   <li><strong>Finisher</strong> – transforms the container into the result</li>
     * </ol>
     */
    static void customMedianSalaryCollector() {
        Collector<Double, ?, OptionalDouble> medianCollector = Collector.of(
                // supplier: mutable list to accumulate all salaries
                ArrayList<Double>::new,
                // accumulator: add each salary
                ArrayList::add,
                // combiner: merge two lists (parallel support)
                (left, right) -> { left.addAll(right); return left; },
                // finisher: sort, then pick the middle element
                list -> {
                    if (list.isEmpty()) return OptionalDouble.empty();
                    Collections.sort(list);
                    int mid = list.size() / 2;
                    double median = list.size() % 2 == 0
                            ? (list.get(mid - 1) + list.get(mid)) / 2.0
                            : list.get(mid);
                    return OptionalDouble.of(median);
                }
        );

        OptionalDouble median = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .map(p -> p.getProfession().salary())
                .collect(medianCollector);

        printExercise("8.1 Median salary (custom collector)",
                median.isPresent() ? "£%,.0f".formatted(median.getAsDouble()) : "N/A");
    }

    /**
     * Computes the salary range (max − min) using a custom collector
     * that tracks running min and max in a single pass.
     *
     * <p><strong>Concepts:</strong> custom accumulation containers,
     * mutable reduction, finisher transformation.</p>
     */
    static void customSalaryRangeCollector() {
        Collector<Double, double[], Double> rangeCollector = Collector.of(
                () -> new double[]{Double.MAX_VALUE, Double.MIN_VALUE}, // [min, max]
                (acc, val) -> {
                    acc[0] = Math.min(acc[0], val);
                    acc[1] = Math.max(acc[1], val);
                },
                (left, right) -> new double[]{
                        Math.min(left[0], right[0]),
                        Math.max(left[1], right[1])
                },
                acc -> acc[1] - acc[0]
        );

        double range = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .map(p -> p.getProfession().salary())
                .collect(rangeCollector);

        printExercise("8.2 Salary range (custom collector)",
                "£%,.0f".formatted(range));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 9 — ADVANCED COLLECTOR COMPOSITIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Groups by company and produces a full {@link DoubleSummaryStatistics}
     * per company, giving min/max/avg/sum/count for each.
     *
     * <p><strong>Concepts:</strong> {@code groupingBy} with
     * {@code summarizingDouble} downstream — combining grouping
     * with comprehensive numeric aggregation.</p>
     */
    static void groupByCompanyWithSalarySummary() {
        Map<String, DoubleSummaryStatistics> summary = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().company(),
                        Collectors.summarizingDouble(
                                p -> p.getProfession().salary())));

        Map<String, String> formatted = new LinkedHashMap<>();
        summary.forEach((company, stats) ->
                formatted.put(company,
                        "count=%d, avg=£%,.0f, min=£%,.0f, max=£%,.0f"
                                .formatted(stats.getCount(), stats.getAverage(),
                                        stats.getMin(), stats.getMax())));

        printExercise("9.1 Company salary summary", formatted);
    }

    /**
     * Uses {@code Collectors.collectingAndThen} to post-process a
     * collected result — here, making the resulting list unmodifiable.
     *
     * <p><strong>Concepts:</strong> {@code collectingAndThen(downstream, finisher)}
     * — applies a finishing transformation after the downstream
     * collector completes. Common uses: wrapping in unmodifiable
     * collections, extracting a single value, applying formatting.</p>
     */
    static void collectingAndThenExample() {
        // Collect names, then make the list unmodifiable and uppercase
        List<String> unmodifiableNames = PEOPLE.stream()
                .map(Person::getFullName)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.stream()
                                .map(String::toUpperCase)
                                .collect(Collectors.toUnmodifiableList())));

        printExercise("9.2 collectingAndThen (uppercase, unmodifiable)",
                unmodifiableNames.stream().limit(5).toList());

        // Prove it's unmodifiable
        try {
            unmodifiableNames.add("SHOULD FAIL");
        } catch (UnsupportedOperationException e) {
            printExercise("    Mutation attempt", "Correctly threw UnsupportedOperationException");
        }
    }

    /**
     * Uses {@code Collectors.filtering} (Java 9+) as a downstream
     * collector inside groupingBy.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.filtering(predicate, downstream)}
     * — unlike {@code stream().filter()}, this preserves empty groups
     * in the output map. If no elements pass the filter for a group,
     * that group still appears with an empty collection.</p>
     */
    static void filteringCollectorExample() {
        // Group by company, but only keep people with salary > 80k
        Map<String, List<String>> highEarnersByCompany = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().company(),
                        Collectors.filtering(
                                p -> p.getProfession().salary() > 80_000,
                                Collectors.mapping(
                                        Person::getFullName,
                                        Collectors.toList()))));

        printExercise("9.3 Collectors.filtering (salary > 80k by company)",
                highEarnersByCompany);
    }

    /**
     * Uses {@code Collectors.flatMapping} (Java 9+) as a downstream
     * collector to flatten nested collections during grouping.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.flatMapping(mapper, downstream)}
     * — flatMaps within the downstream of a groupingBy, enabling
     * you to group by one dimension while flattening a nested
     * collection of another dimension.</p>
     */
    static void flatMappingCollectorExample() {
        // Group by company → all skill names (flattened and distinct)
        Map<String, Set<String>> skillsByCompany = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().company(),
                        Collectors.flatMapping(
                                p -> p.getSkills().stream().map(Skill::name),
                                Collectors.toSet())));

        printExercise("9.4 Collectors.flatMapping (all skills by company)",
                skillsByCompany);
    }

    /**
     * Three-level composition: group by department → partition by
     * seniority (≥10 years experience) → collect names.
     *
     * <p><strong>Concepts:</strong> composing {@code groupingBy},
     * {@code partitioningBy}, and {@code mapping} three levels deep
     * to produce {@code Map<String, Map<Boolean, List<String>>>}.</p>
     */
    static void groupByDepartmentThenPartitionBySenior() {
        Map<String, Map<Boolean, List<String>>> result = PEOPLE.stream()
                .filter(p -> p.getProfession() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().department(),
                        Collectors.partitioningBy(
                                p -> p.getProfession().yearsExperience() >= 10,
                                Collectors.mapping(
                                        Person::getFullName,
                                        Collectors.toList()))));

        printExercise("9.5 Dept → Senior (≥10yrs) / Junior partition", result);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 10 — PARALLEL STREAMS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Computes total salary using a parallel stream.
     *
     * <p><strong>Concepts:</strong> {@code parallelStream()},
     * {@code mapToDouble().sum()} — the ForkJoinPool splits the
     * workload across multiple threads. For small datasets this is
     * overhead-heavy, but demonstrates the API. In production, only
     * use parallel streams for large datasets or CPU-intensive
     * per-element processing.</p>
     */
    static void parallelSalarySum() {
        double totalParallel = PEOPLE.parallelStream()
                .filter(p -> p.getProfession() != null)
                .mapToDouble(p -> p.getProfession().salary())
                .sum();

        printExercise("10.1 Total salary (parallel stream)",
                "£%,.2f".formatted(totalParallel));
    }

    /**
     * Performs grouping on a parallel stream, using a concurrent
     * collector for thread safety.
     *
     * <p><strong>Concepts:</strong> {@code Collectors.groupingByConcurrent}
     * — returns a {@link ConcurrentMap} and is safe for parallel
     * streams. Unlike {@code groupingBy}, it does not guarantee
     * encounter order, but avoids the merge overhead of standard
     * groupingBy in parallel.</p>
     */
    static void parallelGrouping() {
        ConcurrentMap<String, Long> concurrentDeptCounts = PEOPLE.parallelStream()
                .filter(p -> p.getProfession() != null)
                .collect(Collectors.groupingByConcurrent(
                        p -> p.getProfession().department(),
                        Collectors.counting()));

        printExercise("10.2 Parallel groupingByConcurrent (dept counts)",
                concurrentDeptCounts);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 11 — TEEING & MAPMULTI
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Uses {@code Collectors.teeing} (Java 12+) to compute the
     * minimum and maximum salary in a single pass.
     *
     * <p><strong>Concepts:</strong> {@code teeing(downstream1, downstream2, merger)}
     * — feeds each element to two independent downstream collectors
     * simultaneously, then merges the two results with a BiFunction.
     * Eliminates the need for two separate stream traversals.</p>
     */
    static void teeingMinMaxSalary() {
        record MinMax(double min, double max) {}

        MinMax minMax = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .map(p -> p.getProfession().salary())
                .collect(Collectors.teeing(
                        Collectors.minBy(Double::compareTo),
                        Collectors.maxBy(Double::compareTo),
                        (min, max) -> new MinMax(
                                min.orElse(0.0), max.orElse(0.0))));

        printExercise("11.1 Teeing: min & max salary",
                "min=£%,.0f, max=£%,.0f".formatted(minMax.min, minMax.max));
    }

    /**
     * Uses teeing to compute both average salary and head count
     * in a single stream pass, then formats a combined report.
     *
     * <p><strong>Concepts:</strong> teeing with heterogeneous
     * downstream collectors — one computes a double average,
     * the other counts, and the merger combines them.</p>
     */
    static void teeingAverageAndCount() {
        record AvgAndCount(double average, long count) {}

        AvgAndCount result = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .collect(Collectors.teeing(
                        Collectors.averagingDouble(
                                p -> p.getProfession().salary()),
                        Collectors.counting(),
                        AvgAndCount::new));

        printExercise("11.2 Teeing: avg salary & count",
                "%d people, avg=£%,.0f".formatted(result.count, result.average));
    }

    /**
     * Uses {@code mapMulti} (Java 16+) to expand each person into
     * multiple (name, skill) pairs.
     *
     * <p><strong>Concepts:</strong> {@code mapMulti(BiConsumer)} is an
     * imperative alternative to {@code flatMap}. Instead of returning
     * a stream, you push elements into a consumer. Beneficial when
     * you want conditional expansion or need to avoid creating many
     * short-lived intermediate streams.</p>
     */
    static void mapMultiExpandSkills() {
        record PersonSkill(String personName, String skillName, ProficiencyLevel level) {}

        List<String> expanded = PEOPLE.stream()
                .<PersonSkill>mapMulti((person, consumer) -> {
                    for (Skill skill : person.getSkills()) {
                        if (skill.level().getWeight() >= ProficiencyLevel.ADVANCED.getWeight()) {
                            consumer.accept(new PersonSkill(
                                    person.getFullName(),
                                    skill.name(),
                                    skill.level()));
                        }
                    }
                })
                .map(ps -> "%s → %s (%s)".formatted(
                        ps.personName, ps.skillName, ps.level))
                .toList();

        printExercise("11.3 mapMulti: ADVANCED+ skills per person", expanded);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 12 — REAL-WORLD PIPELINE PATTERNS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Produces a mini-dashboard report: for each company, shows
     * head count, average salary, top earner, and skills coverage.
     *
     * <p><strong>Concepts:</strong> composing multiple collectors,
     * teeing, and mapping in a real-world analytics pipeline.
     * Demonstrates how to build complex business reports with
     * a single stream traversal.</p>
     */
    static void employeeDashboardReport() {
        record CompanyReport(String company, long headcount,
                             double avgSalary, String topEarner,
                             Set<String> allSkills) {}

        Map<String, List<Person>> byCompany = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().status() != EmploymentStatus.UNEMPLOYED)
                .collect(Collectors.groupingBy(
                        p -> p.getProfession().company()));

        List<CompanyReport> reports = byCompany.entrySet().stream()
                .map(entry -> {
                    String company = entry.getKey();
                    List<Person> employees = entry.getValue();

                    long headcount = employees.size();

                    double avgSalary = employees.stream()
                            .mapToDouble(p -> p.getProfession().salary())
                            .average().orElse(0);

                    String topEarner = employees.stream()
                            .max(Comparator.comparingDouble(
                                    p -> p.getProfession().salary()))
                            .map(p -> "%s (£%,.0f)".formatted(
                                    p.getFullName(),
                                    p.getProfession().salary()))
                            .orElse("N/A");

                    Set<String> allSkills = employees.stream()
                            .flatMap(p -> p.getSkills().stream())
                            .map(Skill::name)
                            .collect(Collectors.toSet());

                    return new CompanyReport(company, headcount,
                            avgSalary, topEarner, allSkills);
                })
                .sorted(Comparator.comparingLong(
                        CompanyReport::headcount).reversed())
                .toList();

        System.out.println("  ► 12.1 Employee Dashboard Report:");
        reports.forEach(r -> {
            System.out.println("    ┌─ " + r.company);
            System.out.println("    │  Headcount  : " + r.headcount);
            System.out.println("    │  Avg Salary : £%,.0f".formatted(r.avgSalary));
            System.out.println("    │  Top Earner : " + r.topEarner);
            System.out.println("    │  Skills     : " + r.allSkills);
            System.out.println("    └────────────────────────────");
        });
    }

    /**
     * Identifies skills that are in high demand (held by many people)
     * vs. rare skills (held by only one person), along with average
     * proficiency level for each skill.
     *
     * <p><strong>Concepts:</strong> combining flatMap, groupingBy,
     * teeing, and collectingAndThen in a single analytics pipeline
     * that would typically require multiple database queries.</p>
     */
    static void skillGapAnalysis() {
        record SkillStats(String skill, long holders,
                          double avgProficiency) {}

        List<SkillStats> stats = PEOPLE.stream()
                .flatMap(p -> p.getSkills().stream())
                .collect(Collectors.groupingBy(
                        Skill::name,
                        Collectors.teeing(
                                Collectors.counting(),
                                Collectors.averagingInt(
                                        s -> s.level().getWeight()),
                                (count, avgWeight) -> new SkillStats(
                                        "", count, avgWeight))))
                .entrySet().stream()
                .map(e -> new SkillStats(e.getKey(),
                        e.getValue().holders(),
                        e.getValue().avgProficiency()))
                .sorted(Comparator.comparingLong(
                        SkillStats::holders).reversed())
                .toList();

        System.out.println("  ► 12.2 Skill Gap Analysis:");
        System.out.println("    %-22s  Holders  Avg Proficiency".formatted("Skill"));
        System.out.println("    " + "─".repeat(50));
        stats.forEach(s ->
                System.out.println("    %-22s  %4d     %.1f / 4.0"
                        .formatted(s.skill, s.holders, s.avgProficiency)));
    }

    /**
     * Distributes people into salary bands and produces a frequency
     * report, similar to a histogram.
     *
     * <p><strong>Concepts:</strong> computed grouping keys, formatting
     * output as a text histogram, combining groupingBy with counting
     * and collectingAndThen for sort-after-group patterns.</p>
     */
    static void salaryBandDistribution() {
        Map<String, Long> bands = PEOPLE.stream()
                .filter(p -> p.getProfession() != null
                        && p.getProfession().salary() > 0)
                .collect(Collectors.groupingBy(
                        p -> {
                            double salary = p.getProfession().salary();
                            if (salary < 40_000) return "  < £40k";
                            if (salary < 60_000) return " £40k–£60k";
                            if (salary < 80_000) return " £60k–£80k";
                            if (salary < 100_000) return " £80k–£100k";
                            return "£100k+";
                        },
                        TreeMap::new,
                        Collectors.counting()));

        System.out.println("  ► 12.3 Salary Band Distribution:");
        bands.forEach((band, count) ->
                System.out.println("    %-12s │%s %d"
                        .formatted(band, "█".repeat(count.intValue() * 3),
                                count)));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  HELPER METHODS
    // ═══════════════════════════════════════════════════════════════════

    private static void printSection(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  SECTION " + title);
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private static void printExercise(String label, Object result) {
        System.out.println("  ► " + label + ": " + result);
    }

    /**
     * Utility to format a {@code Map<K, List<Person>>} for display
     * by transforming each person in the value list with a mapper.
     */
    private static <K> Map<K, List<String>> formatGroupMap(
            Map<K, List<Person>> map,
            Function<Person, String> mapper) {
        Map<K, List<String>> formatted = new LinkedHashMap<>();
        map.forEach((key, people) ->
                formatted.put(key, people.stream()
                        .map(mapper).toList()));
        return formatted;
    }
}
