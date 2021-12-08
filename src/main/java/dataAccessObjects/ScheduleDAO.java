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
        Query query = session.createQuery(
                "SELECT S.id FROM Schedule S " +
                   "INNER JOIN Auditory A on A.id = S.auditory " +
                   "INNER JOIN Group G on G.id = S.group " +
                   "INNER JOIN Day D on D.id = S.day " +
                   "INNER JOIN Time T on T.id = S.time " +
                   "WHERE A.auditory = '" + schedule.getAuditory().getAuditory() + "' " +
                   "AND S.week = " + schedule.getWeek() +
                  " AND D.day = '" + schedule.getDay().getDay() + "' " +
                   "AND T.start_time = '" + schedule.getTime().getStart_time() + "' " +
                   "AND T.end_time = '" + schedule.getTime().getEnd_time() + "'"
        );
        List<Integer> scheduleIdList = query.list();
        if (scheduleIdList.size() != 0) System.out.println("Введённое расписание нарушает ограничение уникальности");
        else {
            Transaction transaction = session.beginTransaction();
            query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                    schedule.getAuditory().getAuditory() + "'");
            List<Integer> list = query.list();
            if (list.size() != 0) schedule.getAuditory().setId(list.get(0));
            else session.save(schedule.getAuditory());

            query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                    schedule.getGroup().getGroup_() + "'");
            list = query.list();
            if (list.size() != 0) schedule.getGroup().setId(list.get(0));
            else session.save(schedule.getGroup());

            session.save(schedule);
            transaction.commit();
        }

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

    // Id получаемого объекта является порядковым номером для удаления
    @Override
    public void delete(Schedule schedule) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S");
        List<Integer> scheduleIdList = query.list();
        if (scheduleIdList.size() != 0) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(schedule.getId() - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                schedule = scheduleList.get(0);
                Transaction transaction = session.beginTransaction();
                session.delete(schedule);
                transaction.commit();
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }
}
