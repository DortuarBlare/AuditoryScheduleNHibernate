package services;

import dataAccessObjects.ScheduleDAO;
import dataBaseConnection.HibernateSessionFactory;
import entities.*;
import interfaces.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ScheduleService implements Service<Schedule> {
    private ScheduleDAO scheduleDAO;

    public ScheduleService() {
        scheduleDAO = new ScheduleDAO();
    }

    @Override
    public Schedule findById(int id) {
        return scheduleDAO.findById(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleDAO.findAll();
    }

    public List<Schedule> findAllOrdered() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Schedule> list = query.list();
        session.close();
        return list;
    }

    public void findByTime(String startTime, String endTime) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT S FROM Schedule S " +
                   "INNER JOIN Time T on T.id = S.time " +
                   "WHERE T.start_time = '" + startTime + "' " +
                   "AND T.end_time = '" + endTime + "' " +
                   "ORDER BY S.auditory, S.week, S.day"
        );
        List<Schedule> list = query.list();
        session.close();

        System.out.println();
        for (int i = 0; i < list.size(); i++) {
            Schedule schedule = list.get(i);
            System.out.println(schedule.getAuditory().getAuditory() + " аудитория занята в следующие недели и дни:");
            for (int j = i; j < list.size(); j++) {
                if (schedule.getAuditory().getAuditory().compareTo(list.get(j).getAuditory().getAuditory()) == 0) {
                    System.out.print(list.get(j).getWeek() + " неделя: " + list.get(j).getDay().getDay());
                    for (int k = j + 1; k < list.size(); k++) {
                        if (list.get(j).getWeek() == list.get(k).getWeek() &&
                                list.get(j).getAuditory().getAuditory().compareTo(list.get(k).getAuditory().getAuditory()) == 0)
                            System.out.print(" " + list.get(k).getDay().getDay());
                        else {
                            System.out.println();
                            j = k - 1;
                            break;
                        }
                        if (k == list.size() - 1) j++;
                    }
                }
                else {
                    System.out.println();
                    i = j - 1;
                    break;
                }
                if (j == list.size() - 1) i++;
            }
        }
        System.out.println("\n\nОстальные аудитории свободны в любое время");
    }

    public void findByNumberOfHours(int numberOfHours, int week) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT S FROM Schedule S " +
                   "WHERE S.week = " + week +
                   " ORDER BY S.auditory, S.day"
        );
        List<Schedule> list = query.list();
        session.close();

        int amountOfLessons = (int) Math.ceil(((double) numberOfHours * 60) / 90);
        int maxAmountOfLessons = 49 - amountOfLessons;

        for (int i = 0; i < list.size(); i++) {
            Schedule schedule = list.get(i);
            System.out.println("\nНа " + week + " неделе " +
                    schedule.getAuditory().getAuditory() + " аудитория занята в следующие дни и время:");
            for (int j = i; j < list.size(); j++) {
                if (schedule.getAuditory().getAuditory().compareTo(list.get(j).getAuditory().getAuditory()) == 0) {
                    maxAmountOfLessons--;
                    System.out.print(list.get(j).getDay().getDay() + ": "
                            + list.get(j).getTime().getStart_time() + " - " + list.get(j).getTime().getEnd_time());
                    for (int k = j + 1; k < list.size(); k++) {
                        if (list.get(j).getDay().getDay().compareTo(list.get(k).getDay().getDay()) == 0 &&
                                list.get(j).getAuditory().getAuditory().compareTo(list.get(k).getAuditory().getAuditory()) == 0) {
                            maxAmountOfLessons--;
                            System.out.print(" " + list.get(k).getTime().getStart_time() +
                                    " - " + list.get(k).getTime().getEnd_time());
                        }
                        else {
                            j = k - 1;
                            break;
                        }
                        if (k == list.size() - 1) j++;
                    }
                    System.out.println();
                }
                else {
                    i = j - 1;
                    break;
                }
                if (j == list.size() - 1) i++;
            }
            if (maxAmountOfLessons < 0)
                System.out.println("Данная аудитория не подойдет " + amountOfLessons + " занятий");
            else
                System.out.println("Данная аудитория подойдет для " + amountOfLessons + " занятий");
            maxAmountOfLessons = 49 - amountOfLessons;
        }
        System.out.println("\n\nОстальные аудитории свободны в любое время");
    }

    @Override
    public void showAll() {
        int i = 1;
        System.out.println("Всё расписание:");
        for (Schedule schedule: findAllOrdered()) {
            System.out.println("Порядк. номер:\t" + i++);
            System.out.println("Аудитория:\t\t" + schedule.getAuditory());
            System.out.println("Группа:\t\t\t" + schedule.getGroup());
            System.out.println("Неделя:\t\t\t" + schedule.getWeek());
            System.out.println("День недели:\t" + schedule.getDay());
            System.out.println("Время:\t\t\t" + schedule.getTime().toString() + "\n");
        }
    }

    @Override
    public void save(Schedule object) {
        scheduleDAO.save(object);
    }

    public void saveSchedule(String auditory, String group, int week,
                             String day, String startTime, String endTime) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        if (violatesConstraint(auditory, week, day, startTime, endTime))
            System.out.println("Введённое расписание нарушает ограничение уникальности");
        else {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                    auditory + "'");
            List<Integer> list = query.list();
            Auditory auditoryForSchedule = new Auditory(auditory);
            if (list.size() != 0) auditoryForSchedule.setId(list.get(0));
            else session.save(auditoryForSchedule);

            query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                    group + "'");
            list = query.list();
            Group groupForSchedule = new Group(group);
            if (list.size() != 0) groupForSchedule.setId(list.get(0));
            else session.save(groupForSchedule);
            transaction.commit();

            Schedule scheduleToAdd = new Schedule(
                    auditoryForSchedule,
                    groupForSchedule,
                    week,
                    new Day(day),
                    new Time(startTime, endTime)
            );

            save(scheduleToAdd);
        }

        session.close();
    }

    @Override
    public void update(Schedule object) {
        scheduleDAO.update(object);
    }

    public void updateSchedule(int number, String auditory, String group, int week,
                               String day, String startTime, String endTime) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Transaction transaction = session.beginTransaction();
                Schedule scheduleToUpdate = scheduleList.get(0);

                query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                        auditory + "'");
                List<Integer> list = query.list();
                Auditory auditoryForSchedule = new Auditory(auditory);
                boolean auditoryExist = true;
                if (!list.isEmpty()) auditoryForSchedule.setId(list.get(0));
                else {
                    auditoryExist = false;
                    session.save(auditoryForSchedule);
                }

                query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                        group + "'");
                list = query.list();
                Group groupForSchedule = new Group(group);
                boolean groupExist = true;
                if (!list.isEmpty()) groupForSchedule.setId(list.get(0));
                else {
                    groupExist = false;
                    session.save(groupForSchedule);
                }

                scheduleToUpdate.setAuditory(auditoryForSchedule);
                scheduleToUpdate.setGroup(groupForSchedule);
                scheduleToUpdate.setWeek(week);
                scheduleToUpdate.setDay(new Day(day));
                scheduleToUpdate.setTime(new Time(startTime, endTime));

                if (violatesConstraint(scheduleToUpdate)) {
                    if (!auditoryExist) session.delete(auditoryForSchedule);
                    if (!groupExist) session.delete(groupForSchedule);
                    System.out.println("Расписание с новым временем нарушает ограничение уникальности");
                }
                else {
                    transaction.commit();
                    update(scheduleToUpdate);
                }
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    public void updateScheduleAuditory(int number, String newAuditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Transaction transaction = session.beginTransaction();
                Schedule scheduleToUpdate = scheduleList.get(0);

                query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                        newAuditory + "'");
                List<Integer> list = query.list();
                Auditory auditoryForSchedule = new Auditory(newAuditory);
                boolean auditoryExist = true;
                if (!list.isEmpty()) auditoryForSchedule.setId(list.get(0));
                else {
                    auditoryExist = false;
                    session.save(auditoryForSchedule);
                }

                scheduleToUpdate.setAuditory(auditoryForSchedule);

                if (violatesConstraint(scheduleToUpdate)) {
                    if (!auditoryExist) session.delete(auditoryForSchedule);
                    System.out.println("Расписание с новым временем нарушает ограничение уникальности");
                }
                else {
                    transaction.commit();
                    update(scheduleToUpdate);
                }
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    public void updateScheduleGroup(int number, String newGroup) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Transaction transaction = session.beginTransaction();
                Schedule scheduleToUpdate = scheduleList.get(0);

                query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                        newGroup + "'");
                List<Integer> list = query.list();
                Group groupForSchedule = new Group(newGroup);
                if (!list.isEmpty()) groupForSchedule.setId(list.get(0));
                else session.save(groupForSchedule);
                transaction.commit();

                scheduleToUpdate.setGroup(groupForSchedule);

                update(scheduleToUpdate);
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    public void updateScheduleWeek(int number, int newWeek) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Schedule scheduleToUpdate = scheduleList.get(0);
                scheduleToUpdate.setWeek(newWeek);
                if (violatesConstraint(scheduleToUpdate))
                    System.out.println("Расписание с новым временем нарушает ограничение уникальности");
                else
                    update(scheduleToUpdate);
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    public void updateScheduleDay(int number, String newDay) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Schedule scheduleToUpdate = scheduleList.get(0);
                scheduleToUpdate.setDay(new Day(newDay));
                if (violatesConstraint(scheduleToUpdate))
                    System.out.println("Расписание с новым временем нарушает ограничение уникальности");
                else
                    update(scheduleToUpdate);
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    public void updateScheduleTime(int number, String newStartTime, String newEndTime) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Schedule scheduleToUpdate = scheduleList.get(0);
                scheduleToUpdate.setTime(new Time(newStartTime, newEndTime));
                if (violatesConstraint(scheduleToUpdate))
                    System.out.println("Расписание с новым временем нарушает ограничение уникальности");
                else
                    update(scheduleToUpdate);
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    @Override
    public void delete(Schedule object) {
        scheduleDAO.delete(object);
    }

    public void deleteSchedule(int number) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT S.id FROM Schedule S ORDER BY S.auditory, S.group, S.week, S.day");
        List<Integer> scheduleIdList = query.list();
        if (!scheduleIdList.isEmpty()) {
            List<Integer> idList = new ArrayList<>();
            idList.addAll(scheduleIdList);
            query = session.createQuery("SELECT S FROM Schedule S WHERE S.id = " + idList.get(number - 1));
            List<Schedule> scheduleList = query.list();
            if (!scheduleList.isEmpty()) {
                Schedule scheduleToDelete = scheduleList.get(0);
                delete(scheduleToDelete);
            }
            else System.out.println("Расписания с введенным порядковым номером не оказалось");
        }
        else System.out.println("Расписание отсутствует");
        session.close();
    }

    public boolean violatesConstraint(Schedule schedule) {
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
        List<Integer> list = query.list();
        return !list.isEmpty();
    }

    public boolean violatesConstraint(String auditory, int week, String day, String startTime, String endTime) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT S.id FROM Schedule S " +
                        "INNER JOIN Auditory A on A.id = S.auditory " +
                        "INNER JOIN Group G on G.id = S.group " +
                        "INNER JOIN Day D on D.id = S.day " +
                        "INNER JOIN Time T on T.id = S.time " +
                        "WHERE A.auditory = '" + auditory + "' " +
                        "AND S.week = " + week +
                        " AND D.day = '" + day + "' " +
                        "AND T.start_time = '" + startTime + "' " +
                        "AND T.end_time = '" + endTime + "'"
        );
        List<Integer> list = query.list();
        return !list.isEmpty();
    }
}
