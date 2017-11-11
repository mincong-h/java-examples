package io.mincongh.hashcode.good;

/**
 * The class model correctly implemented both the {@code equals()} and the {@code hashcode()}.
 *
 * @author Mincong Huang
 */
public class Athlete {

  private String email;

  private String name;

  public Athlete(String email, String name) {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Athlete other = (Athlete) obj;
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
