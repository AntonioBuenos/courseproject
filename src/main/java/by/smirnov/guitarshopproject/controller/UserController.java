package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.UserDTO;
import by.smirnov.guitarshopproject.model.User;
import by.smirnov.guitarshopproject.repository.user.HibernateUserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final HibernateUserRepo repository;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users",
                repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return "users/index";
    }

    @GetMapping("/deleted")
    public String showDeleted(Model model) {
        model.addAttribute("notUsers",
                repository.showDeletedUsers().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return "users/deleted";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", convertToDTO(repository.findById(id)));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") UserDTO userDTO) {
        return "users/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute("user") UserDTO userDTO) {
        repository.create(convertToEntity(userDTO));
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", repository.findById(id));
        return "users/edit";
    }

    //insert validation
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") UserDTO userDTO,
                         @PathVariable("id") long id) {
        repository.update(convertToEntity(userDTO));
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/users";
    }

    private User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
