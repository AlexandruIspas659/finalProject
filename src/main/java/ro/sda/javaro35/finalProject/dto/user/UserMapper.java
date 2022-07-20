package ro.sda.javaro35.finalProject.dto.user;

import org.springframework.stereotype.Component;
import ro.sda.javaro35.finalProject.entities.user.User;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRoles(user.getRoles());
        dto.setPreferences(user.getPreferences());
        dto.setThumbnail(user.getThumbnail());
        return dto;
    }

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        user.setPreferences(userDto.getPreferences());
        user.setThumbnail(userDto.getThumbnail());
        return user;
    }

}
