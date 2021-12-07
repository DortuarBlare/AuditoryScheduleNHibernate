package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "time_")
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String start_time;
    private String end_time;

    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public Time() {}

    public Time(String start_time, String end_time) {
        this.id = timeToIdTime(start_time, end_time);
        this.start_time = start_time;
        this.end_time = end_time;
    }

    private int timeToIdTime(String startTime, String endTime) {
        if (startTime.compareTo("8:30") == 0 && endTime.compareTo("10:00") == 0) return 1;
        else if (startTime.compareTo("10:15") == 0 && endTime.compareTo("11:45") == 0) return 2;
        else if (startTime.compareTo("12:00") == 0 && endTime.compareTo("13:30") == 0) return 3;
        else if (startTime.compareTo("14:00") == 0 && endTime.compareTo("15:30") == 0) return 4;
        else if (startTime.compareTo("15:45") == 0 && endTime.compareTo("17:15") == 0) return 5;
        else if (startTime.compareTo("17:30") == 0 && endTime.compareTo("19:00") == 0) return 6;
        else if (startTime.compareTo("19:15") == 0 && endTime.compareTo("20:45") == 0) return 7;
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return start_time;
    }
}
