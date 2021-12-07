package dataAccessObjects;

import dataBaseConnection.HibernateSessionFactory;
import entities.Schedule;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ScheduleDAO {
    public Schedule findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Schedule.class, id);
    }

    public void save(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                schedule.getAuditory().getAuditory() + "'");
        List<Integer> list = query.list();
        if (list.size() != 0) schedule.getAuditory().setId(list.get(0));

        query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                schedule.getGroup().getGroup_() + "'");
        list = query.list();
        if (list.size() != 0) schedule.getGroup().setId(list.get(0));

        Transaction transaction = session.beginTransaction();
        session.save(schedule);
        transaction.commit();
        session.close();
    }

    public void update(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(schedule);
        transaction.commit();
        session.close();
    }

    public void delete(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(schedule);
        transaction.commit();
        session.close();
    }
}
