package services;

import dataAccessObjects.ScheduleDAO;
import entities.Schedule;
import interfaces.Service;

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

    @Override
    public void showAll() {
        int i = 1;
        for (Schedule schedule: scheduleDAO.findAll()) {
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

    @Override
    public void update(Schedule object) {
        scheduleDAO.update(object);
    }

    @Override
    public void delete(Schedule object) {
        scheduleDAO.delete(object);
    }
}
