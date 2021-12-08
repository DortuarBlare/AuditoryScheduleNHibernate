package services;

import dataAccessObjects.AuditoryDAO;
import dataBaseConnection.HibernateSessionFactory;
import entities.Auditory;
import interfaces.Service;
import org.hibernate.Session;
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
        if (list.size() != 0)
            for (String auditory: list)
                System.out.println(auditory);
    }

    @Override
    public void save(Auditory object) {
        auditoryDAO.save(object);
    }

    @Override
    public void update(Auditory object) {
        auditoryDAO.update(object);
    }

    @Override
    public void delete(Auditory object) {
        auditoryDAO.delete(object);
    }
}
