package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping()
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        
        return "admin/new";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }


    @RequestMapping("/")
    public String printUsers(ModelMap model) {
            model.addAttribute("allUsers", userService.getAllUsers());

        return "user";
    }

    @RequestMapping("/admin")
    public String printUsersForAdmin(ModelMap model) {
        model.addAttribute("allUsers", userService.getAllUsers());

        return "/admin/users";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);

        return "redirect:/admin/";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);

        return "redirect:/admin/";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserAtId(id));

        return "/admin/edit";
    }

}
