package ro.sda.javaro35.finalProject.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ro.sda.javaro35.finalProject.entities.user.Preferences;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class UserDto {

    Long id;
    String username;
    String email;
    String password;
    String thumbnail;
    String roles;
    Preferences preferences;


}
