package com.aykacltd.comparator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ComparatorDemoTest {

  private List<Person> people;

  @BeforeEach
  void setUp() {
    people = new ArrayList<>();
    people.add(new Person("John", "Smith", 30, 50000.0));
    people.add(new Person("Alice", "Johnson", 25, 60000.0));
    people.add(new Person("Bob", "Smith", 35, 55000.0));
    people.add(new Person("John", "Doe", 28, 52000.0));
    people.add(new Person("John", "Smith", 25, 48000.0));
  }

  @Nested
  @DisplayName("Basic Comparator Tests")
  class BasicComparatorTests {

    @Test
    @DisplayName("Test basic compare()")
    void testBasicComparator() {
      people.sort(new ComparatorDemo.BasicComparator());
      assertEquals("Alice", people.get(0).getName());
      assertEquals("Bob", people.get(1).getName());
    }

    @Test
    @DisplayName("Test comparing() by name")
    void testComparingByName() {
      people.sort(ComparatorDemo.comparingByName());
      assertEquals("Alice", people.get(0).getName());
      assertEquals("John", people.get(4).getName());
    }

    @Test
    @DisplayName("Test case insensitive")
    void testComparingCaseInsensitive() {
      people.add(new Person("alice", "Brown", 22, 45000.0));
      people.sort(ComparatorDemo.comparingByNameCaseInsensitive());
      assertTrue(people.get(0).getName().equalsIgnoreCase("alice"));
    }
  }

  @Nested
  @DisplayName("Primitive Type Comparators")
  class PrimitiveComparatorTests {

    @Test
    @DisplayName("Test comparingInt()")
    void testComparingByAge() {
      people.sort(ComparatorDemo.comparingByAge());
      assertEquals(25, people.get(0).getAge());
      assertEquals(35, people.get(4).getAge());
    }

    @Test
    @DisplayName("Test comparingDouble()")
    void testComparingBySalary() {
      people.sort(ComparatorDemo.comparingBySalary());
      assertEquals(48000.0, people.get(0).getSalary());
      assertEquals(60000.0, people.get(4).getSalary());
    }
  }

  @Nested
  @DisplayName("Chaining Comparators")
  class ChainingComparatorTests {

    @Test
    @DisplayName("Test thenComparing()")
    void testMultiAttributeComparator() {
      people.sort(ComparatorDemo.multiAttributeComparator());
      assertEquals("Alice", people.get(0).getName());

      int johnDoeIndex = -1, johnSmith25Index = -1, johnSmith30Index = -1;
      for (int i = 0; i < people.size(); i++) {
        Person p = people.get(i);
        if ("John".equals(p.getName()) && "Doe".equals(p.getSurname())) {
          johnDoeIndex = i;
        } else if ("John".equals(p.getName()) && "Smith".equals(p.getSurname()) &&
            p.getAge() == 25) {
          johnSmith25Index = i;
        } else if ("John".equals(p.getName()) && "Smith".equals(p.getSurname()) &&
            p.getAge() == 30) {
          johnSmith30Index = i;
        }
      }
      assertTrue(johnDoeIndex < johnSmith25Index);
      assertTrue(johnSmith25Index < johnSmith30Index);
    }

    @Test
    @DisplayName("Test thenComparingInt()")
    void testNamesThenAge() {
      people.sort(ComparatorDemo.namesThenAge());
      List<Person> johns = new ArrayList<>();
      for (Person p : people) {
        if ("John".equals(p.getName())) {
          johns.add(p);
        }
      }
      assertEquals(3, johns.size());
      assertEquals(25, johns.get(0).getAge());
      assertEquals(28, johns.get(1).getAge());
      assertEquals(30, johns.get(2).getAge());
    }
  }

  @Nested
  @DisplayName("Reversed Comparators")
  class ReversedComparatorTests {

    @Test
    @DisplayName("Test reversed()")
    void testReversedByName() {
      people.sort(ComparatorDemo.reversedByName());
      assertTrue(people.get(0).getName().compareTo(people.get(4).getName()) > 0);
    }

    @Test
    @DisplayName("Test naturalOrder()")
    void testNaturalOrder() {
      List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));
      names.sort(ComparatorDemo.naturalOrder());
      assertEquals("Alice", names.get(0));
      assertEquals("Charlie", names.get(2));
    }

    @Test
    @DisplayName("Test reverseOrder()")
    void testReverseOrder() {
      List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));
      names.sort(ComparatorDemo.reverseOrder());
      assertEquals("Charlie", names.get(0));
      assertEquals("Alice", names.get(2));
    }
  }

  @Nested
  @DisplayName("Null Handling")
  class NullHandlingTests {

    @BeforeEach
    void addNullData() {
      people.add(new Person("Charlie", "Brown", null, 45000.0));
      people.add(new Person("David", "Wilson", 40, null));
    }

    @Test
    @DisplayName("Test nullsFirst()")
    void testNullsFirstByAge() {
      people.sort(ComparatorDemo.nullsFirstByAge());
      assertNull(people.get(0).getAge());
      assertNotNull(people.get(1).getAge());
    }

    @Test
    @DisplayName("Test nullsLast()")
    void testNullsLastByAge() {
      people.sort(ComparatorDemo.nullsLastByAge());
      assertNotNull(people.get(0).getAge());
      assertNull(people.get(people.size() - 1).getAge());
    }

    @Test
    @DisplayName("Test nullsFirst() on object")
    void testNullsFirstPerson() {
      people.add(null);
      people.sort(ComparatorDemo.nullsFirstPerson());
      assertNull(people.get(0));
      assertNotNull(people.get(1));
    }

    @Test
    @DisplayName("Test nullsLast() on object")
    void testNullsLastPerson() {
      people.add(null);
      people.sort(ComparatorDemo.nullsLastPerson());
      assertNotNull(people.get(0));
      assertNull(people.get(people.size() - 1));
    }

    @Test
    @DisplayName("Test complex with nulls")
    void testComplexComparator() {
      people.sort(ComparatorDemo.complexComparator());
      assertNotNull(people);
      assertEquals(7, people.size());
    }
  }

  @Nested
  @DisplayName("Lambda Comparators")
  class LambdaComparatorTests {

    @Test
    @DisplayName("Test lambda comparator")
    void testLambdaComparator() {
      people.sort(ComparatorDemo.lambdaComparator());
      assertEquals("Alice", people.get(0).getName());

      List<Person> johns = new ArrayList<>();
      for (Person p : people) {
        if ("John".equals(p.getName())) {
          johns.add(p);
        }
      }
      for (int i = 0; i < johns.size() - 1; i++) {
        assertTrue(johns.get(i).getAge() <= johns.get(i + 1).getAge());
      }
    }

    @Test
    @DisplayName("Test reversed complex")
    void testReversedComplexComparator() {
      people.sort(ComparatorDemo.reversedComplexComparator());
      assertTrue(people.get(0).getName().compareTo(people.get(people.size() - 1).getName()) > 0
          || (people.get(0).getName().equals(people.get(people.size() - 1).getName())
          && people.get(0).getAge() > people.get(people.size() - 1).getAge()));
    }
  }

  @Nested
  @DisplayName("Edge Cases")
  class EdgeCaseTests {

    @Test
    @DisplayName("Test empty list")
    void testEmptyList() {
      List<Person> emptyList = new ArrayList<>();
      assertDoesNotThrow(() -> emptyList.sort(ComparatorDemo.comparingByName()));
      assertTrue(emptyList.isEmpty());
    }

    @Test
    @DisplayName("Test single element")
    void testSingleElement() {
      List<Person> single = new ArrayList<>();
      single.add(new Person("John", "Doe", 30, 50000.0));
      single.sort(ComparatorDemo.comparingByName());
      assertEquals(1, single.size());
    }

    @Test
    @DisplayName("Test all equal")
    void testAllEqual() {
      List<Person> equal = new ArrayList<>();
      Person p = new Person("John", "Doe", 30, 50000.0);
      equal.add(p);
      equal.add(p);
      equal.add(p);
      equal.sort(ComparatorDemo.multiAttributeComparator());
      assertEquals(3, equal.size());
    }
  }

  @Nested
  @DisplayName("Comparator Properties")
  class ComparatorPropertiesTests {

    @Test
    @DisplayName("Test transitivity")
    void testTransitivity() {
      Comparator<Person> comp = ComparatorDemo.comparingByAge();
      Person p1 = new Person("A", "X", 20, 40000.0);
      Person p2 = new Person("B", "Y", 25, 50000.0);
      Person p3 = new Person("C", "Z", 30, 60000.0);
      assertTrue(comp.compare(p1, p2) < 0);
      assertTrue(comp.compare(p2, p3) < 0);
      assertTrue(comp.compare(p1, p3) < 0);
    }

    @Test
    @DisplayName("Test symmetry")
    void testSymmetry() {
      Comparator<Person> comp = ComparatorDemo.comparingByName();
      Person p1 = new Person("Alice", "X", 20, 40000.0);
      Person p2 = new Person("Bob", "Y", 25, 50000.0);
      int r1 = comp.compare(p1, p2);
      int r2 = comp.compare(p2, p1);
      assertEquals(-Integer.signum(r1), Integer.signum(r2));
    }

    @Test
    @DisplayName("Test reflexivity")
    void testReflexivity() {
      Comparator<Person> comp = ComparatorDemo.comparingByName();
      Person p1 = new Person("Alice", "X", 20, 40000.0);
      assertEquals(0, comp.compare(p1, p1));
    }
  }
}
