package com.example.oenskeseddel.model;

public class Bruger {
  private int bruger_id;
  private String bruger_navn;
  private String bruger_password;


  public Bruger(){}
  public Bruger(int bruger_id, String bruger_navn, String password) {
    this.bruger_id = bruger_id;
    this.bruger_navn = bruger_navn;
    this.bruger_password = password;
  }

  public void setBruger_id(int bruger_id) {
    this.bruger_id = bruger_id;
  }

  public void setBruger_navn(String bruger_navn) {
    this.bruger_navn = bruger_navn;
  }

  public void setBruger_password(String bruger_password) {
    this.bruger_password = bruger_password;
  }

  public String getBruger_navn() {
    return bruger_navn;
  }

  public int getBruger_id() {
    return bruger_id;
  }

  public String getBruger_password() {
    return bruger_password;
  }
}
