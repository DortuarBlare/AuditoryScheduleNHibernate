package entities;

import javax.persistence.*;

@Entity
@Table (name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_auditory")
    private Auditory auditory;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_group")
    private Group group;

    private int week;

    @ManyToOne
    @JoinColumn(name = "id_day")
    private Day day;

    @ManyToOne
    @JoinColumn(name = "id_time")
    private Time time;

    public Schedule() {}

    public Schedule(Auditory auditory, Group group, int week, Day day, Time time) {
        this.auditory = auditory;
        this.group = group;
        this.week = week;
        this.day = day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public void setAuditory(Auditory auditory) {
        this.auditory = auditory;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return
                auditory.getAuditory() + "\n" +
                group.getGroup_() + "\n" +
                week + "\n" +
                day.getDay() + "\n" +
                time.toString() + "\n";
    }
}
