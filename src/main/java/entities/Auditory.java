package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "auditory")
public class Auditory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String auditory;

    @OneToMany(mappedBy = "auditory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public Auditory() {}

    public Auditory(String auditory) {
        this.auditory = auditory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return auditory;
    }
}
