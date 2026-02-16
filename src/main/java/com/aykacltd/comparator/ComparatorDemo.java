package com.aykacltd.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo {

  // 2. comparing() - Static factory method
  public static Comparator<Person> comparingByName() {
    return Comparator.comparing(Person::getName);
  }

  // 3. comparing() with custom comparator
  public static Comparator<Person> comparingByNameCaseInsensitive() {
    return Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER);
  }

  // 4. comparingInt()
  public static Comparator<Person> comparingByAge() {
    return Comparator.comparingInt(p -> p.getAge() != null ? p.getAge() : 0);
  }

  // 5. comparingLong()
  public static Comparator<Person> comparingByAgeLong() {
    return Comparator.comparingLong(p -> p.getAge() != null ? p.getAge() : 0L);
  }

  // 6. comparingDouble()
  public static Comparator<Person> comparingBySalary() {
    return Comparator.comparingDouble(p -> p.getSalary() != null ? p.getSalary() : 0.0);
  }

  // 7. thenComparing()
  public static Comparator<Person> multiAttributeComparator() {
    return Comparator.comparing(Person::getName).thenComparing(Person::getSurname)
        .thenComparing(Person::getAge);
  }

  // 8. thenComparing() with custom comparator
  public static Comparator<Person> multiAttributeWithCustomComparator() {
    return Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER)
        .thenComparing(Person::getSurname, String.CASE_INSENSITIVE_ORDER)
        .thenComparingInt(p -> p.getAge() != null ? p.getAge() : 0);
  }

  // 9. thenComparingInt()
  public static Comparator<Person> namesThenAge() {
    return Comparator.comparing(Person::getName)
        .thenComparingInt(p -> p.getAge() != null ? p.getAge() : 0);
  }

  // 10. thenComparingLong()
  public static Comparator<Person> namesThenAgeLong() {
    return Comparator.comparing(Person::getName)
        .thenComparingLong(p -> p.getAge() != null ? p.getAge() : 0L);
  }

  // 11. thenComparingDouble()
  public static Comparator<Person> namesThenSalary() {
    return Comparator.comparing(Person::getName)
        .thenComparingDouble(p -> p.getSalary() != null ? p.getSalary() : 0.0);
  }

  // 12. reversed()
  public static Comparator<Person> reversedByName() {
    return Comparator.comparing(Person::getName).reversed();
  }

  // 13. naturalOrder()
  public static Comparator<String> naturalOrder() {
    return Comparator.naturalOrder();
  }

  // 14. reverseOrder()
  public static Comparator<String> reverseOrder() {
    return Comparator.reverseOrder();
  }

  // 15. nullsFirst()
  public static Comparator<Person> nullsFirstByAge() {
    return Comparator.comparing(Person::getAge, Comparator.nullsFirst(Integer::compareTo));
  }

  // 16. nullsLast()
  public static Comparator<Person> nullsLastByAge() {
    return Comparator.comparing(Person::getAge, Comparator.nullsLast(Integer::compareTo));
  }

  // 17. nullsFirst() on object
  public static Comparator<Person> nullsFirstPerson() {
    return Comparator.nullsFirst(Comparator.comparing(Person::getName));
  }

  // 18. nullsLast() on object
  public static Comparator<Person> nullsLastPerson() {
    return Comparator.nullsLast(Comparator.comparing(Person::getName));
  }

  // 19. Complex chaining with nulls
  public static Comparator<Person> complexComparator() {
    return Comparator.comparing(Person::getName,
            Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
        .thenComparing(Person::getSurname, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
        .thenComparing(Person::getAge, Comparator.nullsLast(Integer::compareTo))
        .thenComparing(Person::getSalary, Comparator.nullsFirst(Double::compareTo));
  }

  // 20. Lambda-based comparator
  public static Comparator<Person> lambdaComparator() {
    return (p1, p2) -> {
      int nameComp = p1.getName().compareTo(p2.getName());
      if (nameComp != 0) {
        return nameComp;
      }
      return Integer.compare(p1.getAge() != null ? p1.getAge() : 0,
          p2.getAge() != null ? p2.getAge() : 0);
    };
  }

  // 21. Reversed complex comparator
  public static Comparator<Person> reversedComplexComparator() {
    return Comparator.comparing(Person::getName).thenComparing(Person::getAge).reversed();
  }

  public static void main(String[] args) {
    List<Person> people = new ArrayList<>();
    people.add(new Person("John", "Smith", 30, 50000.0));
    people.add(new Person("Alice", "Johnson", 25, 60000.0));
    people.add(new Person("Bob", "Smith", 35, 55000.0));
    people.add(new Person("John", "Doe", 28, 52000.0));
    people.add(new Person("John", "Smith", 25, 48000.0));
    people.add(new Person("Charlie", "Brown", null, 45000.0));
    people.add(new Person("David", "Wilson", 40, null));

    System.out.println("=== By Name-Age ===");
    people.stream().sorted(lambdaComparator()).forEach(System.out::println);

    System.out.println("=== By Name ===");
    people.stream().sorted(comparingByName()).forEach(System.out::println);

    System.out.println("\n=== By Age ===");
    people.stream().sorted(comparingByAge()).forEach(System.out::println);

    System.out.println("\n=== Multi-Attribute ===");
    people.stream().sorted(multiAttributeComparator()).forEach(System.out::println);

    System.out.println("\n=== Reversed ===");
    people.stream().sorted(reversedByName()).forEach(System.out::println);

    System.out.println("\n=== Nulls First ===");
    people.stream().sorted(nullsFirstByAge()).forEach(System.out::println);
  }

  // 1. Basic compare() method
  public static class BasicComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
      return p1.getName().compareTo(p2.getName());
    }
  }
}
