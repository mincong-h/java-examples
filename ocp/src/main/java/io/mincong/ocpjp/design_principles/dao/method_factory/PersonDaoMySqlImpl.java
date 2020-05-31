package io.mincong.ocpjp.design_principles.dao.method_factory;

import io.mincong.ocpjp.design_principles.dao.Person;
import io.mincong.ocpjp.design_principles.dao.PersonDao;
import java.util.Collection;

/** @author Mincong Huang */
public class PersonDaoMySqlImpl implements PersonDao {

  @Override
  public int createPerson(Person p) {
    // TODO Implement method
    return 0;
  }

  @Override
  public Person get(int personId) {
    // TODO Implement method
    return null;
  }

  @Override
  public boolean delete(Person p) {
    // TODO Implement method
    return false;
  }

  @Override
  public boolean update(Person p) {
    // TODO Implement method
    return false;
  }

  @Override
  public Collection<Person> getAll() {
    // TODO Implement method
    return null;
  }
}
