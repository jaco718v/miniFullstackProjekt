package com.example.oenskeseddel.controller;

import com.example.oenskeseddel.repository.ØnskeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ØnskeController {
 /* ØnskeRepository ønskeRepository;

  public ØnskeController(ØnskeRepository ø) {
    ønskeRepository = ø;
  }
*/
  @GetMapping("/")
  public String forside() {
    return "forside";
  }
}
