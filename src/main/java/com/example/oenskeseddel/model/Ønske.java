package com.example.oenskeseddel.model;

public class Ønske {
  private int ønske_id;
  private int seddel_id;
  private String ønske_navn;
  private double ønske_pris;

  public Ønske(int ønske_id, int seddel_id, String ønske_navn, double ønske_pris) {
    this.ønske_id = ønske_id;
    this.seddel_id = seddel_id;
    this.ønske_navn = ønske_navn;
    this.ønske_pris = ønske_pris;
  }
}
