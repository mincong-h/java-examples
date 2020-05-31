package io.mincong.ocpjp.design_principles.dao.method_factory;

import io.mincong.ocpjp.design_principles.dao.GroupDao;
import io.mincong.ocpjp.design_principles.dao.PersonDao;

/** @author Mincong Huang */
public abstract class DaoFactory {

  protected abstract PersonDao getPersonDao();

  protected abstract GroupDao getGroupDao();

  public PersonDao getPersonDaoInstance() {
    return getPersonDao();
  }

  public GroupDao getGroupDaoInstance() {
    return getGroupDao();
  }
}
