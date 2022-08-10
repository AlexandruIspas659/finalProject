package ro.sda.javaro35.finalProject.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ro.sda.javaro35.finalProject.entities.user.User;
import ro.sda.javaro35.finalProject.repository.UserRepository;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserMapper {

    UserRepository userRepository;

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRoles(user.getRoles());
        dto.setPreferences(user.getPreferences());
        if (user.getThumbnail() != null) {
            dto.setThumbnail(user.getThumbnail());
        }
        return dto;
    }

    public User toEntity(UserDto userDto) {
        User user = new User();
        if (userDto.getId() != null) {
            user = userRepository.findById(user.getId()).orElse(user);
        }
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        user.setPreferences(userDto.getPreferences());
        if (userDto.getThumbnail() != null) {
            user.setThumbnail(user.getThumbnail());
        }
        return user;
    }

}
