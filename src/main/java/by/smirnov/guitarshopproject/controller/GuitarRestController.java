package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.GuitarDTO;
import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.repository.guitar.HibernateGuitarRepo;
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
@RequestMapping("/rest/guitars")
public class GuitarRestController {
    private final HibernateGuitarRepo repository;

    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<GuitarDTO> guitars =  repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
        return guitars != null &&  !guitars.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap("guitars", guitars), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuitarDTO> show(@PathVariable("id") long id) {
        GuitarDTO guitarDTO = convertToDTO(repository.findById(id));
        return guitarDTO != null
                ? new ResponseEntity<>(guitarDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //insert validation
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody GuitarDTO guitarDTO) {
        repository.create(convertToEntity(guitarDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //insert validation
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody GuitarDTO guitarDTO) {
        Guitar guitar = convertToEntity(guitarDTO);
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

    private Guitar convertToEntity(GuitarDTO guitarDTO){
        return modelMapper.map(guitarDTO, Guitar.class);
    }

    private GuitarDTO convertToDTO(Guitar guitar){
        return modelMapper.map(guitar, GuitarDTO.class);
    }
}
