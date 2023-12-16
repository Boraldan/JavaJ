package boraldan;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private int duration;

    public Course() {
    }

    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }
}
