package dataAccessObjects;

import dataBaseConnection.HibernateSessionFactory;
import entities.Group;
import entities.Schedule;
import interfaces.DAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GroupDAO implements DAO<Group> {
    @Override
    public Group findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Group.class, id);
    }

    @Override
    public List<Group> findAll() {
        return (List<Group>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Group").list();
    }

    @Override
    public void save(Group group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Group group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(group);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Group group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(group);
        transaction.commit();
        session.close();
    }
}
