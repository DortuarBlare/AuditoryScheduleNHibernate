package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "group_")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String group_;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public Group() {}

    public Group(String group_) {
        this.group_ = group_;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_() {
        return group_;
    }

    public void setGroup_(String group_) {
        this.group_ = group_;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return group_;
    }
}
