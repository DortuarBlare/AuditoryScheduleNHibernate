package dataAccessObjects;

import dataBaseConnection.HibernateSessionFactory;
import entities.Auditory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuditoryDAO {
    public Auditory findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Auditory.class, id);
    }

    public void save(Auditory auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(auditory);
        transaction.commit();
        session.close();
    }

    public void update(Auditory auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(auditory);
        transaction.commit();
        session.close();
    }

    public void delete(Auditory auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(auditory);
        transaction.commit();
        session.close();
    }


}
