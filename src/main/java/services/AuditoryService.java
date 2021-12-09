package services;

import dataAccessObjects.AuditoryDAO;
import dataBaseConnection.HibernateSessionFactory;
import entities.Auditory;
import interfaces.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AuditoryService implements Service<Auditory> {
    private AuditoryDAO auditoryDAO;

    public AuditoryService() {
        auditoryDAO = new AuditoryDAO();
    }

    @Override
    public Auditory findById(int id) {
        return auditoryDAO.findById(id);
    }

    @Override
    public List<Auditory> findAll() {
        return auditoryDAO.findAll();
    }

    @Override
    public void showAll() {
        System.out.println("Все аудитории:");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.auditory FROM Auditory A ORDER BY A.auditory");
        List<String> list = query.list();
        session.close();
        if (list.size() != 0)
            for (String auditory: list)
                System.out.println(auditory);
    }

    @Override
    public void save(Auditory object) {
        auditoryDAO.save(object);
    }

    public void saveAuditory(String auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                auditory + "'");
        List<Integer> list = query.list();
        if (!list.isEmpty()) System.out.println("Данная аудитория уже существует");
        else {
            Auditory auditoryToAdd = new Auditory(auditory);
            save(auditoryToAdd);
        }
        session.close();
    }

    @Override
    public void update(Auditory object) {
        auditoryDAO.update(object);
    }

    public void updateAuditory(String newAuditory, String oldAuditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                oldAuditory + "'");
        List<Integer> list = query.list();
        Auditory oldAuditoryObject = new Auditory(oldAuditory);
        oldAuditoryObject.setId(list.get(0));
        if (!list.isEmpty()) {
            query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                    newAuditory + "'");
            list = query.list();
            if (!list.isEmpty()) System.out.println("Новая аудитория нарушает ограничение уникальности");
            else {
                oldAuditoryObject.setAuditory(newAuditory);
                update(oldAuditoryObject);
            }
        }
        else System.out.println("Аудитория для редактирования не существует");
    }

    @Override
    public void delete(Auditory object) {
        auditoryDAO.delete(object);
    }

    public void deleteAuditory(String auditory) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT A.id FROM Auditory A WHERE A.auditory = '" +
                auditory + "'");
        List<Integer> list = query.list();
        if (list.size() != 0) {
            Auditory auditoryToDelete = new Auditory(auditory);
            auditoryToDelete.setId(list.get(0));
            delete(auditoryToDelete);
        }
        else System.out.println("Данная аудитория отсутствует");
        session.close();
    }
}
