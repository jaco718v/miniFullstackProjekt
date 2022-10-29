package com.example.oenskeseddel.controller;

import com.example.oenskeseddel.repository.ØnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("loginside")
  public String showLogin(){
    return "loginside";
  }

  @PostMapping("loginside")
  public String loginSide(@RequestParam String bruger_navn,@RequestParam String bruger_password, Model model) {
    int bruger_id = ønskeRepository.loginBruger(bruger_navn, bruger_password);
    if (!(bruger_id == -1)) {
      model.addAttribute("bruger_id",bruger_id);
      return "redirect:/brugerside";
    }
    return "redirect:/";
  }


  @GetMapping("/brugerside")
  public String brugerside(@ModelAttribute int bruger_id, Model model) {
    model.addAttribute("ønskesedler",ønskeRepository.getØnskesedler(bruger_id));
    return "brugerside";

  }

  @GetMapping("/registerside")
  public String showRegisterSide(){
    return "registerside";
  }

  @PostMapping("registerside")
  public String registerSide(@RequestParam String bruger_navn,@RequestParam String bruger_password, Model model){
    if(ønskeRepository.isBrugerNavnAvailable(bruger_navn)){
      ønskeRepository.opretBruger(bruger_navn,bruger_password);
    }
    return "redirect:/";
  }
}
