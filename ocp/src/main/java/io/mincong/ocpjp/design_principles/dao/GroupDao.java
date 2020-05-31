package io.mincong.ocpjp.design_principles.dao;

import java.util.Collection;

/** @author Mincong Huang */
public interface GroupDao {

  /** @return Group ID */
  int createGroup(Group g);

  Group get(int groupId);

  boolean delete(Group g);

  boolean update(Group g);

  Collection<Group> getAll();
}
