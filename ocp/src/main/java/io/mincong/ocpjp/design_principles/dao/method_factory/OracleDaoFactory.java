package io.mincong.ocpjp.design_principles.dao.method_factory;

import io.mincong.ocpjp.design_principles.dao.GroupDao;
import io.mincong.ocpjp.design_principles.dao.PersonDao;

/**
 * @author Mincong Huang
 */
public class OracleDaoFactory extends DaoFactory {

  @Override
  protected PersonDao getPersonDao() {
    return new PersonDaoOracleImpl();
  }

  @Override
  protected GroupDao getGroupDao() {
    return new GroupDaoOracleImpl();
  }

}
