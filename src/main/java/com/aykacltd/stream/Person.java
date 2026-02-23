package com.aykacltd.stream;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Rich domain entity representing a person with linked {@link Address},
 * {@link Profession}, and {@link Skill} objects.
 *
 * <p>This class serves as the central aggregate for all Stream API exercises.
 * It deliberately uses a traditional class (not a record) to demonstrate
 * builder-style construction, computed properties, and mutable-friendly
 * patterns that are common in enterprise Java codebases.</p>
 *
 * <h3>Relationships</h3>
 * <ul>
 *   <li>{@code addresses}  – one-to-many: a person may have HOME, WORK, and SECONDARY addresses</li>
 *   <li>{@code profession} – one-to-one: current professional details</li>
 *   <li>{@code skills}     – one-to-many: technical and soft skills with proficiency levels</li>
 * </ul>
 */
public class Person {

  private final String firstName;
  private final String lastName;
  private final LocalDate dateOfBirth;
  private final Gender gender;
  private final String email;
  private final List<Address> addresses;
  private final Profession profession;
  private final List<Skill> skills;

  private Person(Builder builder) {
    this.firstName = Objects.requireNonNull(builder.firstName);
    this.lastName = Objects.requireNonNull(builder.lastName);
    this.dateOfBirth = Objects.requireNonNull(builder.dateOfBirth);
    this.gender = Objects.requireNonNull(builder.gender);
    this.email = builder.email;
    this.addresses = builder.addresses == null ? List.of() : List.copyOf(builder.addresses);
    this.profession = builder.profession;
    this.skills = builder.skills == null ? List.of() : List.copyOf(builder.skills);
  }

  public static Builder builder() {
    return new Builder();
  }

  // ───────── Getters ─────────

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public Gender getGender() {
    return gender;
  }

  public String getEmail() {
    return email;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public Profession getProfession() {
    return profession;
  }

  public List<Skill> getSkills() {
    return skills;
  }

  // ───────── Computed Properties ─────────

  /**
   * Calculates the person's current age based on today's date.
   *
   * @return age in complete years
   */
  public int getAge() {
    return java.time.Period.between(dateOfBirth, LocalDate.now()).getYears();
  }

  /**
   * Returns the home city derived from the first HOME-type address,
   * or "Unknown" if no home address exists.
   *
   * @return the city of the person's home address
   */
  public String getHomeCity() {
    return addresses.stream()
        .filter(a -> a.type() == Address.AddressType.HOME)
        .map(Address::city)
        .findFirst()
        .orElse("Unknown");
  }

  /**
   * Returns the country from the first HOME-type address,
   * or "Unknown" if no home address exists.
   *
   * @return the country of the person's home address
   */
  public String getHomeCountry() {
    return addresses.stream()
        .filter(a -> a.type() == Address.AddressType.HOME)
        .map(Address::country)
        .findFirst()
        .orElse("Unknown");
  }

  @Override
  public String toString() {
    return "Person{name='%s %s', age=%d, profession=%s, city=%s}"
        .formatted(firstName, lastName, getAge(),
            profession != null ? profession.title() : "N/A",
            getHomeCity());
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
    return Objects.equals(firstName, person.firstName)
        && Objects.equals(lastName, person.lastName)
        && Objects.equals(dateOfBirth, person.dateOfBirth)
        && Objects.equals(email, person.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, dateOfBirth, email);
  }

  // ───────── Builder ─────────

  /**
   * Gender classification used for demographic grouping exercises.
   */
  public enum Gender {
    MALE, FEMALE, NON_BINARY
  }

  /**
   * Fluent builder for constructing {@link Person} instances.
   */
  public static class Builder {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private List<Address> addresses;
    private Profession profession;
    private List<Skill> skills;

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder dateOfBirth(LocalDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public Builder gender(Gender gender) {
      this.gender = gender;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder addresses(List<Address> addresses) {
      this.addresses = addresses;
      return this;
    }

    public Builder profession(Profession profession) {
      this.profession = profession;
      return this;
    }

    public Builder skills(List<Skill> skills) {
      this.skills = skills;
      return this;
    }

    public Person build() {
      return new Person(this);
    }
  }
}
