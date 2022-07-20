package ro.sda.javaro35.finalProject.entities.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;

    String username;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    String thumbnail;

    // ADMIN, USER, VISITOR
    // TODO SA NE DECIDEM DACA FOLOSIM ROLURI LA LIBER SAU IMPLEMENTAREA CU CLASA SI ENUM
    String roles;

    @Enumerated(EnumType.STRING)
    Preferences preferences;

}

