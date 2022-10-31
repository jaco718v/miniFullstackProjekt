package com.example.oenskeseddel.controller;

import com.example.oenskeseddel.model.Bruger;
import com.example.oenskeseddel.repository.ØnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  public String loginSide(@RequestParam String bruger_navn, @RequestParam String bruger_password,
                          RedirectAttributes attributes) {
    int bruger_id = ønskeRepository.loginBruger(bruger_navn, bruger_password);
    if (!(bruger_id==-1)) {
      attributes.addAttribute("bruger_id",bruger_id);
      return "redirect:/brugerside";
    }
    return "redirect:/fejlside";
  }


  @GetMapping("/brugerside")
  public String brugerside(@RequestParam int bruger_id, Model model) {
    model.addAttribute("bruger",ønskeRepository.findBrugerViaID(bruger_id));
    model.addAttribute("oenskesedler",ønskeRepository.getEgneØnskesedler(bruger_id)); //Ø duer ik'
    model.addAttribute("delteoenskesedler",ønskeRepository.getDelteØnskesedler(bruger_id));
    return "brugerside";

  }

  @GetMapping("/registerside")
  public String showRegisterSide(){
    return "registerside";
  }

  @PostMapping("/registerside")
  public String registerSide(@RequestParam String register_navn, @RequestParam String register_password){
    if(ønskeRepository.isBrugerNavnAvailable(register_navn)){
      ønskeRepository.opretBruger(register_navn, register_password);
      return "redirect:/successide";
    }
    return "redirect:/fejlside";
  }

  @GetMapping("/createseddel/{bruger_id}")
  public String showCreateNewSeddel(@PathVariable("bruger_id") int bruger_id, Model model){
    model.addAttribute("bruger",ønskeRepository.findBrugerViaID(bruger_id));
    return "createseddel";
  }

  @PostMapping("/createseddel")
  public String createNewSeddel(@ModelAttribute Bruger bruger, @RequestParam String seddel_navn,
                                RedirectAttributes attributes){
    ønskeRepository.createØnskeseddel(bruger.getBruger_id(), seddel_navn);
    attributes.addAttribute("bruger_id",bruger.getBruger_id());
    return "redirect:/brugerside";
  }

  @GetMapping("/createønske/{seddel_id}/{bruger_id}")
  public String showCreateNewØnske(@PathVariable("seddel_id") int seddel_id, @PathVariable int bruger_id
                                   ,Model model){
    model.addAttribute(seddel_id);
    model.addAttribute("bruger_id",bruger_id);
    return "createønske";
  }

  @PostMapping("/createønske")
  public String createNewØnske(@RequestParam String ønske_navn, @RequestParam double ønske_pris,
                               @RequestParam("seddel_id") int seddel_id, @RequestParam int bruger_id,
                               RedirectAttributes attributes){
    ønskeRepository.createØnske(seddel_id, ønske_navn, ønske_pris);
    attributes.addAttribute("bruger_id", bruger_id);
    return "redirect:/brugerside";
  }

  @GetMapping("/ønskeseddelview/{seddel_id}")
  public String seØnskeseddel(@PathVariable("seddel_id") int seddel_id, Model model){
    model.addAttribute("oenskeliste",ønskeRepository.getØnsker(seddel_id));
    return "ønskeseddelview";
  }

  @GetMapping("/delseddel/{seddel_id}/{bruger_id}")
  public String showDelSeddelMedBruger(@PathVariable("seddel_id") int seddel_id, @PathVariable int bruger_id,
                                    Model model){
    model.addAttribute("seddel_id",seddel_id);
    model.addAttribute("bruger_id", bruger_id);
    return "delseddel";
  }

  @PostMapping("/delseddel")
  public String delSeddelMedBruger(@RequestParam int seddel_id, @RequestParam int bruger_id,
                                   @RequestParam String delbruger_navn, RedirectAttributes attributes){
  if (ønskeRepository.createDelte_brugere(seddel_id,delbruger_navn)){
    attributes.addAttribute("bruger_id",bruger_id);
    return "redirect:/brugerside";
    }
    return "redirect:/fejlside";
  }

  @GetMapping("/fejlside")
  public String fejlSide(){
    return "fejlside";
  }

  @GetMapping("/successide")
  public String succesSide(){
    return "successide";
  }

}
