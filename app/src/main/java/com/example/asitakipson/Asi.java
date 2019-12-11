package com.example.asitakipson;


public class Asi {

    private String asiAdi;
    private String hastahaneAdi;
    private String asiTarih;

    public Asi() {
    }

    public Asi(String asiAdi,String hastahaneAdi,String asiTarih) {

        this.asiAdi=asiAdi;
        this.hastahaneAdi=hastahaneAdi;
        this.asiTarih=asiTarih;
    }
    public String getAsiAdi() {
        return asiAdi;
    }

    public void setAsiAdi(String asiAdi) {
        this.asiAdi = asiAdi;
    }

    public String getHastahaneAdi() {
        return hastahaneAdi;
    }

    public void setHastahaneAdi(String hastahaneAdi) {
        this.hastahaneAdi = hastahaneAdi;
    }



    public String getAsiTarih() {
        return asiTarih;
    }

    public void setAsiTarih(String asiTarih) {
        this.asiTarih = asiTarih;
    }



}
