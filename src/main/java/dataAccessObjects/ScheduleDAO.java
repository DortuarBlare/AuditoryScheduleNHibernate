package dataAccessObjects;

import dataBaseConnection.HibernateSessionFactory;
import entities.Schedule;
import interfaces.DAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO implements DAO<Schedule> {
    @Override
    public Schedule findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Schedule.class, id);
    }

    @Override
    public List<Schedule> findAll() {
        return (List<Schedule>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Schedule").list();
    }

    @Override
    public void save(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(schedule);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(schedule);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(schedule);
        transaction.commit();
        session.close();
    }
}
