package io.mincongh.hashcode.bad;

/**
 * The class model for demonstrating the phenomenon when only the {@code equals()} is override, but
 * not the {@code hashcode()}
 *
 * @author Mincong Huang
 */
public class AthleteOnlyOverrideEquals {

  private String email;

  private String name;

  public AthleteOnlyOverrideEquals(String email, String name) {
    this.email = email;
    this.name = name;
  }

  // The method is missing!
  // public int hashCode() { ... }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AthleteOnlyOverrideEquals other = (AthleteOnlyOverrideEquals) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Athlete [email=" + email + ", name=" + name + "]";
  }
}
