package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.repository.guitar.HibernateGuitarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/guitars")
public class GuitarRestController {
    private final HibernateGuitarRepo repository;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<Guitar> guitars =  repository.findAll();
        return guitars != null &&  !guitars.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap("guitars", guitars), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guitar> show(@PathVariable("id") long id) {
        Guitar guitar = repository.findById(id);
        return guitar != null
                ? new ResponseEntity<>(guitar, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //insert validation
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Guitar guitar) {
        repository.create(guitar);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //insert validation
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Guitar guitar) {
        final boolean updated = Objects.nonNull(repository.update(guitar));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Guitar guitar = repository.findById(id);
        if (!guitar.isDeleted()) {
            repository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getAveragePrice() {
        return new ResponseEntity<>
                (Collections.singletonMap("avg", String.format("%.2f", repository.showAverageGuitarPrice()) + "$"),
                        HttpStatus.OK);
    }
}
