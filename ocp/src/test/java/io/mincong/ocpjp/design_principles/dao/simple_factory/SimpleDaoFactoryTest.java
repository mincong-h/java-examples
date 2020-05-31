package io.mincong.ocpjp.design_principles.dao.simple_factory;

import static org.assertj.core.api.Assertions.assertThat;

import io.mincong.ocpjp.design_principles.dao.PersonDao;
import org.junit.Test;

/** @author Mincong Huang */
public class SimpleDaoFactoryTest {

  @Test
  public void testGetPersonDaoInstance_mySql() throws Exception {
    PersonDao personDao = DaoFactory.getPersonDaoInstance(DaoFactory.MYSQL);
    assertThat(personDao).isInstanceOf(PersonDaoMySqlImpl.class);
  }

  @Test
  public void testGetPersonDaoInstance_oracle() throws Exception {
    PersonDao personDao = DaoFactory.getPersonDaoInstance(DaoFactory.ORACLE);
    assertThat(personDao).isInstanceOf(PersonDaoOracleImpl.class);
  }
}
