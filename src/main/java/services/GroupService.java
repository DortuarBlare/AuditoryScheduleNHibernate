package services;

import dataAccessObjects.GroupDAO;
import dataBaseConnection.HibernateSessionFactory;
import entities.Group;
import interfaces.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GroupService implements Service<Group> {
    private GroupDAO groupDAO;

    public GroupService() {
        this.groupDAO = new GroupDAO();
    }

    @Override
    public Group findById(int id) {
        return groupDAO.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupDAO.findAll();
    }

    @Override
    public void showAll() {
        System.out.println("Все группы:");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT G.group_ FROM Group G ORDER BY G.group_");
        List<String> list = query.list();
        if (list.size() != 0)
            for (String group: list)
                System.out.println(group);
    }

    @Override
    public void save(Group object) {
        groupDAO.save(object);
    }

    @Override
    public void update(Group object) {
        groupDAO.update(object);
    }

    @Override
    public void delete(Group object) {
        groupDAO.delete(object);
    }
}
