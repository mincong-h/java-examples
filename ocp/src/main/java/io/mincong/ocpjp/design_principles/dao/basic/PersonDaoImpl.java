package io.mincong.ocpjp.design_principles.dao.basic;

import io.mincong.ocpjp.design_principles.dao.Person;
import io.mincong.ocpjp.design_principles.dao.PersonDao;
import java.util.Collection;

/**
 * @author Mincong Huang
 */
public class PersonDaoImpl implements PersonDao {

  @Override
  public int createPerson(Person p) {
    // TODO Connect to data-store
    // Insert data for person
    return 0;
  }

  @Override
  public Person get(int personId) {
    // TODO Connect to data-store
    // Retrieve and return data for person ID
    return null;
  }

  @Override
  public boolean delete(Person p) {
    // TODO Connect to data-store
    // Delete data for person ID
    return false;
  }

  @Override
  public boolean update(Person p) {
    // TODO Connect to data-store
    // Update employee data
    return false;
  }

  @Override
  public Collection<Person> getAll() {
    // TODO Connect to data-store
    // Retrieve person data, return as collection
    return null;
  }

}
