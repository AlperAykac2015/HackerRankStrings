package com.aykacltd.comparator;

import java.util.Objects;

public class Person {
  private String name;
  private String surname;
  private Integer age;
  private Double salary;

  public Person(String name, String surname, Integer age, Double salary) {
    this.name = name;
    this.surname = surname;
    this.age = age;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public Integer getAge() {
    return age;
  }

  public Double getSalary() {
    return salary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(name, person.name) &&
        Objects.equals(surname, person.surname) &&
        Objects.equals(age, person.age) &&
        Objects.equals(salary, person.salary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, age, salary);
  }

  @Override
  public String toString() {
    return "Person{name='" + name + "', surname='" + surname + "', age=" + age + ", salary=" +
        salary + "}";
  }
}
