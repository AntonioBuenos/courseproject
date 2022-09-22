package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.GuitarDTO;
import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.repository.guitar.HibernateGuitarRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static by.smirnov.guitarshopproject.controller.ControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_GUITARS)
public class GuitarController {
    private final HibernateGuitarRepo repository;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(GUITARS,
                repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return "guitars/index";
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(GUITAR, convertToDTO(repository.findById(id)));
        return "guitars/show";
    }

    @GetMapping(MAPPING_NEW)
    public String newGuitar(@ModelAttribute(GUITAR) GuitarDTO guitarDTO) {
        return "guitars/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(GUITAR) GuitarDTO guitarDTO) {
        repository.create(convertToEntity(guitarDTO));
        return REDIRECT_GUITARS;
    }

    @GetMapping(MAPPING_EDIT)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GUITAR, repository.findById(id));
        return "guitars/edit";
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(GUITAR) GuitarDTO guitarDTO,
                         @PathVariable(ID) long id) {
        repository.update(convertToEntity(guitarDTO));
        return REDIRECT_GUITARS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        repository.delete(id);
        return REDIRECT_GUITARS;
    }

    //В сервис
    @GetMapping("/stats")
    public String getAveragePrice(Model model) {
        model.addAttribute("avg", String.format("%.2f", repository.showAverageGuitarPrice()) + "$");
        return "guitars/stats";
    }

    private Guitar convertToEntity(GuitarDTO guitarDTO){
        return modelMapper.map(guitarDTO, Guitar.class);
    }

    private GuitarDTO convertToDTO(Guitar guitar){
        return modelMapper.map(guitar, GuitarDTO.class);
    }
}
