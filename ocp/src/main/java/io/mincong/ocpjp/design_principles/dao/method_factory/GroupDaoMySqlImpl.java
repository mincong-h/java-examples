package io.mincong.ocpjp.design_principles.dao.method_factory;

import io.mincong.ocpjp.design_principles.dao.Group;
import io.mincong.ocpjp.design_principles.dao.GroupDao;
import java.util.Collection;

/** @author Mincong Huang */
public class GroupDaoMySqlImpl implements GroupDao {

  @Override
  public int createGroup(Group g) {
    // TODO Implement method
    return 0;
  }

  @Override
  public Group get(int groupId) {
    // TODO Implement method
    return null;
  }

  @Override
  public boolean delete(Group g) {
    // TODO Implement method
    return false;
  }

  @Override
  public boolean update(Group g) {
    // TODO Implement method
    return false;
  }

  @Override
  public Collection<Group> getAll() {
    // TODO Implement method
    return null;
  }
}
