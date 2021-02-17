package com.sarahlatifahmarif.pantauconrona.model;

public class Kota {
    private String kode;
    private String nama;
    private String jumlah;

    public Kota(String kode, String nama, String jumlah){
        this.kode = kode;
        this.nama = nama;
        this.jumlah = jumlah;
    }
    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
