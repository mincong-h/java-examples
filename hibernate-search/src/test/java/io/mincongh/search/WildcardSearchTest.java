package io.mincongh.search;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Stack Overflow - Question 38851332 Hibernate + Lucene - wildcard search returning empty result
 *
 * @author Mincong Huang
 */
public class WildcardSearchTest {

  private EntityManagerFactory emf;
  private EntityManager em;
  private FullTextEntityManager ftem;

  @Before
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("h2");
    em = emf.createEntityManager();
    em.getTransaction().begin();

    CustomEntity c1 = new CustomEntity();
    c1.setName1("test1");
    c1.setName2("test1");
    c1.setName3("test1");
    em.persist(c1);

    CustomEntity c2 = new CustomEntity();
    c2.setName1("test2");
    c2.setName2("test2");
    c2.setName3("test2");
    em.persist(c2);

    CustomEntity c3 = new CustomEntity();
    c3.setName1("test3");
    c3.setName2("test3");
    c3.setName3("test3");
    em.persist(c3);

    em.getTransaction().commit();
  }

  @Test
  public void testWildcard() {
    ftem = Search.getFullTextEntityManager(em);
    QueryBuilder qb =
        ftem.getSearchFactory().buildQueryBuilder().forEntity(CustomEntity.class).get();
    Query luceneQuery =
        qb.keyword().wildcard().onFields("name1", "name2", "name3").matching("test*").createQuery();
    @SuppressWarnings("unchecked")
    List<CustomEntity> results =
        ftem.createFullTextQuery(luceneQuery, CustomEntity.class).getResultList();
    results.forEach(c -> System.out.println(c.toString()));
    assertEquals(3, results.size());
  }

  @After
  public void shutdown() {
    em.close();
    emf.close();
  }
}
