package io.mincongh.hashcode.bad;

/**
 * The class model for demonstrating the phenomenon when only the {@code hashcode()} is override,
 * but not the {@code equals()}
 *
 * @author Mincong Huang
 */
public class AthleteOnlyOverrideHashcode {

  private String email;

  private String name;

  public AthleteOnlyOverrideHashcode(String email, String name) {
    this.email = email;
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    return result;
  }

  // The method is missing!
  // public boolean equals(Object obj) { ... }

  @Override
  public String toString() {
    return "Athlete [email=" + email + ", name=" + name + "]";
  }
}
