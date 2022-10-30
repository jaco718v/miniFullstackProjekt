package com.example.oenskeseddel.model;

import java.util.ArrayList;

public class Ønskeseddel {
  private int seddel_id;
  private String seddel_navn;
  private int bruger_id;

  public Ønskeseddel(int seddel_id, String seddel_navn, int bruger_id) {
    this.seddel_id = seddel_id;
    this.seddel_navn = seddel_navn;
    this.bruger_id = bruger_id;
  }

  public int getSeddel_id() {
    return seddel_id;
  }

  public int getBruger_id() {
    return bruger_id;
  }

  public String getSeddel_navn() {
    return seddel_navn;
  }

}
