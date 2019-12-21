package io.mincongh.zoo.mockito;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.mincongh.zoo.Animal;
import io.mincongh.zoo.ZooKeeper;

/**
 * Test {@code io.mincongh.zoo.Zeekeeper} with database.
 *
 * @author Mincong Huang
 */
public class ZooKeeperDBTest {

  private static final Animal[] animals = new Animal[] {new Animal("Cat"), new Animal("Dog")};
  private EntityManagerFactory emf;
  private EntityManager em;
  private Session session;

  @Before
  public void setUp() {
    emf = Persistence.createEntityManagerFactory("h2");
    em = emf.createEntityManager();
    em.getTransaction().begin();
    for (Animal animal : animals) {
      em.persist(animal);
    }
    em.getTransaction().commit();
    session = em.unwrap(Session.class);
  }

  @Test
  public void testDoubleCountAnimal() {
    assertEquals(animals.length * 2, ZooKeeper.doubleCountAnimal(session));
  }

  @After
  public void shutDown() {
    try {
      em.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      emf.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
