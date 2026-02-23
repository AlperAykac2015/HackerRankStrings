package com.aykacltd.datetime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPREHENSIVE JAVA DATE & TIME GUIDE  (Java 21+ / Java 25 compatible)
 * Uses: java.time.*  (JSR-310, built-in since Java 8, enhanced through Java 25)
 * ═══════════════════════════════════════════════════════════════════════════════
 * <p>
 * TABLE OF CONTENTS
 * -----------------
 * 1.  Core Classes Overview
 * 2.  Initialising Date & Time Objects
 * 3.  Time Zones & ZoneId
 * 4.  Converting Between Time Zones
 * 5.  Difference Between Two Dates / Times
 * 6.  Formatting  (DateTime → String)
 * 7.  Parsing     (String  → DateTime)
 * 8.  Arithmetic  (plus / minus)
 * 9.  Comparisons & Ordering
 * 10. Truncation, Rounding & Alignment
 * 11. Day-of-Week, Month, Quarter helpers
 * 12. Legacy java.util.Date ↔ java.time Bridge
 * 13. edge Cases & Common Pitfalls
 */
public class JavaDateTimeGuide {

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌1. CORE CLASSES OVERVIEW
  // ═══════════════════════════════════════════════════════════════════════════
  /*
   *  CLASS               | CONTAINS              | TIME-ZONE AWARE?
   *  ──────────────────────────────────────────────────────────────
   *  LocalDate           | date only             | NO  (2025-06-01)
   *  LocalTime           | time only             | NO  (14:30:00)
   *  LocalDateTime       | date + time           | NO  (2025-06-01T14:30:00)
   *  ZonedDateTime       | date + time + zone    | YES (2025-06-01T14:30:00+01:00[Europe/London])
   *  OffsetDateTime      | date + time + offset  | offset only  (2025-06-01T14:30:00+01:00)
   *  OffsetTime          | time + offset         | offset only
   *  Instant             | machine epoch nanos   | UTC by definition
   *  Duration            | time-based amount     | e.g. 3 hours 30 min
   *  Period              | date-based amount     | e.g. 2 years 3 months
   *  ZoneId              | time-zone ID          | "Europe/London", "America/New_York"
   *  ZoneOffset          | fixed offset          | +05:30, -07:00
   *  DateTimeFormatter   | format / parse pattern| ---
   */

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌2. INITIALISING DATE & TIME OBJECTS
  // ═══════════════════════════════════════════════════════════════════════════
  static void section2_Initialising() {
    System.out.println("\n══════ 2. INITIALISING DATE & TIME ══════");

    // ── LocalDate ────────────────────────────────────────────────────────
    LocalDate today = LocalDate.now();                    // system default zone
    LocalDate specific = LocalDate.of(2025, 6, 1);          // year, month, day
    LocalDate fromString = LocalDate.parse("2025-06-01");     // ISO-8601
    LocalDate fromOrdinal = LocalDate.ofYearDay(2025, 152);    // day-of-year
    LocalDate fromEpoch = LocalDate.ofEpochDay(20_000);      // days since 1970-01-01
    LocalDate withEnum = LocalDate.of(2025, Month.JUNE, 1); // Month enum

    System.out.println("today       = " + today);
    System.out.println("specific    = " + specific);
    System.out.println("fromOrdinal = " + fromOrdinal);
    System.out.println("fromEpoch   = " + fromEpoch);

    // ── LocalTime ────────────────────────────────────────────────────────
    LocalTime now = LocalTime.now();
    LocalTime t1 = LocalTime.of(14, 30);              // hour, minute
    LocalTime t2 = LocalTime.of(14, 30, 45);          // + second
    LocalTime t3 = LocalTime.of(14, 30, 45, 500_000_000); // + nanos
    LocalTime fromStr = LocalTime.parse("14:30:45");
    LocalTime midnight = LocalTime.MIDNIGHT;                // 00:00
    LocalTime noon = LocalTime.NOON;                    // 12:00

    System.out.println("now    = " + now);
    System.out.println("t3     = " + t3);
    System.out.println("noon   = " + noon);

    // ── LocalDateTime ────────────────────────────────────────────────────
    LocalDateTime ldt1 = LocalDateTime.now();
    LocalDateTime ldt2 = LocalDateTime.of(2025, 6, 1, 14, 30, 0);
    LocalDateTime ldt3 = LocalDateTime.of(specific, t1);       // combine date + time
    LocalDateTime ldt4 = LocalDateTime.parse("2025-06-01T14:30:00");

    System.out.println("ldt2 = " + ldt2);

    // ── ZonedDateTime ────────────────────────────────────────────────────
    ZoneId london = ZoneId.of("Europe/London");
    ZoneId istanbul = ZoneId.of("Europe/Istanbul");
    ZoneId ny = ZoneId.of("America/New_York");
    ZoneId utc = ZoneId.of("UTC");

    ZonedDateTime zdt1 = ZonedDateTime.now(london);
    ZonedDateTime zdt2 = ZonedDateTime.of(ldt2, london);
    ZonedDateTime zdt3 = ZonedDateTime.of(2025, 6, 1, 14, 30, 0, 0, london);
    ZonedDateTime zdt4 = ZonedDateTime.parse("2025-06-01T14:30:00+03:00[Europe/Istanbul]");

    System.out.println("zdt1 (London)   = " + zdt1);
    System.out.println("zdt2 (London)   = " + zdt2);

    // ── OffsetDateTime ───────────────────────────────────────────────────
    ZoneOffset offset = ZoneOffset.of("+03:00");
    ZoneOffset utcOffset = ZoneOffset.UTC;

    OffsetDateTime odt1 = OffsetDateTime.now(offset);
    OffsetDateTime odt2 = OffsetDateTime.of(ldt2, offset);
    OffsetDateTime odt3 = OffsetDateTime.parse("2025-06-01T14:30:00+03:00");

    System.out.println("odt2 = " + odt2);

    // ── Instant ──────────────────────────────────────────────────────────
    Instant inst1 = Instant.now();                         // current UTC
    Instant inst2 = Instant.ofEpochSecond(1_717_200_000L);
    Instant inst3 = Instant.ofEpochMilli(1_717_200_000_000L);
    Instant inst4 = Instant.parse("2025-06-01T11:30:00Z");

    System.out.println("inst1 = " + inst1);
    System.out.println("epoch seconds of inst1 = " + inst1.getEpochSecond());
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌3. TIME ZONES & ZONE IDs
  // ═══════════════════════════════════════════════════════════════════════════
  static void section3_TimeZones() {
    System.out.println("\n══════ 3. TIME ZONES & ZONE IDS ══════");

    // List all available zone IDs (there are ~600)
    Set<String> allZones = ZoneId.getAvailableZoneIds();
    System.out.println("Total zones available: " + allZones.size());

    // Filter to common regions
    allZones.stream()
        .filter(z -> z.startsWith("Europe/") || z.startsWith("America/") || z.startsWith("Asia/"))
        .sorted()
        .limit(12)
        .forEach(z -> System.out.println("  " + z));

    // System default zone
    ZoneId systemDefault = ZoneId.systemDefault();
    System.out.println("System default zone: " + systemDefault);

    // Creating ZoneId variants
    ZoneId fromId = ZoneId.of("Asia/Istanbul");
    ZoneId fromAlias = ZoneId.of("Turkey");                   // legacy alias
    ZoneId fromOffset = ZoneId.ofOffset("UTC", ZoneOffset.of("+05:30")); // UTC+05:30

    System.out.println("fromId     = " + fromId);
    System.out.println("fromAlias  = " + fromAlias);
    System.out.println("fromOffset = " + fromOffset);

    // Getting offset at a specific instant
    ZoneId london = ZoneId.of("Europe/London");
    ZoneOffset summerOffset = london.getRules().getOffset(LocalDateTime.of(2025, 7, 1, 12, 0));
    ZoneOffset winterOffset = london.getRules().getOffset(LocalDateTime.of(2025, 1, 1, 12, 0));
    System.out.println("London summer offset: " + summerOffset);  // +01:00 (BST)
    System.out.println("London winter offset: " + winterOffset);  // +00:00 (GMT)

    // DST transitions
    ZoneId nyZone = ZoneId.of("America/New_York");
    ZoneRules nyRules = nyZone.getRules();
    System.out.println("NY DST transitions in 2025:");
    nyRules.getTransitions().stream()
        .filter(t -> t.getInstant().isAfter(Instant.parse("2025-01-01T00:00:00Z"))
            && t.getInstant().isBefore(Instant.parse("2026-01-01T00:00:00Z")))
        .forEach(t -> System.out.println(
            "  " + t.getDateTimeAfter() + " → offset: " + t.getOffsetAfter()));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌4. CONVERTING BETWEEN TIME ZONES
  // ═══════════════════════════════════════════════════════════════════════════
  static void section4_ZoneConversions() {
    System.out.println("\n══════ 4. CONVERTING BETWEEN TIME ZONES ══════");

    ZoneId london = ZoneId.of("Europe/London");
    ZoneId istanbul = ZoneId.of("Europe/Istanbul");
    ZoneId ny = ZoneId.of("America/New_York");
    ZoneId tokyo = ZoneId.of("Asia/Tokyo");
    ZoneId sydney = ZoneId.of("Australia/Sydney");

    // ── ZonedDateTime → another zone ────────────────────────────────────
    ZonedDateTime londonTime = ZonedDateTime.of(2025, 6, 1, 14, 30, 0, 0, london);
    System.out.println("London   : " + londonTime);

    ZonedDateTime istanbulTime = londonTime.withZoneSameInstant(istanbul);
    ZonedDateTime nyTime = londonTime.withZoneSameInstant(ny);
    ZonedDateTime tokyoTime = londonTime.withZoneSameInstant(tokyo);
    ZonedDateTime sydneyTime = londonTime.withZoneSameInstant(sydney);

    System.out.println("Istanbul : " + istanbulTime);  // +3 → 17:30
    System.out.println("New York : " + nyTime);         // -4 → 10:30
    System.out.println("Tokyo    : " + tokyoTime);      // +9 → 23:30
    System.out.println("Sydney   : " + sydneyTime);     // +10 → 00:30+1day

    // ── withZoneSameLocal vs withZoneSameInstant ────────────────────────
    // withZoneSameInstant: same point in time, different clock display  ← usually what you want
    // withZoneSameLocal:   same clock display, different point in time

    ZonedDateTime sameInstant = londonTime.withZoneSameInstant(istanbul);
    ZonedDateTime sameLocal = londonTime.withZoneSameLocal(istanbul);

    System.out.println("\nSame instant in Istanbul : " + sameInstant); // 17:30 Istanbul
    System.out.println(
        "Same local   in Istanbul : " + sameLocal);    // 14:30 Istanbul (DIFFERENT moment!)

    // ── Instant as universal intermediary ───────────────────────────────
    Instant now = Instant.now();
    ZonedDateTime inLondon = now.atZone(london);
    ZonedDateTime inIstanbul = now.atZone(istanbul);
    ZonedDateTime inNy = now.atZone(ny);

    System.out.println("\nSame instant expressed in different zones:");
    System.out.println("  London   : " + inLondon);
    System.out.println("  Istanbul : " + inIstanbul);
    System.out.println("  New York : " + inNy);

    // ── LocalDateTime → ZonedDateTime (tricky!) ─────────────────────────
    LocalDateTime local = LocalDateTime.of(2025, 3, 30, 1, 30, 0); // DST gap in London
    ZonedDateTime zonedNormal = local.atZone(london);
    // Java automatically adjusts for DST gaps
    System.out.println("\nDST gap example (2025-03-30 01:30 London): " + zonedNormal);

    // Safe conversion: atZone + withLaterOffsetAtOverlap / withEarlierOffsetAtOverlap
    ZonedDateTime atOverlap = ZonedDateTime.ofLocal(local, london, null);
    System.out.println("atOverlap (default): " + atOverlap);

    // ── OffsetDateTime conversions ───────────────────────────────────────
    OffsetDateTime odt = OffsetDateTime.of(2025, 6, 1, 14, 30, 0, 0, ZoneOffset.of("+03:00"));
    ZonedDateTime zdtFromOdt = odt.atZoneSameInstant(london);
    System.out.println("\nOffsetDateTime → ZonedDateTime (London): " + zdtFromOdt);

    // ── Instant conversions ──────────────────────────────────────────────
    ZonedDateTime zdt = ZonedDateTime.of(2025, 6, 1, 14, 30, 0, 0, london);
    Instant inst = zdt.toInstant();
    ZonedDateTime back = inst.atZone(istanbul);
    System.out.println("\nZonedDateTime → Instant → ZonedDateTime(Istanbul): " + back);

    // ── Utility: display same meeting time across time zones ─────────────
    ZonedDateTime meeting = ZonedDateTime.of(2025, 6, 10, 10, 0, 0, 0, ny);
    System.out.println("\n── Meeting at " + meeting + " ──");
    Map<String, ZoneId> offices = Map.of(
        "New York", ny,
        "London", london,
        "Istanbul", istanbul,
        "Tokyo", tokyo
    );
    offices.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(e -> System.out.printf("  %-10s : %s%n",
            e.getKey(), meeting.withZoneSameInstant(e.getValue())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"))));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌5. DIFFERENCE BETWEEN TWO DATES / TIMES
  // ═══════════════════════════════════════════════════════════════════════════
  static void section5_Differences() {
    System.out.println("\n══════ 5. DIFFERENCES BETWEEN DATES/TIMES ══════");

    // ── Period: date-based difference (years, months, days) ─────────────
    LocalDate start = LocalDate.of(2022, 3, 15);
    LocalDate end = LocalDate.of(2025, 6, 10);

    Period period = Period.between(start, end);
    System.out.println("Period: " + period);
    System.out.printf("  Years: %d, Months: %d, Days: %d%n",
        period.getYears(), period.getMonths(), period.getDays());
    System.out.println("  Total months: " + period.toTotalMonths());

    // Total days using ChronoUnit
    long totalDays = ChronoUnit.DAYS.between(start, end);
    long totalWeeks = ChronoUnit.WEEKS.between(start, end);
    long totalMonths = ChronoUnit.MONTHS.between(start, end);
    long totalYears = ChronoUnit.YEARS.between(start, end);
    System.out.printf("  Total: %d days / %d weeks / %d months / %d years%n",
        totalDays, totalWeeks, totalMonths, totalYears);

    // ── Duration: time-based difference (hours, minutes, seconds, nanos) ─
    LocalTime t1 = LocalTime.of(9, 0, 0);
    LocalTime t2 = LocalTime.of(17, 45, 30);

    Duration duration = Duration.between(t1, t2);
    System.out.println("\nDuration: " + duration);
    System.out.printf("  Hours: %d, Minutes: %d, Seconds: %d%n",
        duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    System.out.println("  Total minutes: " + duration.toMinutes());
    System.out.println("  Total seconds: " + duration.toSeconds());

    // ── Duration between LocalDateTimes ─────────────────────────────────
    LocalDateTime ldt1 = LocalDateTime.of(2025, 6, 1, 8, 0, 0);
    LocalDateTime ldt2 = LocalDateTime.of(2025, 6, 3, 17, 30, 0);
    Duration dur2 = Duration.between(ldt1, ldt2);
    System.out.println("\nDuration between two LocalDateTimes: " + dur2);
    System.out.printf("  Total hours: %d, Days part: %d%n",
        dur2.toHours(), dur2.toDaysPart());

    // ── Duration between ZonedDateTimes (DST-aware!) ─────────────────────
    ZoneId london = ZoneId.of("Europe/London");
    ZonedDateTime zdt1 = ZonedDateTime.of(2025, 3, 29, 12, 0, 0, 0, london); // before DST
    ZonedDateTime zdt2 = ZonedDateTime.of(2025, 3, 31, 12, 0, 0, 0, london); // after DST

    Duration dstDuration = Duration.between(zdt1, zdt2);
    System.out.println("\nDuration across DST change (London):");
    System.out.println("  " + dstDuration.toHours() + " hours (not 48 due to DST spring forward)");

    // ── Difference in specific units ────────────────────────────────────
    ZonedDateTime from = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime to = ZonedDateTime.of(2025, 12, 31, 23, 59, 59, 0, ZoneId.of("UTC"));

    System.out.println("\nDifference in specific units (UTC, whole year 2025):");
    for (ChronoUnit unit : new ChronoUnit[] {ChronoUnit.YEARS, ChronoUnit.MONTHS,
        ChronoUnit.WEEKS, ChronoUnit.DAYS, ChronoUnit.HOURS, ChronoUnit.MINUTES}) {
      System.out.printf("  %-10s: %d%n", unit, unit.between(from, to));
    }

    // ── Between two Instants ─────────────────────────────────────────────
    Instant i1 = Instant.parse("2025-06-01T08:00:00Z");
    Instant i2 = Instant.parse("2025-06-01T20:30:15.500Z");
    Duration instDur = Duration.between(i1, i2);
    System.out.println("\nInstant difference: " + instDur);
    System.out.printf("  %d hours %d minutes %d seconds %d ms%n",
        instDur.toHours(), instDur.toMinutesPart(),
        instDur.toSecondsPart(), instDur.toMillisPart());

    // ── Formatting a duration human-readable ────────────────────────────
    Duration d = Duration.ofHours(2).plusMinutes(34).plusSeconds(15);
    System.out.printf("%nHuman readable: %02d:%02d:%02d%n",
        d.toHours(), d.toMinutesPart(), d.toSecondsPart());
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌6. FORMATTING  (DateTime → String)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section6_Formatting() {
    System.out.println("\n══════ 6. FORMATTING (DateTime → String) ══════");

    ZonedDateTime zdt = ZonedDateTime.of(2025, 6, 1, 14, 30, 45, 500_000_000,
        ZoneId.of("Europe/Istanbul"));

    // ── Predefined ISO formatters ────────────────────────────────────────
    System.out.println("ISO_LOCAL_DATE       : " + DateTimeFormatter.ISO_LOCAL_DATE.format(zdt));
    System.out.println("ISO_LOCAL_TIME       : " + DateTimeFormatter.ISO_LOCAL_TIME.format(zdt));
    System.out.println(
        "ISO_LOCAL_DATE_TIME  : " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zdt));
    System.out.println(
        "ISO_OFFSET_DATE_TIME : " + DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zdt));
    System.out.println(
        "ISO_ZONED_DATE_TIME  : " + DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zdt));
    System.out.println("ISO_INSTANT          : " + DateTimeFormatter.ISO_INSTANT.format(zdt));
    System.out.println(
        "RFC_1123_DATE_TIME   : " + DateTimeFormatter.RFC_1123_DATE_TIME.format(zdt));

    // ── Custom patterns ──────────────────────────────────────────────────
    // Pattern symbols:
    //  y = year,  M = month,  d = day
    //  H = hour(0-23), h = hour(1-12), m = minute, s = second, S = fraction-of-second
    //  E = day-of-week, MMMM = full month name, MMM = short month name
    //  z = time zone name, Z = offset, VV = zone ID
    //  a = AM/PM, n = nano

    String[][] patterns = {
        {"dd/MM/yyyy", "British date"},
        {"MM/dd/yyyy", "US date"},
        {"yyyy-MM-dd", "ISO date"},
        {"dd MMM yyyy", "Readable date"},
        {"EEEE, dd MMMM yyyy", "Full date with day name"},
        {"HH:mm:ss", "24h time"},
        {"hh:mm:ss a", "12h time with AM/PM"},
        {"HH:mm:ss.SSS", "Time with milliseconds"},
        {"HH:mm:ss.SSSSSSSSS", "Time with nanoseconds"},
        {"dd/MM/yyyy HH:mm:ss", "DateTime British"},
        {"yyyy-MM-dd'T'HH:mm:ssXXX", "ISO with offset"},
        {"dd MMM yyyy, HH:mm z", "Readable with zone"},
        {"EEEE, MMMM d, yyyy 'at' h:mm a", "US newspaper style"},
        {"yyyyMMddHHmmss", "Compact timestamp"},
        {"dd-MM-yyyy HH:mm:ss VV", "With zone ID"},
    };

    for (String[] p : patterns) {
      DateTimeFormatter fmt = DateTimeFormatter.ofPattern(p[0]);
      System.out.printf("  %-40s → %s%n", p[1], zdt.format(fmt));
    }

    // ── Locale-specific formatting ───────────────────────────────────────
    System.out.println("\nLocale-specific:");
    for (Locale locale : new Locale[] {Locale.UK, Locale.US, new Locale("tr", "TR"),
        Locale.JAPAN}) {
      DateTimeFormatter locFmt = DateTimeFormatter
          .ofLocalizedDateTime(FormatStyle.LONG)
          .withLocale(locale);
      System.out.printf("  %-10s: %s%n", locale, zdt.format(locFmt));
    }

    // ── FormatStyle combinations ─────────────────────────────────────────
    System.out.println("\nFormatStyle combinations (UK locale):");
    for (FormatStyle dateStyle : FormatStyle.values()) {
      for (FormatStyle timeStyle : FormatStyle.values()) {
        try {
          DateTimeFormatter f = DateTimeFormatter
              .ofLocalizedDateTime(dateStyle, timeStyle)
              .withLocale(Locale.UK);
          System.out.printf("  [%s/%s] %s%n", dateStyle, timeStyle, zdt.format(f));
        } catch (Exception ignored) {
        }
      }
    }

    // ── DateTimeFormatterBuilder for complex patterns ────────────────────
    DateTimeFormatter complex = new DateTimeFormatterBuilder()
        .appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
        .appendLiteral(", ")
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendLiteral(" ")
        .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
        .appendLiteral(" ")
        .appendValue(ChronoField.YEAR, 4)
        .appendLiteral(" at ")
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .appendLiteral(":")
        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
        .appendLiteral(":")
        .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
        .appendLiteral(" [")
        .appendZoneId()
        .appendLiteral("]")
        .toFormatter(Locale.ENGLISH);

    System.out.println("\nComplex builder: " + zdt.format(complex));

    // ── Optional sections in pattern ─────────────────────────────────────
    DateTimeFormatter withOptional = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd[' 'HH:mm[:ss]]");
    System.out.println("Optional sections: " + LocalDate.of(2025, 6, 1).format(withOptional));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌7. PARSING  (String → DateTime)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section7_Parsing() {
    System.out.println("\n══════ 7. PARSING (String → DateTime) ══════");

    // ── LocalDate parsing ────────────────────────────────────────────────
    LocalDate d1 = LocalDate.parse("2025-06-01");                     // ISO default
    LocalDate d2 = LocalDate.parse("01/06/2025",
        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    LocalDate d3 = LocalDate.parse("June 1, 2025",
        DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH));
    LocalDate d4 = LocalDate.parse("01-Jun-2025",
        DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH));
    System.out.println("d1=" + d1 + "  d2=" + d2 + "  d3=" + d3 + "  d4=" + d4);

    // ── LocalTime parsing ────────────────────────────────────────────────
    LocalTime t1 = LocalTime.parse("14:30:45");
    LocalTime t2 = LocalTime.parse("14:30");
    LocalTime t3 = LocalTime.parse("02:30 PM",
        DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
    System.out.println("t1=" + t1 + "  t2=" + t2 + "  t3=" + t3);

    // ── LocalDateTime parsing ────────────────────────────────────────────
    LocalDateTime ldt1 = LocalDateTime.parse("2025-06-01T14:30:45");
    LocalDateTime ldt2 = LocalDateTime.parse("01/06/2025 14:30:45",
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    System.out.println("ldt1=" + ldt1 + "  ldt2=" + ldt2);

    // ── ZonedDateTime parsing ────────────────────────────────────────────
    ZonedDateTime zdt1 = ZonedDateTime.parse("2025-06-01T14:30:00+03:00[Europe/Istanbul]");
    ZonedDateTime zdt2 = ZonedDateTime.parse("2025-06-01T14:30:00Z");
    ZonedDateTime zdt3 = ZonedDateTime.parse("2025-06-01T14:30:00+01:00");
    System.out.println("zdt1=" + zdt1);
    System.out.println("zdt2=" + zdt2);

    // ── OffsetDateTime parsing ───────────────────────────────────────────
    OffsetDateTime odt = OffsetDateTime.parse("2025-06-01T14:30:00+03:00");
    System.out.println("odt=" + odt);

    // ── Instant parsing ──────────────────────────────────────────────────
    Instant inst = Instant.parse("2025-06-01T11:30:00Z");
    System.out.println("inst=" + inst);

    // ── Multi-format / lenient parsing with DateTimeFormatterBuilder ─────
    DateTimeFormatter flexFmt = new DateTimeFormatterBuilder()
        .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
        .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        .toFormatter();

    String[] inputs = {
        "2025-06-01 14:30:45",
        "01/06/2025 14:30:45",
        "01-06-2025 14:30",
        "2025-06-01T14:30:45"
    };
    for (String input : inputs) {
      LocalDateTime parsed = LocalDateTime.parse(input, flexFmt);
      System.out.println("Flex parsed: '" + input + "' → " + parsed);
    }

    // ── Error handling ───────────────────────────────────────────────────
    try {
      LocalDate.parse("not-a-date");
    } catch (DateTimeParseException e) {
      System.out.println("\nParse error: " + e.getMessage());
      System.out.println("Error index: " + e.getErrorIndex());
    }
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌8. ARITHMETIC  (plus / minus)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section8_Arithmetic() {
    System.out.println("\n══════ 8. ARITHMETIC ══════");

    LocalDate date = LocalDate.of(2025, 6, 1);
    System.out.println("Original: " + date);
    System.out.println("  +1 day      : " + date.plusDays(1));
    System.out.println("  +2 weeks    : " + date.plusWeeks(2));
    System.out.println("  +3 months   : " + date.plusMonths(3));
    System.out.println("  +1 year     : " + date.plusYears(1));
    System.out.println("  -5 days     : " + date.minusDays(5));
    System.out.println("  plus Period : " + date.plus(Period.of(1, 2, 3)));

    LocalDateTime ldt = LocalDateTime.of(2025, 6, 1, 14, 30, 0);
    System.out.println("\nDateTime: " + ldt);
    System.out.println("  +90 min     : " + ldt.plusMinutes(90));
    System.out.println("  +3 hours    : " + ldt.plusHours(3));
    System.out.println("  +Duration   : " + ldt.plus(Duration.ofHours(2).plusMinutes(30)));

    ZonedDateTime zdt = ZonedDateTime.of(2025, 3, 29, 12, 0, 0, 0, ZoneId.of("Europe/London"));
    System.out.println("\nZonedDateTime (DST aware): " + zdt);
    // +1 day crosses the DST boundary; clock will adjust offset
    System.out.println("  +1 day (DST adjusted): " + zdt.plusDays(1));
    System.out.println("  +24 hours (exact):     " + zdt.plusHours(24)); // may differ from +1 day

    // ── using TemporalAdjusters ──────────────────────────────────────────
    System.out.println("\nTemporalAdjusters:");
    LocalDate d = LocalDate.of(2025, 6, 15);
    System.out.println("  firstDayOfMonth    : " + d.with(TemporalAdjusters.firstDayOfMonth()));
    System.out.println("  lastDayOfMonth     : " + d.with(TemporalAdjusters.lastDayOfMonth()));
    System.out.println("  firstDayOfNextMonth: " + d.with(TemporalAdjusters.firstDayOfNextMonth()));
    System.out.println("  firstDayOfYear     : " + d.with(TemporalAdjusters.firstDayOfYear()));
    System.out.println("  lastDayOfYear      : " + d.with(TemporalAdjusters.lastDayOfYear()));
    System.out.println(
        "  next MONDAY        : " + d.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
    System.out.println(
        "  nextOrSame SUNDAY  : " + d.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
    System.out.println(
        "  previous FRIDAY    : " + d.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)));
    System.out.println(
        "  firstInMonth MON   : " + d.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
    System.out.println("  dayOfWeekInMonth(3,MON): " +
        d.with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY)));

    // ── Custom TemporalAdjuster (next business day) ──────────────────────
    TemporalAdjuster nextBusinessDay = temporal -> {
      LocalDate ld = LocalDate.from(temporal);
      DayOfWeek dow = ld.getDayOfWeek();
      int daysToAdd = switch (dow) {
        case FRIDAY -> 3;
        case SATURDAY -> 2;
        default -> 1;
      };
      return ld.plusDays(daysToAdd);
    };
    System.out.println("  nextBusinessDay    : " + d.with(nextBusinessDay));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌9. COMPARISONS & ORDERING
  // ═══════════════════════════════════════════════════════════════════════════
  static void section9_Comparisons() {
    System.out.println("\n══════ 9. COMPARISONS & ORDERING ══════");

    LocalDate d1 = LocalDate.of(2025, 1, 1);
    LocalDate d2 = LocalDate.of(2025, 6, 15);
    LocalDate d3 = LocalDate.of(2025, 1, 1);

    System.out.println("d1.isBefore(d2) = " + d1.isBefore(d2));   // true
    System.out.println("d1.isAfter(d2)  = " + d1.isAfter(d2));    // false
    System.out.println("d1.isEqual(d3)  = " + d1.isEqual(d3));    // true
    System.out.println("d1.equals(d3)   = " + d1.equals(d3));     // true
    System.out.println("d1.compareTo(d2)= " + d1.compareTo(d2));  // negative

    // ── Checking ranges ──────────────────────────────────────────────────
    LocalDate rangeStart = LocalDate.of(2025, 3, 1);
    LocalDate rangeEnd = LocalDate.of(2025, 9, 30);
    LocalDate check = LocalDate.of(2025, 6, 15);
    boolean inRange = !check.isBefore(rangeStart) && !check.isAfter(rangeEnd);
    System.out.println(
        "\n" + check + " in range [" + rangeStart + ", " + rangeEnd + "]: " + inRange);

    // ── ZonedDateTime comparison (compares instant, not local time) ──────
    ZoneId london = ZoneId.of("Europe/London");
    ZoneId istanbul = ZoneId.of("Europe/Istanbul");

    ZonedDateTime zdt1 = ZonedDateTime.of(2025, 6, 1, 14, 0, 0, 0, london);
    ZonedDateTime zdt2 = ZonedDateTime.of(2025, 6, 1, 16, 0, 0, 0, istanbul);

    // 14:00 London BST = 16:00 Istanbul — same instant!
    System.out.println("\n14:00 London == 16:00 Istanbul (same instant)? "
        + zdt1.isEqual(zdt2));

    // Sorting a list of ZonedDateTimes
    List<ZonedDateTime> meetings = List.of(
        ZonedDateTime.of(2025, 6, 1, 16, 0, 0, 0, istanbul),
        ZonedDateTime.of(2025, 6, 1, 14, 0, 0, 0, london),
        ZonedDateTime.of(2025, 6, 1, 9, 0, 0, 0, ZoneId.of("America/New_York"))
    );
    meetings.stream()
        .sorted()   // Comparable<ZonedDateTime> compares by instant
        .forEach(z -> System.out.println("  " + z));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌10. TRUNCATION, ROUNDING & ALIGNMENT
  // ═══════════════════════════════════════════════════════════════════════════
  static void section10_Truncation() {
    System.out.println("\n══════ 10. TRUNCATION & ROUNDING ══════");

    LocalDateTime ldt = LocalDateTime.of(2025, 6, 1, 14, 37, 45, 987_654_321);
    System.out.println("Original    : " + ldt);
    System.out.println("To minutes  : " + ldt.truncatedTo(ChronoUnit.MINUTES));
    System.out.println("To hours    : " + ldt.truncatedTo(ChronoUnit.HOURS));
    System.out.println("To days     : " + ldt.truncatedTo(ChronoUnit.DAYS));
    System.out.println("To seconds  : " + ldt.truncatedTo(ChronoUnit.SECONDS));
    System.out.println("To millis   : " + ldt.truncatedTo(ChronoUnit.MILLIS));

    // Rounding up to next hour
    LocalDateTime rounded = ldt.truncatedTo(ChronoUnit.HOURS).plusHours(1);
    System.out.println("Next hour   : " + rounded);

    // Rounding to nearest 15 minutes
    int minutes = ldt.getMinute();
    int roundedMin = (int) Math.round(minutes / 15.0) * 15;
    LocalDateTime nearest15 = ldt.withMinute(roundedMin % 60)
        .withSecond(0).withNano(0)
        .plusHours(roundedMin / 60);
    System.out.println("Nearest 15m : " + nearest15);

    // Start and end of day
    LocalDate day = LocalDate.of(2025, 6, 1);
    LocalDateTime startOfDay = day.atStartOfDay();
    LocalDateTime endOfDay = day.atTime(LocalTime.MAX);   // 23:59:59.999999999
    System.out.println("Start of day: " + startOfDay);
    System.out.println("End of day  : " + endOfDay);
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌11. DAY-OF-WEEK, MONTH, QUARTER HELPERS
  // ═══════════════════════════════════════════════════════════════════════════
  static void section11_Helpers() {
    System.out.println("\n══════ 11. DAY/MONTH/QUARTER HELPERS ══════");

    LocalDate d = LocalDate.of(2025, 6, 15);

    // Day info
    System.out.println("DayOfWeek    : " + d.getDayOfWeek());           // SUNDAY
    System.out.println("DayOfWeek #  : " + d.getDayOfWeek().getValue());// 7
    System.out.println("DayOfYear    : " + d.getDayOfYear());           // 166
    System.out.println("DayOfMonth   : " + d.getDayOfMonth());
    System.out.println("Is weekend?  : " + (d.getDayOfWeek().getValue() >= 6));

    // Month info
    System.out.println("Month        : " + d.getMonth());               // JUNE
    System.out.println("Month #      : " + d.getMonthValue());          // 6
    System.out.println("Month length : " + d.getMonth().length(d.isLeapYear())); // 30
    System.out.println("Is leap year?: " + d.isLeapYear());

    // Quarter
    int quarter = (d.getMonthValue() - 1) / 3 + 1;
    LocalDate quarterStart =
        d.with(TemporalAdjusters.firstDayOfMonth()).withMonth((quarter - 1) * 3 + 1);
    LocalDate quarterEnd = quarterStart.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
    System.out.println("Quarter      : Q" + quarter);
    System.out.println("Quarter start: " + quarterStart);
    System.out.println("Quarter end  : " + quarterEnd);

    // Week of year (ISO)
    System.out.println("ISO week #   : " + d.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
    System.out.println("ISO week year: " + d.get(IsoFields.WEEK_BASED_YEAR));

    // Iterating days in a month
    System.out.println("\nBusinesss days in June 2025:");
    LocalDate firstOfJune = LocalDate.of(2025, 6, 1);
    long businessDays = firstOfJune.datesUntil(firstOfJune.plusMonths(1))
        .filter(ld -> ld.getDayOfWeek() != DayOfWeek.SATURDAY
            && ld.getDayOfWeek() != DayOfWeek.SUNDAY)
        .count();
    System.out.println("  Count: " + businessDays);

    // datesUntil (Java 9+) — stream of dates
    System.out.println("Every Monday in June 2025:");
    firstOfJune.datesUntil(firstOfJune.plusMonths(1))
        .filter(ld -> ld.getDayOfWeek() == DayOfWeek.MONDAY)
        .forEach(ld -> System.out.println("  " + ld));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌12. LEGACY java.util.Date ↔ java.time BRIDGE
  // ═══════════════════════════════════════════════════════════════════════════
  static void section12_LegacyBridge() {
    System.out.println("\n══════ 12. LEGACY java.util.Date ↔ java.time BRIDGE ══════");

    // java.util.Date → java.time
    java.util.Date legacyDate = new java.util.Date();
    Instant instant = legacyDate.toInstant();
    ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
    LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

    System.out.println("legacy Date : " + legacyDate);
    System.out.println("→ Instant   : " + instant);
    System.out.println("→ ZonedDT   : " + zdt);
    System.out.println("→ LocalDT   : " + ldt);

    // java.time → java.util.Date
    ZonedDateTime modern = ZonedDateTime.of(2025, 6, 1, 14, 30, 0, 0, ZoneId.of("Europe/London"));
    java.util.Date fromModern = java.util.Date.from(modern.toInstant());
    System.out.println("\nModern ZDT  : " + modern);
    System.out.println("→ legacy    : " + fromModern);

    // java.util.Calendar
    java.util.Calendar cal = java.util.Calendar.getInstance();
    Instant calInst = cal.toInstant();
    System.out.println("\nCalendar → Instant: " + calInst);

    // java.sql.Date / java.sql.Timestamp
    java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.of(2025, 6, 1));
    LocalDate back = sqlDate.toLocalDate();
    System.out.println("\nsql.Date → LocalDate: " + back);

    java.sql.Timestamp ts = java.sql.Timestamp.valueOf(LocalDateTime.of(2025, 6, 1, 14, 30));
    LocalDateTime tsLdt = ts.toLocalDateTime();
    System.out.println("sql.Timestamp → LocalDateTime: " + tsLdt);

    // From LocalDate to sql.Date
    java.sql.Date sqlFromLocal = java.sql.Date.valueOf(LocalDate.now());
    System.out.println("LocalDate → sql.Date: " + sqlFromLocal);
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌13. EDGE CASES & COMMON PITFALLS
  // ═══════════════════════════════════════════════════════════════════════════
  static void section13_EdgeCases() {
    System.out.println("\n══════ 13. EDGE CASES & COMMON PITFALLS ══════");

    // ── Pitfall 1: LocalDateTime has NO timezone — don't use for absolute times ──
    LocalDateTime ldt = LocalDateTime.of(2025, 6, 1, 12, 0);
    // This is ambiguous — 12:00 where?! Always use ZonedDateTime for storage/APIs

    // ── Pitfall 2: Month is 1-based in java.time (unlike Calendar!) ──────
    LocalDate correct = LocalDate.of(2025, 6, 1);   // June → 6
    LocalDate viaEnum = LocalDate.of(2025, Month.JUNE, 1);
    System.out.println("June correctly: " + correct + " / " + viaEnum);

    // ── Pitfall 3: Year 0 exists in java.time (= 1 BC) ──────────────────
    LocalDate yearZero = LocalDate.of(0, 1, 1);
    System.out.println("Year 0: " + yearZero);  // 0000-01-01

    // ── Pitfall 4: ZonedDateTime +1 day vs +24 hours across DST ─────────
    ZoneId london = ZoneId.of("Europe/London");
    ZonedDateTime beforeDST = ZonedDateTime.of(2025, 3, 29, 12, 0, 0, 0, london);
    System.out.println("\nBefore DST : " + beforeDST);
    System.out.println("+1 day     : " + beforeDST.plusDays(1));    // 12:00+01:00 (calendar day)
    System.out.println("+24 hours  : " + beforeDST.plusHours(24));  // 13:00+01:00 (actual time)

    // ── Pitfall 5: Comparing ZonedDateTimes across zones ─────────────────
    // equals() compares all fields including zone; isEqual() compares instant only
    ZonedDateTime z1 = ZonedDateTime.of(2025, 6, 1, 12, 0, 0, 0, london);
    ZonedDateTime z2 = z1.withZoneSameInstant(ZoneId.of("UTC"));
    System.out.println("\nz1.equals(z2)   = " + z1.equals(z2));   // FALSE — different zones
    System.out.println("z1.isEqual(z2)  = " + z1.isEqual(z2));   // TRUE  — same instant

    // ── Pitfall 6: Period.between on different year-lengths ───────────────
    LocalDate leap1 = LocalDate.of(2024, 1, 1);  // leap year
    LocalDate leap2 = LocalDate.of(2025, 1, 1);  // not leap
    System.out.println("\nPeriod between leap boundaries: " + Period.between(leap1, leap2));
    System.out.println(
        "Days between:                  " + ChronoUnit.DAYS.between(leap1, leap2)); // 366

    // ── Pitfall 7: Instant.now() precision ───────────────────────────────
    Instant i1 = Instant.now();
    Instant i2 = Instant.now();
    System.out.println("\nTwo Instants are equal? " + i1.equals(i2));
    // Usually false — nanosecond precision

    // ── Pitfall 8: Ambiguous times during DST fall-back ──────────────────
    // In autumn, 01:30 occurs twice in London (GMT → BST rollback)
    LocalDateTime ambiguous = LocalDateTime.of(2025, 10, 26, 1, 30, 0);
    ZonedDateTime earlier = ZonedDateTime.ofLocal(ambiguous, london,
        london.getRules().getValidOffsets(ambiguous).get(0));  // BST +01:00
    ZonedDateTime later = ZonedDateTime.ofLocal(ambiguous, london,
        london.getRules().getValidOffsets(ambiguous).get(1));  // GMT +00:00
    System.out.println("\nAmbiguous DST time (fall-back):");
    System.out.println("  Earlier (BST): " + earlier);
    System.out.println("  Later   (GMT): " + later);

    // ── Useful constants ─────────────────────────────────────────────────
    System.out.println("\nUseful constants:");
    System.out.println("LocalDate.MIN  : " + LocalDate.MIN);     // -999999999-01-01
    System.out.println("LocalDate.MAX  : " + LocalDate.MAX);     // +999999999-12-31
    System.out.println("Instant.EPOCH  : " + Instant.EPOCH);     // 1970-01-01T00:00:00Z
    System.out.println("Instant.MIN    : " + Instant.MIN);
    System.out.println("Instant.MAX    : " + Instant.MAX);
    System.out.println("LocalTime.MIN  : " + LocalTime.MIN);     // 00:00
    System.out.println("LocalTime.MAX  : " + LocalTime.MAX);     // 23:59:59.999999999
    System.out.println("LocalTime.NOON : " + LocalTime.NOON);    // 12:00
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌MAIN — run all sections
  // ═══════════════════════════════════════════════════════════════════════════
  public static void main(String[] args) {
    section2_Initialising();
    section3_TimeZones();
    section4_ZoneConversions();
    section5_Differences();
    section6_Formatting();
    section7_Parsing();
    section8_Arithmetic();
    section9_Comparisons();
    section10_Truncation();
    section11_Helpers();
    section12_LegacyBridge();
    section13_EdgeCases();
  }
}
