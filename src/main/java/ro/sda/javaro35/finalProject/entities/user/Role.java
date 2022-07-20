package ro.sda.javaro35.finalProject.entities.user;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<User> userList;
    private RoleType roleType;



}
