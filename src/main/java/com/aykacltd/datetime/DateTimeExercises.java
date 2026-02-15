package com.aykacltd.datetime;

import data.SampleData;
import model.*;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

/**
 * <h1>Java Date &amp; Time API — Comprehensive Exercise Suite</h1>
 *
 * <p>This class contains <strong>80+</strong> exercises covering the
 * modern {@code java.time} package introduced in Java 8 (JSR-310).
 * Every exercise uses the new date-time classes — the legacy
 * {@code java.util.Date} and {@code java.util.Calendar} are only
 * shown in the interop/conversion section.</p>
 *
 * <h2>Core Classes Covered</h2>
 * <pre>
 *   LocalDate       — date without time or timezone (e.g. 2025-03-15)
 *   LocalTime       — time without date or timezone (e.g. 14:30:00)
 *   LocalDateTime   — date + time without timezone
 *   ZonedDateTime   — date + time + timezone (full civil datetime)
 *   OffsetDateTime  — date + time + UTC offset
 *   Instant         — machine timestamp (epoch seconds + nanos)
 *   Duration        — time-based amount (hours, minutes, seconds, nanos)
 *   Period          — date-based amount (years, months, days)
 *   ZoneId          — timezone identifier (e.g. "Europe/London")
 *   ZoneOffset      — fixed UTC offset (e.g. "+05:30")
 *   DateTimeFormatter — parsing and formatting
 *   TemporalAdjusters — query/manipulate operations
 * </pre>
 *
 * <h2>Table of Contents</h2>
 * <ol>
 *   <li>Section 1  – Creating Date &amp; Time Objects</li>
 *   <li>Section 2  – Parsing Strings into Date/Time</li>
 *   <li>Section 3  – Formatting Date/Time to Strings</li>
 *   <li>Section 4  – Conversions Between Types</li>
 *   <li>Section 5  – Epoch Time &amp; Timestamps</li>
 *   <li>Section 6  – Duration: Time-Based Amounts</li>
 *   <li>Section 7  – Period: Date-Based Amounts</li>
 *   <li>Section 8  – Arithmetic: plus, minus, until</li>
 *   <li>Section 9  – Comparison &amp; Ordering</li>
 *   <li>Section 10 – Timezone Operations (ZonedDateTime)</li>
 *   <li>Section 11 – Geographic / City-Based Timing</li>
 *   <li>Section 12 – Daylight Saving Time (DST) Pitfalls</li>
 *   <li>Section 13 – TemporalAdjusters &amp; Queries</li>
 *   <li>Section 14 – ChronoUnit &amp; Truncation</li>
 *   <li>Section 15 – Legacy Interop (Date, Calendar, SQL)</li>
 *   <li>Section 16 – Common Pitfalls &amp; Gotchas</li>
 *   <li>Section 17 – Date/Time with Streams &amp; the Person Model</li>
 *   <li>Section 18 – Real-World Scenarios</li>
 * </ol>
 *
 * @author Date/Time Exercise Suite
 * @see java.time
 * @see java.time.format.DateTimeFormatter
 */
public class DateTimeExercises {

    private static final List<Person> PEOPLE = SampleData.getPeople();

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  JAVA DATE & TIME API — COMPREHENSIVE EXERCISES");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        printSection("1 — CREATING DATE & TIME OBJECTS");
        creatingLocalDate();
        creatingLocalTime();
        creatingLocalDateTime();
        creatingZonedDateTime();
        creatingOffsetDateTime();
        creatingInstant();
        creatingFromFields();

        printSection("2 — PARSING STRINGS INTO DATE/TIME");
        parseIsoFormats();
        parseCustomFormats();
        parseLenientAndStrict();
        parseWithOptionalParts();
        parseEdgeCases();

        printSection("3 — FORMATTING DATE/TIME TO STRINGS");
        formatWithPredefinedFormatters();
        formatWithCustomPatterns();
        formatLocalised();
        formatToIso8601();

        printSection("4 — CONVERSIONS BETWEEN TYPES");
        localDateToLocalDateTime();
        localDateTimeToZonedDateTime();
        zonedDateTimeToInstant();
        instantToZonedDateTime();
        localDateTimeToOffsetDateTime();
        offsetDateTimeToZonedDateTime();
        conversionChainDemo();

        printSection("5 — EPOCH TIME & TIMESTAMPS");
        epochSecondsAndMillis();
        instantFromEpoch();
        epochConversionsRoundTrip();
        unixTimestampUseCases();
        nanoPrecision();

        printSection("6 — DURATION: TIME-BASED AMOUNTS");
        creatingDurations();
        durationArithmetic();
        durationBetween();
        durationToUnits();
        durationComparison();

        printSection("7 — PERIOD: DATE-BASED AMOUNTS");
        creatingPeriods();
        periodArithmetic();
        periodBetween();
        periodNormalisation();
        periodVsDuration();

        printSection("8 — ARITHMETIC: PLUS, MINUS, UNTIL");
        addAndSubtract();
        addPeriodAndDuration();
        untilCalculation();
        withMethodsForSetting();

        printSection("9 — COMPARISON & ORDERING");
        compareLocalDates();
        compareZonedDateTimesAcrossZones();
        sortingDates();
        minMaxDate();

        printSection("10 — TIMEZONE OPERATIONS");
        listAvailableZones();
        convertBetweenZones();
        withZoneSameInstantVsSameLocal();
        utcNormalization();
        currentTimeMultipleZones();

        printSection("11 — GEOGRAPHIC / CITY-BASED TIMING");
        cityTimeComparison();
        meetingSchedulerAcrossZones();
        internationalDateLine();
        halfHourAndQuarterHourOffsets();

        printSection("12 — DAYLIGHT SAVING TIME PITFALLS");
        dstSpringForwardGap();
        dstFallBackOverlap();
        durationAcrossDstBoundary();
        dstSafeScheduling();

        printSection("13 — TEMPORAL ADJUSTERS & QUERIES");
        builtInAdjusters();
        customAdjuster();
        temporalQueries();
        businessDayCalculations();

        printSection("14 — CHRONOUNIT & TRUNCATION");
        chronoUnitBetween();
        truncation();
        supportedUnits();

        printSection("15 — LEGACY INTEROP (Date, Calendar, SQL)");
        convertToFromJavaUtilDate();
        convertToFromCalendar();
        convertToFromSqlTypes();

        printSection("16 — COMMON PITFALLS & GOTCHAS");
        pitfallImmutability();
        pitfallEqualsVsIsEqual();
        pitfallMonthIndexing();
        pitfallParsingAmbiguity();
        pitfallLocalDateTimeHasNoZone();
        pitfallDurationVsPeriod();
        pitfallDstArithmetic();
        pitfallEpochAssumptions();
        pitfallToStringFormats();
        pitfallYearOf();

        printSection("17 — DATE/TIME WITH STREAMS & PERSON MODEL");
        personsGroupedByBirthMonth();
        personsGroupedByAgeDecade();
        averageAgeInDays();
        oldestAndYoungestPerson();
        birthdayThisMonth();
        daysUntilNextBirthday();
        dateOfBirthStatistics();

        printSection("18 — REAL-WORLD SCENARIOS");
        calculateAge();
        findNextBusinessDay();
        isWeekendCheck();
        generateDateRange();
        calculateWorkingDaysBetween();
        formatForApiResponse();
        calculateSlaDeadline();
        timeZoneAwareAuditLog();

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  ALL DATE/TIME EXERCISES COMPLETED SUCCESSFULLY");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 1 — CREATING DATE & TIME OBJECTS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Creating {@link LocalDate} instances — a date without time or zone.
     *
     * <p><strong>Key point:</strong> {@code LocalDate} stores only year,
     * month, and day. It has NO time component and NO timezone. Use it
     * when you care about the calendar date itself (birthdays, holidays,
     * due dates).</p>
     */
    static void creatingLocalDate() {
        LocalDate today = LocalDate.now();
        LocalDate specific = LocalDate.of(2025, 6, 15);
        LocalDate fromMonth = LocalDate.of(2025, Month.MARCH, 1);
        LocalDate parsed = LocalDate.parse("2025-12-25");
        LocalDate epochDay = LocalDate.ofEpochDay(0);               // 1970-01-01
        LocalDate yearDay = LocalDate.ofYearDay(2025, 100);         // 100th day of 2025

        printExercise("1.1 LocalDate.now()", today);
        printExercise("    LocalDate.of(2025, 6, 15)", specific);
        printExercise("    Using Month enum", fromMonth);
        printExercise("    Parsed from ISO string", parsed);
        printExercise("    Epoch day 0", epochDay);
        printExercise("    100th day of 2025", yearDay);
    }

    /**
     * Creating {@link LocalTime} instances — a time without date or zone.
     *
     * <p><strong>Key point:</strong> {@code LocalTime} stores hour, minute,
     * second, and nanosecond. It represents a time of day (e.g. 14:30)
     * without any date or timezone context. Useful for opening hours,
     * alarm times, daily schedules.</p>
     */
    static void creatingLocalTime() {
        LocalTime now = LocalTime.now();
        LocalTime hourMin = LocalTime.of(14, 30);
        LocalTime hourMinSec = LocalTime.of(14, 30, 45);
        LocalTime withNanos = LocalTime.of(14, 30, 45, 123_456_789);
        LocalTime noon = LocalTime.NOON;
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime max = LocalTime.MAX;  // 23:59:59.999999999
        LocalTime parsed = LocalTime.parse("09:15:30");

        printExercise("1.2 LocalTime.now()", now);
        printExercise("    of(14, 30)", hourMin);
        printExercise("    of(14, 30, 45)", hourMinSec);
        printExercise("    with nanoseconds", withNanos);
        printExercise("    NOON", noon);
        printExercise("    MIDNIGHT", midnight);
        printExercise("    MAX", max);
        printExercise("    parsed", parsed);
    }

    /**
     * Creating {@link LocalDateTime} — date + time without timezone.
     *
     * <p><strong>Key point:</strong> {@code LocalDateTime} is the combination
     * of {@code LocalDate} and {@code LocalTime}. It still has NO timezone,
     * meaning "2025-06-15T14:30" is ambiguous — it could be 14:30 in
     * London, Tokyo, or New York. Use it when the timezone is implied
     * or irrelevant (UI display, local events).</p>
     */
    static void creatingLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime specific = LocalDateTime.of(2025, 6, 15, 14, 30);
        LocalDateTime fromParts = LocalDateTime.of(
                LocalDate.of(2025, 6, 15),
                LocalTime.of(14, 30));
        LocalDateTime parsed = LocalDateTime.parse("2025-06-15T14:30:00");

        printExercise("1.3 LocalDateTime.now()", now);
        printExercise("    of(2025, 6, 15, 14, 30)", specific);
        printExercise("    from LocalDate + LocalTime", fromParts);
        printExercise("    parsed ISO", parsed);
    }

    /**
     * Creating {@link ZonedDateTime} — the full civil date-time with timezone.
     *
     * <p><strong>Key point:</strong> {@code ZonedDateTime} is the most complete
     * date-time class. It stores date + time + timezone, and correctly
     * handles DST transitions. Use it when the timezone matters
     * (scheduling meetings, flight times, log timestamps).</p>
     */
    static void creatingZonedDateTime() {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime nowLondon = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime specific = ZonedDateTime.of(
                2025, 6, 15, 14, 30, 0, 0,
                ZoneId.of("Europe/London"));
        ZonedDateTime fromLdt = LocalDateTime.of(2025, 6, 15, 14, 30)
                .atZone(ZoneId.of("Asia/Tokyo"));

        printExercise("1.4 ZonedDateTime.now(UTC)", nowUtc);
        printExercise("    now(Europe/London)", nowLondon);
        printExercise("    specific", specific);
        printExercise("    from LocalDateTime.atZone(Tokyo)", fromLdt);
    }

    /**
     * Creating {@link OffsetDateTime} — date + time + UTC offset.
     *
     * <p><strong>Key point:</strong> Unlike {@code ZonedDateTime}, this
     * uses a fixed UTC offset (e.g. +05:30) rather than a timezone ID.
     * It does NOT handle DST. Use it for database storage, APIs, and
     * serialisation where you need an unambiguous point in time without
     * the complexity of timezone rules.</p>
     */
    static void creatingOffsetDateTime() {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime specific = OffsetDateTime.of(
                2025, 6, 15, 14, 30, 0, 0,
                ZoneOffset.ofHours(5));
        OffsetDateTime parsed = OffsetDateTime.parse("2025-06-15T14:30:00+01:00");

        printExercise("1.5 OffsetDateTime.now()", now);
        printExercise("    now(UTC)", utc);
        printExercise("    of(..., +05:00)", specific);
        printExercise("    parsed with offset", parsed);
    }

    /**
     * Creating {@link Instant} — a machine timestamp on the UTC timeline.
     *
     * <p><strong>Key point:</strong> {@code Instant} represents a single
     * point on the universal timeline, stored as seconds + nanoseconds
     * from the Unix epoch (1970-01-01T00:00:00Z). It has NO timezone
     * and NO calendar concept. Use it for timestamps, measuring elapsed
     * time, and interoperating with legacy {@code java.util.Date}.</p>
     */
    static void creatingInstant() {
        Instant now = Instant.now();
        Instant epoch = Instant.EPOCH;                          // 1970-01-01T00:00:00Z
        Instant fromEpochSec = Instant.ofEpochSecond(1_700_000_000L);
        Instant fromEpochMilli = Instant.ofEpochMilli(1_700_000_000_000L);
        Instant parsed = Instant.parse("2025-06-15T14:30:00Z");

        printExercise("1.6 Instant.now()", now);
        printExercise("    EPOCH", epoch);
        printExercise("    ofEpochSecond(1_700_000_000)", fromEpochSec);
        printExercise("    ofEpochMilli(1_700_000_000_000)", fromEpochMilli);
        printExercise("    parsed UTC string", parsed);
    }

    /**
     * Creating dates from individual field values using
     * {@code of()} factory methods.
     *
     * <p><strong>Key point:</strong> Always prefer the {@link Month} enum
     * over raw integers to avoid off-by-one errors. January is
     * {@code Month.JANUARY} (value 1), not 0 like in the legacy API.</p>
     */
    static void creatingFromFields() {
        // Month enum avoids the classic off-by-one error
        LocalDate withEnum = LocalDate.of(2025, Month.JANUARY, 15);
        LocalDate withInt = LocalDate.of(2025, 1, 15);

        // Year and YearMonth for partial dates
        Year year = Year.of(2025);
        YearMonth yearMonth = YearMonth.of(2025, Month.JUNE);
        MonthDay birthday = MonthDay.of(Month.MARCH, 15);

        printExercise("1.7 Using Month enum", withEnum);
        printExercise("    Using int month (same result)", withInt);
        printExercise("    Year", year);
        printExercise("    YearMonth", yearMonth);
        printExercise("    MonthDay (e.g. birthday)", birthday);
        printExercise("    Is this a leap year?", year.isLeap());
        printExercise("    Days in June 2025", yearMonth.lengthOfMonth());
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 2 — PARSING STRINGS INTO DATE/TIME
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Parsing ISO-8601 formatted strings using the default parsers.
     *
     * <p><strong>Key point:</strong> All {@code java.time} classes have
     * a {@code parse(CharSequence)} method that accepts ISO-8601 format
     * by default. No formatter needed for standard formats.</p>
     */
    static void parseIsoFormats() {
        LocalDate date = LocalDate.parse("2025-06-15");
        LocalTime time = LocalTime.parse("14:30:45");
        LocalDateTime dateTime = LocalDateTime.parse("2025-06-15T14:30:45");
        ZonedDateTime zoned = ZonedDateTime.parse("2025-06-15T14:30:45+01:00[Europe/London]");
        OffsetDateTime offset = OffsetDateTime.parse("2025-06-15T14:30:45+05:30");
        Instant instant = Instant.parse("2025-06-15T14:30:45Z");

        printExercise("2.1 Parse ISO LocalDate", date);
        printExercise("    Parse ISO LocalTime", time);
        printExercise("    Parse ISO LocalDateTime", dateTime);
        printExercise("    Parse ISO ZonedDateTime", zoned);
        printExercise("    Parse ISO OffsetDateTime", offset);
        printExercise("    Parse ISO Instant", instant);
    }

    /**
     * Parsing non-standard date/time strings using custom
     * {@link DateTimeFormatter} patterns.
     *
     * <p><strong>Key point:</strong> Use {@code DateTimeFormatter.ofPattern()}
     * for custom formats. Common pattern letters:</p>
     * <pre>
     *   y = year     M = month      d = day
     *   H = hour(24) h = hour(12)   m = minute    s = second
     *   a = AM/PM    E = day name   MMMM = full month name
     *   VV = zone ID z = zone name  X/x = offset
     * </pre>
     */
    static void parseCustomFormats() {
        // UK date format: dd/MM/yyyy
        DateTimeFormatter ukFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ukDate = LocalDate.parse("15/06/2025", ukFormat);

        // US date format: MM-dd-yyyy
        DateTimeFormatter usFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate usDate = LocalDate.parse("06-15-2025", usFormat);

        // Full date-time with AM/PM
        DateTimeFormatter amPmFormat = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
        LocalDateTime amPmTime = LocalDateTime.parse("15 Jun 2025 02:30 PM", amPmFormat);

        // European verbose format
        DateTimeFormatter verbose = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.UK);
        LocalDate verboseDate = LocalDate.parse("Sunday, 15 June 2025", verbose);

        // Date-time with timezone
        DateTimeFormatter withZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV");
        ZonedDateTime zonedParsed = ZonedDateTime.parse("2025-06-15 14:30:00 Europe/London", withZone);

        printExercise("2.2 UK format dd/MM/yyyy", ukDate);
        printExercise("    US format MM-dd-yyyy", usDate);
        printExercise("    AM/PM format", amPmTime);
        printExercise("    Verbose UK format", verboseDate);
        printExercise("    With timezone", zonedParsed);
    }

    /**
     * Demonstrates strict vs lenient parsing with
     * {@link ResolverStyle}.
     *
     * <p><strong>Key point:</strong></p>
     * <ul>
     *   <li>{@code STRICT} — all fields must be valid; Feb 29 on non-leap year throws</li>
     *   <li>{@code SMART} (default) — some leniency; e.g. Feb 30 → Feb 28/29</li>
     *   <li>{@code LENIENT} — overflows roll over; e.g. month 13 → Jan next year</li>
     * </ul>
     */
    static void parseLenientAndStrict() {
        DateTimeFormatter strict = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        DateTimeFormatter smart = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.SMART);
        DateTimeFormatter lenient = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.LENIENT);

        // Strict rejects invalid dates
        try {
            // Note: STRICT mode requires 'uuuu' (proleptic year) not 'yyyy' (era year)
            DateTimeFormatter strictProleptic = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse("2025-02-29", strictProleptic);
            printExercise("2.3 STRICT Feb 29 2025", "accepted (unexpected)");
        } catch (DateTimeParseException e) {
            printExercise("2.3 STRICT Feb 29 2025", "REJECTED: " + e.getMessage());
        }

        // Smart silently adjusts
        LocalDate smartDate = LocalDate.parse("2025-02-29", smart);
        printExercise("    SMART Feb 29 2025", smartDate + " (adjusted to last day)");

        // Lenient rolls over
        LocalDate lenientDate = LocalDate.parse("2025-02-30", lenient);
        printExercise("    LENIENT Feb 30 2025", lenientDate + " (rolled over)");
    }

    /**
     * Parsing with optional sections using square brackets in patterns.
     *
     * <p><strong>Key point:</strong> Wrap optional parts in {@code [...]}.
     * This is extremely useful when parsing dates that may or may not
     * have time or timezone components.</p>
     */
    static void parseWithOptionalParts() {
        DateTimeFormatter flexible = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd['T'HH:mm:ss[.SSS]][' 'HH:mm]");

        LocalDateTime withTime = LocalDateTime.parse("2025-06-15T14:30:00", flexible);
        LocalDateTime withMillis = LocalDateTime.parse("2025-06-15T14:30:00.123", flexible);
        // Date-only parses to midnight via default
        DateTimeFormatter dateOptionalTime = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart()
                .appendPattern("'T'HH:mm:ss")
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        LocalDateTime dateOnly = LocalDateTime.parse("2025-06-15", dateOptionalTime);
        LocalDateTime full = LocalDateTime.parse("2025-06-15T14:30:00", dateOptionalTime);

        printExercise("2.4 Optional time", withTime);
        printExercise("    Optional millis", withMillis);
        printExercise("    Date-only with defaults", dateOnly);
        printExercise("    Full with optional time", full);
    }

    /**
     * Parsing edge cases: two-digit years, single-digit months,
     * case-insensitive parsing.
     *
     * <p><strong>Key point:</strong> {@code DateTimeFormatterBuilder}
     * gives fine-grained control over parsing behaviour including
     * case sensitivity, default values, and optional sections.</p>
     */
    static void parseEdgeCases() {
        // Case-insensitive parsing (e.g. "jun" instead of "Jun")
        DateTimeFormatter caseInsensitive = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd-MMM-yyyy")
                .toFormatter(Locale.UK);
        LocalDate caseDate = LocalDate.parse("15-jun-2025", caseInsensitive);

        // Padded vs unpadded: single-digit day/month
        DateTimeFormatter unpadded = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate unpaddedDate = LocalDate.parse("5/3/2025", unpadded);

        printExercise("2.5 Case-insensitive 'jun'", caseDate);
        printExercise("    Unpadded d/M/yyyy", unpaddedDate);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 3 — FORMATTING DATE/TIME TO STRINGS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Formatting using built-in predefined formatters.
     *
     * <p><strong>Key point:</strong> {@link DateTimeFormatter} provides
     * many ISO and RFC constants. These are thread-safe singletons
     * and should be reused.</p>
     */
    static void formatWithPredefinedFormatters() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 30, 45);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/London"));
        LocalDate date = ldt.toLocalDate();

        printExercise("3.1 ISO_LOCAL_DATE", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        printExercise("    ISO_LOCAL_DATE_TIME", ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        printExercise("    ISO_ZONED_DATE_TIME", zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        printExercise("    ISO_OFFSET_DATE_TIME", zdt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        printExercise("    ISO_INSTANT", zdt.format(DateTimeFormatter.ISO_INSTANT));
        printExercise("    ISO_ORDINAL_DATE", date.format(DateTimeFormatter.ISO_ORDINAL_DATE));
        printExercise("    BASIC_ISO_DATE", date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    /**
     * Formatting with custom patterns.
     *
     * <p><strong>Key point:</strong> Custom patterns use the same letters
     * as parsing. The number of pattern letters controls the output:</p>
     * <pre>
     *   M    → 6          (minimum digits)
     *   MM   → 06         (zero-padded)
     *   MMM  → Jun        (short text)
     *   MMMM → June       (full text)
     * </pre>
     */
    static void formatWithCustomPatterns() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 30, 45);

        printExercise("3.2 dd/MM/yyyy",
                ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        printExercise("    MM-dd-yyyy HH:mm",
                ldt.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")));
        printExercise("    d MMM yyyy",
                ldt.format(DateTimeFormatter.ofPattern("d MMM yyyy")));
        printExercise("    EEEE, d MMMM yyyy",
                ldt.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.UK)));
        printExercise("    hh:mm a (12-hour)",
                ldt.format(DateTimeFormatter.ofPattern("hh:mm a")));
        printExercise("    HH:mm:ss (24-hour)",
                ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        printExercise("    yyyy-MM-dd'T'HH:mm:ss",
                ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    }

    /**
     * Locale-specific formatting using localised styles.
     *
     * <p><strong>Key point:</strong> {@code DateTimeFormatter.ofLocalizedDate/Time()}
     * produces culture-appropriate output. Always pass a {@link Locale}
     * to control the output language and conventions.</p>
     */
    static void formatLocalised() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 30, 45);

        printExercise("3.3 UK FULL",
                ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                        .withLocale(Locale.UK)));
        printExercise("    UK MEDIUM",
                ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.UK)));
        printExercise("    US MEDIUM",
                ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.US)));
        printExercise("    France LONG",
                ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                        .withLocale(Locale.FRANCE)));
        printExercise("    Germany SHORT",
                ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMANY)));
        printExercise("    Japan MEDIUM",
                ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.JAPAN)));
    }

    /**
     * Formatting to ISO-8601 standard strings for API communication.
     *
     * <p><strong>Key point:</strong> For REST APIs and data interchange,
     * always use ISO-8601 with explicit timezone. The 'Z' suffix
     * means UTC. Use {@code Instant.toString()} or
     * {@code DateTimeFormatter.ISO_INSTANT} for this.</p>
     */
    static void formatToIso8601() {
        Instant now = Instant.parse("2025-06-15T14:30:45Z");
        ZonedDateTime zdt = now.atZone(ZoneId.of("Europe/London"));
        OffsetDateTime odt = now.atOffset(ZoneOffset.ofHours(5));

        printExercise("3.4 Instant.toString() (always UTC/Z)", now.toString());
        printExercise("    ZonedDateTime ISO", zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        printExercise("    OffsetDateTime ISO", odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        printExercise("    For APIs (ISO_INSTANT)", zdt.format(DateTimeFormatter.ISO_INSTANT));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 4 — CONVERSIONS BETWEEN TYPES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Converting LocalDate → LocalDateTime by adding a time component.
     *
     * <p><strong>Key point:</strong> A date alone is not a date-time.
     * You must explicitly provide the time with {@code atTime()} or
     * {@code atStartOfDay()}.</p>
     */
    static void localDateToLocalDateTime() {
        LocalDate date = LocalDate.of(2025, 6, 15);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime atNoon = date.atTime(12, 0);
        LocalDateTime atSpecific = date.atTime(LocalTime.of(14, 30));

        printExercise("4.1 LocalDate → LocalDateTime (start of day)", startOfDay);
        printExercise("    atTime(12, 0)", atNoon);
        printExercise("    atTime(LocalTime)", atSpecific);
    }

    /**
     * Converting LocalDateTime → ZonedDateTime by assigning a timezone.
     *
     * <p><strong>Key point:</strong> {@code atZone()} declares "this local
     * time IS in this timezone". It does not convert — it labels.
     * If the local time falls in a DST gap, the time is adjusted forward.</p>
     */
    static void localDateTimeToZonedDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 30);
        ZonedDateTime london = ldt.atZone(ZoneId.of("Europe/London"));
        ZonedDateTime tokyo = ldt.atZone(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime newYork = ldt.atZone(ZoneId.of("America/New_York"));

        printExercise("4.2 LDT.atZone(London)", london);
        printExercise("    LDT.atZone(Tokyo)", tokyo);
        printExercise("    LDT.atZone(NewYork)", newYork);
        printExercise("    ⚠ Same local time, DIFFERENT instants!",
                "London=%s, Tokyo=%s".formatted(
                        london.toInstant(), tokyo.toInstant()));
    }

    /**
     * Converting ZonedDateTime → Instant (dropping the timezone).
     *
     * <p><strong>Key point:</strong> {@code toInstant()} extracts the
     * absolute point in time (UTC). The timezone information is lost.
     * Two ZonedDateTimes at different zones but the same instant
     * produce equal Instants.</p>
     */
    static void zonedDateTimeToInstant() {
        ZonedDateTime zdt = ZonedDateTime.of(2025, 6, 15, 14, 30, 0, 0,
                ZoneId.of("Europe/London"));
        Instant instant = zdt.toInstant();

        printExercise("4.3 ZonedDateTime → Instant", instant);
        printExercise("    Epoch seconds", instant.getEpochSecond());
    }

    /**
     * Converting Instant → ZonedDateTime by applying a timezone.
     *
     * <p><strong>Key point:</strong> An Instant is timezone-agnostic.
     * Applying different zones to the same Instant gives different
     * wall-clock times but the SAME point in time.</p>
     */
    static void instantToZonedDateTime() {
        Instant instant = Instant.parse("2025-06-15T13:30:00Z");
        ZonedDateTime london = instant.atZone(ZoneId.of("Europe/London"));
        ZonedDateTime tokyo = instant.atZone(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime newYork = instant.atZone(ZoneId.of("America/New_York"));

        printExercise("4.4 Same Instant in different zones:");
        printExercise("    UTC", instant);
        printExercise("    London", london);
        printExercise("    Tokyo", tokyo);
        printExercise("    New York", newYork);
    }

    /**
     * Converting LocalDateTime → OffsetDateTime.
     *
     * <p><strong>Key point:</strong> You must provide the offset manually.
     * Unlike {@code atZone()}, offsets don't know about DST rules.</p>
     */
    static void localDateTimeToOffsetDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 30);
        OffsetDateTime odt = ldt.atOffset(ZoneOffset.ofHours(1));

        printExercise("4.5 LDT → OffsetDateTime (+01:00)", odt);
    }

    /**
     * Converting OffsetDateTime → ZonedDateTime and vice versa.
     *
     * <p><strong>Key point:</strong> {@code toZonedDateTime()} on
     * OffsetDateTime creates a ZDT with a fixed-offset zone.
     * To get a proper named zone, use {@code atZoneSameInstant()}
     * or {@code atZoneSimilarLocal()}.</p>
     */
    static void offsetDateTimeToZonedDateTime() {
        OffsetDateTime odt = OffsetDateTime.of(2025, 6, 15, 14, 30, 0, 0,
                ZoneOffset.ofHours(1));
        ZonedDateTime zdtFixed = odt.toZonedDateTime();
        ZonedDateTime zdtNamed = odt.atZoneSameInstant(ZoneId.of("Europe/London"));

        // Reverse: ZDT → ODT
        ZonedDateTime zdtOriginal = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        OffsetDateTime fromZdt = zdtOriginal.toOffsetDateTime();

        printExercise("4.6 ODT → ZDT (fixed offset zone)", zdtFixed);
        printExercise("    ODT → ZDT (named zone)", zdtNamed);
        printExercise("    ZDT → ODT", fromZdt);
    }

    /**
     * A full conversion chain demonstrating the relationships
     * between all the date-time types.
     *
     * <p><strong>Conversion map:</strong></p>
     * <pre>
     *   LocalDate  ──atTime()──→  LocalDateTime  ──atZone()──→  ZonedDateTime
     *                                    │                            │
     *                              atOffset()                   toInstant()
     *                                    │                            │
     *                                    ▼                            ▼
     *                            OffsetDateTime  ◄──atOffset()──  Instant
     * </pre>
     */
    static void conversionChainDemo() {
        // Start from a LocalDate and walk through every conversion
        LocalDate date = LocalDate.of(2025, 6, 15);

        LocalDateTime ldt = date.atTime(14, 30);                              // + time
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/London"));           // + zone
        Instant instant = zdt.toInstant();                                     // → UTC point
        OffsetDateTime odt = instant.atOffset(ZoneOffset.ofHours(5));          // + offset
        ZonedDateTime backToZdt = odt.atZoneSameInstant(ZoneId.of("Asia/Tokyo")); // → named zone
        LocalDateTime backToLdt = backToZdt.toLocalDateTime();                 // drop zone
        LocalDate backToDate = backToLdt.toLocalDate();                        // drop time
        LocalTime extractedTime = backToLdt.toLocalTime();                     // extract time

        System.out.println("  ► 4.7 Full conversion chain:");
        System.out.println("    LocalDate      : " + date);
        System.out.println("    → LocalDateTime : " + ldt);
        System.out.println("    → ZonedDateTime : " + zdt);
        System.out.println("    → Instant       : " + instant);
        System.out.println("    → OffsetDateTime: " + odt);
        System.out.println("    → ZDT (Tokyo)   : " + backToZdt);
        System.out.println("    → LocalDateTime : " + backToLdt);
        System.out.println("    → LocalDate     : " + backToDate);
        System.out.println("    → LocalTime     : " + extractedTime);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 5 — EPOCH TIME & TIMESTAMPS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Working with epoch seconds and milliseconds.
     *
     * <p><strong>Key point:</strong> The Unix epoch is 1970-01-01T00:00:00Z.
     * Epoch seconds = seconds since epoch (what most systems use).
     * Epoch millis = milliseconds since epoch (what {@code java.util.Date} uses).
     * These are always in UTC — they have no timezone.</p>
     */
    static void epochSecondsAndMillis() {
        Instant now = Instant.now();
        long epochSec = now.getEpochSecond();
        long epochMilli = now.toEpochMilli();
        int nano = now.getNano();  // nanoseconds within the second

        printExercise("5.1 Current epoch seconds", epochSec);
        printExercise("    Current epoch millis", epochMilli);
        printExercise("    Nano within second", nano);
        printExercise("    Relationship", "millis = seconds * 1000 + nano / 1_000_000");

        // LocalDate also has epoch day
        LocalDate today = LocalDate.now();
        long epochDay = today.toEpochDay();
        printExercise("    Epoch day (days since 1970-01-01)", epochDay);
    }

    /**
     * Creating Instants from epoch values.
     *
     * <p><strong>Key point:</strong> Use {@code ofEpochSecond} for Unix
     * timestamps, {@code ofEpochMilli} for Java/JavaScript timestamps.</p>
     */
    static void instantFromEpoch() {
        Instant fromSec = Instant.ofEpochSecond(1_700_000_000L);
        Instant fromMilli = Instant.ofEpochMilli(1_700_000_000_000L);
        Instant fromSecAndNano = Instant.ofEpochSecond(1_700_000_000L, 500_000_000);
        Instant negative = Instant.ofEpochSecond(-86400);  // day before epoch

        printExercise("5.2 From epoch seconds", fromSec);
        printExercise("    From epoch millis", fromMilli);
        printExercise("    From sec + 500ms nano", fromSecAndNano);
        printExercise("    Negative epoch (1969-12-31)", negative);
    }

    /**
     * Round-trip: Instant → epoch → Instant, proving lossless conversion.
     */
    static void epochConversionsRoundTrip() {
        Instant original = Instant.parse("2025-06-15T14:30:45.123456789Z");
        long epochSec = original.getEpochSecond();
        int nano = original.getNano();
        Instant reconstructed = Instant.ofEpochSecond(epochSec, nano);

        printExercise("5.3 Round-trip: original", original);
        printExercise("    Reconstructed", reconstructed);
        printExercise("    Equal?", original.equals(reconstructed));
        printExercise("    ⚠ Millis loses nano precision",
                "original nano=%d, millis-based nano=%d"
                        .formatted(nano, Instant.ofEpochMilli(original.toEpochMilli()).getNano()));
    }

    /**
     * Common epoch/timestamp use cases in backend systems.
     */
    static void unixTimestampUseCases() {
        // Converting a Unix timestamp from an external API
        long apiTimestamp = 1750000000L;
        Instant apiTime = Instant.ofEpochSecond(apiTimestamp);
        ZonedDateTime userTime = apiTime.atZone(ZoneId.of("Europe/London"));

        // Generating a timestamp for a database record
        long dbTimestamp = Instant.now().toEpochMilli();

        // Comparing timestamps
        Instant event1 = Instant.ofEpochSecond(1_700_000_000L);
        Instant event2 = Instant.ofEpochSecond(1_700_100_000L);
        long diffSeconds = event2.getEpochSecond() - event1.getEpochSecond();

        printExercise("5.4 API timestamp → user time", userTime);
        printExercise("    DB timestamp (millis)", dbTimestamp);
        printExercise("    Time between events", "%d seconds (%.1f hours)".formatted(
                diffSeconds, diffSeconds / 3600.0));
    }

    /**
     * Nanosecond precision available in {@code Instant}.
     *
     * <p><strong>Key point:</strong> {@code Instant} supports nanosecond
     * precision, but not all clocks provide it. {@code Instant.now()}
     * precision depends on the OS — typically microseconds on Linux,
     * milliseconds on older Windows.</p>
     */
    static void nanoPrecision() {
        Instant precise = Instant.ofEpochSecond(0, 123_456_789);
        printExercise("5.5 Nanosecond precision", precise);
        printExercise("    getNano()", precise.getNano());
        printExercise("    Clock precision demo",
                "Two Instant.now() calls: %s, %s".formatted(Instant.now(), Instant.now()));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 6 — DURATION: TIME-BASED AMOUNTS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Creating {@link Duration} objects — amounts of time in
     * hours, minutes, seconds, and nanoseconds.
     *
     * <p><strong>Key point:</strong> {@code Duration} measures time-based
     * amounts. It is stored internally as seconds + nanoseconds.
     * It does NOT understand calendar concepts like months or years
     * (use {@link Period} for those).</p>
     */
    static void creatingDurations() {
        Duration ofHours = Duration.ofHours(2);
        Duration ofMinutes = Duration.ofMinutes(90);
        Duration ofSeconds = Duration.ofSeconds(3661);
        Duration ofMillis = Duration.ofMillis(1500);
        Duration ofNanos = Duration.ofNanos(1_000_000_000);
        Duration parsed = Duration.parse("PT2H30M");  // ISO-8601 duration
        Duration complex = Duration.ofHours(1).plusMinutes(30).plusSeconds(45);

        printExercise("6.1 2 hours", ofHours);
        printExercise("    90 minutes", ofMinutes);
        printExercise("    3661 seconds", ofSeconds);
        printExercise("    1500 millis", ofMillis);
        printExercise("    1 billion nanos", ofNanos);
        printExercise("    Parsed 'PT2H30M'", parsed);
        printExercise("    1h 30m 45s", complex);
    }

    /**
     * Arithmetic with Duration: adding, subtracting, multiplying.
     */
    static void durationArithmetic() {
        Duration d1 = Duration.ofHours(2);
        Duration d2 = Duration.ofMinutes(45);

        Duration sum = d1.plus(d2);
        Duration diff = d1.minus(d2);
        Duration doubled = d1.multipliedBy(2);
        Duration halved = d1.dividedBy(2);
        Duration negated = d1.negated();
        Duration abs = negated.abs();

        printExercise("6.2 2h + 45m", sum);
        printExercise("    2h - 45m", diff);
        printExercise("    2h × 2", doubled);
        printExercise("    2h ÷ 2", halved);
        printExercise("    negated", negated);
        printExercise("    abs of negated", abs);
    }

    /**
     * Computing Duration between two temporal objects.
     */
    static void durationBetween() {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 30);
        Duration workDay = Duration.between(start, end);

        Instant i1 = Instant.parse("2025-06-15T09:00:00Z");
        Instant i2 = Instant.parse("2025-06-15T17:30:00Z");
        Duration betweenInstants = Duration.between(i1, i2);

        printExercise("6.3 Work day duration", workDay);
        printExercise("    Between instants", betweenInstants);
        printExercise("    In hours", workDay.toHours());
        printExercise("    In minutes", workDay.toMinutes());
    }

    /**
     * Converting Duration to various units.
     *
     * <p><strong>Key point:</strong> {@code toHours()}, {@code toMinutes()},
     * {@code toSeconds()} give the TOTAL in that unit.
     * {@code toHoursPart()}, {@code toMinutesPart()} etc. give the
     * REMAINING part after extracting larger units (Java 9+).</p>
     */
    static void durationToUnits() {
        Duration d = Duration.ofSeconds(90061);  // 25h 1m 1s

        printExercise("6.4 Duration: 90061 seconds");
        printExercise("    Total hours", d.toHours());
        printExercise("    Total minutes", d.toMinutes());
        printExercise("    Total seconds", d.toSeconds());
        printExercise("    Parts: %dh %dm %ds".formatted(
                d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart()), "");
        printExercise("    toDays()", d.toDays());
        printExercise("    toDaysPart()", d.toDaysPart());
    }

    /**
     * Comparing Durations.
     */
    static void durationComparison() {
        Duration d1 = Duration.ofHours(2);
        Duration d2 = Duration.ofMinutes(90);

        printExercise("6.5 2h vs 90m: compareTo", d1.compareTo(d2) > 0 ? "2h is longer" : "90m is longer");
        printExercise("    isZero?", Duration.ZERO.isZero());
        printExercise("    isNegative?", d1.negated().isNegative());
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 7 — PERIOD: DATE-BASED AMOUNTS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Creating {@link Period} objects — amounts of date-based time
     * in years, months, and days.
     *
     * <p><strong>Key point:</strong> {@code Period} works with calendar
     * concepts. "1 month" added to Jan 31 gives Feb 28 (or 29).
     * {@code Duration} cannot represent months/years because their
     * length varies.</p>
     */
    static void creatingPeriods() {
        Period ofYears = Period.ofYears(1);
        Period ofMonths = Period.ofMonths(6);
        Period ofWeeks = Period.ofWeeks(2);              // stored as 14 days
        Period ofDays = Period.ofDays(30);
        Period complex = Period.of(1, 6, 15);            // 1yr 6mo 15d
        Period parsed = Period.parse("P1Y6M15D");

        printExercise("7.1 1 year", ofYears);
        printExercise("    6 months", ofMonths);
        printExercise("    2 weeks", ofWeeks);
        printExercise("    30 days", ofDays);
        printExercise("    1Y 6M 15D", complex);
        printExercise("    Parsed", parsed);
    }

    /**
     * Arithmetic with Period.
     */
    static void periodArithmetic() {
        Period p1 = Period.of(1, 6, 0);
        Period p2 = Period.ofMonths(3);

        Period sum = p1.plus(p2);
        Period diff = p1.minus(p2);
        Period multiplied = p1.multipliedBy(2);
        Period negated = p1.negated();

        printExercise("7.2 1Y6M + 3M", sum);
        printExercise("    1Y6M - 3M", diff);
        printExercise("    1Y6M × 2", multiplied);
        printExercise("    negated", negated);
    }

    /**
     * Computing Period between two dates.
     */
    static void periodBetween() {
        LocalDate birth = LocalDate.of(1990, 3, 15);
        LocalDate today = LocalDate.now();
        Period age = Period.between(birth, today);

        printExercise("7.3 Period since 1990-03-15",
                "%d years, %d months, %d days".formatted(
                        age.getYears(), age.getMonths(), age.getDays()));
        printExercise("    Total months", age.toTotalMonths());
    }

    /**
     * Period normalisation.
     *
     * <p><strong>Key point:</strong> {@code normalized()} adjusts months
     * into years + months (e.g. 15 months → 1 year 3 months), but
     * does NOT adjust days into months because day length varies.</p>
     */
    static void periodNormalisation() {
        Period unnormalized = Period.of(0, 15, 40);
        Period normalized = unnormalized.normalized();

        printExercise("7.4 Unnormalized: 0Y 15M 40D", unnormalized);
        printExercise("    Normalized", normalized);
        printExercise("    ⚠ Days are NOT normalized (40 stays 40)", "");
    }

    /**
     * Contrasting Period vs Duration.
     *
     * <p><strong>Key point:</strong> This is one of the most important
     * distinctions in the java.time API:</p>
     * <ul>
     *   <li><strong>Period</strong> = calendar-based: "1 month" means next month same day</li>
     *   <li><strong>Duration</strong> = time-based: fixed number of seconds/nanos</li>
     * </ul>
     * <p>Adding a Period of 1 month to Jan 31 → Feb 28.
     * Adding a Duration of 30 days to Jan 31 → Mar 2.</p>
     */
    static void periodVsDuration() {
        LocalDate jan31 = LocalDate.of(2025, 1, 31);
        LocalDate plusPeriod = jan31.plus(Period.ofMonths(1));
        LocalDate plusDuration = jan31.plusDays(30);

        printExercise("7.5 Jan 31 + 1 month (Period)", plusPeriod + " (Feb 28)");
        printExercise("    Jan 31 + 30 days (Duration)", plusDuration + " (Mar 2)");
        printExercise("    ⚠ They are NOT the same!", "Period is calendar-aware, Duration is not");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 8 — ARITHMETIC: PLUS, MINUS, UNTIL
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Adding and subtracting from date-time objects.
     *
     * <p><strong>Key point:</strong> All java.time classes are
     * <strong>immutable</strong>. {@code plus/minus} always return a
     * NEW object — the original is never modified.</p>
     */
    static void addAndSubtract() {
        LocalDate date = LocalDate.of(2025, 6, 15);

        printExercise("8.1 Original", date);
        printExercise("    + 10 days", date.plusDays(10));
        printExercise("    - 2 months", date.minusMonths(2));
        printExercise("    + 1 year", date.plusYears(1));
        printExercise("    + 3 weeks", date.plusWeeks(3));

        LocalTime time = LocalTime.of(14, 30);
        printExercise("    Time + 2 hours", time.plusHours(2));
        printExercise("    Time - 45 minutes", time.minusMinutes(45));
        printExercise("    Time + 90 minutes (wraps)", time.plusMinutes(90));
    }

    /**
     * Adding both a Period and a Duration to a LocalDateTime.
     */
    static void addPeriodAndDuration() {
        LocalDateTime ldt = LocalDateTime.of(2025, 1, 31, 14, 30);

        LocalDateTime plusPeriod = ldt.plus(Period.ofMonths(1));
        LocalDateTime plusDuration = ldt.plus(Duration.ofHours(48));
        LocalDateTime plusBoth = ldt.plus(Period.ofMonths(1)).plus(Duration.ofHours(2));

        printExercise("8.2 LDT + 1 month (Period)", plusPeriod);
        printExercise("    LDT + 48 hours (Duration)", plusDuration);
        printExercise("    LDT + 1 month + 2 hours", plusBoth);
    }

    /**
     * Using {@code until()} to compute the amount between two dates.
     *
     * <p><strong>Key point:</strong> {@code until()} returns the amount
     * in the specified unit. Unlike {@code Period.between()}, it gives
     * a single-unit answer. Use {@code ChronoUnit} for the unit.</p>
     */
    static void untilCalculation() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        long days = start.until(end, ChronoUnit.DAYS);
        long months = start.until(end, ChronoUnit.MONTHS);
        long weeks = start.until(end, ChronoUnit.WEEKS);

        printExercise("8.3 Jan 1 until Dec 31", "");
        printExercise("    in days", days);
        printExercise("    in months", months);
        printExercise("    in weeks", weeks);
    }

    /**
     * Using {@code with()} methods to set specific fields.
     *
     * <p><strong>Key point:</strong> {@code with} creates a copy with
     * one field changed. It's the setter equivalent for immutable objects.</p>
     */
    static void withMethodsForSetting() {
        LocalDate date = LocalDate.of(2025, 6, 15);

        printExercise("8.4 Original", date);
        printExercise("    withYear(2030)", date.withYear(2030));
        printExercise("    withMonth(1)", date.withMonth(1));
        printExercise("    withDayOfMonth(1)", date.withDayOfMonth(1));

        LocalDateTime ldt = date.atTime(14, 30);
        printExercise("    withHour(9)", ldt.withHour(9));
        printExercise("    withMinute(0)", ldt.withMinute(0));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 9 — COMPARISON & ORDERING
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Comparing LocalDate instances.
     *
     * <p><strong>Key point:</strong> Use {@code isBefore()}, {@code isAfter()},
     * and {@code isEqual()} for semantic comparisons. Do NOT use
     * {@code equals()} for ZonedDateTime (see pitfalls section).</p>
     */
    static void compareLocalDates() {
        LocalDate d1 = LocalDate.of(2025, 6, 15);
        LocalDate d2 = LocalDate.of(2025, 12, 25);

        printExercise("9.1 isBefore", d1.isBefore(d2));
        printExercise("    isAfter", d1.isAfter(d2));
        printExercise("    isEqual", d1.isEqual(d1));
        printExercise("    compareTo", d1.compareTo(d2) + " (negative = d1 before d2)");
    }

    /**
     * Comparing ZonedDateTimes across different timezones.
     *
     * <p><strong>Key point:</strong> {@code ZonedDateTime.isAfter()} compares
     * the underlying {@code Instant}, not the wall-clock time. 3pm London
     * is AFTER 3pm Tokyo on the same date because Tokyo is ahead of London.</p>
     */
    static void compareZonedDateTimesAcrossZones() {
        ZonedDateTime london3pm = ZonedDateTime.of(2025, 6, 15, 15, 0, 0, 0,
                ZoneId.of("Europe/London"));
        ZonedDateTime tokyo3pm = ZonedDateTime.of(2025, 6, 15, 15, 0, 0, 0,
                ZoneId.of("Asia/Tokyo"));

        printExercise("9.2 London 3pm vs Tokyo 3pm:");
        printExercise("    London instant", london3pm.toInstant());
        printExercise("    Tokyo instant", tokyo3pm.toInstant());
        printExercise("    London.isAfter(Tokyo)?", london3pm.isAfter(tokyo3pm));
        printExercise("    ⚠ Same wall time, different instants!", "");
    }

    /**
     * Sorting a list of dates using streams.
     */
    static void sortingDates() {
        List<LocalDate> dates = List.of(
                LocalDate.of(2025, 12, 25),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 6, 15),
                LocalDate.of(2025, 3, 8));

        List<LocalDate> sorted = dates.stream()
                .sorted()
                .toList();

        List<LocalDate> reversed = dates.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        printExercise("9.3 Sorted asc", sorted);
        printExercise("    Sorted desc", reversed);
    }

    /**
     * Finding min and max dates in a collection.
     */
    static void minMaxDate() {
        List<LocalDate> dates = PEOPLE.stream()
                .map(Person::getDateOfBirth)
                .toList();

        LocalDate earliest = dates.stream().min(Comparator.naturalOrder()).orElseThrow();
        LocalDate latest = dates.stream().max(Comparator.naturalOrder()).orElseThrow();

        printExercise("9.4 Earliest DOB", earliest);
        printExercise("    Latest DOB", latest);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 10 — TIMEZONE OPERATIONS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Listing available timezone IDs.
     *
     * <p><strong>Key point:</strong> Java maintains a database of all
     * IANA timezone IDs (e.g. "Europe/London", "America/New_York").
     * There are 600+ zones. Always use full IANA IDs, never
     * abbreviations like "EST" or "BST" which are ambiguous.</p>
     */
    static void listAvailableZones() {
        long totalZones = ZoneId.getAvailableZoneIds().size();

        List<String> europeanZones = ZoneId.getAvailableZoneIds().stream()
                .filter(z -> z.startsWith("Europe/"))
                .sorted()
                .toList();

        printExercise("10.1 Total available zones", totalZones);
        printExercise("     European zones (sample)", europeanZones.stream().limit(10).toList());
    }

    /**
     * Converting between timezones.
     *
     * <p><strong>Key point:</strong> {@code withZoneSameInstant()} converts
     * to another timezone while preserving the same point in time
     * (the wall clock changes). This is the most common conversion.</p>
     */
    static void convertBetweenZones() {
        ZonedDateTime london = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0,
                ZoneId.of("Europe/London"));

        ZonedDateTime newYork = london.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime tokyo = london.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime sydney = london.withZoneSameInstant(ZoneId.of("Australia/Sydney"));
        ZonedDateTime utc = london.withZoneSameInstant(ZoneId.of("UTC"));

        printExercise("10.2 London 2pm BST →");
        printExercise("    New York", newYork.toLocalTime() + " " + newYork.getZone());
        printExercise("    Tokyo", tokyo.toLocalTime() + " " + tokyo.getZone());
        printExercise("    Sydney", sydney.toLocalTime() + " " + sydney.getZone());
        printExercise("    UTC", utc.toLocalTime() + " " + utc.getZone());
    }

    /**
     * Critical difference: withZoneSameInstant vs withZoneSameLocal.
     *
     * <p><strong>Key point:</strong></p>
     * <ul>
     *   <li>{@code withZoneSameInstant} — keeps the SAME moment in time,
     *       changes the wall clock</li>
     *   <li>{@code withZoneSameLocal} — keeps the SAME wall clock reading,
     *       changes the moment in time</li>
     * </ul>
     * <p>You almost always want {@code withZoneSameInstant}.</p>
     */
    static void withZoneSameInstantVsSameLocal() {
        ZonedDateTime london2pm = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0,
                ZoneId.of("Europe/London"));

        ZonedDateTime sameInstant = london2pm.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime sameLocal = london2pm.withZoneSameLocal(ZoneId.of("Asia/Tokyo"));

        printExercise("10.3 London 2pm BST:");
        printExercise("    withZoneSameInstant(Tokyo)",
                sameInstant + " (same moment, different clock)");
        printExercise("    withZoneSameLocal(Tokyo)",
                sameLocal + " (same clock, DIFFERENT moment!)");
        printExercise("    ⚠ sameLocal shifted by 8 hours!", "");
    }

    /**
     * Normalising all times to UTC for storage.
     *
     * <p><strong>Key point:</strong> Best practice is to store all times
     * as UTC (Instant or OffsetDateTime with +00:00) and convert to
     * local time only for display.</p>
     */
    static void utcNormalization() {
        ZonedDateTime london = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime tokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));

        Instant londonUtc = london.toInstant();
        Instant tokyoUtc = tokyo.toInstant();

        printExercise("10.4 London wall time", london.toLocalTime());
        printExercise("    Tokyo wall time", tokyo.toLocalTime());
        printExercise("    Both as UTC Instant — equal?", londonUtc.equals(tokyoUtc));
        printExercise("    ⚠ Always store as UTC, display as local", "");
    }

    /**
     * Showing current time across multiple zones.
     */
    static void currentTimeMultipleZones() {
        List<String> zones = List.of(
                "UTC", "Europe/London", "Europe/Paris", "America/New_York",
                "America/Los_Angeles", "Asia/Tokyo", "Asia/Kolkata",
                "Australia/Sydney");

        Instant now = Instant.now();
        System.out.println("  ► 10.5 Current time worldwide (at %s UTC):".formatted(
                now.atZone(ZoneId.of("UTC")).toLocalTime()));
        zones.forEach(z -> {
            ZonedDateTime zdt = now.atZone(ZoneId.of(z));
            System.out.println("    %-25s %s  (UTC%s)".formatted(
                    z, zdt.toLocalTime(),
                    zdt.getOffset()));
        });
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 11 — GEOGRAPHIC / CITY-BASED TIMING
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Comparing times in cities where the Person entities live.
     *
     * <p><strong>Key point:</strong> Map real cities to IANA timezone IDs.
     * A single country may span multiple timezones.</p>
     */
    static void cityTimeComparison() {
        Map<String, String> cityToZone = Map.of(
                "London", "Europe/London",
                "Manchester", "Europe/London",
                "Birmingham", "Europe/London",
                "Paris", "Europe/Paris",
                "Berlin", "Europe/Berlin",
                "Milan", "Europe/Rome",
                "Dublin", "Europe/Dublin");

        Instant now = Instant.now();
        System.out.println("  ► 11.1 Person cities — current times:");
        PEOPLE.stream()
                .map(Person::getHomeCity)
                .distinct()
                .filter(cityToZone::containsKey)
                .forEach(city -> {
                    ZoneId zone = ZoneId.of(cityToZone.get(city));
                    ZonedDateTime localTime = now.atZone(zone);
                    System.out.println("    %-15s %s (%s, UTC%s)".formatted(
                            city,
                            localTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                            zone,
                            localTime.getOffset()));
                });
    }

    /**
     * Meeting scheduler: finding a time that works across timezones.
     *
     * <p><strong>Key point:</strong> To find overlapping business hours,
     * convert each zone's 9am–5pm to UTC, then find the intersection.
     * This is a common real-world problem in distributed teams.</p>
     */
    static void meetingSchedulerAcrossZones() {
        List<String> zones = List.of(
                "Europe/London", "America/New_York", "Asia/Tokyo");

        LocalDate meetingDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("  ► 11.2 Meeting scheduler for " + meetingDate + ":");

        // Find each zone's business hours in UTC
        zones.forEach(zoneStr -> {
            ZoneId zone = ZoneId.of(zoneStr);
            ZonedDateTime start = meetingDate.atTime(9, 0).atZone(zone);
            ZonedDateTime end = meetingDate.atTime(17, 0).atZone(zone);
            System.out.println("    %-25s Local 9am–5pm = UTC %s–%s".formatted(
                    zoneStr,
                    start.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime(),
                    end.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime()));
        });

        // Overlap: find the latest start and earliest end in UTC
        ZonedDateTime latestStart = zones.stream()
                .map(z -> meetingDate.atTime(9, 0).atZone(ZoneId.of(z)))
                .max(Comparator.comparing(ZonedDateTime::toInstant))
                .orElseThrow();
        ZonedDateTime earliestEnd = zones.stream()
                .map(z -> meetingDate.atTime(17, 0).atZone(ZoneId.of(z)))
                .min(Comparator.comparing(ZonedDateTime::toInstant))
                .orElseThrow();

        System.out.println("    ───────────────────────────────────────");
        if (latestStart.toInstant().isBefore(earliestEnd.toInstant())) {
            System.out.println("    ✓ Overlap: %s – %s UTC".formatted(
                    latestStart.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime(),
                    earliestEnd.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime()));
        } else {
            System.out.println("    ✗ No overlapping business hours!");
        }
    }

    /**
     * International date line effects.
     *
     * <p><strong>Key point:</strong> Crossing the date line can change
     * the date. Auckland (UTC+12) is almost a full day ahead of
     * Honolulu (UTC-10). The same instant can be two different dates.</p>
     */
    static void internationalDateLine() {
        ZonedDateTime auckland = ZonedDateTime.of(2025, 6, 16, 2, 0, 0, 0,
                ZoneId.of("Pacific/Auckland"));
        ZonedDateTime honolulu = auckland.withZoneSameInstant(
                ZoneId.of("Pacific/Honolulu"));

        printExercise("11.3 International Date Line:");
        printExercise("    Auckland", auckland.toLocalDate() + " " + auckland.toLocalTime());
        printExercise("    Honolulu (same instant)", honolulu.toLocalDate() + " " + honolulu.toLocalTime());
        printExercise("    ⚠ Different date for the same moment!", "");
    }

    /**
     * Timezones with non-standard offsets (half-hour, quarter-hour).
     *
     * <p><strong>Key point:</strong> Not all timezones are whole hours from UTC.
     * India is +05:30, Nepal is +05:45, and parts of Australia are +09:30.
     * Always use IANA timezone IDs — never assume hourly offsets.</p>
     */
    static void halfHourAndQuarterHourOffsets() {
        Instant now = Instant.now();
        List<String> unusualZones = List.of(
                "Asia/Kolkata",           // +05:30 (India)
                "Asia/Kathmandu",         // +05:45 (Nepal)
                "Australia/Adelaide",     // +09:30/+10:30 (DST)
                "Asia/Yangon",            // +06:30 (Myanmar)
                "Pacific/Chatham");       // +12:45/+13:45 (NZ Chatham)

        System.out.println("  ► 11.4 Non-standard timezone offsets:");
        unusualZones.forEach(z -> {
            ZonedDateTime zdt = now.atZone(ZoneId.of(z));
            System.out.println("    %-25s UTC%s  %s".formatted(
                    z, zdt.getOffset(), zdt.toLocalTime()));
        });
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 12 — DAYLIGHT SAVING TIME PITFALLS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * DST Spring Forward: the "gap" where times don't exist.
     *
     * <p><strong>Key point:</strong> When clocks spring forward (e.g. UK
     * last Sunday in March, 1:00am → 2:00am), the times between 1:00
     * and 2:00 do not exist. If you create a ZonedDateTime in the gap,
     * Java silently adjusts it forward.</p>
     */
    static void dstSpringForwardGap() {
        // UK clocks spring forward last Sunday of March at 1am → 2am
        ZoneId london = ZoneId.of("Europe/London");
        // 2025-03-30 is the spring-forward date for UK
        LocalDateTime inGap = LocalDateTime.of(2025, 3, 30, 1, 30);
        ZonedDateTime resolved = inGap.atZone(london);

        printExercise("12.1 DST Spring Forward Gap:");
        printExercise("    Requested 01:30 on 2025-03-30", inGap);
        printExercise("    Resolved by Java", resolved);
        printExercise("    ⚠ 01:30 doesn't exist — moved to", resolved.toLocalTime());
    }

    /**
     * DST Fall Back: the "overlap" where times exist twice.
     *
     * <p><strong>Key point:</strong> When clocks fall back (e.g. UK
     * last Sunday in October, 2:00am → 1:00am), times between 1:00
     * and 2:00 exist TWICE — once in BST and once in GMT. Java
     * defaults to the earlier offset. Use
     * {@code withEarlierOffsetAtOverlap()} or
     * {@code withLaterOffsetAtOverlap()} to control which one.</p>
     */
    static void dstFallBackOverlap() {
        ZoneId london = ZoneId.of("Europe/London");
        // 2025-10-26 is the fall-back date for UK
        LocalDateTime inOverlap = LocalDateTime.of(2025, 10, 26, 1, 30);
        ZonedDateTime earlier = inOverlap.atZone(london).withEarlierOffsetAtOverlap();
        ZonedDateTime later = inOverlap.atZone(london).withLaterOffsetAtOverlap();

        printExercise("12.2 DST Fall Back Overlap:");
        printExercise("    01:30 on 2025-10-26 exists TWICE:");
        printExercise("    Earlier (BST +01:00)", earlier + " instant=" + earlier.toInstant());
        printExercise("    Later (GMT +00:00)", later + " instant=" + later.toInstant());
        printExercise("    ⚠ Same wall time, 1 hour apart!", "");
    }

    /**
     * Duration across a DST boundary may not be what you expect.
     *
     * <p><strong>Key point:</strong> Adding 24 hours across a DST change
     * does not always land on the same time tomorrow. Adding 1 day
     * (Period) vs 24 hours (Duration) gives different results.</p>
     */
    static void durationAcrossDstBoundary() {
        ZoneId london = ZoneId.of("Europe/London");
        ZonedDateTime beforeSpring = ZonedDateTime.of(2025, 3, 29, 14, 0, 0, 0, london);

        ZonedDateTime plus1Day = beforeSpring.plus(Period.ofDays(1));
        ZonedDateTime plus24Hours = beforeSpring.plus(Duration.ofHours(24));

        printExercise("12.3 Across spring-forward (2025-03-29 → 30):");
        printExercise("    + 1 day (Period)", plus1Day.toLocalTime() + " (same wall time)");
        printExercise("    + 24 hours (Duration)", plus24Hours.toLocalTime() + " (1 hour different!)");
        printExercise("    ⚠ Period preserves wall time, Duration counts real hours", "");
    }

    /**
     * Safe scheduling that accounts for DST.
     *
     * <p><strong>Key point:</strong> For daily events at a fixed local
     * time (e.g. "9am every day"), add Period.ofDays(1), not
     * Duration.ofHours(24). Period respects DST; Duration does not.</p>
     */
    static void dstSafeScheduling() {
        ZoneId london = ZoneId.of("Europe/London");
        ZonedDateTime dailyMeeting = ZonedDateTime.of(2025, 3, 28, 9, 0, 0, 0, london);

        System.out.println("  ► 12.4 Safe daily scheduling (9am London):");
        ZonedDateTime current = dailyMeeting;
        for (int i = 0; i < 5; i++) {
            System.out.println("    Day %d: %s %s (UTC%s)".formatted(
                    i, current.toLocalDate(), current.toLocalTime(),
                    current.getOffset()));
            current = current.plus(Period.ofDays(1));  // safe: preserves 9am
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 13 — TEMPORAL ADJUSTERS & QUERIES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Built-in {@link TemporalAdjusters}.
     *
     * <p><strong>Key point:</strong> TemporalAdjusters provide reusable
     * date manipulation strategies. They follow the Strategy pattern
     * and are composable with {@code with()}.</p>
     */
    static void builtInAdjusters() {
        LocalDate date = LocalDate.of(2025, 6, 15);  // a Sunday

        printExercise("13.1 Built-in adjusters (from " + date + ", " + date.getDayOfWeek() + "):");
        printExercise("    firstDayOfMonth", date.with(TemporalAdjusters.firstDayOfMonth()));
        printExercise("    lastDayOfMonth", date.with(TemporalAdjusters.lastDayOfMonth()));
        printExercise("    firstDayOfYear", date.with(TemporalAdjusters.firstDayOfYear()));
        printExercise("    lastDayOfYear", date.with(TemporalAdjusters.lastDayOfYear()));
        printExercise("    firstDayOfNextMonth", date.with(TemporalAdjusters.firstDayOfNextMonth()));
        printExercise("    firstDayOfNextYear", date.with(TemporalAdjusters.firstDayOfNextYear()));
        printExercise("    next(MONDAY)", date.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
        printExercise("    nextOrSame(SUNDAY)", date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
        printExercise("    previous(FRIDAY)", date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)));
        printExercise("    firstInMonth(MONDAY)", date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
        printExercise("    lastInMonth(FRIDAY)", date.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));
    }

    /**
     * Creating custom TemporalAdjusters.
     *
     * <p><strong>Key point:</strong> A {@code TemporalAdjuster} is a
     * {@code @FunctionalInterface} — you can create one with a lambda.
     * This enables reusable, testable date logic.</p>
     */
    static void customAdjuster() {
        // Custom adjuster: next working day (skips weekends)
        TemporalAdjuster nextWorkingDay = temporal -> {
            LocalDate date = LocalDate.from(temporal);
            DayOfWeek dow = date.getDayOfWeek();
            return switch (dow) {
                case FRIDAY -> date.plusDays(3);
                case SATURDAY -> date.plusDays(2);
                default -> date.plusDays(1);
            };
        };

        // Custom adjuster: last day of quarter
        TemporalAdjuster lastDayOfQuarter = temporal -> {
            LocalDate date = LocalDate.from(temporal);
            int quarterEnd = ((date.getMonthValue() - 1) / 3 + 1) * 3;
            return date.withMonth(quarterEnd).with(TemporalAdjusters.lastDayOfMonth());
        };

        LocalDate friday = LocalDate.of(2025, 6, 13);  // Friday
        LocalDate midQuarter = LocalDate.of(2025, 5, 15);

        printExercise("13.2 Custom: next working day after " + friday,
                friday.with(nextWorkingDay));
        printExercise("    Custom: last day of quarter for " + midQuarter,
                midQuarter.with(lastDayOfQuarter));
    }

    /**
     * Temporal queries — extracting information from date-time objects.
     *
     * <p><strong>Key point:</strong> {@code TemporalQuery} is the read-only
     * counterpart to {@code TemporalAdjuster}. Built-in queries include
     * {@code precision()}, {@code zone()}, and {@code localDate()}.</p>
     */
    static void temporalQueries() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Europe/London"));

        TemporalUnit precision = zdt.query(TemporalQueries.precision());
        ZoneId zone = zdt.query(TemporalQueries.zone());
        LocalDate date = zdt.query(TemporalQueries.localDate());
        LocalTime time = zdt.query(TemporalQueries.localTime());

        // Custom query: which quarter is this date in?
        TemporalQuery<Integer> quarterQuery = temporal ->
                (LocalDate.from(temporal).getMonthValue() - 1) / 3 + 1;

        printExercise("13.3 precision()", precision);
        printExercise("    zone()", zone);
        printExercise("    localDate()", date);
        printExercise("    localTime()", time);
        printExercise("    Custom quarter query", "Q" + zdt.query(quarterQuery));
    }

    /**
     * Business day calculations using TemporalAdjusters.
     */
    static void businessDayCalculations() {
        LocalDate start = LocalDate.of(2025, 6, 2);  // Monday

        // Count business days in a range
        LocalDate end = LocalDate.of(2025, 6, 30);
        long businessDays = start.datesUntil(end)
                .filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY
                        && d.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();

        // Add N business days
        int daysToAdd = 10;
        LocalDate result = start;
        int added = 0;
        while (added < daysToAdd) {
            result = result.plusDays(1);
            if (result.getDayOfWeek() != DayOfWeek.SATURDAY
                    && result.getDayOfWeek() != DayOfWeek.SUNDAY) {
                added++;
            }
        }

        printExercise("13.4 Business days Jun 2–30", businessDays);
        printExercise("    10 business days from " + start, result);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 14 — CHRONOUNIT & TRUNCATION
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Computing differences using {@link ChronoUnit#between}.
     */
    static void chronoUnitBetween() {
        LocalDate d1 = LocalDate.of(1990, 3, 15);
        LocalDate d2 = LocalDate.now();

        printExercise("14.1 ChronoUnit.between (1990-03-15 to today):");
        printExercise("    DAYS", ChronoUnit.DAYS.between(d1, d2));
        printExercise("    WEEKS", ChronoUnit.WEEKS.between(d1, d2));
        printExercise("    MONTHS", ChronoUnit.MONTHS.between(d1, d2));
        printExercise("    YEARS", ChronoUnit.YEARS.between(d1, d2));
        printExercise("    DECADES", ChronoUnit.DECADES.between(d1, d2));

        LocalTime t1 = LocalTime.of(9, 0);
        LocalTime t2 = LocalTime.of(17, 30);
        printExercise("    HOURS (9am to 5:30pm)", ChronoUnit.HOURS.between(t1, t2));
        printExercise("    MINUTES", ChronoUnit.MINUTES.between(t1, t2));
    }

    /**
     * Truncating date-time values to specific units.
     *
     * <p><strong>Key point:</strong> {@code truncatedTo()} zeroes out
     * all units smaller than the specified unit. Useful for rounding
     * timestamps to the nearest hour, minute, etc.</p>
     */
    static void truncation() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 37, 45, 123_456_789);

        printExercise("14.2 Truncation of " + ldt + ":");
        printExercise("    to HOURS", ldt.truncatedTo(ChronoUnit.HOURS));
        printExercise("    to MINUTES", ldt.truncatedTo(ChronoUnit.MINUTES));
        printExercise("    to SECONDS", ldt.truncatedTo(ChronoUnit.SECONDS));
        printExercise("    to MILLIS", ldt.truncatedTo(ChronoUnit.MILLIS));
        printExercise("    to DAYS", ldt.truncatedTo(ChronoUnit.DAYS));

        Instant instant = Instant.now();
        printExercise("    Instant to SECONDS", instant.truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Querying which units a type supports.
     */
    static void supportedUnits() {
        printExercise("14.3 Supported units:");
        printExercise("    LocalDate", LocalDate.now().isSupported(ChronoUnit.DAYS) + " (DAYS), "
                + LocalDate.now().isSupported(ChronoUnit.HOURS) + " (HOURS)");
        printExercise("    LocalTime", LocalTime.now().isSupported(ChronoUnit.HOURS) + " (HOURS), "
                + LocalTime.now().isSupported(ChronoUnit.DAYS) + " (DAYS)");
        printExercise("    Instant", Instant.now().isSupported(ChronoUnit.SECONDS) + " (SECONDS), "
                + Instant.now().isSupported(ChronoUnit.DAYS) + " (DAYS)");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 15 — LEGACY INTEROP
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Converting between {@code java.util.Date} and {@code java.time}.
     *
     * <p><strong>Key point:</strong> {@code Date.toInstant()} and
     * {@code Date.from(Instant)} are the bridge methods. All conversions
     * go through {@code Instant} because {@code Date} is internally
     * a millisecond timestamp (UTC).</p>
     */
    @SuppressWarnings("deprecation")
    static void convertToFromJavaUtilDate() {
        // java.util.Date → Instant → ZonedDateTime → LocalDate
        java.util.Date legacyDate = new java.util.Date();
        Instant instant = legacyDate.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zdt.toLocalDate();
        LocalDateTime localDateTime = zdt.toLocalDateTime();

        // Reverse: LocalDateTime → Instant → java.util.Date
        Instant backToInstant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date backToDate = java.util.Date.from(backToInstant);

        printExercise("15.1 java.util.Date → Instant", instant);
        printExercise("    → ZonedDateTime", zdt);
        printExercise("    → LocalDate", localDate);
        printExercise("    Reverse: Date.from(instant)", backToDate);
    }

    /**
     * Converting between {@code Calendar} and {@code java.time}.
     */
    static void convertToFromCalendar() {
        Calendar cal = Calendar.getInstance();
        Instant calInstant = cal.toInstant();
        ZonedDateTime zdt = calInstant.atZone(cal.getTimeZone().toZoneId());

        // Reverse
        ZonedDateTime now = ZonedDateTime.now();
        GregorianCalendar gcal = GregorianCalendar.from(now);

        printExercise("15.2 Calendar → Instant", calInstant);
        printExercise("    → ZonedDateTime", zdt);
        printExercise("    ZonedDateTime → GregorianCalendar", gcal.getTime());
    }

    /**
     * Converting between {@code java.sql} types and {@code java.time}.
     *
     * <p><strong>Key point:</strong> {@code java.sql.Date} maps to
     * {@code LocalDate}, {@code java.sql.Time} to {@code LocalTime},
     * and {@code java.sql.Timestamp} to {@code LocalDateTime}.
     * Modern JDBC drivers support java.time directly.</p>
     */
    static void convertToFromSqlTypes() {
        // java.sql.Date ↔ LocalDate
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.of(2025, 6, 15));
        LocalDate fromSqlDate = sqlDate.toLocalDate();

        // java.sql.Time ↔ LocalTime
        java.sql.Time sqlTime = java.sql.Time.valueOf(LocalTime.of(14, 30, 0));
        LocalTime fromSqlTime = sqlTime.toLocalTime();

        // java.sql.Timestamp ↔ LocalDateTime / Instant
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(
                LocalDateTime.of(2025, 6, 15, 14, 30));
        LocalDateTime fromSqlTimestamp = sqlTimestamp.toLocalDateTime();
        Instant fromSqlInstant = sqlTimestamp.toInstant();

        printExercise("15.3 java.sql.Date → LocalDate", fromSqlDate);
        printExercise("    java.sql.Time → LocalTime", fromSqlTime);
        printExercise("    java.sql.Timestamp → LocalDateTime", fromSqlTimestamp);
        printExercise("    java.sql.Timestamp → Instant", fromSqlInstant);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 16 — COMMON PITFALLS & GOTCHAS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * PITFALL #1: Forgetting that java.time objects are immutable.
     *
     * <p><strong>The bug:</strong> Calling {@code date.plusDays(5)} and
     * expecting {@code date} to be modified. It isn't — the result
     * is a new object. You must assign the return value.</p>
     */
    static void pitfallImmutability() {
        LocalDate date = LocalDate.of(2025, 6, 15);
        date.plusDays(5);  // ⚠ return value is DISCARDED
        printExercise("16.1 ⚠ PITFALL: Immutability");
        printExercise("    After date.plusDays(5), date is still", date);
        printExercise("    Correct: date = date.plusDays(5)", date.plusDays(5));
    }

    /**
     * PITFALL #2: Using equals() vs isEqual() on ZonedDateTime.
     *
     * <p><strong>The bug:</strong> {@code equals()} compares ALL fields
     * including timezone. Two ZonedDateTimes representing the exact
     * same instant but in different zones will NOT be equal().
     * Use {@code isEqual()} to compare the instant, or convert
     * to Instant first.</p>
     */
    static void pitfallEqualsVsIsEqual() {
        ZonedDateTime london = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0,
                ZoneId.of("Europe/London"));
        ZonedDateTime paris = london.withZoneSameInstant(ZoneId.of("Europe/Paris"));

        printExercise("16.2 ⚠ PITFALL: equals() vs isEqual()");
        printExercise("    london.equals(paris)", london.equals(paris) + " ← WRONG for same-instant check");
        printExercise("    london.isEqual(paris)", london.isEqual(paris) + " ← CORRECT");
        printExercise("    Instants equal?", london.toInstant().equals(paris.toInstant()));
    }

    /**
     * PITFALL #3: Month indexing — legacy vs new API.
     *
     * <p><strong>The bug:</strong> In legacy {@code Calendar}, January = 0.
     * In java.time, January = 1. Mixing up the two causes off-by-one month errors.</p>
     */
    static void pitfallMonthIndexing() {
        // Legacy: January = 0
        Calendar cal = Calendar.getInstance();
        cal.set(2025, 0, 15);  // 0 = January!

        // java.time: January = 1
        LocalDate ld = LocalDate.of(2025, 1, 15);  // 1 = January

        printExercise("16.3 ⚠ PITFALL: Month indexing");
        printExercise("    Calendar month 0", "= January (legacy is 0-based)");
        printExercise("    LocalDate month 1", ld.getMonth() + " (java.time is 1-based)");
        printExercise("    Tip: use Month.JANUARY enum to avoid confusion", "");
    }

    /**
     * PITFALL #4: Ambiguous parsing without explicit formatter.
     *
     * <p><strong>The bug:</strong> "01/02/2025" — is this Jan 2 or Feb 1?
     * It depends on locale. Always specify the format explicitly.</p>
     */
    static void pitfallParsingAmbiguity() {
        String ambiguous = "01/02/2025";

        DateTimeFormatter usFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter ukFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate usDate = LocalDate.parse(ambiguous, usFormat);
        LocalDate ukDate = LocalDate.parse(ambiguous, ukFormat);

        printExercise("16.4 ⚠ PITFALL: Ambiguous parsing of '01/02/2025'");
        printExercise("    US format (MM/dd/yyyy)", usDate + " = January 2");
        printExercise("    UK format (dd/MM/yyyy)", ukDate + " = February 1");
        printExercise("    ⚠ Always specify the format!", "");
    }

    /**
     * PITFALL #5: LocalDateTime has no timezone — it is NOT a timestamp.
     *
     * <p><strong>The bug:</strong> Using {@code LocalDateTime} to store
     * event times that involve multiple timezones. Since it has no zone,
     * "14:00" is ambiguous — use {@code ZonedDateTime} or {@code Instant}.</p>
     */
    static void pitfallLocalDateTimeHasNoZone() {
        LocalDateTime ldt = LocalDateTime.of(2025, 6, 15, 14, 0);

        // Same LocalDateTime in different zones = different moments
        Instant londonInstant = ldt.atZone(ZoneId.of("Europe/London")).toInstant();
        Instant tokyoInstant = ldt.atZone(ZoneId.of("Asia/Tokyo")).toInstant();

        printExercise("16.5 ⚠ PITFALL: LocalDateTime has no zone");
        printExercise("    LDT 14:00 in London", londonInstant);
        printExercise("    LDT 14:00 in Tokyo", tokyoInstant);
        printExercise("    Same LDT, different Instants!", "8 hours apart");
        printExercise("    ⚠ Never use LDT for cross-timezone scheduling", "");
    }

    /**
     * PITFALL #6: Duration vs Period — they are not interchangeable.
     *
     * <p><strong>The bug:</strong> Using Duration for "1 day" when DST
     * changes mean a day is 23 or 25 hours. Or using Period for
     * measuring elapsed time between two instants.</p>
     */
    static void pitfallDurationVsPeriod() {
        printExercise("16.6 ⚠ PITFALL: Duration vs Period");
        printExercise("    Duration.ofDays(1)", Duration.ofDays(1) + " = exactly 24 hours");
        printExercise("    Period.ofDays(1)", Period.ofDays(1) + " = calendar day (may be 23 or 25 hours)");
        printExercise("    ⚠ For scheduling: use Period. For elapsed time: use Duration.", "");

        // Duration.ofDays(1) can't be added to LocalDate
        try {
            LocalDate.now().plus(Duration.ofDays(1));
            printExercise("    LocalDate + Duration", "worked (surprising but valid for whole days)");
        } catch (UnsupportedTemporalTypeException e) {
            printExercise("    ⚠ LocalDate + Duration", "THROWS — use Period instead");
        }
    }

    /**
     * PITFALL #7: DST arithmetic giving unexpected results.
     */
    static void pitfallDstArithmetic() {
        ZoneId london = ZoneId.of("Europe/London");
        ZonedDateTime march29 = ZonedDateTime.of(2025, 3, 29, 12, 0, 0, 0, london);
        ZonedDateTime march30 = march29.plusDays(1);

        Duration realDuration = Duration.between(march29, march30);

        printExercise("16.7 ⚠ PITFALL: DST arithmetic");
        printExercise("    Mar 29 noon + 1 day", march30.toLocalTime() + " (looks right: noon)");
        printExercise("    But real Duration between them", realDuration + " (only 23 hours!)");
        printExercise("    ⚠ plusDays(1) preserves wall time, not real hours", "");
    }

    /**
     * PITFALL #8: Epoch assumptions.
     *
     * <p><strong>The bug:</strong> Assuming epoch millis from JavaScript
     * are the same as epoch seconds in Unix. JavaScript uses millis,
     * most Unix tools use seconds. Mixing them up gives dates in
     * year 53,870 or 1970-01-01.</p>
     */
    static void pitfallEpochAssumptions() {
        long jsTimestamp = 1_700_000_000_000L;  // JavaScript millis
        long unixTimestamp = 1_700_000_000L;    // Unix seconds

        // Wrong: treating JS millis as seconds
        Instant wrongFromJs = Instant.ofEpochSecond(jsTimestamp);
        // Correct
        Instant correctFromJs = Instant.ofEpochMilli(jsTimestamp);
        Instant correctFromUnix = Instant.ofEpochSecond(unixTimestamp);

        printExercise("16.8 ⚠ PITFALL: Epoch millis vs seconds");
        printExercise("    JS millis as seconds (WRONG)", wrongFromJs + " ← year 55,000+!");
        printExercise("    JS millis correct", correctFromJs);
        printExercise("    Unix seconds correct", correctFromUnix);
        printExercise("    ⚠ Check if timestamp is seconds or millis!", "");
    }

    /**
     * PITFALL #9: toString() format varies by class.
     */
    static void pitfallToStringFormats() {
        printExercise("16.9 ⚠ PITFALL: toString() varies by type");
        printExercise("    LocalDate", LocalDate.of(2025, 6, 15).toString());
        printExercise("    LocalTime", LocalTime.of(14, 30, 0).toString());
        printExercise("    LocalTime with nano", LocalTime.of(14, 30, 0, 1).toString());
        printExercise("    Instant", Instant.EPOCH.toString());
        printExercise("    ⚠ Trailing zeros are dropped — don't rely on format length", "");
    }

    /**
     * PITFALL #10: Year.of() vs date.getYear() for era handling.
     *
     * <p><strong>The bug:</strong> In STRICT mode, 'yyyy' means
     * year-of-era (always positive), while 'uuuu' means proleptic
     * year (can be negative for BC). For modern dates this doesn't
     * matter, but for historical dates it can cause parsing failures.</p>
     */
    static void pitfallYearOf() {
        printExercise("16.10 ⚠ PITFALL: yyyy vs uuuu in strict parsing");
        printExercise("    'yyyy' = year-of-era (always positive, needs era G)", "");
        printExercise("    'uuuu' = proleptic year (negative for BC)", "");
        printExercise("    For STRICT mode: use 'uuuu-MM-dd'", "");

        // Demonstrate
        try {
            DateTimeFormatter strict = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate bcDate = LocalDate.parse("-0044-03-15", strict);
            printExercise("    Parsed BC date", bcDate + " (44 BC)");
        } catch (Exception e) {
            printExercise("    BC date parsing", e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 17 — DATE/TIME WITH STREAMS & PERSON MODEL
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Groups people by the month of their birth using streams.
     */
    static void personsGroupedByBirthMonth() {
        Map<Month, List<String>> byMonth = PEOPLE.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getDateOfBirth().getMonth(),
                        () -> new TreeMap<>(Comparator.comparingInt(Month::getValue)),
                        Collectors.mapping(Person::getFullName, Collectors.toList())));

        printExercise("17.1 People by birth month", byMonth);
    }

    /**
     * Groups people by age decade using streams.
     */
    static void personsGroupedByAgeDecade() {
        Map<String, List<String>> byDecade = PEOPLE.stream()
                .collect(Collectors.groupingBy(
                        p -> {
                            int decade = (p.getAge() / 10) * 10;
                            return "%ds".formatted(decade);
                        },
                        TreeMap::new,
                        Collectors.mapping(Person::getFullName, Collectors.toList())));

        printExercise("17.2 People by age decade", byDecade);
    }

    /**
     * Calculates the average age in days across all people.
     */
    static void averageAgeInDays() {
        OptionalDouble avgDays = PEOPLE.stream()
                .mapToLong(p -> ChronoUnit.DAYS.between(p.getDateOfBirth(), LocalDate.now()))
                .average();

        printExercise("17.3 Average age in days",
                avgDays.isPresent() ? "%.0f days (≈%.1f years)".formatted(
                        avgDays.getAsDouble(),
                        avgDays.getAsDouble() / 365.25) : "N/A");
    }

    /**
     * Finds the oldest and youngest person using stream comparisons.
     */
    static void oldestAndYoungestPerson() {
        Person oldest = PEOPLE.stream()
                .min(Comparator.comparing(Person::getDateOfBirth))
                .orElseThrow();
        Person youngest = PEOPLE.stream()
                .max(Comparator.comparing(Person::getDateOfBirth))
                .orElseThrow();

        printExercise("17.4 Oldest", "%s (born %s, age %d)"
                .formatted(oldest.getFullName(), oldest.getDateOfBirth(), oldest.getAge()));
        printExercise("    Youngest", "%s (born %s, age %d)"
                .formatted(youngest.getFullName(), youngest.getDateOfBirth(), youngest.getAge()));
    }

    /**
     * Finds people with birthdays in the current month.
     */
    static void birthdayThisMonth() {
        Month currentMonth = LocalDate.now().getMonth();
        List<String> birthdaysThisMonth = PEOPLE.stream()
                .filter(p -> p.getDateOfBirth().getMonth() == currentMonth)
                .map(p -> "%s (%s)".formatted(p.getFullName(), p.getDateOfBirth()))
                .toList();

        printExercise("17.5 Birthdays in " + currentMonth, birthdaysThisMonth);
    }

    /**
     * Computes days until each person's next birthday.
     */
    static void daysUntilNextBirthday() {
        LocalDate today = LocalDate.now();

        System.out.println("  ► 17.6 Days until next birthday:");
        PEOPLE.stream()
                .map(p -> {
                    MonthDay birthday = MonthDay.from(p.getDateOfBirth());
                    LocalDate nextBirthday = birthday.atYear(today.getYear());
                    if (!nextBirthday.isAfter(today)) {
                        nextBirthday = birthday.atYear(today.getYear() + 1);
                    }
                    long days = ChronoUnit.DAYS.between(today, nextBirthday);
                    return Map.entry(p.getFullName(), days);
                })
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println("    %-20s %3d days (%s)"
                        .formatted(e.getKey(), e.getValue(),
                                e.getValue() == 0 ? "TODAY!" :
                                        e.getValue() <= 30 ? "soon!" : "")));
    }

    /**
     * Summary statistics on birth years.
     */
    static void dateOfBirthStatistics() {
        IntSummaryStatistics yearStats = PEOPLE.stream()
                .mapToInt(p -> p.getDateOfBirth().getYear())
                .summaryStatistics();

        printExercise("17.7 Birth year statistics",
                "min=%d, max=%d, avg=%.0f".formatted(
                        yearStats.getMin(), yearStats.getMax(), yearStats.getAverage()));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 18 — REAL-WORLD SCENARIOS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Calculates exact age in years, months, and days.
     *
     * <p><strong>Real-world use:</strong> Age verification, insurance
     * calculations, HR systems.</p>
     */
    static void calculateAge() {
        LocalDate birthDate = LocalDate.of(1990, 3, 15);
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDate, today);

        printExercise("18.1 Exact age from 1990-03-15",
                "%d years, %d months, %d days".formatted(
                        age.getYears(), age.getMonths(), age.getDays()));
    }

    /**
     * Finds the next business day (skipping weekends).
     *
     * <p><strong>Real-world use:</strong> SLA calculations, payment
     * processing, delivery date estimation.</p>
     */
    static void findNextBusinessDay() {
        LocalDate today = LocalDate.now();
        LocalDate nextBiz = today;
        do {
            nextBiz = nextBiz.plusDays(1);
        } while (nextBiz.getDayOfWeek() == DayOfWeek.SATURDAY
                || nextBiz.getDayOfWeek() == DayOfWeek.SUNDAY);

        printExercise("18.2 Today", today + " (" + today.getDayOfWeek() + ")");
        printExercise("    Next business day", nextBiz + " (" + nextBiz.getDayOfWeek() + ")");
    }

    /**
     * Checks if a date falls on a weekend.
     */
    static void isWeekendCheck() {
        LocalDate date = LocalDate.now();
        boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        printExercise("18.3 Is today a weekend?", isWeekend + " (" + date.getDayOfWeek() + ")");
    }

    /**
     * Generates a range of dates using {@code datesUntil} (Java 9+).
     *
     * <p><strong>Key point:</strong> {@code LocalDate.datesUntil(end)}
     * returns a {@code Stream<LocalDate>} — perfect for generating
     * date ranges, calendars, and schedules.</p>
     */
    static void generateDateRange() {
        LocalDate start = LocalDate.of(2025, 6, 9);
        LocalDate end = LocalDate.of(2025, 6, 16);

        List<String> week = start.datesUntil(end)
                .map(d -> "%s %s".formatted(d, d.getDayOfWeek()))
                .toList();

        // With step (every 3rd day)
        List<String> everyThirdDay = start.datesUntil(end, Period.ofDays(3))
                .map(d -> "%s %s".formatted(d, d.getDayOfWeek()))
                .toList();

        printExercise("18.4 Date range (daily)", week);
        printExercise("    Every 3rd day", everyThirdDay);
    }

    /**
     * Counts working days between two dates.
     *
     * <p><strong>Real-world use:</strong> Leave management, project
     * planning, SLA deadline calculations.</p>
     */
    static void calculateWorkingDaysBetween() {
        LocalDate start = LocalDate.of(2025, 6, 1);
        LocalDate end = LocalDate.of(2025, 6, 30);

        long workingDays = start.datesUntil(end)
                .filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY
                        && d.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();

        long weekendDays = start.datesUntil(end)
                .filter(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY
                        || d.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();

        printExercise("18.5 Working days Jun 1–30", workingDays);
        printExercise("    Weekend days", weekendDays);
    }

    /**
     * Formatting timestamps for REST API responses.
     *
     * <p><strong>Real-world use:</strong> API response headers,
     * JSON serialization, audit trails. ISO-8601 with UTC is the
     * standard for API communication.</p>
     */
    static void formatForApiResponse() {
        Instant now = Instant.now();

        // ISO-8601 UTC (recommended for APIs)
        String isoUtc = now.toString();

        // RFC 1123 (used in HTTP headers)
        String rfc1123 = DateTimeFormatter.RFC_1123_DATE_TIME
                .format(now.atZone(ZoneId.of("UTC")));

        // ISO with explicit offset
        String isoOffset = OffsetDateTime.ofInstant(now, ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        printExercise("18.6 API format — ISO UTC", isoUtc);
        printExercise("    HTTP header — RFC 1123", rfc1123);
        printExercise("    ISO with offset", isoOffset);
    }

    /**
     * Calculating an SLA deadline: "5 business days from now".
     *
     * <p><strong>Real-world use:</strong> Support ticket SLAs,
     * delivery promises, payment processing deadlines.</p>
     */
    static void calculateSlaDeadline() {
        LocalDate requestDate = LocalDate.now();
        int slaBusinessDays = 5;

        LocalDate deadline = requestDate;
        int added = 0;
        while (added < slaBusinessDays) {
            deadline = deadline.plusDays(1);
            if (deadline.getDayOfWeek() != DayOfWeek.SATURDAY
                    && deadline.getDayOfWeek() != DayOfWeek.SUNDAY) {
                added++;
            }
        }

        printExercise("18.7 SLA: 5 business days from " + requestDate,
                deadline + " (" + deadline.getDayOfWeek() + ")");
    }

    /**
     * Timezone-aware audit log: recording events with precise
     * timestamps that include timezone context.
     *
     * <p><strong>Real-world use:</strong> Compliance logging, financial
     * transaction records, security audit trails.</p>
     */
    static void timeZoneAwareAuditLog() {
        record AuditEntry(String event, Instant timestamp, ZoneId userZone) {
            String format() {
                ZonedDateTime userTime = timestamp.atZone(userZone);
                return "[%s UTC | %s %s] %s".formatted(
                        timestamp.truncatedTo(ChronoUnit.SECONDS),
                        userTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        userZone.getId(),
                        event);
            }
        }

        Instant now = Instant.now();
        List<AuditEntry> log = List.of(
                new AuditEntry("User login", now, ZoneId.of("Europe/London")),
                new AuditEntry("Record updated", now.plusSeconds(120), ZoneId.of("Asia/Tokyo")),
                new AuditEntry("Report exported", now.plusSeconds(300), ZoneId.of("America/New_York")));

        System.out.println("  ► 18.8 Timezone-aware audit log:");
        log.forEach(entry -> System.out.println("    " + entry.format()));
        System.out.println("    ⚠ Store UTC, display in user's timezone");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  HELPERS
    // ═══════════════════════════════════════════════════════════════════

    private static void printSection(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  SECTION " + title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private static void printExercise(String label, Object result) {
        System.out.println("  ► " + label + ": " + result);
    }
}
