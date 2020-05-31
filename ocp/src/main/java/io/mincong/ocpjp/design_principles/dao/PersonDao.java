package io.mincong.ocpjp.design_principles.dao;

import java.util.Collection;

/** @author Mincong Huang */
public interface PersonDao {

  /** @return Person ID */
  int createPerson(Person p);

  Person get(int personId);

  boolean delete(Person p);

  boolean update(Person p);

  Collection<Person> getAll();
}
