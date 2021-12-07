import dataAccessObjects.AuditoryDAO;
import dataAccessObjects.GroupDAO;
import dataAccessObjects.ScheduleDAO;
import dataBaseConnection.HibernateSessionFactory;
import entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.postgresql.core.ParameterList;
import org.postgresql.core.SqlCommand;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        AuditoryDAO auditoryDAO = new AuditoryDAO();
        GroupDAO groupDAO = new GroupDAO();
        Auditory auditory = new Auditory("515");
        Group group = new Group("АВТ-816");
        Day day = new Day("Понедельник");
        Time time = new Time("8:30", "10:00");

        Schedule schedule = new Schedule(auditory, group,1, day, time);
        scheduleDAO.save(schedule);
    }
}
