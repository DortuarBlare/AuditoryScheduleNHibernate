package dataAccessObjects;

import dataBaseConnection.HibernateSessionFactory;
import entities.Group;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GroupDAO {
    public Group findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Group.class, id);
    }

    public void save(Group group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }

    public void update(Group group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(group);
        transaction.commit();
        session.close();
    }

    public void delete(Group group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(group);
        transaction.commit();
        session.close();
    }
}
