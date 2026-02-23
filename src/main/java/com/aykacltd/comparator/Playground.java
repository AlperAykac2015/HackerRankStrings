package com.aykacltd.comparator;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Playground {

  public static void main(String[] args) {
    List<Person> people = samplePeople();

    demoGroupPeopleByAge(people);
    demoGroupNamesByAge(people);
    demoCountPeopleByAge(people);
    demoAverageAgeByCity(people);
    demoCitiesWithMoreThanOneInhabitant(people);
    demoMostPopularAgeAlper(people);
    demoMostPopularAgeAlper2(people);
    demoMostPopularAge(people);

    demoNameToCityMapFromPeople(people);
    demoNameToCityUsingRepository(people);

    demoDoubleStreamMapMulti();
  }

  // ======================
  // Sample data
  // ======================
  static List<Person> samplePeople() {
    Person jon = new Person(City.Tulsa, "Jon", 42);
    Person amy = new Person(City.Athens, "Amy", 21);
    Person bill = new Person(City.London, "Bill", 33);
    Person eric = new Person(City.Athens, "Eric", 33);

    return Arrays.asList(jon, amy, bill, eric);
  }

  // ======================
  // Stream demos: grouping/aggregations
  // ======================
  static void demoGroupPeopleByAge(List<Person> people) {
    System.out.println("\n== People grouped by age ==");
    Map<Integer, List<Person>> peopleByAge =
        people.stream().collect(groupingBy(Person::age));
    System.out.println(peopleByAge);
  }

  static void demoGroupNamesByAge(List<Person> people) {
    System.out.println("\n== Names grouped by age ==");
    Map<Integer, List<String>> namesByAge =
        people.stream().collect(groupingBy(
            Person::age,
            Collectors.mapping(Person::name, Collectors.toList())
        ));
    System.out.println(namesByAge);
  }

  static void demoCountPeopleByAge(List<Person> people) {
    System.out.println("\n== Count (population) by age ==");
    Map<Integer, Long> populationByAge =
        people.stream().collect(groupingBy(Person::age, counting()));
    System.out.println(populationByAge);
  }

  static void demoAverageAgeByCity(List<Person> people) {
    System.out.println("\n== Average age by city ==");
    Map<City, Double> averageAgeByCity =
        people.stream().collect(groupingBy(
            Person::city,
            Collectors.averagingInt(Person::age)
        ));
    System.out.println(averageAgeByCity);
  }

  static void demoCitiesWithMoreThanOneInhabitant(List<Person> people) {
    System.out.println("\n== Cities with > 1 inhabitant ==");
    Set<City> populousCities =
        people.stream()
            .collect(groupingBy(Person::city, counting()))
            .entrySet()
            .stream()
            .filter(e -> e.getValue() > 1)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());

    System.out.println(populousCities);
  }

  static void demoMostPopularAgeAlper(List<Person> people) {
    System.out.println("\n== Most popular age (max count) ==");
    int mostPopularAge = people.stream()
        .collect(groupingBy(Person::age, counting()))
        .entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // descending
        .limit(1)
        .map(e -> e.getKey())
        .findFirst()
        .orElse(-1);
    System.out.println("Alper>>" + mostPopularAge);
  }

  static void demoMostPopularAgeAlper2(List<Person> people) {
    System.out.println("\n== Most popular age (max count) ==");
    int mostPopularAge = people.stream()
        .collect(groupingBy(Person::age, counting()))
        .entrySet().stream()
        .reduce((e1, e2) -> e1.getValue() >= e2.getValue() ? e1 : e2)
        .map(e -> e.getKey())
        .orElse(-1);
    System.out.println("Alper>>" + mostPopularAge);
  }

  static void demoMostPopularAge(List<Person> people) {
    System.out.println("\n== Most popular age (max count) ==");
    int mostPopularAge =
        people.stream()
            .collect(groupingBy(Person::age, counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(-1);

    System.out.println(mostPopularAge);
  }

  // ======================
  // Stream demo: toMap(name -> city)
  // ======================
  static void demoNameToCityMapFromPeople(List<Person> people) {
    System.out.println("\n== Map: name -> city (direct from Person) ==");
    Map<String, City> nameToCity =
        people.stream().collect(Collectors.toMap(Person::name, Person::city));
    System.out.println(nameToCity);
  }

  // ======================
  // Repo demo: findCityByUsername(...)
  // ======================
  static void demoNameToCityUsingRepository(List<Person> people) {
    System.out.println("\n== Map: name -> city (via CityRepository.findCityByUsername) ==");

    CityRepository repo = new CityRepository(
        Map.of("Jon", City.Tulsa, "Amy", City.Athens, "Bill", City.London, "Eric", City.Athens)
    );

    // Using only names from people, but resolving city via repo:
    Map<String, City> nameToCityViaRepo =
        people.stream()
            .map(Person::name)
            .distinct()
            .collect(Collectors.toMap(
                name -> name,
                repo::findCityByUsernameOrThrow
            ));

    System.out.println(nameToCityViaRepo);
  }

  // ======================
  // DoubleStream.mapMulti demo
  // ======================
  static void demoDoubleStreamMapMulti() {
    System.out.println("\n== DoubleStream.mapMulti demo ==");

    DoubleStream out =
        DoubleStream.of(1.0, 2.0, 3.0, 4.0)
            .mapMulti((value, consumer) -> {
              // Emit 0..n values per input:
              // - if value is even: emit value twice
              // - if value is odd : emit value and value^2
              if (((int) value) % 2 == 0) {
                consumer.accept(value);
                consumer.accept(value);
              } else {
                consumer.accept(value);
                consumer.accept(value * value);
              }
            });

    out.forEach(v -> System.out.println("emit: " + v));
  }

  // === Domain model used across the demos ===
  enum City { Tulsa, Athens, London }

  record Person(City city, String name, int age) {
  }

  // === “Repo” demo (pretend this is DB/service) ===
  static final class CityRepository {
    private final Map<String, City> db;

    CityRepository(Map<String, City> db) {
      this.db = Objects.requireNonNull(db);
    }

    City findCityByUsername(String username) {
      return db.get(username); // null if not found
    }

    City findCityByUsernameOrThrow(String username) {
      City city = db.get(username);
      if (city == null) {
        throw new NoSuchElementException("No city for username=" + username);
      }
      return city;
    }
  }
}
