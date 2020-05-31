package io.mincong.ocpjp.design_principles.dao.simple_factory;

import io.mincong.ocpjp.design_principles.dao.PersonDao;

/**
 * DAO Factory uses the <i>Simple Factory</i> pattern.
 *
 * @author Mincong Huang
 */
public abstract class DaoFactory {

  public static final int ORACLE = 1;

  public static final int MYSQL = 2;

  public static PersonDao getPersonDaoInstance(int databaseType) {
    if (databaseType == ORACLE) {
      return new PersonDaoOracleImpl();
    }
    if (databaseType == MYSQL) {
      return new PersonDaoMySqlImpl();
    }
    throw new IllegalArgumentException("Unknown databaseType=" + databaseType);
  }
}
