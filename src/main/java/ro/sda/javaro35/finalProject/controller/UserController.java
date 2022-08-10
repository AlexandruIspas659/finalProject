package ro.sda.javaro35.finalProject.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ro.sda.javaro35.finalProject.dto.user.UserDto;
import ro.sda.javaro35.finalProject.entities.user.User;
import ro.sda.javaro35.finalProject.services.SpringUserService;
import ro.sda.javaro35.finalProject.utils.ImageUtil;

import java.util.List;

// mvc controller
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final SpringUserService springUserService;

    @Autowired
    public UserController(SpringUserService springUserService) {
        this.springUserService = springUserService;
    }

    // http://localhost:8080/users
    // map url to controller method

    @GetMapping("/admin/users")
    public String showUsersPage(Model model) {
        // return a html page with users
        // add list of users
        List<User> users = springUserService.findAll();
        model.addAttribute("usersInView", users);
        // resolved by the view resolver
        return "users-list";
    }

    @GetMapping("/admin/users/add")
    public String showAddFrom(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "user-add";
    }

    @PostMapping("/admin/users/add")
    public String add(@RequestParam("file") MultipartFile thumbnail, @ModelAttribute UserDto userDto) {
        if (!thumbnail.isEmpty()) {
            userDto.setThumbnail(ImageUtil.resizeAndCrop(thumbnail, 300, 175));
        }
        springUserService.save(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        model.addAttribute("user", springUserService.findById(id));
        return "user-edit";
    }

    @PostMapping("/admin/users/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam("file") MultipartFile thumbnail, @ModelAttribute UserDto userDto) {
        if (!thumbnail.isEmpty()) {
            userDto.setThumbnail(ImageUtil.resizeAndCrop(thumbnail, 300, 175));
        }
        springUserService.update(id, userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(@PathVariable long id) {
        springUserService.delete(id);
        return "redirect:/admin/users";
    }
}
