package io.mincong.ocpjp.design_principles.dao.simple_factory;

import io.mincong.ocpjp.design_principles.dao.Person;
import io.mincong.ocpjp.design_principles.dao.PersonDao;
import java.util.Collection;

/** @author Mincong Huang */
public class PersonDaoOracleImpl implements PersonDao {

  @Override
  public int createPerson(Person p) {
    // TODO Implement the method logic
    return 0;
  }

  @Override
  public Person get(int personId) {
    // TODO Implement the method logic
    return null;
  }

  @Override
  public boolean delete(Person p) {
    // TODO Implement the method logic
    return false;
  }

  @Override
  public boolean update(Person p) {
    // TODO Implement the method logic
    return false;
  }

  @Override
  public Collection<Person> getAll() {
    // TODO Implement the method logic
    return null;
  }
}
