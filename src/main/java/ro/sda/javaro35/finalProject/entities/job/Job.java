package ro.sda.javaro35.finalProject.entities.job;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "job")
@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Job {

    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    String title;

    @Column(nullable = false)
    String description;

    @Enumerated(EnumType.STRING)
    Categories category;

    @Column(nullable = false)
    String salary;

    @Lob
    String image;
}
