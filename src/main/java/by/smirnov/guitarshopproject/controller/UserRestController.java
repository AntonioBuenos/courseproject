package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.UserDTO;
import by.smirnov.guitarshopproject.model.User;
import by.smirnov.guitarshopproject.repository.user.HibernateUserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserRestController {

    private final HibernateUserRepo repository;

    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<UserDTO> users = repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap("users", users), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> show(@PathVariable("id") long id) {
        UserDTO userDTO = convertToDTO(repository.findById(id));
        return userDTO != null
                ? new ResponseEntity<>(userDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        repository.create(convertToEntity(userDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        final boolean updated = Objects.nonNull(repository.update(user));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        User user = repository.findById(id);
        if (!user.isDeleted()) {
            repository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
