package com.aykacltd.datetime;


/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPREHENSIVE JODA-TIME GUIDE
 * Library : org.joda.time  (joda-time:2.12.x)
 * <p>
 * Maven dependency:
 * <dependency>
 * <groupId>joda-time</groupId>
 * <artifactId>joda-time</artifactId>
 * <version>2.12.7</version>
 * </dependency>
 * <p>
 * NOTE: Joda-Time is now in maintenance mode.
 * The java.time API (JSR-310) is the modern replacement.
 * Use Joda-Time when maintaining legacy codebases.
 * <p>
 * TABLE OF CONTENTS
 * -----------------
 * 1.  Core Classes Overview
 * 2.  Initialising Date & Time Objects
 * 3.  Time Zones (DateTimeZone)
 * 4.  Converting Between Time Zones
 * 5.  Difference: Period, Duration, Interval
 * 6.  Formatting  (DateTime → String)
 * 7.  Parsing     (String  → DateTime)
 * 8.  Arithmetic  (plus / minus)
 * 9.  Comparisons & Ordering
 * 10. Partial Types: LocalDate, LocalTime, LocalDateTime
 * 11. Chronologies (ISO, Buddhist, Coptic, GregorianJulian …)
 * 12. Joda-Time ↔ java.time Bridge
 * 13. Joda-Time ↔ java.util.Date Bridge
 * 14. Edge Cases & Common Pitfalls
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.chrono.BuddhistChronology;
import org.joda.time.chrono.CopticChronology;
import org.joda.time.chrono.EthiopicChronology;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.chrono.JulianChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;

public class JodaTimeDateGuide {

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌1. CORE CLASSES OVERVIEW
  // ═══════════════════════════════════════════════════════════════════════════
  /*
   *  CLASS               | DESCRIPTION
   *  ────────────────────────────────────────────────────────────────────────
   *  DateTime            | Instant + DateTimeZone (most commonly used)
   *  LocalDate           | Date only, no time zone
   *  LocalTime           | Time only, no time zone
   *  LocalDateTime       | Date + time, no time zone
   *  DateMidnight        | Deprecated — start of a day in a time zone
   *  Instant             | Milliseconds from epoch (immutable)
   *  MutableDateTime     | Mutable equivalent of DateTime
   *  Duration            | Exact millisecond-based duration
   *  Period              | Calendar-based duration (years, months, days)
   *  Interval            | A span between two Instants
   *  DateTimeZone        | Joda time zone wrapper
   *  DateTimeFormatter   | Format / parse dates
   *  DateTimeFormatterBuilder | Builder for complex formatters
   *  Chronology          | Calendar system (ISO, Buddhist, Coptic, etc.)
   *
   *  KEY INTERFACES
   *  ReadableInstant     | Millisecond-based (DateTime, Instant, MutableDateTime)
   *  ReadableDateTime    | ReadableInstant + datetime fields
   *  ReadablePartial     | No reference to an instant (LocalDate, LocalTime)
   *  ReadableDuration    | Duration in millis
   *  ReadablePeriod      | Duration in calendar fields
   *  ReadableInterval    | Interval between two instants
   */

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌2. INITIALISING DATE & TIME OBJECTS
  // ═══════════════════════════════════════════════════════════════════════════
  static void section2_Initialising() {
    System.out.println("\n══════ 2. INITIALISING DATE & TIME ══════");

    // ── DateTime ─────────────────────────────────────────────────────────
    DateTime now = new DateTime();                                  // current, default zone
    DateTime utcNow = new DateTime(DateTimeZone.UTC);                 // current, UTC
    DateTime london = new DateTime(DateTimeZone.forID("Europe/London"));

    // Specify year, month, day, hour, minute, second, millis
    DateTime dt1 = new DateTime(2025, 6, 1, 14, 30, 0, 0);            // default zone
    DateTime dt2 = new DateTime(2025, 6, 1, 14, 30, 0, 0, DateTimeZone.forID("Europe/Istanbul"));
    DateTime dt3 = new DateTime(2025, 6, 1, 14, 30, 0, DateTimeZone.UTC); // omit millis

    // From epoch millis
    DateTime fromMillis = new DateTime(1_717_200_000_000L);
    DateTime fromMillisUTC = new DateTime(1_717_200_000_000L, DateTimeZone.UTC);

    // From String (ISO-8601)
    DateTime fromISO = new DateTime("2025-06-01T14:30:00.000+03:00");

    System.out.println("now      = " + now);
    System.out.println("dt2      = " + dt2);
    System.out.println("fromISO  = " + fromISO);

    // ── LocalDate ────────────────────────────────────────────────────────
    LocalDate today = new LocalDate();                             // today
    LocalDate ld1 = new LocalDate(2025, 6, 1);
    LocalDate ld2 = LocalDate.parse("2025-06-01");
    LocalDate ld3 = new LocalDate(1_717_200_000_000L, DateTimeZone.UTC);

    System.out.println("ld1 = " + ld1);

    // ── LocalTime ────────────────────────────────────────────────────────
    LocalTime lt1 = new LocalTime();                                  // current time
    LocalTime lt2 = new LocalTime(14, 30, 0, 0);                     // h, m, s, ms
    LocalTime lt3 = new LocalTime(14, 30);
    LocalTime lt4 = LocalTime.parse("14:30:45.000");

    System.out.println("lt2 = " + lt2);

    // ── LocalDateTime ────────────────────────────────────────────────────
    LocalDateTime ldt1 = new LocalDateTime();
    LocalDateTime ldt2 = new LocalDateTime(2025, 6, 1, 14, 30, 0, 0);
    LocalDateTime ldt3 = LocalDateTime.parse("2025-06-01T14:30:00.000");

    System.out.println("ldt2 = " + ldt2);

    // ── Instant ──────────────────────────────────────────────────────────
    Instant inst1 = new Instant();                                     // now in UTC
    Instant inst2 = new Instant(1_717_200_000_000L);
    Instant inst3 = Instant.now();

    System.out.println("inst2 = " + inst2);

    // ── MutableDateTime ──────────────────────────────────────────────────
    MutableDateTime mdt = new MutableDateTime(2025, 6, 1, 14, 30, 0, 0, DateTimeZone.UTC);
    mdt.addHours(2);
    mdt.setMinuteOfHour(0);
    System.out.println("mdt (after mutation) = " + mdt);

    // ── Field accessors ──────────────────────────────────────────────────
    DateTime dt = new DateTime(2025, 6, 15, 14, 30, 45, 500, DateTimeZone.UTC);
    System.out.println("\nField accessors on " + dt + ":");
    System.out.println("  getYear()          = " + dt.getYear());
    System.out.println("  getMonthOfYear()   = " + dt.getMonthOfYear());
    System.out.println("  getDayOfMonth()    = " + dt.getDayOfMonth());
    System.out.println("  getDayOfWeek()     = " + dt.getDayOfWeek());        // 1=Mon, 7=Sun
    System.out.println("  getDayOfYear()     = " + dt.getDayOfYear());
    System.out.println("  getHourOfDay()     = " + dt.getHourOfDay());
    System.out.println("  getMinuteOfHour()  = " + dt.getMinuteOfHour());
    System.out.println("  getSecondOfMinute()= " + dt.getSecondOfMinute());
    System.out.println("  getMillisOfSecond()= " + dt.getMillisOfSecond());
    System.out.println("  getMillis()        = " + dt.getMillis());           // epoch ms
    System.out.println("  getWeekOfWeekyear()= " + dt.getWeekOfWeekyear());
    System.out.println("  getWeekyear()      = " + dt.getWeekyear());
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌3. TIME ZONES (DateTimeZone)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section3_TimeZones() {
    System.out.println("\n══════ 3. TIME ZONES ══════");

    // Getting zones
    DateTimeZone utc = DateTimeZone.UTC;
    DateTimeZone london = DateTimeZone.forID("Europe/London");
    DateTimeZone istanbul = DateTimeZone.forID("Europe/Istanbul");
    DateTimeZone ny = DateTimeZone.forID("America/New_York");
    DateTimeZone fixed = DateTimeZone.forOffsetHours(5);            // +05:00 fixed
    DateTimeZone fixedHM = DateTimeZone.forOffsetHoursMinutes(5, 30);// +05:30 fixed
    DateTimeZone dflt = DateTimeZone.getDefault();                 // system default

    System.out.println("UTC      : " + utc);
    System.out.println("London   : " + london);
    System.out.println("Istanbul : " + istanbul);
    System.out.println("Fixed+5  : " + fixed);
    System.out.println("Fixed+5:30: " + fixedHM);
    System.out.println("Default  : " + dflt);

    // All available zones
    Set<String> allZones = DateTimeZone.getAvailableIDs();
    System.out.println("Total zones: " + allZones.size());

    // Filter Europe zones
    allZones.stream()
        .filter(id -> id.startsWith("Europe/"))
        .sorted()
        .limit(8)
        .forEach(id -> System.out.println("  " + id));

    // Zone offset at a given instant
    DateTime dt = new DateTime(2025, 7, 1, 12, 0, 0, 0, london);
    int offsetMs = london.getOffset(dt.getMillis());
    System.out.println("\nLondon offset in July 2025: " + offsetMs / 3600_000 + " hours");

    // DST info
    System.out.println("London is DST now? " + london.isLocalDateTimeGap(
        new LocalDateTime(2025, 3, 30, 1, 0, 0, 0)));

    // Temporarily change default zone (NOT recommended in production)
    DateTimeZone original = DateTimeZone.getDefault();
    DateTimeZone.setDefault(istanbul);
    System.out.println("Default set to Istanbul: " + DateTimeZone.getDefault());
    DateTimeZone.setDefault(original); // restore
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌4. CONVERTING BETWEEN TIME ZONES
  // ═══════════════════════════════════════════════════════════════════════════
  static void section4_ZoneConversions() {
    System.out.println("\n══════ 4. CONVERTING BETWEEN TIME ZONES ══════");

    DateTimeZone london = DateTimeZone.forID("Europe/London");
    DateTimeZone istanbul = DateTimeZone.forID("Europe/Istanbul");
    DateTimeZone ny = DateTimeZone.forID("America/New_York");
    DateTimeZone tokyo = DateTimeZone.forID("Asia/Tokyo");

    DateTime londonTime = new DateTime(2025, 6, 1, 14, 30, 0, 0, london);
    System.out.println("London   : " + londonTime);

    // withZone: converts to same instant in different zone (like withZoneSameInstant in java.time)
    DateTime istanbulTime = londonTime.withZone(istanbul);
    DateTime nyTime = londonTime.withZone(ny);
    DateTime tokyoTime = londonTime.withZone(tokyo);

    System.out.println("Istanbul : " + istanbulTime);  // +3 → 17:30
    System.out.println("New York : " + nyTime);         // -4 → 10:30
    System.out.println("Tokyo    : " + tokyoTime);      // +9 → 23:30

    // withZoneRetainFields: same local time in new zone (like withZoneSameLocal)
    DateTime sameFields = londonTime.withZoneRetainFields(istanbul);
    System.out.println("\nSame local time in Istanbul : " + sameFields);  // 14:30 Istanbul
    System.out.println("Different instant? " + (londonTime.getMillis() != sameFields.getMillis()));

    // Instant as universal intermediary
    Instant now = new Instant();
    DateTime inLondon = now.toDateTime(london);
    DateTime inIstanbul = now.toDateTime(istanbul);
    DateTime inNy = now.toDateTime(ny);
    System.out.println("\nSame instant in different zones:");
    System.out.println("  London   : " + inLondon);
    System.out.println("  Istanbul : " + inIstanbul);
    System.out.println("  New York : " + inNy);

    // LocalDateTime → DateTime for a zone
    LocalDateTime local = new LocalDateTime(2025, 6, 1, 14, 30, 0, 0);
    DateTime zonedUp = local.toDateTime(london);   // interpret local time as London time
    System.out.println("\nLocalDateTime → DateTime (London): " + zonedUp);

    // Cross-zone meeting display
    DateTime meeting = new DateTime(2025, 6, 10, 10, 0, 0, 0, ny);
    System.out.println("\nMeeting at: " + meeting);
    for (String[] tz : new String[][] {
        {"New York", "America/New_York"},
        {"London", "Europe/London"},
        {"Istanbul", "Europe/Istanbul"},
        {"Tokyo", "Asia/Tokyo"}}) {
      DateTime inZone = meeting.withZone(DateTimeZone.forID(tz[1]));
      System.out.printf("  %-10s: %s%n", tz[0],
          ISODateTimeFormat.dateTimeNoMillis().print(inZone));
    }
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌5. DIFFERENCE: Period, Duration, Interval
  // ═══════════════════════════════════════════════════════════════════════════
  static void section5_Differences() {
    System.out.println("\n══════ 5. DIFFERENCES ══════");

    // ── Duration (millisecond-exact) ─────────────────────────────────────
    DateTime dt1 = new DateTime(2025, 6, 1, 9, 0, 0, 0, DateTimeZone.UTC);
    DateTime dt2 = new DateTime(2025, 6, 3, 17, 30, 0, 0, DateTimeZone.UTC);

    Duration duration = new Duration(dt1, dt2);
    System.out.println("Duration : " + duration);
    System.out.println("  Millis : " + duration.getMillis());
    System.out.println("  Seconds: " + duration.getStandardSeconds());
    System.out.println("  Minutes: " + duration.getStandardMinutes());
    System.out.println("  Hours  : " + duration.getStandardHours());
    System.out.println("  Days   : " + duration.getStandardDays());

    // Creating Duration directly
    Duration d3h = Duration.standardHours(3);
    Duration d90m = Duration.standardMinutes(90);
    Duration dCombo = Duration.standardHours(2).plus(Duration.standardMinutes(30));
    System.out.println("3 hours duration: " + d3h.getStandardMinutes() + " minutes");

    // ── Period (calendar-based) ──────────────────────────────────────────
    LocalDate start = new LocalDate(2022, 3, 15);
    LocalDate end = new LocalDate(2025, 6, 10);

    Period period = new Period(start, end);
    System.out.println("\nPeriod (calendar): " + period);
    System.out.println("  Years  : " + period.getYears());
    System.out.println("  Months : " + period.getMonths());
    System.out.println("  Weeks  : " + period.getWeeks());
    System.out.println("  Days   : " + period.getDays());

    // Period with PeriodType
    Period yearMonthDay = new Period(start, end, PeriodType.yearMonthDay());
    Period dayOnly = new Period(start, end, PeriodType.days());
    System.out.println("yearMonthDay: " + yearMonthDay);
    System.out.println("days only   : " + dayOnly.getDays());

    // Period between two DateTimes
    DateTime dta = new DateTime(2025, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    DateTime dtb = new DateTime(2025, 6, 15, 14, 30, 0, 0, DateTimeZone.UTC);
    Period dtPeriod = new Period(dta, dtb, PeriodType.yearMonthDayTime());
    System.out.println("DateTime period: " + dtPeriod);
    System.out.printf("  %dY %dM %dD %dh %dm%n",
        dtPeriod.getYears(), dtPeriod.getMonths(), dtPeriod.getDays(),
        dtPeriod.getHours(), dtPeriod.getMinutes());

    // Days/Months/Years between (utility classes)
    int days = Days.daysBetween(start, end).getDays();
    int months = Months.monthsBetween(start, end).getMonths();
    int years = Years.yearsBetween(start, end).getYears();
    int weeks = Weeks.weeksBetween(start, end).getWeeks();
    int hours = Hours.hoursBetween(dta, dtb).getHours();
    int minutes = Minutes.minutesBetween(dta, dtb).getMinutes();
    System.out.printf("\nBetween %s and %s: %d days / %d months / %d years / %d weeks%n",
        start, end, days, months, years, weeks);
    System.out.printf("Between %s and %s: %d hours / %d minutes%n", dta, dtb, hours, minutes);

    // ── Interval ─────────────────────────────────────────────────────────
    Interval interval = new Interval(dt1, dt2);
    System.out.println("\nInterval: " + interval);
    System.out.println("  Start   : " + interval.getStart());
    System.out.println("  End     : " + interval.getEnd());
    System.out.println("  Duration: " + interval.toDuration().getStandardHours() + " hours");
    System.out.println("  Contains " + dt1.plusHours(5) + "? "
        + interval.contains(dt1.plusHours(5)));
    System.out.println("  Contains " + dt2.plusHours(1) + "? "
        + interval.contains(dt2.plusHours(1)));

    // Interval operations
    Interval a = new Interval(
        new DateTime(2025, 6, 1, 0, 0, 0, 0, DateTimeZone.UTC),
        new DateTime(2025, 6, 10, 0, 0, 0, 0, DateTimeZone.UTC));
    Interval b = new Interval(
        new DateTime(2025, 6, 7, 0, 0, 0, 0, DateTimeZone.UTC),
        new DateTime(2025, 6, 15, 0, 0, 0, 0, DateTimeZone.UTC));

    System.out.println("\nInterval a: " + a);
    System.out.println("Interval b: " + b);
    System.out.println("  a.overlaps(b)  : " + a.overlaps(b));
    System.out.println("  a.abuts(b)     : " + a.abuts(b));
    System.out.println("  a.overlap(b)   : " + a.overlap(b));     // the overlapping interval
    System.out.println("  a.gap(b)       : " + a.gap(b));         // null if overlapping
    System.out.println("  a.isAfter(b)   : " + a.isAfter(b));
    System.out.println("  a.isBefore(b)  : " + a.isBefore(b));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌6. FORMATTING  (DateTime → String)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section6_Formatting() {
    System.out.println("\n══════ 6. FORMATTING (DateTime → String) ══════");

    DateTime dt = new DateTime(2025, 6, 1, 14, 30, 45, 500, DateTimeZone.forID("Europe/Istanbul"));

    // ── Built-in ISO formatters ───────────────────────────────────────────
    System.out.println("basicDate()             : " + ISODateTimeFormat.basicDate().print(dt));
    System.out.println("basicDateTime()         : " + ISODateTimeFormat.basicDateTime().print(dt));
    System.out.println(
        "basicDateTimeNoMillis() : " + ISODateTimeFormat.basicDateTimeNoMillis().print(dt));
    System.out.println("date()                  : " + ISODateTimeFormat.date().print(dt));
    System.out.println("dateTime()              : " + ISODateTimeFormat.dateTime().print(dt));
    System.out.println(
        "dateTimeNoMillis()      : " + ISODateTimeFormat.dateTimeNoMillis().print(dt));
    System.out.println("dateHour()              : " + ISODateTimeFormat.dateHour().print(dt));
    System.out.println(
        "hourMinuteSecond()      : " + ISODateTimeFormat.hourMinuteSecond().print(dt));
    System.out.println("time()                  : " + ISODateTimeFormat.time().print(dt));
    System.out.println("timeNoMillis()          : " + ISODateTimeFormat.timeNoMillis().print(dt));

    // ── Custom pattern with DateTimeFormat.forPattern ────────────────────
    // Pattern symbols (similar to SimpleDateFormat but via Joda):
    //  G=era  C=centuryOfEra  Y=weekyear  x=weekyear  y=year  e=dayOfWeek
    //  D=dayOfYear  M=monthOfYear  d=dayOfMonth  a=halfday  K=hourOfHalfday
    //  h=clockhourOfHalfday  H=hourOfDay  k=clockhourOfDay  m=minuteOfHour
    //  s=secondOfMinute  S=fractionOfSecond  z=timezone  Z=offset  '=literal

    String[][] patterns = {
        {"dd/MM/yyyy", "British date"},
        {"MM/dd/yyyy", "US date"},
        {"dd MMM yyyy", "Readable date"},
        {"EEEE, dd MMMM yyyy", "Full with day name"},
        {"HH:mm:ss", "24h time"},
        {"hh:mm:ss a", "12h with AM/PM"},
        {"HH:mm:ss.SSS", "With millis"},
        {"dd/MM/yyyy HH:mm:ss", "DateTime British"},
        {"yyyy-MM-dd'T'HH:mm:ssZZ", "ISO with offset"},
        {"dd MMM yyyy, HH:mm z", "Readable with zone"},
        {"yyyyMMddHHmmss", "Compact timestamp"},
    };

    for (String[] p : patterns) {
      DateTimeFormatter fmt = DateTimeFormat.forPattern(p[0]);
      System.out.printf("  %-35s → %s%n", p[1], fmt.print(dt));
    }

    // ── Locale-specific formatting ───────────────────────────────────────
    System.out.println("\nLocale-specific (MEDIUM style):");
    for (Locale locale : new Locale[] {Locale.UK, Locale.US, new Locale("tr", "TR"),
        Locale.JAPAN}) {
      DateTimeFormatter locFmt = DateTimeFormat.mediumDateTime().withLocale(locale);
      System.out.printf("  %-10s: %s%n", locale, locFmt.print(dt));
    }

    // forStyle: first char=date style, second=time style (S=short M=medium L=long F=full -=none)
    System.out.println("\nFormatStyle combinations:");
    for (String style : new String[] {"SS", "MM", "LL", "FF", "MS", "LS", "L-", "-M"}) {
      try {
        DateTimeFormatter f = DateTimeFormat.forStyle(style).withLocale(Locale.UK);
        System.out.printf("  [%s] %s%n", style, f.print(dt));
      } catch (Exception e) {
        System.out.println("  [" + style + "] unsupported");
      }
    }

    // ── DateTimeFormatterBuilder ─────────────────────────────────────────
    DateTimeFormatter complex = new DateTimeFormatterBuilder()
        .appendDayOfWeekText()
        .appendLiteral(", ")
        .appendDayOfMonth(2)
        .appendLiteral(" ")
        .appendMonthOfYearText()
        .appendLiteral(" ")
        .appendYear(4, 4)
        .appendLiteral(" at ")
        .appendHourOfDay(2)
        .appendLiteral(":")
        .appendMinuteOfHour(2)
        .appendLiteral(":")
        .appendSecondOfMinute(2)
        .appendLiteral(" [")
        .appendTimeZoneId()
        .appendLiteral("]")
        .toFormatter()
        .withLocale(Locale.ENGLISH);

    System.out.println("\nComplex builder: " + complex.print(dt));

    // Printing LocalDate / LocalTime
    LocalDate ld = new LocalDate(2025, 6, 1);
    LocalTime lt = new LocalTime(14, 30, 45, 500);
    System.out.println("\nLocalDate : " + DateTimeFormat.forPattern("dd/MM/yyyy").print(ld));
    System.out.println("LocalTime : " + DateTimeFormat.forPattern("HH:mm:ss.SSS").print(lt));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌7. PARSING  (String → DateTime)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section7_Parsing() {
    System.out.println("\n══════ 7. PARSING (String → DateTime) ══════");

    // ── DateTime parsing ─────────────────────────────────────────────────
    DateTime dt1 = new DateTime("2025-06-01T14:30:00.000+03:00");          // ISO-8601
    DateTime dt2 = DateTime.parse("2025-06-01T14:30:00+03:00");
    DateTime dt3 = DateTime.parse("01/06/2025 14:30:45",
        DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
    DateTime dt4 = DateTime.parse("June 1, 2025 at 14:30",
        DateTimeFormat.forPattern("MMMM d, yyyy 'at' HH:mm").withLocale(Locale.ENGLISH));
    System.out.println("dt1 = " + dt1);
    System.out.println("dt2 = " + dt2);
    System.out.println("dt3 = " + dt3);
    System.out.println("dt4 = " + dt4);

    // ── LocalDate parsing ────────────────────────────────────────────────
    LocalDate ld1 = new LocalDate("2025-06-01");
    LocalDate ld2 = LocalDate.parse("01/06/2025",
        DateTimeFormat.forPattern("dd/MM/yyyy"));
    LocalDate ld3 = LocalDate.parse("01-Jun-2025",
        DateTimeFormat.forPattern("dd-MMM-yyyy").withLocale(Locale.ENGLISH));
    System.out.println("ld1=" + ld1 + "  ld2=" + ld2 + "  ld3=" + ld3);

    // ── LocalTime parsing ────────────────────────────────────────────────
    LocalTime lt1 = new LocalTime("14:30:45.000");
    LocalTime lt2 = LocalTime.parse("02:30 PM",
        DateTimeFormat.forPattern("hh:mm aa").withLocale(Locale.ENGLISH));
    System.out.println("lt1=" + lt1 + "  lt2=" + lt2);

    // ── LocalDateTime parsing ────────────────────────────────────────────
    LocalDateTime ldt1 = new LocalDateTime("2025-06-01T14:30:00");
    LocalDateTime ldt2 = LocalDateTime.parse("01/06/2025 14:30:45",
        DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
    System.out.println("ldt1=" + ldt1 + "  ldt2=" + ldt2);

    // ── Multi-format parser ──────────────────────────────────────────────
    DateTimeParser[] parsers = {
        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").getParser(),
        DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").getParser(),
        DateTimeFormat.forPattern("dd-MM-yyyy HH:mm").getParser(),
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").getParser(),
    };
    DateTimeFormatter flexFmt = new DateTimeFormatterBuilder()
        .append(null, parsers)
        .toFormatter();

    String[] inputs = {
        "2025-06-01 14:30:45",
        "01/06/2025 14:30:45",
        "01-06-2025 14:30",
        "2025-06-01T14:30:45"
    };
    for (String input : inputs) {
      DateTime parsed = DateTime.parse(input, flexFmt);
      System.out.println("Flex parsed: '" + input + "' → " + parsed);
    }

    // ── Error handling ───────────────────────────────────────────────────
    try {
      DateTime.parse("not-a-date");
    } catch (IllegalArgumentException e) {
      System.out.println("\nParse error: " + e.getMessage());
    }
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌8. ARITHMETIC  (plus / minus)
  // ═══════════════════════════════════════════════════════════════════════════
  static void section8_Arithmetic() {
    System.out.println("\n══════ 8. ARITHMETIC ══════");

    DateTime dt = new DateTime(2025, 6, 1, 14, 30, 0, 0, DateTimeZone.forID("Europe/London"));
    System.out.println("Original: " + dt);

    // plus/minus with field amounts
    System.out.println("  +1 day      : " + dt.plusDays(1));
    System.out.println("  +2 weeks    : " + dt.plusWeeks(2));
    System.out.println("  +3 months   : " + dt.plusMonths(3));
    System.out.println("  +1 year     : " + dt.plusYears(1));
    System.out.println("  +3 hours    : " + dt.plusHours(3));
    System.out.println("  +90 minutes : " + dt.plusMinutes(90));
    System.out.println("  +30 seconds : " + dt.plusSeconds(30));
    System.out.println("  +500 millis : " + dt.plusMillis(500));
    System.out.println("  -5 days     : " + dt.minusDays(5));

    // plus with Period and Duration
    System.out.println("  +Period(1y,2m,3d): " + dt.plus(new Period(1, 2, 0, 3, 0, 0, 0, 0)));
    System.out.println("  +Duration(3h)    : " + dt.plus(Duration.standardHours(3)));

    // LocalDate arithmetic
    LocalDate ld = new LocalDate(2025, 6, 1);
    System.out.println("\nLocalDate: " + ld);
    System.out.println("  +1 month   : " + ld.plusMonths(1));
    System.out.println("  +30 days   : " + ld.plusDays(30));
    System.out.println("  last day of month: " + ld.dayOfMonth().withMaximumValue());
    System.out.println("  first day of month: " + ld.dayOfMonth().withMinimumValue());
    System.out.println("  first day of year : " + ld.dayOfYear().withMinimumValue());
    System.out.println(
        "  next Monday : " + ld.plusWeeks(1).withDayOfWeek(DateTimeConstants.MONDAY));

    // Field manipulation with .withXxx()
    DateTime withFields = dt
        .withYear(2026)
        .withMonthOfYear(12)
        .withDayOfMonth(25)
        .withHourOfDay(0)
        .withMinuteOfHour(0)
        .withSecondOfMinute(0);
    System.out.println("\nWith specific fields: " + withFields);

    // MutableDateTime for chained mutations
    MutableDateTime mdt = dt.toMutableDateTime();
    mdt.addYears(1);
    mdt.addMonths(6);
    mdt.setHourOfDay(9);
    System.out.println("Mutable after changes: " + mdt);
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌9. COMPARISONS & ORDERING
  // ═══════════════════════════════════════════════════════════════════════════
  static void section9_Comparisons() {
    System.out.println("\n══════ 9. COMPARISONS ══════");

    DateTime dt1 = new DateTime(2025, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    DateTime dt2 = new DateTime(2025, 6, 15, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime dt3 = new DateTime(2025, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);

    System.out.println("dt1.isBefore(dt2) = " + dt1.isBefore(dt2));
    System.out.println("dt1.isAfter(dt2)  = " + dt1.isAfter(dt2));
    System.out.println("dt1.isEqual(dt3)  = " + dt1.isEqual(dt3));
    System.out.println("dt1.equals(dt3)   = " + dt1.equals(dt3));
    System.out.println("dt1.compareTo(dt2)= " + dt1.compareTo(dt2));

    // Cross-zone comparison (compares instants)
    DateTimeZone london = DateTimeZone.forID("Europe/London");
    DateTimeZone istanbul = DateTimeZone.forID("Europe/Istanbul");
    DateTime l = new DateTime(2025, 6, 1, 14, 0, 0, 0, london);
    DateTime i = new DateTime(2025, 6, 1, 16, 0, 0, 0, istanbul); // same instant
    System.out.println("\n14:00 London == 16:00 Istanbul? " + l.isEqual(i));

    // In-range check
    DateTime rangeStart = new DateTime(2025, 3, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    DateTime rangeEnd = new DateTime(2025, 9, 30, 23, 59, 59, 0, DateTimeZone.UTC);
    DateTime check = new DateTime(2025, 6, 15, 12, 0, 0, 0, DateTimeZone.UTC);
    Interval range = new Interval(rangeStart, rangeEnd);
    System.out.println(check + " in range? " + range.contains(check));

    // Sorting
    List<DateTime> list = new ArrayList<>(List.of(
        new DateTime(2025, 6, 1, 16, 0, 0, 0, istanbul),
        new DateTime(2025, 6, 1, 14, 0, 0, 0, london),
        new DateTime(2025, 6, 1, 9, 0, 0, 0, DateTimeZone.forID("America/New_York"))
    ));
    list.sort(DateTime::compareTo);
    System.out.println("\nSorted by instant:");
    list.forEach(d -> System.out.println("  " + d));
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌10. PARTIAL TYPES: LocalDate, LocalTime, LocalDateTime
  // ═══════════════════════════════════════════════════════════════════════════
  static void section10_PartialTypes() {
    System.out.println("\n══════ 10. PARTIAL TYPES ══════");

    LocalDate ld = new LocalDate(2025, 6, 15);
    System.out.println("LocalDate: " + ld);
    System.out.println("  Year      : " + ld.getYear());
    System.out.println("  Month     : " + ld.getMonthOfYear());
    System.out.println("  Day       : " + ld.getDayOfMonth());
    System.out.println("  DayOfWeek : " + ld.getDayOfWeek());
    System.out.println("  DayOfYear : " + ld.getDayOfYear());
    System.out.println("  Is Leap?  : " + ld.year().isLeap());

    // Combining LocalDate + LocalTime → LocalDateTime
    LocalTime lt = new LocalTime(14, 30, 0, 0);
    LocalDateTime ldt = ld.toLocalDateTime(lt);
    System.out.println("\nLocalDate + LocalTime = LocalDateTime: " + ldt);

    // LocalDateTime → DateTime for a zone
    DateTimeZone london = DateTimeZone.forID("Europe/London");
    DateTime dt = ldt.toDateTime(london);
    System.out.println("LocalDateTime → DateTime (London): " + dt);

    // Convert DateTime → LocalDate
    DateTime zonedDt =
        new DateTime(2025, 6, 15, 14, 30, 0, 0, DateTimeZone.forID("Europe/Istanbul"));
    LocalDate extractedDate = zonedDt.toLocalDate();
    LocalTime extractedTime = zonedDt.toLocalTime();
    System.out.println("\nExtracted from DateTime:");
    System.out.println("  LocalDate: " + extractedDate);
    System.out.println("  LocalTime: " + extractedTime);

    // Iterating days in a month using LocalDate
    System.out.println("\nWeekends in June 2025:");
    LocalDate firstOfJune = new LocalDate(2025, 6, 1);
    LocalDate lastOfJune = new LocalDate(2025, 6, 30);
    LocalDate cursor = firstOfJune;
    while (!cursor.isAfter(lastOfJune)) {
      if (cursor.getDayOfWeek() == DateTimeConstants.SATURDAY
          || cursor.getDayOfWeek() == DateTimeConstants.SUNDAY) {
        System.out.println(
            "  " + cursor + " (" + cursor.dayOfWeek().getAsText(Locale.ENGLISH) + ")");
      }
      cursor = cursor.plusDays(1);
    }
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌11. CHRONOLOGIES
  // ═══════════════════════════════════════════════════════════════════════════
  static void section11_Chronologies() {
    System.out.println("\n══════ 11. CHRONOLOGIES ══════");

    // Joda-Time supports multiple calendar systems via Chronology
    long millis = new DateTime(2025, 6, 1, 0, 0, 0, 0, DateTimeZone.UTC).getMillis();

    Chronology iso = ISOChronology.getInstanceUTC();
    Chronology gregorian = GregorianChronology.getInstanceUTC();
    Chronology julian = JulianChronology.getInstanceUTC();
    Chronology buddhist = BuddhistChronology.getInstanceUTC();
    Chronology coptic = CopticChronology.getInstanceUTC();
    Chronology ethiopic = EthiopicChronology.getInstanceUTC();
    Chronology hebrew = IslamicChronology.getInstanceUTC(); // NOTE: Islamic, not Hebrew
    Chronology gc = GJChronology.getInstanceUTC();      // proleptic Gregorian/Julian hybrid

    System.out.println("Same millis in different chronologies:");
    for (Object[] row : new Object[][] {
        {"ISO", iso},
        {"Gregorian", gregorian},
        {"Julian", julian},
        {"Buddhist", buddhist},
        {"Coptic", coptic},
        {"Ethiopic", ethiopic},
        {"Islamic", hebrew},
        {"GJ", gc}}) {
      Chronology chrono = (Chronology) row[1];
      DateTime dt = new DateTime(millis, chrono.withZone(DateTimeZone.UTC));
      System.out.printf("  %-12s: %d-%02d-%02d%n", row[0],
          dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
    }

    // Working with Buddhist calendar (Thailand)
    DateTime budDt = new DateTime(2025, 6, 1, 0, 0, 0, 0,
        BuddhistChronology.getInstance(DateTimeZone.forID("Asia/Bangkok")));
    System.out.println("\nBuddhist year 2025 CE = year " + budDt.getYear() + " BE");

    // Converting between chronologies
    DateTime isoDt = new DateTime(2025, 6, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    DateTime buddhist2 = isoDt.withChronology(BuddhistChronology.getInstanceUTC());
    DateTime backToISO = buddhist2.withChronology(ISOChronology.getInstanceUTC());
    System.out.println("ISO: " + isoDt + " → Buddhist: " + buddhist2 + " → ISO: " + backToISO);
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌12. JODA-TIME ↔ java.time BRIDGE
  // ═══════════════════════════════════════════════════════════════════════════
  static void section12_JodaToJavaTimeBridge() {
    System.out.println("\n══════ 12. JODA-TIME ↔ java.time BRIDGE ══════");

    // Joda DateTime → java.time ZonedDateTime
    DateTime joda = new DateTime(2025, 6, 1, 14, 30, 0, 0, DateTimeZone.forID("Europe/London"));
    java.time.ZonedDateTime javaZDT = joda.toGregorianCalendar().toZonedDateTime();
    System.out.println("Joda DateTime          : " + joda);
    System.out.println("→ java.time ZonedDT    : " + javaZDT);

    // Via Instant
    java.time.Instant javaInstant = java.time.Instant.ofEpochMilli(joda.getMillis());
    java.time.ZoneId javaZone = java.time.ZoneId.of(joda.getZone().getID());
    java.time.ZonedDateTime viaMilli = javaInstant.atZone(javaZone);
    System.out.println("→ java.time (via millis): " + viaMilli);

    // java.time ZonedDateTime → Joda DateTime
    java.time.ZonedDateTime javaZdt2 = java.time.ZonedDateTime.of(
        2025, 6, 1, 14, 30, 0, 0, java.time.ZoneId.of("Europe/Istanbul"));
    DateTime jodaBack = new DateTime(
        javaZdt2.toInstant().toEpochMilli(),
        DateTimeZone.forID(javaZdt2.getZone().getId()));
    System.out.println("\njava.time ZonedDT      : " + javaZdt2);
    System.out.println("→ Joda DateTime        : " + jodaBack);

    // LocalDate bridge
    org.joda.time.LocalDate jodaLD = new org.joda.time.LocalDate(2025, 6, 1);
    java.time.LocalDate javaLD = java.time.LocalDate.of(
        jodaLD.getYear(), jodaLD.getMonthOfYear(), jodaLD.getDayOfMonth());
    System.out.println("\nJoda LocalDate         : " + jodaLD);
    System.out.println("→ java.time LocalDate  : " + javaLD);

    // java.time LocalDate → Joda LocalDate
    java.time.LocalDate javaLD2 = java.time.LocalDate.of(2025, 6, 15);
    org.joda.time.LocalDate jodaLD2 = new org.joda.time.LocalDate(
        javaLD2.getYear(), javaLD2.getMonthValue(), javaLD2.getDayOfMonth());
    System.out.println("java.time LocalDate    : " + javaLD2);
    System.out.println("→ Joda LocalDate       : " + jodaLD2);

    // java.time Duration ↔ Joda Duration
    java.time.Duration javaDur = java.time.Duration.ofHours(3).plusMinutes(30);
    Duration jodaDur = Duration.millis(javaDur.toMillis());
    System.out.println("\njava.time Duration     : " + javaDur);
    System.out.println("→ Joda Duration        : " + jodaDur.getStandardMinutes() + " minutes");
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌13. JODA-TIME ↔ java.util.Date BRIDGE
  // ═══════════════════════════════════════════════════════════════════════════
  static void section13_LegacyBridge() {
    System.out.println("\n══════ 13. JODA-TIME ↔ java.util.Date BRIDGE ══════");

    // java.util.Date → Joda DateTime
    java.util.Date legacyDate = new java.util.Date();
    DateTime fromLegacy = new DateTime(legacyDate);
    DateTime fromLegacyUTC = new DateTime(legacyDate, DateTimeZone.UTC);
    System.out.println("java.util.Date  : " + legacyDate);
    System.out.println("→ Joda (default): " + fromLegacy);
    System.out.println("→ Joda (UTC)    : " + fromLegacyUTC);

    // Joda DateTime → java.util.Date
    DateTime jodaDt = new DateTime(2025, 6, 1, 14, 30, 0, 0, DateTimeZone.forID("Europe/London"));
    java.util.Date toJavaDate = jodaDt.toDate();
    System.out.println("\nJoda DateTime   : " + jodaDt);
    System.out.println("→ java.util.Date: " + toJavaDate);

    // Joda → java.util.Calendar
    java.util.Calendar cal = jodaDt.toCalendar(Locale.UK);
    System.out.println("→ Calendar      : " + cal.getTime());

    // java.util.Calendar → Joda
    DateTime fromCal = new DateTime(cal);
    System.out.println("Calendar → Joda : " + fromCal);

    // java.sql.Date / Timestamp
    java.sql.Date sqlDate = new java.sql.Date(jodaDt.getMillis());
    java.sql.Timestamp ts = new java.sql.Timestamp(jodaDt.getMillis());
    DateTime fromSqlDate = new DateTime(sqlDate.getTime());
    DateTime fromSqlTs = new DateTime(ts.getTime());
    System.out.println("\nSQL Date    : " + sqlDate + " → Joda: " + fromSqlDate);
    System.out.println("SQL Timestamp: " + ts + " → Joda: " + fromSqlTs);
  }

  // ═══════════════════════════════════════════════════════════════════════════
  // ▌14. EDGE CASES & PITFALLS
  // ═══════════════════════════════════════════════════════════════════════════
  static void section14_EdgeCases() {
    System.out.println("\n══════ 14. EDGE CASES & PITFALLS ══════");

    // ── Pitfall 1: Month is 1-based (same as java.time, unlike java.util.Calendar) ──
    DateTime dt = new DateTime(2025, 6, 1, 14, 30, 0, 0);  // 6 = June ✓
    System.out.println("June (1-based): " + dt.getMonthOfYear());

    // ── Pitfall 2: DayOfWeek is 1=Monday … 7=Sunday (ISO) ────────────────
    LocalDate sunday = new LocalDate(2025, 6, 1);
    System.out.println("2025-06-01 is day: " + sunday.getDayOfWeek()
        + " (7=Sunday in Joda)");

    // ── Pitfall 3: DateTimeZone.setDefault affects all Joda operations ───
    // Do NOT call setDefault in production multi-threaded code!

    // ── Pitfall 4: Duration vs Period for months ─────────────────────────
    // Duration(30 days) ≠ Period(1 month) because months vary in length
    Duration thirtyDays = Duration.standardDays(30);
    Period oneMonth = Period.months(1);
    DateTime base = new DateTime(2025, 1, 31, 0, 0, 0, 0, DateTimeZone.UTC);
    System.out.println("\nBase: " + base);
    System.out.println("+30 days (Duration): " + base.plus(thirtyDays));
    System.out.println("+1 month (Period)  : " + base.plus(oneMonth));  // Feb 28!

    // ── Pitfall 5: Interval.contains is half-open [start, end) ──────────
    DateTime start = new DateTime(2025, 6, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = new DateTime(2025, 6, 2, 0, 0, 0, 0, DateTimeZone.UTC);
    Interval interval = new Interval(start, end);
    System.out.println("\nInterval [2025-06-01, 2025-06-02):");
    System.out.println("  contains start : " + interval.contains(start));  // true
    System.out.println("  contains end   : " + interval.contains(end));    // FALSE — half-open!

    // ── Pitfall 6: withZone vs withZoneRetainFields ───────────────────────
    DateTimeZone london = DateTimeZone.forID("Europe/London");
    DateTimeZone istanbul = DateTimeZone.forID("Europe/Istanbul");
    DateTime londonDt = new DateTime(2025, 6, 1, 14, 0, 0, 0, london);
    DateTime withZone = londonDt.withZone(istanbul);            // 17:00 Istanbul
    DateTime withZoneRetain =
        londonDt.withZoneRetainFields(istanbul);// 14:00 Istanbul (diff instant!)
    System.out.println("\nwithZone       : " + withZone);
    System.out.println("withZoneRetain : " + withZoneRetain);
    System.out.println(
        "Same instant?  : " + (londonDt.getMillis() == withZone.getMillis()));         // true
    System.out.println(
        "Same instant?  : " + (londonDt.getMillis() == withZoneRetain.getMillis()));   // false

    // ── Useful constants ─────────────────────────────────────────────────
    System.out.println("\nDateTimeConstants:");
    System.out.println("MONDAY   = " + DateTimeConstants.MONDAY);    // 1
    System.out.println("SUNDAY   = " + DateTimeConstants.SUNDAY);    // 7
    System.out.println("JANUARY  = " + DateTimeConstants.JANUARY);   // 1
    System.out.println("DECEMBER = " + DateTimeConstants.DECEMBER);  // 12
    System.out.println("MILLIS_PER_HOUR = " + DateTimeConstants.MILLIS_PER_HOUR);
    System.out.println("MILLIS_PER_DAY  = " + DateTimeConstants.MILLIS_PER_DAY);
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
    section10_PartialTypes();
    section11_Chronologies();
    section12_JodaToJavaTimeBridge();
    section13_LegacyBridge();
    section14_EdgeCases();
  }
}
