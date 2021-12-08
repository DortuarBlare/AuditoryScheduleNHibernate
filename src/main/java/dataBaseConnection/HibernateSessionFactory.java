package dataBaseConnection;

import entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory = null;

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Schedule.class);
                configuration.addAnnotatedClass(Auditory.class);
                configuration.addAnnotatedClass(Group.class);
                configuration.addAnnotatedClass(Day.class);
                configuration.addAnnotatedClass(Time.class);
                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            }
            catch (Exception e) { System.out.println("Исключение!" + e);}
        }
        return sessionFactory;
    }
}