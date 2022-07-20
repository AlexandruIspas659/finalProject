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
import ro.sda.javaro35.finalProject.entities.user.User;
import ro.sda.javaro35.finalProject.exceptions.ResourceAlreadyExistsException;
import ro.sda.javaro35.finalProject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpringUserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SpringUserService.class);

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

    public void save(User user) {
        log.info("saving user {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles("USER");
        }

        userRepository.save(user);
    }
    // find all

    public List<User> findAll() {
        log.info("finding all users");
        return userRepository.findAll();
    }
    // find by id

    public User findById(Long id) {
        log.info("finding by id");
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
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

    public void updateNew(User user) {
        log.info("update user {}", user);

        String name = user.getUsername();
        userRepository.findByUsernameIgnoreCase(name)
                .filter(existingUser -> existingUser.getId().equals(user.getId()))
                .map(existingUser -> userRepository.save(user))
                .orElseThrow(() -> {
                    log.error("user with name {} already exists", name);
                    throw new ResourceAlreadyExistsException("user with name " + name + " already exists");
                });
    }
    // delete

    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        userRepository.deleteById(id);
    }
}
