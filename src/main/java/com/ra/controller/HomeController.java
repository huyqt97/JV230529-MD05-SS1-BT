package com.ra.controller;

import com.ra.model.dto.PersonDtoForm;
import com.ra.model.entity.Person;
import com.ra.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public String demo(Model model) {
        List<Person> list = personService.findAll();
        model.addAttribute("list", list);
        return "home";
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("add", "person", new PersonDtoForm());
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute PersonDtoForm personDtoForm) {
        personService.save(personDtoForm);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView findById(@PathVariable Long id) {
        return new ModelAndView("edit", "person", personService.findById(id));
    }

    @PostMapping("/update")
    public String doUpdate(@ModelAttribute PersonDtoForm person) {
        personService.save(person);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        personService.delete(id);
        return "redirect:/";
    }
}
