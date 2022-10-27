package com.example.oenskeseddel.controller;

import com.example.oenskeseddel.repository.ØnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ØnskeController {
  ØnskeRepository ønskeRepository;

  public ØnskeController(ØnskeRepository ø) {
    ønskeRepository = ø;
  }

  @GetMapping("/")
  public String forside() {
    return "forside";
  }

  @PostMapping("/login")
  public String loginSide(@ModelAttribute String navn, String password, Model model) {
    int bruger_id = ønskeRepository.loginBruger(navn, password);
    if (!(bruger_id == -1)) {
      model.addAttribute("bruger_id",bruger_id);
      return "redirect:/side";
    }
    return "loginside";
  }

  @GetMapping("/side")
  public String brugerside(@ModelAttribute int bruger_id) {
    return "brugerside";

  }


}
