package com.aykacltd.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h1>Java Arrays — Interval Operations Comprehensive Guide</h1>
 *
 * <p>This class covers all major interval/range array problems commonly
 * seen in real-world systems (calendar scheduling, IP ranges, time slots)
 * and coding interviews. An <strong>interval</strong> is a pair
 * {@code [start, end]} representing a continuous range.</p>
 *
 * <h2>Table of Contents</h2>
 * <ol>
 *   <li>Section 1  – Representing Intervals</li>
 *   <li>Section 2  – Merge Overlapping Intervals</li>
 *   <li>Section 3  – Insert Interval into Sorted List</li>
 *   <li>Section 4  – Intersection of Two Interval Lists</li>
 *   <li>Section 5  – Merge Two Interval Lists (Union)</li>
 *   <li>Section 6  – Finding Gaps Between Intervals</li>
 *   <li>Section 7  – Interval Covering &amp; Overlap Count</li>
 *   <li>Section 8  – Meeting Room / Scheduling Problems</li>
 *   <li>Section 9  – Interval Contains &amp; Queries</li>
 *   <li>Section 10 – Real-World Scenarios</li>
 * </ol>
 *
 * @author Interval Exercise Suite
 */
public class ArrayIntervalExercises {

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  JAVA ARRAYS — INTERVAL OPERATIONS GUIDE");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        printSection("1 — REPRESENTING INTERVALS");
        representingIntervals();
        intervalFromArrays();

        printSection("2 — MERGE OVERLAPPING INTERVALS");
        mergeOverlapping();
        mergeOverlappingWith2DArray();
        mergeOverlappingStream();

        printSection("3 — INSERT INTO SORTED LIST");
        insertInterval();
        insertAndMerge();

        printSection("4 — INTERSECTION OF TWO LISTS");
        intersectTwoLists();
        intersectTwoListsStream();

        printSection("5 — MERGE TWO INTERVAL LISTS (UNION)");
        unionOfTwoLists();

        printSection("6 — FINDING GAPS");
        findGapsBetweenIntervals();
        findGapsCoverage();

        printSection("7 — COVERING & OVERLAP COUNT");
        countOverlapsAtEachPoint();
        maxOverlappingIntervals();
        minIntervalsToRemoveForNoOverlap();

        printSection("8 — MEETING ROOMS / SCHEDULING");
        canAttendAllMeetings();
        minMeetingRooms();
        findFreeSlots();

        printSection("9 — INTERVAL CONTAINS & QUERIES");
        pointInIntervalQuery();
        rangeContainmentCheck();
        totalCoveredLength();

        printSection("10 — REAL-WORLD SCENARIOS");
        mergeTimeSlots();
        calendarAvailability();
        consolidateIpRanges();
        employeeShiftOverlaps();

        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  ALL INTERVAL EXERCISES COMPLETED");
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    /**
     * Different ways to represent intervals in Java.
     *
     * <p><strong>Key point:</strong> You can represent intervals as:</p>
     * <ul>
     *   <li>{@code int[][]} — each {@code int[2]} is [start, end]</li>
     *   <li>{@code List<int[]>} — more flexible than raw 2D array</li>
     *   <li>{@code record Interval(int start, int end)} — type-safe, self-documenting</li>
     * </ul>
     * <p>The record approach is strongly preferred for readability and safety.</p>
     */
    static void representingIntervals() {
        // As a record (best practice)
        Interval iv = new Interval(1, 5);

        // As a 2D array (common in interview settings)
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};

        // Convert 2D array to List<Interval>
        List<Interval> asList = Arrays.stream(intervals)
            .map(arr -> new Interval(arr[0], arr[1]))
            .toList();

        printExercise("1.1 Record interval", iv);
        printExercise("    overlaps [3,7]?", iv.overlaps(new Interval(3, 7)));
        printExercise("    contains [2,4]?", iv.contains(new Interval(2, 4)));
        printExercise("    length", iv.length());
        printExercise("    2D array → List<Interval>", asList);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 1 — REPRESENTING INTERVALS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Converting between 2D int arrays and Interval records.
     */
    static void intervalFromArrays() {
        int[][] raw = {{1, 5}, {6, 10}, {11, 15}};

        // int[][] → List<Interval>
        List<Interval> intervals = Arrays.stream(raw)
            .map(a -> new Interval(a[0], a[1]))
            .toList();

        // List<Interval> → int[][]
        int[][] backToArray = intervals.stream()
            .map(iv -> new int[] {iv.start(), iv.end()})
            .toArray(int[][]::new);

        printExercise("1.2 int[][] → Intervals", intervals);
        printExercise("    Back to int[][]", Arrays.deepToString(backToArray));
    }

    /**
     * Merges all overlapping intervals in a list.
     *
     * <p><strong>This is the classic interval merge algorithm:</strong></p>
     * <ol>
     *   <li>Sort intervals by start time</li>
     *   <li>Iterate: if current overlaps with previous result, merge them</li>
     *   <li>Otherwise, add current as a new non-overlapping interval</li>
     * </ol>
     *
     * <p><strong>Time:</strong> O(n log n) for sorting, O(n) for merging</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     *   Input:  [1,3], [2,6], [8,10], [15,18]
     *   Output: [1,6], [8,10], [15,18]
     * </pre>
     */
    static void mergeOverlapping() {
        List<Interval> input = List.of(
            new Interval(1, 3),
            new Interval(2, 6),
            new Interval(8, 10),
            new Interval(15, 18));

        List<Interval> merged = mergeIntervals(input);

        printExercise("2.1 Input", input);
        printExercise("    Merged", merged);

        // Edge case: all overlapping
        List<Interval> allOverlap = List.of(
            new Interval(1, 4), new Interval(2, 5),
            new Interval(3, 7), new Interval(6, 8));
        printExercise("    All overlapping", mergeIntervals(allOverlap));

        // Edge case: none overlapping
        List<Interval> noOverlap = List.of(
            new Interval(1, 2), new Interval(5, 6), new Interval(9, 10));
        printExercise("    None overlapping", mergeIntervals(noOverlap));

        // Edge case: touching at boundary
        List<Interval> touching = List.of(
            new Interval(1, 3), new Interval(3, 5), new Interval(5, 7));
        printExercise("    Touching at boundary", mergeIntervals(touching));
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 2 — MERGE OVERLAPPING INTERVALS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Same merge algorithm using raw 2D int arrays (interview style).
     *
     * <p><strong>Key point:</strong> This version works entirely with
     * {@code int[][]} — the format commonly expected in coding interviews.</p>
     */
    static void mergeOverlappingWith2DArray() {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] last = merged.getLast();
            if (intervals[i][0] <= last[1]) {
                last[1] = Math.max(last[1], intervals[i][1]);
            } else {
                merged.add(intervals[i]);
            }
        }

        int[][] result = merged.toArray(int[][]::new);
        printExercise("2.2 Merge with int[][] (interview style)", Arrays.deepToString(result));
    }

    /**
     * Merge overlapping intervals using a Stream + reduce pattern.
     *
     * <p><strong>Key point:</strong> This is a functional approach using
     * {@code reduce} with an accumulator list. Less efficient than the
     * imperative version but demonstrates stream composition.</p>
     */
    static void mergeOverlappingStream() {
        List<Interval> input = List.of(
            new Interval(1, 3), new Interval(2, 6),
            new Interval(8, 10), new Interval(15, 18));

        List<Interval> merged = input.stream()
            .sorted()
            .reduce(
                new ArrayList<Interval>(),
                (acc, interval) -> {
                    if (!acc.isEmpty() && acc.getLast().overlaps(interval)) {
                        acc.set(acc.size() - 1, acc.getLast().merge(interval));
                    } else {
                        acc.add(interval);
                    }
                    return acc;
                },
                (a, b) -> {
                    a.addAll(b);
                    return a;
                });

        printExercise("2.3 Merge (stream reduce)", merged);
    }

    /**
     * Inserts a new interval into a sorted, non-overlapping list
     * without merging.
     */
    static void insertInterval() {
        List<Interval> sorted = new ArrayList<>(List.of(
            new Interval(1, 3), new Interval(6, 9), new Interval(12, 15)));
        Interval toInsert = new Interval(4, 5);

        // Find the insertion point using binary search on start values
        int pos = 0;
        while (pos < sorted.size() && sorted.get(pos).start() < toInsert.start()) {
            pos++;
        }
        sorted.add(pos, toInsert);

        printExercise("3.1 Insert [4,5] (no merge)", sorted);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 3 — INSERT INTO SORTED LIST
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Inserts a new interval and merges any resulting overlaps.
     *
     * <p><strong>Algorithm:</strong></p>
     * <ol>
     *   <li>Collect all intervals that come before (no overlap)</li>
     *   <li>Merge all intervals that overlap with the new one</li>
     *   <li>Collect all intervals that come after (no overlap)</li>
     * </ol>
     *
     * <p><strong>Time:</strong> O(n)</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     *   Existing: [1,3], [6,9]
     *   Insert:   [2,5]
     *   Result:   [1,5], [6,9]
     * </pre>
     */
    static void insertAndMerge() {
        List<Interval> intervals = List.of(
            new Interval(1, 3), new Interval(6, 9), new Interval(12, 15));
        Interval newIv = new Interval(2, 7);

        List<Interval> result = new ArrayList<>();
        int i = 0;

        // 1. Add all intervals before the new one
        while (i < intervals.size() && intervals.get(i).end() < newIv.start()) {
            result.add(intervals.get(i++));
        }

        // 2. Merge all overlapping intervals with the new one
        Interval merged = newIv;
        while (i < intervals.size() && intervals.get(i).start() <= merged.end()) {
            merged = merged.merge(intervals.get(i++));
        }
        result.add(merged);

        // 3. Add all intervals after
        while (i < intervals.size()) {
            result.add(intervals.get(i++));
        }

        printExercise("3.2 Insert [2,7] and merge", result);
    }

    /**
     * Finds the intersection of two lists of sorted intervals.
     *
     * <p><strong>Algorithm:</strong> Two-pointer technique. At each step,
     * compute the overlap of the current pair. The pointer whose interval
     * ends first advances.</p>
     *
     * <p><strong>Time:</strong> O(n + m)</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     *   A: [0,2], [5,10], [13,23], [24,25]
     *   B: [1,5], [8,12], [15,24], [25,26]
     *   →  [1,2], [5,5],  [8,10], [15,23], [24,24], [25,25]
     * </pre>
     */
    static void intersectTwoLists() {
        List<Interval> a = List.of(
            new Interval(0, 2), new Interval(5, 10),
            new Interval(13, 23), new Interval(24, 25));
        List<Interval> b = List.of(
            new Interval(1, 5), new Interval(8, 12),
            new Interval(15, 24), new Interval(25, 26));

        List<Interval> intersection = new ArrayList<>();
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            int lo = Math.max(a.get(i).start(), b.get(j).start());
            int hi = Math.min(a.get(i).end(), b.get(j).end());
            if (lo <= hi) {
                intersection.add(new Interval(lo, hi));
            }
            // Advance the pointer whose interval ends first
            if (a.get(i).end() < b.get(j).end()) {
                i++;
            } else {
                j++;
            }
        }

        printExercise("4.1 List A", a);
        printExercise("    List B", b);
        printExercise("    Intersection", intersection);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 4 — INTERSECTION
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Interval intersection using Stream + flatMap.
     */
    static void intersectTwoListsStream() {
        List<Interval> a = List.of(new Interval(1, 5), new Interval(8, 12));
        List<Interval> b = List.of(new Interval(3, 7), new Interval(10, 15));

        List<Interval> intersection = a.stream()
            .flatMap(iv1 -> b.stream()
                .map(iv1::intersect)
                .filter(Optional::isPresent)
                .map(Optional::get))
            .toList();

        printExercise("4.2 Stream intersection", intersection);
    }

    /**
     * Merges (unions) two sorted interval lists into one merged list.
     *
     * <p><strong>Algorithm:</strong> Concatenate both lists, sort, then
     * apply the standard merge-overlapping algorithm.</p>
     */
    static void unionOfTwoLists() {
        List<Interval> a = List.of(new Interval(1, 3), new Interval(6, 9));
        List<Interval> b = List.of(new Interval(2, 5), new Interval(7, 12));

        List<Interval> union = mergeIntervals(
            Stream.concat(a.stream(), b.stream()).toList());

        printExercise("5.1 List A", a);
        printExercise("    List B", b);
        printExercise("    Union (merged)", union);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 5 — UNION OF TWO INTERVAL LISTS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Finds the gaps (uncovered ranges) between a set of intervals.
     *
     * <p><strong>Algorithm:</strong> Merge all intervals first, then
     * the gaps are the spaces between consecutive merged intervals.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     *   Merged: [1,5], [8,12], [15,20]
     *   Gaps:   [6,7], [13,14]
     * </pre>
     */
    static void findGapsBetweenIntervals() {
        List<Interval> intervals = List.of(
            new Interval(1, 5), new Interval(3, 7),
            new Interval(10, 14), new Interval(18, 22));

        List<Interval> merged = mergeIntervals(intervals);

        List<Interval> gaps = IntStream.range(0, merged.size() - 1)
            .filter(i -> merged.get(i).end() + 1 < merged.get(i + 1).start())
            .mapToObj(i -> new Interval(
                merged.get(i).end() + 1,
                merged.get(i + 1).start() - 1))
            .toList();

        printExercise("6.1 Merged", merged);
        printExercise("    Gaps", gaps);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 6 — FINDING GAPS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Finds gaps within a given coverage range [min, max].
     *
     * <p><strong>Use case:</strong> Finding unscheduled time slots in a
     * calendar, or uncovered ranges in a data partition.</p>
     */
    static void findGapsCoverage() {
        int coverageStart = 0, coverageEnd = 24;
        List<Interval> booked = List.of(
            new Interval(1, 3), new Interval(5, 8),
            new Interval(10, 12), new Interval(15, 20));

        List<Interval> merged = mergeIntervals(booked);
        List<Interval> gaps = new ArrayList<>();

        int current = coverageStart;
        for (Interval iv : merged) {
            if (current < iv.start()) {
                gaps.add(new Interval(current, iv.start() - 1));
            }
            current = Math.max(current, iv.end() + 1);
        }
        if (current <= coverageEnd) {
            gaps.add(new Interval(current, coverageEnd));
        }

        printExercise("6.2 Coverage [0, 24], booked", merged);
        printExercise("    Free gaps", gaps);
    }

    /**
     * Counts how many intervals overlap at each critical point.
     *
     * <p><strong>Algorithm (Sweep Line / Event-based):</strong></p>
     * <ol>
     *   <li>For each interval, create +1 event at start and -1 at end+1</li>
     *   <li>Sort events by time</li>
     *   <li>Sweep through events, maintaining a running count</li>
     * </ol>
     *
     * <p><strong>Time:</strong> O(n log n)</p>
     */
    static void countOverlapsAtEachPoint() {
        List<Interval> intervals = List.of(
            new Interval(1, 4), new Interval(2, 6),
            new Interval(3, 5), new Interval(7, 9));

        // Build event map: point → delta
        TreeMap<Integer, Integer> events = new TreeMap<>();
        intervals.forEach(iv -> {
            events.merge(iv.start(), 1, Integer::sum);
            events.merge(iv.end() + 1, -1, Integer::sum);
        });

        // Sweep to find overlaps at each transition point
        System.out.println("  ► 7.1 Overlap count at each point:");
        int count = 0;
        for (var entry : events.entrySet()) {
            count += entry.getValue();
            System.out.println("    Point %2d: %d overlapping interval(s) %s"
                .formatted(entry.getKey(), count, "█".repeat(count)));
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 7 — COVERING & OVERLAP COUNT
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Finds the maximum number of overlapping intervals at any point.
     *
     * <p><strong>Use case:</strong> Maximum concurrent meetings, peak
     * server load, maximum bandwidth usage.</p>
     */
    static void maxOverlappingIntervals() {
        List<Interval> intervals = List.of(
            new Interval(1, 5), new Interval(2, 6),
            new Interval(3, 7), new Interval(8, 10),
            new Interval(4, 9));

        TreeMap<Integer, Integer> events = new TreeMap<>();
        intervals.forEach(iv -> {
            events.merge(iv.start(), 1, Integer::sum);
            events.merge(iv.end() + 1, -1, Integer::sum);
        });

        int maxOverlap = 0, current = 0, maxPoint = 0;
        for (var entry : events.entrySet()) {
            current += entry.getValue();
            if (current > maxOverlap) {
                maxOverlap = current;
                maxPoint = entry.getKey();
            }
        }

        printExercise("7.2 Max overlapping intervals", maxOverlap + " (at point " + maxPoint + ")");
    }

    /**
     * Minimum number of intervals to remove so that no two overlap.
     *
     * <p><strong>Algorithm (Greedy):</strong> Sort by end time. Always
     * keep the interval that ends earliest; remove any that overlap with it.</p>
     *
     * <p><strong>Time:</strong> O(n log n)</p>
     */
    static void minIntervalsToRemoveForNoOverlap() {
        List<Interval> intervals = List.of(
            new Interval(1, 2), new Interval(2, 3),
            new Interval(3, 4), new Interval(1, 3));

        List<Interval> sorted = intervals.stream()
            .sorted(Comparator.comparingInt(Interval::end))
            .toList();

        int removals = 0;
        int lastEnd = Integer.MIN_VALUE;
        List<Interval> kept = new ArrayList<>();
        for (Interval iv : sorted) {
            if (iv.start() >= lastEnd) {
                kept.add(iv);
                lastEnd = iv.end();
            } else {
                removals++;
            }
        }

        printExercise("7.3 Input", intervals);
        printExercise("    Removals needed for no overlap", removals);
        printExercise("    Kept intervals", kept);
    }

    /**
     * Determines if a person can attend all meetings (no overlap).
     *
     * <p><strong>Algorithm:</strong> Sort by start time. If any meeting
     * starts before the previous one ends, there's a conflict.</p>
     */
    static void canAttendAllMeetings() {
        List<Interval> meetings = List.of(
            new Interval(0, 30), new Interval(5, 10), new Interval(15, 20));
        List<Interval> noConflict = List.of(
            new Interval(0, 5), new Interval(6, 10), new Interval(15, 20));

        printExercise("8.1 Meetings " + meetings,
            canAttendAll(meetings) ? "✓ no conflict" : "✗ conflict!");
        printExercise("    Meetings " + noConflict,
            canAttendAll(noConflict) ? "✓ no conflict" : "✗ conflict!");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 8 — MEETING ROOMS / SCHEDULING
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Minimum number of meeting rooms required (max concurrent meetings).
     *
     * <p><strong>Algorithm (Sweep Line):</strong> Same as max overlap —
     * count the peak of concurrent intervals.</p>
     */
    static void minMeetingRooms() {
        List<Interval> meetings = List.of(
            new Interval(0, 30), new Interval(5, 10),
            new Interval(15, 20), new Interval(7, 25));

        TreeMap<Integer, Integer> events = new TreeMap<>();
        meetings.forEach(m -> {
            events.merge(m.start(), 1, Integer::sum);
            events.merge(m.end(), -1, Integer::sum);
        });

        int maxRooms = 0, current = 0;
        for (int delta : events.values()) {
            current += delta;
            maxRooms = Math.max(maxRooms, current);
        }

        printExercise("8.2 Meetings", meetings);
        printExercise("    Min rooms needed", maxRooms);
    }

    /**
     * Finds free time slots in a person's schedule.
     */
    static void findFreeSlots() {
        int dayStart = 9, dayEnd = 18;
        List<Interval> schedule = List.of(
            new Interval(9, 10), new Interval(11, 13),
            new Interval(14, 15), new Interval(16, 17));

        List<Interval> merged = mergeIntervals(schedule);
        List<Interval> freeSlots = new ArrayList<>();

        int current = dayStart;
        for (Interval meeting : merged) {
            if (current < meeting.start()) {
                freeSlots.add(new Interval(current, meeting.start()));
            }
            current = Math.max(current, meeting.end());
        }
        if (current < dayEnd) {
            freeSlots.add(new Interval(current, dayEnd));
        }

        printExercise("8.3 Schedule (9am-6pm)", schedule);
        printExercise("    Free slots", freeSlots);
    }

    /**
     * Determines which intervals contain a given query point.
     */
    static void pointInIntervalQuery() {
        List<Interval> intervals = List.of(
            new Interval(1, 5), new Interval(3, 8),
            new Interval(7, 12), new Interval(15, 20));
        int queryPoint = 4;

        List<Interval> containing = intervals.stream()
            .filter(iv -> iv.containsPoint(queryPoint))
            .toList();

        printExercise("9.1 Intervals containing point " + queryPoint, containing);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 9 — INTERVAL CONTAINS & QUERIES
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Checks if one set of intervals fully covers another range.
     */
    static void rangeContainmentCheck() {
        List<Interval> intervals = List.of(
            new Interval(1, 5), new Interval(4, 8), new Interval(7, 12));
        Interval target = new Interval(2, 10);

        List<Interval> merged = mergeIntervals(intervals);
        boolean covers = merged.stream().anyMatch(iv -> iv.contains(target));

        printExercise("9.2 Merged", merged);
        printExercise("    Covers " + target + "?", covers);
    }

    /**
     * Computes the total length covered by all intervals (handling overlaps).
     */
    static void totalCoveredLength() {
        List<Interval> intervals = List.of(
            new Interval(1, 4), new Interval(3, 6),
            new Interval(8, 10), new Interval(9, 12));

        int totalLength = mergeIntervals(intervals).stream()
            .mapToInt(Interval::length)
            .sum();

        printExercise("9.3 Intervals", intervals);
        printExercise("    Total covered length", totalLength);
    }

    /**
     * Merging overlapping time slots for a booking system.
     */
    static void mergeTimeSlots() {
        // Time slots as hours (e.g., 9 = 9am, 13 = 1pm)
        record TimeSlot(String label, Interval time) {
        }

        List<TimeSlot> bookings = List.of(
            new TimeSlot("Meeting A", new Interval(9, 11)),
            new TimeSlot("Meeting B", new Interval(10, 12)),
            new TimeSlot("Meeting C", new Interval(14, 16)),
            new TimeSlot("Meeting D", new Interval(15, 17)));

        List<Interval> mergedSlots = mergeIntervals(
            bookings.stream().map(TimeSlot::time).toList());

        printExercise("10.1 Bookings", bookings);
        printExercise("     Merged busy times", mergedSlots);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SECTION 10 — REAL-WORLD SCENARIOS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Finding mutual free time across multiple people's calendars.
     */
    static void calendarAvailability() {
        int dayStart = 9, dayEnd = 18;

        Map<String, List<Interval>> calendars = Map.of(
            "Alice", List.of(new Interval(9, 10), new Interval(12, 14)),
            "Bob", List.of(new Interval(10, 11), new Interval(13, 15)),
            "Clara", List.of(new Interval(9, 11), new Interval(14, 16)));

        // Union all busy times
        List<Interval> allBusy = mergeIntervals(
            calendars.values().stream()
                .flatMap(Collection::stream)
                .toList());

        // Find gaps = mutual free time
        List<Interval> mutualFree = new ArrayList<>();
        int current = dayStart;
        for (Interval busy : allBusy) {
            if (current < busy.start()) {
                mutualFree.add(new Interval(current, busy.start()));
            }
            current = Math.max(current, busy.end());
        }
        if (current < dayEnd) {
            mutualFree.add(new Interval(current, dayEnd));
        }

        System.out.println("  ► 10.2 Calendar availability (9am–6pm):");
        calendars.forEach((name, slots) ->
            System.out.println("    %-8s busy: %s".formatted(name, slots)));
        printExercise("    All busy (merged)", allBusy);
        printExercise("    Mutual free time", mutualFree);
    }

    /**
     * Consolidating overlapping IP/port ranges.
     */
    static void consolidateIpRanges() {
        // Simulated as integer ranges for simplicity
        List<Interval> ranges = List.of(
            new Interval(100, 200), new Interval(150, 300),
            new Interval(400, 500), new Interval(450, 600),
            new Interval(700, 800));

        List<Interval> consolidated = mergeIntervals(ranges);

        printExercise("10.3 IP ranges", ranges);
        printExercise("     Consolidated", consolidated);
    }

    /**
     * Finding time overlaps between employee shifts.
     */
    static void employeeShiftOverlaps() {
        record Shift(String employee, Interval time) {
        }

        List<Shift> shifts = List.of(
            new Shift("Alice", new Interval(8, 16)),
            new Shift("Bob", new Interval(10, 18)),
            new Shift("Clara", new Interval(12, 20)));

        // Find all pairwise overlaps
        System.out.println("  ► 10.4 Employee shift overlaps:");
        for (int i = 0; i < shifts.size(); i++) {
            for (int j = i + 1; j < shifts.size(); j++) {
                Shift s1 = shifts.get(i), s2 = shifts.get(j);
                s1.time().intersect(s2.time()).ifPresent(overlap ->
                    System.out.println("    %s ∩ %s = %s (%dh overlap)".formatted(
                        s1.employee(), s2.employee(), overlap, overlap.length())));
            }
        }

        // Find when ALL employees overlap
        Optional<Interval> allOverlap = shifts.stream()
            .map(Shift::time)
            .reduce(Interval::intersect)
            .flatMap(opt -> opt);

        // The reduce here doesn't quite work because intersect returns Optional.
        // Let's do it properly:
        Interval current = shifts.getFirst().time();
        Optional<Interval> universalOverlap = Optional.of(current);
        for (int i = 1; i < shifts.size() && universalOverlap.isPresent(); i++) {
            universalOverlap = universalOverlap.get().intersect(shifts.get(i).time());
        }

        printExercise("    All-three overlap",
            universalOverlap.map(Interval::toString).orElse("none"));
    }

    /**
     * The core merge-overlapping-intervals algorithm.
     *
     * <p><strong>Steps:</strong> sort by start → iterate → merge or append.</p>
     *
     * @param intervals the input intervals (may be unsorted, may overlap)
     * @return a new list of non-overlapping, sorted, merged intervals
     */
    static List<Interval> mergeIntervals(List<Interval> intervals) {
        if (intervals.isEmpty()) {
            return List.of();
        }

        List<Interval> sorted = intervals.stream().sorted().toList();
        List<Interval> merged = new ArrayList<>();
        merged.add(sorted.getFirst());

        for (int i = 1; i < sorted.size(); i++) {
            Interval last = merged.getLast();
            Interval current = sorted.get(i);
            if (last.overlaps(current)) {
                merged.set(merged.size() - 1, last.merge(current));
            } else {
                merged.add(current);
            }
        }
        return merged;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  SHARED UTILITIES
    // ═══════════════════════════════════════════════════════════════════

    static boolean canAttendAll(List<Interval> meetings) {
        List<Interval> sorted = meetings.stream().sorted().toList();
        return IntStream.range(1, sorted.size())
            .noneMatch(i -> sorted.get(i).start() < sorted.get(i - 1).end());
    }

    private static void printExercise(String label, Object result) {
        System.out.println("  ► " + label + ": " + result);
    }

    private static void printSection(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  SECTION " + title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    /**
     * Represents a closed interval [start, end].
     *
     * <p>Using a record for immutability and built-in toString/equals.
     * Implements {@link Comparable} for natural ordering by start time.</p>
     *
     * @param start the inclusive lower bound
     * @param end   the inclusive upper bound
     */
    record Interval(int start, int end) implements Comparable<Interval> {
        Interval {
            if (start > end) {
                throw new IllegalArgumentException(
                    "start (%d) > end (%d)".formatted(start, end));
            }
        }

        /**
         * Returns true if this interval overlaps with other.
         */
        boolean overlaps(Interval other) {
            return this.start <= other.end && other.start <= this.end;
        }

        /**
         * Returns true if this interval fully contains other.
         */
        boolean contains(Interval other) {
            return this.start <= other.start && this.end >= other.end;
        }

        /**
         * Returns true if this interval contains the given point.
         */
        boolean containsPoint(int point) {
            return start <= point && point <= end;
        }

        /**
         * Merges this interval with an overlapping interval.
         */
        Interval merge(Interval other) {
            return new Interval(Math.min(start, other.start), Math.max(end, other.end));
        }

        /**
         * Returns the intersection with another interval, or empty if none.
         */
        Optional<Interval> intersect(Interval other) {
            int lo = Math.max(start, other.start);
            int hi = Math.min(end, other.end);
            return lo <= hi ? Optional.of(new Interval(lo, hi)) : Optional.empty();
        }

        /**
         * Returns the length/size of this interval.
         */
        int length() {
            return end - start;
        }

        @Override
        public int compareTo(Interval o) {
            return start != o.start ? Integer.compare(start, o.start)
                : Integer.compare(end, o.end);
        }

        @Override
        public String toString() {
            return "[%d, %d]".formatted(start, end);
        }
    }
}
