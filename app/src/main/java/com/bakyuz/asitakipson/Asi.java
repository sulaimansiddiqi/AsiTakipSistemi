package com.bakyuz.asitakipson;


public class Asi {
    private String asiId;

    private String asiAdi;
    private String hastahaneAdi;
    private String asiTarih;
    private Boolean asiDurum;

    public Asi() {
    }

    public Asi(String AsiID,String asiAdi,String hastahaneAdi,String asiTarih, Boolean asiDurum) {
        this.asiId = AsiID;
        this.asiAdi=asiAdi;
        this.hastahaneAdi=hastahaneAdi;
        this.asiTarih=asiTarih;
        this.asiDurum = asiDurum;
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

    public  void setAsiDurum(Boolean asiDurum) {this.asiDurum = asiDurum;}
    public  Boolean getAsiDurum(){return asiDurum;}


}
