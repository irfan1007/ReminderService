package reminder.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ReminderEnitityManager {
	 private static final EntityManagerFactory entityManagerFactory;
	  static {
	    try {
	      entityManagerFactory = Persistence.createEntityManagerFactory("reminder");

	    } catch (Throwable ex) {
	      System.err.println("Initial SessionFactory creation failed." + ex);
	      throw new ExceptionInInitializerError(ex);
	    }
	  }

	  public static EntityManager getEntityManager() {
	    return entityManagerFactory.createEntityManager();

	  }
}
