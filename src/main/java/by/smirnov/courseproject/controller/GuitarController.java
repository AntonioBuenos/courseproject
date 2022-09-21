package by.smirnov.courseproject.controller;

import by.smirnov.courseproject.model.Guitar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/guitars")
public class GuitarController {
    private final JdbcTmpltGuitarRepo repository;

    @Autowired
    public GuitarController(JdbcTmpltGuitarRepo repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("guitars", repository.findAll());
        return "guitars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("guitar", repository.findById(id));
        return "guitars/show";
    }

    @GetMapping("/new")
    public String newGuitar(@ModelAttribute("guitar") Guitar guitar) {
        return "guitars/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute("guitar") Guitar guitar) {
        repository.create(guitar);
        return "redirect:/guitars";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("guitar", repository.findById(id));
        return "guitars/edit";
    }

    //insert validation
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("guitar") Guitar guitar,
                         @PathVariable("id") long id) {
        repository.update(guitar);
        return "redirect:/guitars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/guitars";
    }

    @GetMapping("/stats")
    public String getAveragePrice(Model model) {
        model.addAttribute("avg", String.format("%.2f", repository.showAverageGuitarPrice()) + "$");
        return "guitars/stats";
    }
}
