package dataAccessObjects;

import dataBaseConnection.HibernateSessionFactory;
import entities.Auditory;
import interfaces.DAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AuditoryDAO implements DAO<Auditory> {
    @Override
    public Auditory findById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Auditory.class, id);
    }

    @Override
    public List<Auditory> findAll() {
        return (List<Auditory>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Auditory").list();
    }

    @Override
    public void save(Auditory auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                auditory.getAuditory() + "'");
        List<Integer> list = query.list();
        if (list.size() != 0) System.out.println("Данная аудитория уже существует");
        else {
            Transaction transaction = session.beginTransaction();
            session.save(auditory);
            transaction.commit();
        }
        session.close();
    }

    @Override
    public void update(Auditory auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(auditory);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Auditory auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                auditory.getAuditory() + "'");
        List<Integer> list = query.list();
        if (list.size() != 0) {
            auditory.setId(list.get(0));
            Transaction transaction = session.beginTransaction();
            session.delete(auditory);
            transaction.commit();
        }
        else System.out.println("Данная аудитория отсутствует");
        session.close();
    }
}