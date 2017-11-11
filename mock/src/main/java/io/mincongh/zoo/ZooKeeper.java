package io.mincongh.zoo;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Projections;

/**
 * @author Mincong Huang
 */
public class ZooKeeper {

  private ZooKeeper() {
    // Utility class, do not instantiate
  }

  public static int doubleCountAnimal(Session session) {
    Criteria criteria = session.createCriteria(Animal.class);
    criteria = criteria.setProjection(Projections.rowCount());
    long rowCount = (long) criteria.uniqueResult();
    return (int) rowCount * 2;
  }

  public static List<Animal> scrollAnimals(SessionFactory sf) {
    List<Animal> animals = new ArrayList<>();
    StatelessSession ss = null;
    ScrollableResults scroll = null;
    try {
      ss = sf.openStatelessSession();
      scroll = ss.createCriteria(Animal.class)
          .setReadOnly(true)
          .setFetchSize(1000)
          .scroll(ScrollMode.FORWARD_ONLY);
      int i = 0;
      while (scroll.next()) {
        System.out.println("Getting the next animal ... " + i);
        Animal a = (Animal) scroll.get(0);
        animals.add(a);
        System.out.println(a.getName());
        i++;
      }
    } finally {
      if (scroll != null) {
        scroll.close();
      }
      if (ss != null) {
        ss.close();
      }
    }
    return animals;
  }
}
