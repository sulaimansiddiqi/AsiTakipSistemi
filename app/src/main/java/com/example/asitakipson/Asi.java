package com.example.asitakipson;


public class Asi {

    private String asiID;
    private String hastahaneAdi;
    private String asiTarih;

    public Asi() {
    }

    public Asi(String asiID,String hastahaneAdi,String asiTarih) {

        this.asiID=asiID;
        this.hastahaneAdi=hastahaneAdi;
        this.asiTarih=asiTarih;
    }
    public String getAsiID() {
        return asiID;
    }

    public void setAsiID(String asiID) {
        this.asiID = asiID;
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
