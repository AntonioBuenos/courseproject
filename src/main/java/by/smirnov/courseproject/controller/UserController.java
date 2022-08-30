package by.smirnov.courseproject.controller;

import by.smirnov.courseproject.model.User;
import by.smirnov.courseproject.repository.JdbcTemplUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final JdbcTemplUserRepository repository;

    @Autowired
    public UserController(JdbcTemplUserRepository jdbcTemplUserRepository) {
        this.repository = jdbcTemplUserRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", repository.findById(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        repository.create(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", repository.findById(id));
        return "users/edit";
    }

    //insert validation
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id) {
        repository.update(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/users";
    }
}
