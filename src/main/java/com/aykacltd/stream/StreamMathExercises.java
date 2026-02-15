package com.aykacltd.stream;

import com.aykacltd.stream.Person.Gender;
import com.aykacltd.stream.Profession.EmploymentStatus;
import com.aykacltd.stream.Skill.ProficiencyLevel;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * <h1>Java Stream API — Statistical Analysis &amp; Math Operations</h1>
 *
 * <p>This class contains <strong>50+</strong> exercises focused exclusively
 * on numerical computation, statistical analysis, and mathematical
 * operations using the Stream API. Every example uses the same
 * {@link Person}, {@link Profession}, {@link Skill}, and {@link Address}
 * domain model.</p>
 *
 * <h2>Table of Contents</h2>
 * <ol>
 *   <li>Section 1  – Primitive Streams: IntStream, LongStream, DoubleStream</li>
 *   <li>Section 2  – Basic Aggregations: sum, average, min, max, count</li>
 *   <li>Section 3  – SummaryStatistics: single-pass multi-stat collection</li>
 *   <li>Section 4  – Reduce: custom accumulations &amp; multiplications</li>
 *   <li>Section 5  – Grouped Aggregations: sum/avg/min/max per group</li>
 *   <li>Section 6  – Multi-Level Grouped Statistics</li>
 *   <li>Section 7  – Weighted Averages &amp; Ratios</li>
 *   <li>Section 8  – Percentiles, Median &amp; Distribution Analysis</li>
 *   <li>Section 9  – Variance, Standard Deviation &amp; Z-Scores</li>
 *   <li>Section 10 – Running Totals, Cumulative Sums &amp; Moving Averages</li>
 *   <li>Section 11 – Ranking, Top-N &amp; Percentile Ranking</li>
 *   <li>Section 12 – Correlation &amp; Comparative Analysis</li>
 *   <li>Section 13 – Histograms &amp; Frequency Distributions</li>
 *   <li>Section 14 – Teeing for Dual-Statistic Computations</li>
 *   <li>Section 15 – Real-World Salary Analytics Dashboard</li>
 * </ol>
 *
 * @author Stream API Statistical Exercises
 * @see com.aykacltd.stream.Person
 * @see com.aykacltd.stream.SampleData
 */
public class StreamMathExercises {

    /**
     * Shared dataset used by every exercise.
     */
    private static final List<Person> PEOPLE = SampleData.getPeople();

    /**
     * Convenience: all employed people with a positive salary.
     * Many exercises filter on this so we pre-compute it once.
     */
    private static final List<Person> EARNERS = PEOPLE.stream()
        .filter(p -> p.getProfession() != null && p.getProfession().salary() > 0)
        .toList();

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  JAVA STREAM API — STATISTICAL ANALYSIS & MATH OPS");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        // Section 1
        printSection("1 — PRIMITIVE STREAMS");
        intStreamFromRange();
        doubleStreamFromMapToDouble();
        longStreamFromMapToLong();
        primitiveStreamBoxingUnboxing();

        // Section 2
        printSection("2 — BASIC AGGREGATIONS");
        sumWithMapToDouble();
        sumWithReduce();
        sumWithCollector();
        averageWithPrimitiveStream();
        averageWithCollector();
        minSalary();
        maxSalary();
        minAndMaxExperience();
        countWithVariousMethods();

        // Section 3
        printSection("3 — SUMMARY STATISTICS");
        salarySummaryStatistics();
        experienceSummaryStatistics();
        ageSummaryStatistics();
        skillCountSummaryStatistics();
        compareSummaryStatisticsPerGender();

        // Section 4
        printSection("4 — REDUCE: CUSTOM ACCUMULATIONS");
        sumSalariesWithThreeArgReduce();
        productOfExperienceYears();
        findHighestSalaryWithReduce();
        concatenateSalariesAsString();
        reduceToCustomAccumulator();

        // Section 5
        printSection("5 — GROUPED AGGREGATIONS");
        sumSalaryByCompany();
        averageSalaryByDepartment();
        maxSalaryByCountry();
        minExperienceByCompany();
        countByGender();
        sumSalaryByEmploymentStatus();
        averageAgeByCompany();
        totalSkillCountByDepartment();

        // Section 6
        printSection("6 — MULTI-LEVEL GROUPED STATISTICS");
        avgSalaryByCompanyAndGender();
        sumSalaryByCountryAndDepartment();
        statsPerCompanyPerDepartment();

        // Section 7
        printSection("7 — WEIGHTED AVERAGES & RATIOS");
        weightedAverageProficiency();
        salaryToExperienceRatio();
        averageSalaryPerSkillHeld();
        experienceToAgeRatio();

        // Section 8
        printSection("8 — PERCENTILES, MEDIAN & DISTRIBUTION");
        medianSalary();
        percentileSalary(25);
        percentileSalary(75);
        percentileSalary(90);
        interquartileRange();
        salaryQuartiles();

        // Section 9
        printSection("9 — VARIANCE, STD DEVIATION & Z-SCORES");
        varianceAndStdDeviation();
        zScoresPerPerson();
        coefficientOfVariationByCompany();

        // Section 10
        printSection("10 — RUNNING TOTALS & CUMULATIVE SUMS");
        cumulativeSalarySum();
        movingAverageSalary(3);
        cumulativeHeadcountByExperience();

        // Section 11
        printSection("11 — RANKING, TOP-N & PERCENTILE RANK");
        denseRankBySalary();
        topNPerCompany(2);
        percentileRankPerPerson();

        // Section 12
        printSection("12 — CORRELATION & COMPARATIVE ANALYSIS");
        experienceVsSalaryCorrelation();
        salaryGapBetweenGenders();
        aboveOrBelowCompanyAverage();

        // Section 13
        printSection("13 — HISTOGRAMS & FREQUENCY DISTRIBUTIONS");
        salaryHistogram(20_000);
        experienceHistogram();
        ageHistogram();
        skillProficiencyDistribution();

        // Section 14
        printSection("14 — TEEING FOR DUAL-STATISTIC COMPUTATIONS");
        teeingSumAndCount();
        teeingMinMaxRange();
        teeingMeanAndMedian();
        teeingTopAndBottomEarner();
        teeingAboveBelowAverage();

        // Section 15
        printSection("15 — REAL-WORLD SALARY ANALYTICS DASHBOARD");
        fullSalaryAnalyticsDashboard();

        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  ALL STATISTICAL EXERCISES COMPLETED SUCCESSFULLY");
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 1 — PRIMITIVE STREAMS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Demonstrates {@link IntStream#range} and {@link IntStream#rangeClosed}
     * for generating sequences of integers without boxing overhead.
     *
     * <p><strong>Key point:</strong> {@code IntStream.range(0, 5)} produces
     * {0,1,2,3,4} (exclusive end), while {@code rangeClosed} includes
     * the upper bound. These are cheaper than
     * {@code Stream.of(0,1,2,3,4)} because they avoid Integer boxing.</p>
     */
    static void intStreamFromRange() {
        int sumRange = IntStream.range(1, 11).sum();           // 1..10
        int sumClosed = IntStream.rangeClosed(1, 10).sum();    // 1..10

        printExercise("1.1 IntStream.range(1,11).sum()", sumRange);
        printExercise("    IntStream.rangeClosed(1,10).sum()", sumClosed);
    }

    /**
     * Converts a {@code Stream<Person>} to a {@link DoubleStream}
     * using {@code mapToDouble}, which avoids autoboxing and enables
     * primitive-specialised terminal operations like {@code sum()}.
     *
     * <p><strong>Key point:</strong> {@code mapToDouble} returns a
     * primitive {@code DoubleStream}, which has built-in {@code sum()},
     * {@code average()}, {@code min()}, {@code max()} — none of which
     * exist on {@code Stream<Double>}.</p>
     */
    static void doubleStreamFromMapToDouble() {
        double total = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .sum();

        printExercise("1.2 mapToDouble → sum()", "£%,.2f".formatted(total));
    }

    /**
     * Converts experience years to a {@link LongStream} via
     * {@code mapToLong}, then sums all experience across the company.
     *
     * <p><strong>Key point:</strong> Use {@code mapToLong} when working
     * with long-valued attributes or when the sum might overflow int.</p>
     */
    static void longStreamFromMapToLong() {
        long totalExperience = EARNERS.stream()
            .mapToLong(p -> p.getProfession().yearsExperience())
            .sum();

        printExercise("1.3 mapToLong → total experience years", totalExperience);
    }

    /**
     * Shows the conversion between primitive and boxed streams
     * using {@code boxed()} and the reverse with {@code mapToInt()}.
     *
     * <p><strong>Key point:</strong> {@code DoubleStream.boxed()} wraps
     * each primitive into {@code Double}, returning a {@code Stream<Double>}.
     * This is needed when you want to use Collectors (which only work
     * with object streams), but comes at the cost of boxing overhead.</p>
     */
    static void primitiveStreamBoxingUnboxing() {
        // Primitive → Boxed: needed for Collectors
        List<Double> salaryList = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .boxed()
            .toList();

        // Boxed → Primitive: use mapToDouble on Stream<Double>
        double sum = salaryList.stream()
            .mapToDouble(Double::doubleValue)
            .sum();

        printExercise("1.4 boxed() list size", salaryList.size());
        printExercise("    unboxed sum", "£%,.2f".formatted(sum));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 2 — BASIC AGGREGATIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Calculates total salary using {@code mapToDouble().sum()}.
     *
     * <p><strong>Approach 1 of 3 for summation.</strong> This is the
     * most idiomatic and efficient way — it avoids boxing entirely
     * and uses a specialised double accumulator internally.</p>
     */
    static void sumWithMapToDouble() {
        double total = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .sum();

        printExercise("2.1 Sum via mapToDouble().sum()", "£%,.2f".formatted(total));
    }

    /**
     * Calculates total salary using {@code Stream.reduce()}.
     *
     * <p><strong>Approach 2 of 3.</strong> The three-argument reduce:
     * (identity, accumulator, combiner). The combiner is only used
     * in parallel streams to merge partial results. For sequential
     * streams it is never called, but must still be provided.</p>
     */
    static void sumWithReduce() {
        double total = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .reduce(0.0, Double::sum);

        printExercise("2.2 Sum via reduce(identity, accumulator)", "£%,.2f".formatted(total));
    }

    /**
     * Calculates total salary using {@code Collectors.summingDouble}.
     *
     * <p><strong>Approach 3 of 3.</strong> Collector-based summation.
     * Useful as a downstream collector inside {@code groupingBy}
     * — you cannot nest {@code mapToDouble().sum()} inside groupingBy,
     * but you can nest {@code summingDouble}.</p>
     */
    static void sumWithCollector() {
        double total = EARNERS.stream()
            .collect(Collectors.summingDouble(
                p -> p.getProfession().salary()));

        printExercise("2.3 Sum via Collectors.summingDouble()", "£%,.2f".formatted(total));
    }

    /**
     * Computes average salary via the primitive stream's
     * {@code average()} method, which returns {@code OptionalDouble}.
     *
     * <p><strong>Key point:</strong> {@code average()} returns
     * {@code OptionalDouble} (not {@code double}) because the stream
     * may be empty. Always handle the empty case with
     * {@code orElse()} or {@code ifPresent()}.</p>
     */
    static void averageWithPrimitiveStream() {
        OptionalDouble avg = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .average();

        printExercise("2.4 Average via mapToDouble().average()",
            avg.isPresent() ? "£%,.2f".formatted(avg.getAsDouble()) : "N/A");
    }

    /**
     * Computes average salary using {@code Collectors.averagingDouble}.
     *
     * <p><strong>Key point:</strong> Unlike the primitive stream version,
     * this returns a plain {@code double} (defaults to 0.0 for empty
     * streams). Useful as a downstream collector in groupingBy.</p>
     */
    static void averageWithCollector() {
        double avg = EARNERS.stream()
            .collect(Collectors.averagingDouble(
                p -> p.getProfession().salary()));

        printExercise("2.5 Average via Collectors.averagingDouble()", "£%,.2f".formatted(avg));
    }

    /**
     * Finds the minimum salary using {@code DoubleStream.min()}.
     *
     * <p><strong>Key point:</strong> Returns {@code OptionalDouble}
     * because the stream could be empty. The primitive specialisation
     * avoids boxing compared to {@code Stream.min(Comparator)}.</p>
     */
    static void minSalary() {
        OptionalDouble min = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .min();

        printExercise("2.6 Min salary",
            min.isPresent() ? "£%,.2f".formatted(min.getAsDouble()) : "N/A");
    }

    /**
     * Finds the maximum salary using {@code DoubleStream.max()}.
     *
     * <p><strong>Key point:</strong> Same semantics as {@code min()},
     * but returns the largest value.</p>
     */
    static void maxSalary() {
        OptionalDouble max = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .max();

        printExercise("2.7 Max salary",
            max.isPresent() ? "£%,.2f".formatted(max.getAsDouble()) : "N/A");
    }

    /**
     * Finds both the minimum and maximum years of experience
     * using two separate {@code IntStream} passes.
     *
     * <p><strong>Key point:</strong> Each terminal operation consumes
     * the stream, so for min AND max you need two streams (or use
     * {@code summaryStatistics()} for a single-pass solution — see
     * Section 3).</p>
     */
    static void minAndMaxExperience() {
        IntStream expStream1 = EARNERS.stream()
            .mapToInt(p -> p.getProfession().yearsExperience());
        IntStream expStream2 = EARNERS.stream()
            .mapToInt(p -> p.getProfession().yearsExperience());

        OptionalInt minExp = expStream1.min();
        OptionalInt maxExp = expStream2.max();

        printExercise("2.8 Min experience", minExp.orElse(-1) + " years");
        printExercise("    Max experience", maxExp.orElse(-1) + " years");
    }

    /**
     * Demonstrates three different ways to count elements.
     *
     * <p><strong>Key point:</strong> {@code Stream.count()} is terminal,
     * {@code Collectors.counting()} is a collector (useful as downstream),
     * and {@code list.size()} works if you already collected to a list.</p>
     */
    static void countWithVariousMethods() {
        long countStream = EARNERS.stream().count();
        long countCollector = EARNERS.stream()
            .collect(Collectors.counting());
        int countList = EARNERS.size();

        printExercise("2.9 Count — stream.count()", countStream);
        printExercise("    Count — Collectors.counting()", countCollector);
        printExercise("    Count — list.size()", countList);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 3 — SUMMARY STATISTICS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Produces a complete {@link DoubleSummaryStatistics} for salaries
     * in a single pass — giving count, sum, min, max, and average.
     *
     * <p><strong>Key point:</strong> {@code summaryStatistics()} on a
     * primitive stream is the most efficient way to compute multiple
     * aggregations simultaneously. It traverses the stream only once,
     * unlike calling {@code sum()}, {@code min()}, {@code max()}
     * separately (which requires three passes).</p>
     */
    static void salarySummaryStatistics() {
        DoubleSummaryStatistics stats = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .summaryStatistics();

        printExercise("3.1 Salary DoubleSummaryStatistics",
            formatStats(stats, "£"));
    }

    /**
     * Produces {@link IntSummaryStatistics} for years of experience.
     *
     * <p><strong>Key point:</strong> {@code mapToInt().summaryStatistics()}
     * works on {@code IntStream} and returns {@code IntSummaryStatistics},
     * which has the same methods but for int values.</p>
     */
    static void experienceSummaryStatistics() {
        IntSummaryStatistics stats = EARNERS.stream()
            .mapToInt(p -> p.getProfession().yearsExperience())
            .summaryStatistics();

        printExercise("3.2 Experience IntSummaryStatistics",
            "count=%d, min=%d, max=%d, avg=%.1f, sum=%d"
                .formatted(stats.getCount(), stats.getMin(),
                    stats.getMax(), stats.getAverage(),
                    (long) stats.getSum()));
    }

    /**
     * Produces summary statistics for ages.
     *
     * <p><strong>Key point:</strong> computed properties like
     * {@code getAge()} work seamlessly as mapper functions.</p>
     */
    static void ageSummaryStatistics() {
        IntSummaryStatistics stats = PEOPLE.stream()
            .mapToInt(Person::getAge)
            .summaryStatistics();

        printExercise("3.3 Age IntSummaryStatistics",
            "count=%d, min=%d, max=%d, avg=%.1f"
                .formatted(stats.getCount(), stats.getMin(),
                    stats.getMax(), stats.getAverage()));
    }

    /**
     * Produces summary statistics for the number of skills each person has.
     *
     * <p><strong>Key point:</strong> {@code mapToInt(p → p.getSkills().size())}
     * converts a collection size into a numeric stream for aggregation.</p>
     */
    static void skillCountSummaryStatistics() {
        IntSummaryStatistics stats = PEOPLE.stream()
            .mapToInt(p -> p.getSkills().size())
            .summaryStatistics();

        printExercise("3.4 Skills-per-person statistics",
            "count=%d, min=%d, max=%d, avg=%.1f"
                .formatted(stats.getCount(), stats.getMin(),
                    stats.getMax(), stats.getAverage()));
    }

    /**
     * Computes separate salary summary statistics for each gender
     * using {@code groupingBy} with {@code summarizingDouble} downstream.
     *
     * <p><strong>Key point:</strong> {@code Collectors.summarizingDouble}
     * as a downstream collector produces per-group statistics without
     * needing multiple stream traversals.</p>
     */
    static void compareSummaryStatisticsPerGender() {
        Map<Gender, DoubleSummaryStatistics> statsByGender = EARNERS.stream()
            .collect(Collectors.groupingBy(
                Person::getGender,
                Collectors.summarizingDouble(
                    p -> p.getProfession().salary())));

        System.out.println("  ► 3.5 Salary statistics per gender:");
        statsByGender.forEach((gender, stats) ->
            System.out.println("    %s: %s".formatted(gender, formatStats(stats, "£"))));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 4 — REDUCE: CUSTOM ACCUMULATIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Three-argument reduce for summing salaries, showing each parameter.
     *
     * <p><strong>Key point:</strong> The three-arg reduce signature is
     * {@code reduce(identity, accumulator, combiner)}:</p>
     * <ul>
     *   <li><strong>Identity</strong> – starting/neutral value (0.0 for addition)</li>
     *   <li><strong>Accumulator</strong> – {@code (partialSum, person) → newPartialSum}</li>
     *   <li><strong>Combiner</strong> – merges partial sums from parallel sub-streams</li>
     * </ul>
     *
     * <p>This overload is required when the result type differs from
     * the stream element type (here: Double vs Person).</p>
     */
    static void sumSalariesWithThreeArgReduce() {
        double total = EARNERS.stream()
            .reduce(
                0.0,                                                // identity
                (partialSum, person) -> partialSum +                // accumulator
                    person.getProfession().salary(),
                Double::sum                                         // combiner
            );

        printExercise("4.1 Three-arg reduce (sum)", "£%,.2f".formatted(total));
    }

    /**
     * Multiplies all experience years together using {@code reduce}.
     *
     * <p><strong>Key point:</strong> For multiplication, the identity
     * is 1 (not 0). This exercise demonstrates that {@code reduce}
     * is not limited to addition — any associative operation works.</p>
     *
     * <p><strong>Warning:</strong> In production, multiplying many
     * integers together causes overflow quickly. This is for
     * demonstration only.</p>
     */
    static void productOfExperienceYears() {
        // Using only employed people with experience > 0
        long product = EARNERS.stream()
            .map(p -> (long) p.getProfession().yearsExperience())
            .filter(exp -> exp > 0)
            .reduce(1L, (a, b) -> a * b);

        printExercise("4.2 Product of experience years (reduce with *)", product);
    }

    /**
     * Uses the two-argument reduce (no identity) to find the highest
     * salary, which returns {@code Optional<Double>}.
     *
     * <p><strong>Key point:</strong> Without an identity value, reduce
     * cannot assume a default, so it returns {@code Optional} to
     * handle the empty-stream case safely.</p>
     */
    static void findHighestSalaryWithReduce() {
        Optional<Double> highest = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .reduce(Double::max);

        printExercise("4.3 Max salary via reduce(Double::max)",
            highest.map("£%,.2f"::formatted).orElse("N/A"));
    }

    /**
     * Concatenates all salaries into a formatted string using reduce,
     * demonstrating string accumulation.
     *
     * <p><strong>Key point:</strong> While {@code Collectors.joining()}
     * is preferred for string concatenation, this shows that reduce
     * can accumulate any type. The three-arg version is needed because
     * the accumulator type (String) differs from the element type (Person).</p>
     */
    static void concatenateSalariesAsString() {
        String salaryChain = EARNERS.stream()
            .reduce(
                "",
                (str, p) -> str + (str.isEmpty() ? "" : " + ") +
                    "£%,.0f".formatted(p.getProfession().salary()),
                (s1, s2) -> s1 + (s1.isEmpty() ? "" : " + ") + s2
            );

        printExercise("4.4 Salaries concatenated via reduce", salaryChain);
    }

    /**
     * Reduces into a custom accumulator record that tracks both
     * the running total and count, computing the average at the end.
     *
     * <p><strong>Key point:</strong> You can reduce into any mutable
     * or immutable type. Here we use a record as an accumulator,
     * proving that reduce is a general-purpose folding operation.</p>
     */
    static void reduceToCustomAccumulator() {
        record SumCount(double sum, long count) {
            double average() {
                return count == 0 ? 0 : sum / count;
            }

            SumCount add(double value) {
                return new SumCount(sum + value, count + 1);
            }

            SumCount merge(SumCount other) {
                return new SumCount(sum + other.sum, count + other.count);
            }
        }

        SumCount result = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .reduce(
                new SumCount(0, 0),
                (acc, salary) -> acc.add(salary),
                SumCount::merge
            );

        printExercise("4.5 Custom accumulator reduce",
            "sum=£%,.0f, count=%d, avg=£%,.0f"
                .formatted(result.sum(), result.count(), result.average()));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 5 — GROUPED AGGREGATIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Total salary per company using
     * {@code groupingBy → summingDouble}.
     *
     * <p><strong>Key point:</strong> {@code Collectors.summingDouble} as
     * a downstream collector computes per-group sums. This is the
     * Stream equivalent of {@code SELECT company, SUM(salary) GROUP BY company}.</p>
     */
    static void sumSalaryByCompany() {
        Map<String, Double> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.summingDouble(
                    p -> p.getProfession().salary())));

        printExercise("5.1 SUM salary by company", formatMoney(result));
    }

    /**
     * Average salary per department using
     * {@code groupingBy → averagingDouble}.
     *
     * <p><strong>Key point:</strong> Equivalent to
     * {@code SELECT dept, AVG(salary) GROUP BY dept}.</p>
     */
    static void averageSalaryByDepartment() {
        Map<String, Double> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().department(),
                Collectors.averagingDouble(
                    p -> p.getProfession().salary())));

        printExercise("5.2 AVG salary by department", formatMoney(result));
    }

    /**
     * Maximum salary per country using
     * {@code groupingBy → collectingAndThen(maxBy, unwrap)}.
     *
     * <p><strong>Key point:</strong> {@code Collectors.maxBy} returns
     * {@code Optional<Person>}, so we wrap it in
     * {@code collectingAndThen} to extract the salary value,
     * avoiding Optional in the map values.</p>
     */
    static void maxSalaryByCountry() {
        Map<String, Double> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                Person::getHomeCountry,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparingDouble(
                        p -> p.getProfession().salary())),
                    opt -> opt.map(p -> p.getProfession().salary()).orElse(0.0)
                )));

        printExercise("5.3 MAX salary by country", formatMoney(result));
    }

    /**
     * Minimum experience per company using
     * {@code groupingBy → mapping → minBy}.
     *
     * <p><strong>Key point:</strong> We chain {@code mapping} to
     * extract the int before applying {@code minBy}, demonstrating
     * collector composition for type transformation within groups.</p>
     */
    static void minExperienceByCompany() {
        Map<String, Optional<Integer>> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.mapping(
                    p -> p.getProfession().yearsExperience(),
                    Collectors.minBy(Integer::compareTo))));

        Map<String, String> formatted = new LinkedHashMap<>();
        result.forEach((k, v) -> formatted.put(k, v.map(y -> y + " years").orElse("N/A")));

        printExercise("5.4 MIN experience by company", formatted);
    }

    /**
     * Head count per gender using {@code groupingBy → counting}.
     *
     * <p><strong>Key point:</strong> Equivalent to
     * {@code SELECT gender, COUNT(*) GROUP BY gender}.</p>
     */
    static void countByGender() {
        Map<Gender, Long> result = PEOPLE.stream()
            .collect(Collectors.groupingBy(Person::getGender, Collectors.counting()));

        printExercise("5.5 COUNT by gender", result);
    }

    /**
     * Total salary per employment status.
     *
     * <p><strong>Key point:</strong> Groups by enum value, showing that
     * any type can serve as a grouping key.</p>
     */
    static void sumSalaryByEmploymentStatus() {
        Map<EmploymentStatus, Double> result = PEOPLE.stream()
            .filter(p -> p.getProfession() != null)
            .collect(Collectors.groupingBy(
                p -> p.getProfession().status(),
                Collectors.summingDouble(
                    p -> p.getProfession().salary())));

        printExercise("5.6 SUM salary by employment status", formatMoney(result));
    }

    /**
     * Average age per company.
     *
     * <p><strong>Key point:</strong> Demonstrates using a computed
     * property ({@code getAge()}) in the averaging function.</p>
     */
    static void averageAgeByCompany() {
        Map<String, Double> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.averagingInt(Person::getAge)));

        Map<String, String> formatted = new LinkedHashMap<>();
        result.forEach((k, v) -> formatted.put(k, "%.1f years".formatted(v)));

        printExercise("5.7 AVG age by company", formatted);
    }

    /**
     * Total number of skills held per department (using flatMap
     * to count individual skills, not people).
     *
     * <p><strong>Key point:</strong> {@code Collectors.summingInt} with
     * the skill list size as the mapper — a simple way to sum
     * a derived integer per group.</p>
     */
    static void totalSkillCountByDepartment() {
        Map<String, Integer> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().department(),
                Collectors.summingInt(p -> p.getSkills().size())));

        printExercise("5.8 Total skill slots by department", result);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 6 — MULTI-LEVEL GROUPED STATISTICS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Average salary grouped first by company, then by gender.
     * Produces {@code Map<String, Map<Gender, Double>>}.
     *
     * <p><strong>Key point:</strong> Nesting two {@code groupingBy}
     * with {@code averagingDouble} at the leaf level creates a
     * two-dimensional pivot table in a single stream pass.</p>
     */
    static void avgSalaryByCompanyAndGender() {
        Map<String, Map<Gender, Double>> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.groupingBy(
                    Person::getGender,
                    Collectors.averagingDouble(
                        p -> p.getProfession().salary()))));

        System.out.println("  ► 6.1 AVG salary by company → gender:");
        result.forEach((company, genderMap) -> {
            System.out.println("    " + company + ":");
            genderMap.forEach((gender, avg) ->
                System.out.println("      %s: £%,.0f".formatted(gender, avg)));
        });
    }

    /**
     * Total salary grouped by country, then by department.
     * Produces {@code Map<String, Map<String, Double>>}.
     *
     * <p><strong>Key point:</strong> demonstrates summingDouble
     * as the innermost downstream in a nested groupingBy chain.</p>
     */
    static void sumSalaryByCountryAndDepartment() {
        Map<String, Map<String, Double>> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                Person::getHomeCountry,
                Collectors.groupingBy(
                    p -> p.getProfession().department(),
                    Collectors.summingDouble(
                        p -> p.getProfession().salary()))));

        System.out.println("  ► 6.2 SUM salary by country → department:");
        result.forEach((country, deptMap) -> {
            System.out.println("    " + country + ":");
            deptMap.forEach((dept, sum) ->
                System.out.println("      %-20s £%,.0f".formatted(dept, sum)));
        });
    }

    /**
     * Full summary statistics per company per department.
     *
     * <p><strong>Key point:</strong> three levels deep —
     * {@code groupingBy → groupingBy → summarizingDouble}.
     * Each leaf is a {@code DoubleSummaryStatistics} containing
     * count/sum/min/max/avg for that company-department pair.</p>
     */
    static void statsPerCompanyPerDepartment() {
        Map<String, Map<String, DoubleSummaryStatistics>> result = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.groupingBy(
                    p -> p.getProfession().department(),
                    Collectors.summarizingDouble(
                        p -> p.getProfession().salary()))));

        System.out.println("  ► 6.3 Full stats per company → department:");
        result.forEach((company, deptMap) -> {
            System.out.println("    " + company + ":");
            deptMap.forEach((dept, stats) ->
                System.out.println("      %-15s %s".formatted(dept, formatStats(stats, "£"))));
        });
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 7 — WEIGHTED AVERAGES & RATIOS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Calculates a weighted average skill proficiency across all people,
     * where the weight is the person's years of experience.
     *
     * <p><strong>Formula:</strong>
     * {@code Σ(experience × proficiencyWeight) / Σ(experience)}</p>
     *
     * <p><strong>Key point:</strong> weighted averages require two
     * parallel accumulations (numerator and denominator), which
     * can be done with {@code reduce} using a custom accumulator
     * or with {@code teeing}.</p>
     */
    static void weightedAverageProficiency() {
        record WeightedAcc(double weightedSum, double totalWeight) {
            WeightedAcc add(double value, double weight) {
                return new WeightedAcc(weightedSum + value * weight, totalWeight + weight);
            }

            WeightedAcc merge(WeightedAcc other) {
                return new WeightedAcc(weightedSum + other.weightedSum,
                    totalWeight + other.totalWeight);
            }

            double average() {
                return totalWeight == 0 ? 0 : weightedSum / totalWeight;
            }
        }

        WeightedAcc result = EARNERS.stream()
            .flatMap(p -> p.getSkills().stream()
                .map(
                    s -> new double[] {s.level().getWeight(), p.getProfession().yearsExperience()}))
            .reduce(
                new WeightedAcc(0, 0),
                (acc, pair) -> acc.add(pair[0], pair[1]),
                WeightedAcc::merge);

        printExercise("7.1 Weighted avg proficiency (by experience)",
            "%.2f / 4.0".formatted(result.average()));
    }

    /**
     * Calculates salary-to-experience ratio for each person.
     *
     * <p><strong>Formula:</strong> {@code salary / yearsExperience}</p>
     *
     * <p><strong>Key point:</strong> demonstrates creating a derived
     * metric per element, then sorting by that metric to find
     * the "best value" employees.</p>
     */
    static void salaryToExperienceRatio() {
        record Ratio(String name, double ratio) {
        }

        List<Ratio> ratios = EARNERS.stream()
            .filter(p -> p.getProfession().yearsExperience() > 0)
            .map(p -> new Ratio(
                p.getFullName(),
                p.getProfession().salary() / p.getProfession().yearsExperience()))
            .sorted(Comparator.comparingDouble(Ratio::ratio).reversed())
            .toList();

        System.out.println("  ► 7.2 Salary / Experience ratio (desc):");
        ratios.forEach(r ->
            System.out.println("    %-20s £%,.0f per year of experience"
                .formatted(r.name, r.ratio)));
    }

    /**
     * Average salary per skill held: total salary / number of skills.
     * Shows how much salary "investment" per skill each person represents.
     *
     * <p><strong>Key point:</strong> dividing one aggregated measure by
     * another to create a normalised metric.</p>
     */
    static void averageSalaryPerSkillHeld() {
        double totalSalary = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .sum();

        long totalSkills = EARNERS.stream()
            .mapToLong(p -> p.getSkills().size())
            .sum();

        printExercise("7.3 Avg salary per skill held",
            "£%,.0f (total £%,.0f / %d skills)"
                .formatted(totalSalary / totalSkills, totalSalary, totalSkills));
    }

    /**
     * Experience-to-age ratio: what fraction of each person's life
     * has been spent in professional work.
     *
     * <p><strong>Key point:</strong> demonstrates a ratio computed from
     * two independent attributes, mapped and sorted.</p>
     */
    static void experienceToAgeRatio() {
        record ExpRatio(String name, int age, int exp, double ratio) {
        }

        List<ExpRatio> ratios = EARNERS.stream()
            .map(p -> new ExpRatio(
                p.getFullName(),
                p.getAge(),
                p.getProfession().yearsExperience(),
                (double) p.getProfession().yearsExperience() / p.getAge()))
            .sorted(Comparator.comparingDouble(ExpRatio::ratio).reversed())
            .toList();

        System.out.println("  ► 7.4 Experience / Age ratio:");
        ratios.forEach(r ->
            System.out.println("    %-20s age=%d, exp=%d, ratio=%.2f"
                .formatted(r.name, r.age, r.exp, r.ratio)));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 8 — PERCENTILES, MEDIAN & DISTRIBUTION ANALYSIS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Computes the median salary (50th percentile).
     *
     * <p><strong>Formula:</strong> Sort all values, take the middle
     * element. For even-sized arrays, average the two middle elements.</p>
     *
     * <p><strong>Key point:</strong> The Stream API has no built-in
     * median function. We must collect, sort, and compute manually.
     * This uses {@code Collector.of} for a reusable custom collector.</p>
     */
    static void medianSalary() {
        double median = computeMedian(
            EARNERS.stream()
                .map(p -> p.getProfession().salary())
                .toList());

        printExercise("8.1 Median salary", "£%,.0f".formatted(median));
    }

    /**
     * Computes a given percentile of salaries.
     *
     * <p><strong>Formula:</strong> Sort values, find the index at
     * {@code percentile/100 × (n-1)}, interpolate if needed.</p>
     *
     * @param percentile the percentile to compute (0–100)
     */
    static void percentileSalary(int percentile) {
        List<Double> sorted = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .sorted()
            .toList();

        double value = computePercentile(sorted, percentile);

        printExercise(
            "8.%d P%d salary".formatted(percentile == 25 ? 2 : percentile == 75 ? 3 : 4,
                percentile),
            "£%,.0f".formatted(value));
    }

    /**
     * Computes the interquartile range (IQR): P75 − P25.
     *
     * <p><strong>Key point:</strong> IQR is a robust measure of spread
     * that is less sensitive to outliers than standard deviation.
     * It represents the range of the "middle 50%" of data.</p>
     */
    static void interquartileRange() {
        List<Double> sorted = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .sorted()
            .toList();

        double p25 = computePercentile(sorted, 25);
        double p75 = computePercentile(sorted, 75);
        double iqr = p75 - p25;

        printExercise("8.5 Interquartile range (P75-P25)",
            "£%,.0f (P25=£%,.0f, P75=£%,.0f)".formatted(iqr, p25, p75));
    }

    /**
     * Splits salaries into four quartile groups (Q1–Q4) and lists
     * the people in each quartile.
     *
     * <p><strong>Key point:</strong> computed grouping key based on
     * the percentile rank of each person's salary, creating
     * a data-driven classifier function.</p>
     */
    static void salaryQuartiles() {
        List<Double> sorted = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .sorted()
            .toList();

        double q1 = computePercentile(sorted, 25);
        double q2 = computePercentile(sorted, 50);
        double q3 = computePercentile(sorted, 75);

        Map<String, List<String>> quartiles = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> {
                    double s = p.getProfession().salary();
                    if (s <= q1) {
                        return "Q1 (≤£%,.0f)".formatted(q1);
                    }
                    if (s <= q2) {
                        return "Q2 (≤£%,.0f)".formatted(q2);
                    }
                    if (s <= q3) {
                        return "Q3 (≤£%,.0f)".formatted(q3);
                    }
                    return "Q4 (>£%,.0f)".formatted(q3);
                },
                TreeMap::new,
                Collectors.mapping(
                    p -> "%s (£%,.0f)".formatted(p.getFullName(), p.getProfession().salary()),
                    Collectors.toList())));

        System.out.println("  ► 8.6 Salary quartile groups:");
        quartiles.forEach((q, people) ->
            System.out.println("    %-20s → %s".formatted(q, people)));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 9 — VARIANCE, STANDARD DEVIATION & Z-SCORES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Calculates population variance and standard deviation of salaries.
     *
     * <p><strong>Formulas:</strong></p>
     * <pre>
     *   Variance = Σ(xᵢ - μ)² / N
     *   Std Dev  = √Variance
     * </pre>
     *
     * <p><strong>Key point:</strong> Requires two passes — first to
     * compute the mean, then to compute the sum of squared deviations.
     * Alternatively, use the computational formula:
     * {@code Var = E[X²] - (E[X])²} for a single-pass approach.</p>
     */
    static void varianceAndStdDeviation() {
        // Two-pass approach (clearer)
        double mean = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .average().orElse(0);

        double variance = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .map(s -> Math.pow(s - mean, 2))
            .average().orElse(0);

        double stdDev = Math.sqrt(variance);

        printExercise("9.1 Population variance", "£²%,.0f".formatted(variance));
        printExercise("    Standard deviation", "£%,.0f".formatted(stdDev));
        printExercise("    Mean ± 1 StdDev",
            "£%,.0f – £%,.0f".formatted(mean - stdDev, mean + stdDev));
    }

    /**
     * Calculates a Z-score for each person's salary, showing how many
     * standard deviations they are from the mean.
     *
     * <p><strong>Formula:</strong> {@code Z = (x - μ) / σ}</p>
     *
     * <p><strong>Key point:</strong> Z-scores standardise different
     * distributions to a common scale. A Z-score of +2 means the
     * person earns 2 standard deviations above the mean.</p>
     */
    static void zScoresPerPerson() {
        double mean = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .average().orElse(0);

        double stdDev = Math.sqrt(EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .map(s -> Math.pow(s - mean, 2))
            .average().orElse(0));

        record ZScore(String name, double salary, double z) {
        }

        List<ZScore> zScores = EARNERS.stream()
            .map(p -> new ZScore(
                p.getFullName(),
                p.getProfession().salary(),
                (p.getProfession().salary() - mean) / stdDev))
            .sorted(Comparator.comparingDouble(ZScore::z).reversed())
            .toList();

        System.out.println("  ► 9.2 Z-scores (mean=£%,.0f, σ=£%,.0f):".formatted(mean, stdDev));
        zScores.forEach(z ->
            System.out.println("    %-20s £%,8.0f  z=%+.2f %s"
                .formatted(z.name, z.salary, z.z,
                    Math.abs(z.z) > 1.5 ? "⚠ outlier" : "")));
    }

    /**
     * Calculates the coefficient of variation (CV) per company.
     *
     * <p><strong>Formula:</strong> {@code CV = (σ / μ) × 100%}</p>
     *
     * <p><strong>Key point:</strong> CV measures relative variability.
     * A high CV means salaries within that company are more spread
     * out relative to their mean, indicating pay inequality.</p>
     */
    static void coefficientOfVariationByCompany() {
        Map<String, List<Double>> salariesByCompany = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.mapping(
                    p -> p.getProfession().salary(),
                    Collectors.toList())));

        System.out.println("  ► 9.3 Coefficient of Variation by company:");
        salariesByCompany.forEach((company, salaries) -> {
            double mean = salaries.stream().mapToDouble(d -> d).average().orElse(0);
            double variance = salaries.stream().mapToDouble(d -> d)
                .map(s -> Math.pow(s - mean, 2)).average().orElse(0);
            double stdDev = Math.sqrt(variance);
            double cv = mean == 0 ? 0 : (stdDev / mean) * 100;

            System.out.println("    %-20s mean=£%,8.0f  σ=£%,8.0f  CV=%.1f%%  %s"
                .formatted(company, mean, stdDev, cv,
                    cv > 30 ? "(high disparity)" : "(relatively uniform)"));
        });
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 10 — RUNNING TOTALS, CUMULATIVE SUMS & MOVING AVERAGES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Computes a cumulative salary sum, sorted by salary ascending.
     *
     * <p><strong>Key point:</strong> Streams are inherently stateless
     * per-element, so cumulative (running) totals require either
     * collecting to a list first and iterating, or using an
     * {@code AtomicReference}/array as a mutable accumulator in
     * {@code map} (which breaks the functional purity but works
     * in sequential streams).</p>
     *
     * <p>Here we use the collect-then-iterate approach for safety.</p>
     */
    static void cumulativeSalarySum() {
        List<double[]> sorted = EARNERS.stream()
            .sorted(Comparator.comparingDouble(p -> p.getProfession().salary()))
            .map(p -> new double[] {p.getProfession().salary()})
            .toList();

        double runningTotal = 0;
        System.out.println("  ► 10.1 Cumulative salary sum:");
        List<Person> sortedPeople = EARNERS.stream()
            .sorted(Comparator.comparingDouble(p -> p.getProfession().salary()))
            .toList();

        for (Person p : sortedPeople) {
            runningTotal += p.getProfession().salary();
            System.out.println("    %-20s £%,8.0f  cumulative: £%,10.0f"
                .formatted(p.getFullName(), p.getProfession().salary(), runningTotal));
        }
    }

    /**
     * Computes a simple moving average of salary over a window of N people
     * (sorted by salary).
     *
     * <p><strong>Key point:</strong> Moving averages smooth out fluctuations.
     * The Stream API doesn't support windowed operations natively, so we
     * collect to a list and use {@code IntStream.range} to iterate with
     * index access.</p>
     *
     * @param windowSize the number of elements in the moving average window
     */
    static void movingAverageSalary(int windowSize) {
        List<Person> sorted = EARNERS.stream()
            .sorted(Comparator.comparingDouble(p -> p.getProfession().salary()))
            .toList();

        System.out.println("  ► 10.2 Moving average salary (window=%d):".formatted(windowSize));
        IntStream.range(0, sorted.size()).forEach(i -> {
            int start = Math.max(0, i - windowSize + 1);
            double movingAvg = IntStream.rangeClosed(start, i)
                .mapToDouble(j -> sorted.get(j).getProfession().salary())
                .average().orElse(0);

            System.out.println("    %-20s salary=£%,8.0f  MA(%d)=£%,8.0f"
                .formatted(sorted.get(i).getFullName(),
                    sorted.get(i).getProfession().salary(),
                    windowSize, movingAvg));
        });
    }

    /**
     * Cumulative headcount by experience threshold: how many people
     * have at least N years of experience, for each N.
     *
     * <p><strong>Key point:</strong> cumulative distribution function (CDF)
     * style computation using streams and index iteration.</p>
     */
    static void cumulativeHeadcountByExperience() {
        int maxExp = EARNERS.stream()
            .mapToInt(p -> p.getProfession().yearsExperience())
            .max().orElse(0);

        System.out.println("  ► 10.3 Cumulative headcount (≥ N years experience):");
        IntStream.rangeClosed(0, maxExp)
            .filter(threshold -> threshold % 2 == 0) // every 2 years
            .forEach(threshold -> {
                long count = EARNERS.stream()
                    .filter(p -> p.getProfession().yearsExperience() >= threshold)
                    .count();
                System.out.println("    ≥ %2d years: %d people  %s"
                    .formatted(threshold, count,
                        "█".repeat((int) count * 2)));
            });
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 11 — RANKING, TOP-N & PERCENTILE RANK
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Assigns a dense rank to each person based on salary (highest = rank 1).
     *
     * <p><strong>Key point:</strong> Dense ranking means tied salaries
     * share the same rank, and the next rank increments by 1 (no gaps).
     * We sort, collect, then use index-based iteration to assign ranks.</p>
     */
    static void denseRankBySalary() {
        List<Person> sorted = EARNERS.stream()
            .sorted(Comparator.comparingDouble(
                (Person p) -> p.getProfession().salary()).reversed())
            .toList();

        System.out.println("  ► 11.1 Dense rank by salary:");
        int rank = 1;
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0 && sorted.get(i).getProfession().salary()
                != sorted.get(i - 1).getProfession().salary()) {
                rank++;
            }
            System.out.println("    Rank %2d: %-20s £%,8.0f"
                .formatted(rank, sorted.get(i).getFullName(),
                    sorted.get(i).getProfession().salary()));
        }
    }

    /**
     * Finds the top N earners within each company.
     *
     * <p><strong>Key point:</strong> {@code groupingBy} followed by
     * {@code collectingAndThen} that sorts and limits the grouped list.
     * This is the Stream equivalent of a SQL window function:
     * {@code ROW_NUMBER() OVER (PARTITION BY company ORDER BY salary DESC)}.</p>
     *
     * @param n top N to retrieve per company
     */
    static void topNPerCompany(int n) {
        Map<String, List<String>> topPerCompany = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> list.stream()
                        .sorted(Comparator.comparingDouble(
                            (Person p) -> p.getProfession().salary()).reversed())
                        .limit(n)
                        .map(p -> "%s (£%,.0f)".formatted(
                            p.getFullName(), p.getProfession().salary()))
                        .toList())));

        printExercise("11.2 Top %d earners per company".formatted(n), topPerCompany);
    }

    /**
     * Computes percentile rank for each person: what percentage of
     * earners they earn more than.
     *
     * <p><strong>Formula:</strong>
     * {@code percentileRank = (countBelow / (N - 1)) × 100}</p>
     *
     * <p><strong>Key point:</strong> requires knowing the count of people
     * earning less than each person, which means comparing against the
     * full sorted dataset.</p>
     */
    static void percentileRankPerPerson() {
        long total = EARNERS.size();

        record PercentileRank(String name, double salary, double percentile) {
        }

        List<PercentileRank> ranks = EARNERS.stream()
            .map(p -> {
                long countBelow = EARNERS.stream()
                    .filter(other -> other.getProfession().salary()
                        < p.getProfession().salary())
                    .count();
                double pctRank = total <= 1 ? 100.0 : (countBelow * 100.0) / (total - 1);
                return new PercentileRank(p.getFullName(),
                    p.getProfession().salary(), pctRank);
            })
            .sorted(Comparator.comparingDouble(PercentileRank::percentile).reversed())
            .toList();

        System.out.println("  ► 11.3 Percentile rank:");
        ranks.forEach(r ->
            System.out.println("    %-20s £%,8.0f  → P%.0f (earns more than %.0f%% of people)"
                .formatted(r.name, r.salary, r.percentile, r.percentile)));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 12 — CORRELATION & COMPARATIVE ANALYSIS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Calculates Pearson correlation coefficient between experience
     * and salary.
     *
     * <p><strong>Formula:</strong>
     * {@code r = Σ((xᵢ-x̄)(yᵢ-ȳ)) / √(Σ(xᵢ-x̄)² × Σ(yᵢ-ȳ)²)}</p>
     *
     * <p><strong>Key point:</strong> This requires computing means first,
     * then three separate sums (cross-product, x-variance, y-variance).
     * The Stream API handles each sum cleanly with {@code mapToDouble}.</p>
     *
     * <p>Interpretation: r ≈ 1 means strong positive correlation
     * (more experience → higher salary), r ≈ 0 means no correlation.</p>
     */
    static void experienceVsSalaryCorrelation() {
        double meanExp = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().yearsExperience())
            .average().orElse(0);
        double meanSal = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .average().orElse(0);

        double crossProduct = EARNERS.stream()
            .mapToDouble(p ->
                (p.getProfession().yearsExperience() - meanExp) *
                    (p.getProfession().salary() - meanSal))
            .sum();

        double varExp = EARNERS.stream()
            .mapToDouble(p -> Math.pow(p.getProfession().yearsExperience() - meanExp, 2))
            .sum();

        double varSal = EARNERS.stream()
            .mapToDouble(p -> Math.pow(p.getProfession().salary() - meanSal, 2))
            .sum();

        double r = crossProduct / Math.sqrt(varExp * varSal);

        printExercise("12.1 Pearson correlation (experience vs salary)",
            "r = %.4f %s".formatted(r,
                r > 0.7 ? "(strong positive)" :
                    r > 0.3 ? "(moderate positive)" :
                        r > -0.3 ? "(weak/none)" : "(negative)"));
    }

    /**
     * Computes the salary gap between genders (average male − average female).
     *
     * <p><strong>Key point:</strong> groups by gender, computes average
     * per group, then compares the two averages. Demonstrates using
     * grouped statistics for comparative analysis.</p>
     */
    static void salaryGapBetweenGenders() {
        Map<Gender, Double> avgByGender = EARNERS.stream()
            .collect(Collectors.groupingBy(
                Person::getGender,
                Collectors.averagingDouble(p -> p.getProfession().salary())));

        double maleAvg = avgByGender.getOrDefault(Gender.MALE, 0.0);
        double femaleAvg = avgByGender.getOrDefault(Gender.FEMALE, 0.0);
        double gap = maleAvg - femaleAvg;
        double gapPercent = femaleAvg == 0 ? 0 : (gap / femaleAvg) * 100;

        printExercise("12.2 Gender salary comparison",
            "Male avg=£%,.0f, Female avg=£%,.0f, Gap=£%,.0f (%.1f%%)"
                .formatted(maleAvg, femaleAvg, gap, gapPercent));
    }

    /**
     * For each person, determines whether they are above or below
     * their company's average salary, and by how much.
     *
     * <p><strong>Key point:</strong> computes per-company averages first
     * (using groupingBy), then maps each person against their company
     * average — a two-pass pattern common in analytical pipelines.</p>
     */
    static void aboveOrBelowCompanyAverage() {
        Map<String, Double> companyAvg = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> p.getProfession().company(),
                Collectors.averagingDouble(p -> p.getProfession().salary())));

        System.out.println("  ► 12.3 Above/below company average:");
        EARNERS.stream()
            .sorted(Comparator.comparing((Person p) -> p.getProfession().company())
                .thenComparingDouble(p -> p.getProfession().salary()))
            .forEach(p -> {
                double avg = companyAvg.get(p.getProfession().company());
                double diff = p.getProfession().salary() - avg;
                String indicator = diff >= 0 ? "▲" : "▼";
                System.out.println("    [%-15s] %-20s £%,8.0f  %s £%,8.0f vs avg £%,8.0f"
                    .formatted(p.getProfession().company(),
                        p.getFullName(),
                        p.getProfession().salary(),
                        indicator, Math.abs(diff), avg));
            });
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 13 — HISTOGRAMS & FREQUENCY DISTRIBUTIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Creates a salary histogram with configurable bin width.
     *
     * <p><strong>Key point:</strong> uses a computed grouping key that
     * rounds each salary down to the nearest bin boundary, then
     * counts per bin. This is the Stream equivalent of a histogram.</p>
     *
     * @param binWidth the width of each histogram bin
     */
    static void salaryHistogram(double binWidth) {
        Map<String, Long> histogram = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> {
                    long bin =
                        (long) (Math.floor(p.getProfession().salary() / binWidth) * binWidth);
                    return "£%,6d – £%,6d".formatted(bin, (long) (bin + binWidth - 1));
                },
                TreeMap::new,
                Collectors.counting()));

        System.out.println("  ► 13.1 Salary histogram (bin=£%,.0f):".formatted(binWidth));
        histogram.forEach((bin, count) ->
            System.out.println("    %s │%s %d"
                .formatted(bin, "██".repeat(count.intValue()), count)));
    }

    /**
     * Creates a histogram of years of experience.
     *
     * <p><strong>Key point:</strong> uses integer division for binning
     * into 5-year ranges (0–4, 5–9, 10–14, etc.).</p>
     */
    static void experienceHistogram() {
        Map<String, Long> histogram = EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> {
                    int exp = p.getProfession().yearsExperience();
                    int bin = (exp / 5) * 5;
                    return "%2d–%2d yrs".formatted(bin, bin + 4);
                },
                TreeMap::new,
                Collectors.counting()));

        System.out.println("  ► 13.2 Experience histogram:");
        histogram.forEach((bin, count) ->
            System.out.println("    %s │%s %d"
                .formatted(bin, "██".repeat(count.intValue()), count)));
    }

    /**
     * Creates a histogram of ages in 5-year bins.
     */
    static void ageHistogram() {
        Map<String, Long> histogram = PEOPLE.stream()
            .collect(Collectors.groupingBy(
                p -> {
                    int bin = (p.getAge() / 5) * 5;
                    return "%2d–%2d".formatted(bin, bin + 4);
                },
                TreeMap::new,
                Collectors.counting()));

        System.out.println("  ► 13.3 Age histogram:");
        histogram.forEach((bin, count) ->
            System.out.println("    %s │%s %d"
                .formatted(bin, "██".repeat(count.intValue()), count)));
    }

    /**
     * Distribution of skill proficiency levels across all people.
     *
     * <p><strong>Key point:</strong> flatMap over skills, then group
     * by proficiency level and count — giving the frequency of each
     * level in the entire dataset.</p>
     */
    static void skillProficiencyDistribution() {
        Map<ProficiencyLevel, Long> distribution = PEOPLE.stream()
            .flatMap(p -> p.getSkills().stream())
            .collect(Collectors.groupingBy(
                Skill::level,
                () -> new TreeMap<>(Comparator.comparingInt(ProficiencyLevel::getWeight)),
                Collectors.counting()));

        System.out.println("  ► 13.4 Skill proficiency distribution:");
        long total = distribution.values().stream().mapToLong(Long::longValue).sum();
        distribution.forEach((level, count) ->
            System.out.println("    %-14s │%s %d (%.0f%%)"
                .formatted(level, "██".repeat(count.intValue()),
                    count, count * 100.0 / total)));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 14 — TEEING FOR DUAL-STATISTIC COMPUTATIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Uses {@code Collectors.teeing} to compute sum and count
     * simultaneously, then derives the average in the merger.
     *
     * <p><strong>Key point:</strong> teeing feeds each element to two
     * collectors in parallel (conceptually), then merges. This avoids
     * two separate stream traversals.</p>
     */
    static void teeingSumAndCount() {
        record SumAndCount(double sum, long count, double average) {
        }

        SumAndCount result = EARNERS.stream()
            .collect(Collectors.teeing(
                Collectors.summingDouble(p -> p.getProfession().salary()),
                Collectors.counting(),
                (sum, count) -> new SumAndCount(sum, count,
                    count == 0 ? 0 : sum / count)));

        printExercise("14.1 Teeing: sum + count → average",
            "sum=£%,.0f, count=%d, avg=£%,.0f"
                .formatted(result.sum, result.count, result.average));
    }

    /**
     * Computes min and max salary in one pass and derives the range.
     *
     * <p><strong>Key point:</strong> instead of calling min() and max()
     * separately (two passes), teeing does it in one.</p>
     */
    static void teeingMinMaxRange() {
        record MinMaxRange(double min, double max, double range) {
        }

        MinMaxRange result = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .collect(Collectors.teeing(
                Collectors.minBy(Double::compareTo),
                Collectors.maxBy(Double::compareTo),
                (min, max) -> new MinMaxRange(
                    min.orElse(0.0), max.orElse(0.0),
                    max.orElse(0.0) - min.orElse(0.0))));

        printExercise("14.2 Teeing: min, max & range",
            "min=£%,.0f, max=£%,.0f, range=£%,.0f"
                .formatted(result.min, result.max, result.range));
    }

    /**
     * Computes both mean and median salary in one logical operation
     * using teeing with two different collection strategies.
     *
     * <p><strong>Key point:</strong> one downstream computes the
     * average, the other collects to a list for median calculation.
     * The merger function calls our helper to find the median.</p>
     */
    static void teeingMeanAndMedian() {
        record MeanMedian(double mean, double median) {
        }

        MeanMedian result = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .collect(Collectors.teeing(
                Collectors.averagingDouble(d -> d),
                Collectors.toList(),
                (mean, list) -> new MeanMedian(mean, computeMedian(list))));

        printExercise("14.3 Teeing: mean & median",
            "mean=£%,.0f, median=£%,.0f, skew=%s"
                .formatted(result.mean, result.median,
                    result.mean > result.median ? "right (mean > median)" :
                        "left (median > mean)"));
    }

    /**
     * Finds both the top earner and lowest earner simultaneously
     * using teeing with maxBy and minBy.
     */
    static void teeingTopAndBottomEarner() {
        record TopBottom(String top, String bottom) {
        }

        TopBottom result = EARNERS.stream()
            .collect(Collectors.teeing(
                Collectors.maxBy(Comparator.comparingDouble(
                    p -> p.getProfession().salary())),
                Collectors.minBy(Comparator.comparingDouble(
                    p -> p.getProfession().salary())),
                (max, min) -> new TopBottom(
                    max.map(p -> "%s (£%,.0f)".formatted(
                        p.getFullName(), p.getProfession().salary())).orElse("N/A"),
                    min.map(p -> "%s (£%,.0f)".formatted(
                        p.getFullName(), p.getProfession().salary())).orElse("N/A"))));

        printExercise("14.4 Teeing: top & bottom earner",
            "Top: %s, Bottom: %s".formatted(result.top, result.bottom));
    }

    /**
     * Uses teeing to count how many people are above vs below the
     * average salary — computed in the same pass that determines the split.
     *
     * <p><strong>Key point:</strong> first collector computes the mean,
     * second collects the full list. The merger then partitions
     * the list using the computed mean.</p>
     */
    static void teeingAboveBelowAverage() {
        record AboveBelow(long above, long below, double avg) {
        }

        AboveBelow result = EARNERS.stream()
            .collect(Collectors.teeing(
                Collectors.averagingDouble(p -> p.getProfession().salary()),
                Collectors.toList(),
                (avg, list) -> {
                    long above = list.stream()
                        .filter(p -> p.getProfession().salary() >= avg)
                        .count();
                    return new AboveBelow(above, list.size() - above, avg);
                }));

        printExercise("14.5 Teeing: above/below average",
            "%d above, %d below avg of £%,.0f"
                .formatted(result.above, result.below, result.avg));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 15 — REAL-WORLD SALARY ANALYTICS DASHBOARD
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Produces a comprehensive salary analytics dashboard combining
     * multiple statistical techniques into one coherent report.
     *
     * <p>This exercise ties together nearly every concept from the
     * preceding sections: summary statistics, grouped aggregations,
     * percentiles, standard deviation, rankings, and distribution
     * analysis — all in a single analytical pipeline.</p>
     */
    static void fullSalaryAnalyticsDashboard() {
        System.out.println("  ► 15.1 FULL SALARY ANALYTICS DASHBOARD");
        System.out.println("  ═══════════════════════════════════════\n");

        // ── Global Statistics ──
        DoubleSummaryStatistics global = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .summaryStatistics();

        List<Double> sortedSalaries = EARNERS.stream()
            .map(p -> p.getProfession().salary())
            .sorted()
            .toList();

        double median = computeMedian(sortedSalaries);
        double variance = EARNERS.stream()
            .mapToDouble(p -> p.getProfession().salary())
            .map(s -> Math.pow(s - global.getAverage(), 2))
            .average().orElse(0);
        double stdDev = Math.sqrt(variance);

        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │           GLOBAL SALARY OVERVIEW         │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  Headcount     : %d".formatted(global.getCount()));
        System.out.println("  │  Total Payroll : £%,.0f".formatted(global.getSum()));
        System.out.println("  │  Mean Salary   : £%,.0f".formatted(global.getAverage()));
        System.out.println("  │  Median Salary : £%,.0f".formatted(median));
        System.out.println("  │  Min Salary    : £%,.0f".formatted(global.getMin()));
        System.out.println("  │  Max Salary    : £%,.0f".formatted(global.getMax()));
        System.out.println("  │  Std Deviation : £%,.0f".formatted(stdDev));
        System.out.println("  │  P25 / P75     : £%,.0f / £%,.0f".formatted(
            computePercentile(sortedSalaries, 25),
            computePercentile(sortedSalaries, 75)));
        System.out.println("  └─────────────────────────────────────────┘\n");

        // ── Per-Company Breakdown ──
        System.out.println(
            "  ┌─────────────────────────────────────────────────────────────────────┐");
        System.out.println(
            "  │                     PER-COMPANY BREAKDOWN                            │");
        System.out.println(
            "  ├──────────────────┬──────┬────────────┬────────────┬─────────────────┤");
        System.out.println(
            "  │ Company          │ Head │ Avg Salary │ Top Earner │ Skill Coverage  │");
        System.out.println(
            "  ├──────────────────┼──────┼────────────┼────────────┼─────────────────┤");

        EARNERS.stream()
            .collect(Collectors.groupingBy(p -> p.getProfession().company()))
            .forEach((company, employees) -> {
                double avg = employees.stream()
                    .mapToDouble(p -> p.getProfession().salary())
                    .average().orElse(0);
                String topEarner = employees.stream()
                    .max(Comparator.comparingDouble(p -> p.getProfession().salary()))
                    .map(Person::getFullName).orElse("N/A");
                long uniqueSkills = employees.stream()
                    .flatMap(p -> p.getSkills().stream())
                    .map(Skill::name).distinct().count();

                System.out.println("  │ %-16s │  %2d  │ £%,8.0f │ %-10s │ %2d unique skills│"
                    .formatted(company, employees.size(), avg,
                        topEarner, uniqueSkills));
            });
        System.out.println(
            "  └──────────────────┴──────┴────────────┴────────────┴─────────────────┘\n");

        // ── Salary Distribution Bar Chart ──
        System.out.println("  SALARY DISTRIBUTION:");
        double binWidth = 20_000;
        EARNERS.stream()
            .collect(Collectors.groupingBy(
                p -> (long) (Math.floor(p.getProfession().salary() / binWidth) * binWidth),
                TreeMap::new,
                Collectors.counting()))
            .forEach((bin, count) ->
                System.out.println("  £%,6d+ │%s %d"
                    .formatted(bin, "████".repeat(count.intValue()), count)));

        // ── Outlier Detection ──
        System.out.println("\n  OUTLIER DETECTION (|z| > 1.5):");
        EARNERS.stream()
            .filter(
                p -> Math.abs((p.getProfession().salary() - global.getAverage()) / stdDev) > 1.5)
            .forEach(p -> {
                double z = (p.getProfession().salary() - global.getAverage()) / stdDev;
                System.out.println("  ⚠ %-20s £%,8.0f  z=%+.2f"
                    .formatted(p.getFullName(), p.getProfession().salary(), z));
            });

        long outlierCount = EARNERS.stream()
            .filter(
                p -> Math.abs((p.getProfession().salary() - global.getAverage()) / stdDev) > 1.5)
            .count();
        if (outlierCount == 0) {
            System.out.println("  ✓ No salary outliers detected.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Computes the median of a list of doubles.
     * For even-sized lists, returns the average of the two middle values.
     */
    private static double computeMedian(List<Double> values) {
        List<Double> sorted = values.stream().sorted().toList();
        int n = sorted.size();
        if (n == 0) {
            return 0;
        }
        if (n % 2 == 1) {
            return sorted.get(n / 2);
        }
        return (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
    }

    /**
     * Computes a given percentile from a pre-sorted list using
     * linear interpolation.
     *
     * @param sorted     the sorted list of values
     * @param percentile the percentile (0–100) to compute
     * @return the interpolated percentile value
     */
    private static double computePercentile(List<Double> sorted, int percentile) {
        if (sorted.isEmpty()) {
            return 0;
        }
        double index = (percentile / 100.0) * (sorted.size() - 1);
        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);
        if (lower == upper) {
            return sorted.get(lower);
        }
        double fraction = index - lower;
        return sorted.get(lower) + fraction * (sorted.get(upper) - sorted.get(lower));
    }

    private static String formatStats(DoubleSummaryStatistics stats, String prefix) {
        return "count=%d, sum=%s%,.0f, min=%s%,.0f, max=%s%,.0f, avg=%s%,.0f"
            .formatted(stats.getCount(),
                prefix, stats.getSum(),
                prefix, stats.getMin(),
                prefix, stats.getMax(),
                prefix, stats.getAverage());
    }

    private static <K> Map<K, String> formatMoney(Map<K, Double> map) {
        Map<K, String> formatted = new LinkedHashMap<>();
        map.entrySet().stream()
            .sorted(Map.Entry.<K, Double>comparingByValue().reversed())
            .forEach(e -> formatted.put(e.getKey(), "£%,.0f".formatted(e.getValue())));
        return formatted;
    }

    private static void printSection(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  SECTION " + title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private static void printExercise(String label, Object result) {
        System.out.println("  ► " + label + ": " + result);
    }
}
