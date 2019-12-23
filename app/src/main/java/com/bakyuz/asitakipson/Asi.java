package com.bakyuz.asitakipson;


public class Asi {
    private String asiId;

    private String asiAdi;
    private String hastahaneAdi;
    private String asiTarih;

    public Asi() {
    }

    public Asi(String asiAdi,String hastahaneAdi,String asiTarih) {
        this.asiId = asiId;
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

    public String getAsiId() {
        return asiId;
    }
    public void setAsiId(String asiId) {
        this.asiId = asiId;
    }

    public String getAsiTarih() {
        return asiTarih;
    }

    public void setAsiTarih(String asiTarih) {
        this.asiTarih = asiTarih;
    }



}
