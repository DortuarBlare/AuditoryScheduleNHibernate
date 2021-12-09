package services;

import dataAccessObjects.GroupDAO;
import dataBaseConnection.HibernateSessionFactory;
import entities.Auditory;
import entities.Group;
import interfaces.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        session.close();
        if (list.size() != 0)
            for (String group: list)
                System.out.println(group);
    }

    @Override
    public void save(Group object) {
        groupDAO.save(object);
    }

    public void saveGroup(String group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                group + "'");
        List<Integer> list = query.list();
        if (list.size() != 0) System.out.println("Данная группа уже существует");
        else {
            Group groupToAdd = new Group(group);
            save(groupToAdd);
        }
        session.close();
    }

    @Override
    public void update(Group object) {
        groupDAO.update(object);
    }

    public void updateGroup(String newGroup, String oldGroup) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                oldGroup + "'");
        List<Integer> list = query.list();
        Group groupToUpdate = new Group(oldGroup);
        groupToUpdate.setId(list.get(0));
        if (!list.isEmpty()) {
            query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                    newGroup + "'");
            list = query.list();
            if (!list.isEmpty()) System.out.println("Новая группа нарушает ограничение уникальности");
            else {
                groupToUpdate.setGroup_(newGroup);
                update(groupToUpdate);
            }
        }
        else System.out.println("Группа для редактирования не существует");
        session.close();
    }

    @Override
    public void delete(Group object) {
        groupDAO.delete(object);
    }

    public void deleteGroup(String group) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT G.id FROM Group G WHERE G.group_ = '" +
                group + "'");
        List<Integer> list = query.list();
        if (list.size() != 0) {
            Group groupToDelete = new Group(group);
            groupToDelete.setId(list.get(0));
            delete(groupToDelete);
        }
        else System.out.println("Данная группа отсутствует");
        session.close();
    }
}
