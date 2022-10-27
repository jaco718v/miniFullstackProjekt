package com.example.oenskeseddel.model;

import java.util.ArrayList;

public class Ønskeseddel {
  private int seddel_id;
  private String seddel_navn;
  private ArrayList<Ønske> ønskeListe;
  private int bruger_id;

  public Ønskeseddel(int seddel_id, String seddel_navn, int bruger_id) {
    this.seddel_id = seddel_id;
    this.seddel_navn = seddel_navn;
    this.bruger_id = bruger_id;
  }

  public void addØnske(int ønske_id, int seddel_id, String name, double pris){
    ønskeListe.add(new Ønske(ønske_id, seddel_id,name, pris));
  }
}
