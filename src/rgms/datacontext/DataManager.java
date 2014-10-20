package rgms.datacontext;

import org.hibernate.Session;

public abstract class DataManager {
  static Session session = HibernateUtil.getSessionFactory().getCurrentSession();
}