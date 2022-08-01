package ro.sda.javaro35.finalProject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sda.javaro35.finalProject.dto.user.UserDto;
import ro.sda.javaro35.finalProject.dto.user.UserMapper;
import ro.sda.javaro35.finalProject.entities.user.User;
import ro.sda.javaro35.finalProject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpringUserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SpringUserService.class);

    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Used by Spring Security to identify and pass and UserDetails to its classes
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List roles = new ArrayList();
        String role = "ROLE_" + user.getRoles();
        roles.add(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), roles);
    }

    // create

    public void save(UserDto userDto) {
        log.info("saving user {}", userDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRoles() == null) {
            userDto.setRoles("USER");
        }
        User userEntity = userMapper.toEntity(userDto);
        userRepository.save(userEntity);
    }
    // find all

    public List<User> findAll() {
        log.info("finding all users");
        return userRepository.findAll();
    }
    // find by id

    public UserDto findById(Long id) {
        log.info("finding by id");
        User userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        return userMapper.toDto(userEntity);
    }
    // update

    public void update(Long userId, UserDto userData) {
        log.info("update user {}", userData);

        userRepository.findById(userId)
                .map(existingUser -> updateEntity(userData, existingUser))
                .map(updatedUser -> userRepository.save(updatedUser))
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private User updateEntity(UserDto userData, User existingUser) {
        existingUser.setUsername(userData.getUsername());
        existingUser.setEmail(userData.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userData.getPassword()));
        existingUser.setRoles(userData.getRoles());
        existingUser.setPreferences(userData.getPreferences());
        existingUser.setThumbnail(userData.getThumbnail());
        return existingUser;
    }

    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        userRepository.deleteById(id);
    }
}
