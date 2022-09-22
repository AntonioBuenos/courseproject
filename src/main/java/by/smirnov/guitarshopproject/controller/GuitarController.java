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

@Controller
@RequiredArgsConstructor
@RequestMapping("/guitars")
public class GuitarController {
    private final HibernateGuitarRepo repository;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("guitars",
                repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return "guitars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("guitar", convertToDTO(repository.findById(id)));
        return "guitars/show";
    }

    @GetMapping("/new")
    public String newGuitar(@ModelAttribute("guitar") GuitarDTO guitarDTO) {
        return "guitars/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute("guitar") GuitarDTO guitarDTO) {
        repository.create(convertToEntity(guitarDTO));
        return "redirect:/guitars";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("guitar", repository.findById(id));
        return "guitars/edit";
    }

    //insert validation
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("guitar") GuitarDTO guitarDTO,
                         @PathVariable("id") long id) {
        repository.update(convertToEntity(guitarDTO));
        return "redirect:/guitars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/guitars";
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
