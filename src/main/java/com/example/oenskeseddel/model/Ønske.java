package com.example.oenskeseddel.model;

public class Ønske {
  private int oenske_id;
  private int seddel_id;
  private String oenske_navn;
  private double oenske_pris;

  private String reserveret;

  public Ønske(int oenske_id, int seddel_id, String oenske_navn, double oenske_pris, String reserveret) {
    this.oenske_id = oenske_id;
    this.seddel_id = seddel_id;
    this.oenske_navn = oenske_navn;
    this.oenske_pris = oenske_pris;
    this.reserveret = reserveret;
  }

  public int getOenske_id() {
    return oenske_id;
  }

  public int getSeddel_id() {
    return seddel_id;
  }

  public String getOenske_navn() {
    return oenske_navn;
  }

  public double getOenske_pris() {
    return oenske_pris;
  }

  public void setOenske_id(int oenske_id) {
    this.oenske_id = oenske_id;
  }

  public void setSeddel_id(int seddel_id) {
    this.seddel_id = seddel_id;
  }

  public void setOenske_navn(String oenske_navn) {
    this.oenske_navn = oenske_navn;
  }

  public void setOenske_pris(double oenske_pris) {
    this.oenske_pris = oenske_pris;
  }

  public String getReserveret() {
    return reserveret;
  }

  public void setReserveret(String reserveret) {
    this.reserveret = reserveret;
  }
}
