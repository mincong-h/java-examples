package io.mincong.ocpjp.design_principles.dao.method_factory;

import static org.assertj.core.api.Assertions.assertThat;

import io.mincong.ocpjp.design_principles.dao.GroupDao;
import io.mincong.ocpjp.design_principles.dao.PersonDao;
import org.junit.Test;

/** @author Mincong Huang */
public class AbstractDaoFactoryTest {

  @Test
  public void testDaoFactory_oracle() throws Exception {
    DaoFactory factory = new OracleDaoFactory();
    GroupDao groupDao = factory.getGroupDaoInstance();
    PersonDao personDao = factory.getPersonDaoInstance();

    assertThat(groupDao).isInstanceOf(GroupDaoOracleImpl.class);
    assertThat(personDao).isInstanceOf(PersonDaoOracleImpl.class);
  }

  @Test
  public void testDaoFactory_mySql() throws Exception {
    DaoFactory factory = new MySqlDaoFactory();
    GroupDao groupDao = factory.getGroupDaoInstance();
    PersonDao personDao = factory.getPersonDaoInstance();

    assertThat(groupDao).isInstanceOf(GroupDaoMySqlImpl.class);
    assertThat(personDao).isInstanceOf(PersonDaoMySqlImpl.class);
  }
}
