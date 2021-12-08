package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String day;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public Day() {}

    public Day(String day) {
        this.id = dayToIdDay(day);
        this.day = day;
    }

    private int dayToIdDay(String day) {
        if (day.compareTo("Понедельник") == 0) return 1;
        else if (day.compareTo("Вторник") == 0) return 2;
        else if (day.compareTo("Среда") == 0) return 3;
        else if (day.compareTo("Четверг") == 0) return 4;
        else if (day.compareTo("Пятница") == 0) return 5;
        else if (day.compareTo("Суббота") == 0) return 6;
        else if (day.compareTo("Воскресенье") == 0) return 7;
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return day;
    }
}
