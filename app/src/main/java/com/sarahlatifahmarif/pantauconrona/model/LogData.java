package com.sarahlatifahmarif.pantauconrona.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LogData implements Parcelable {
    private String tanggal;
    private  String jumlah;
    private String selisih;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getSelisih() {
        return selisih;
    }

    public void setSelisih(String selisih) {
        this.selisih = selisih;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tanggal);
        dest.writeString(this.jumlah);
        dest.writeString(this.selisih);
    }

    public LogData() {
    }

    protected LogData(Parcel in) {
        this.tanggal = in.readString();
        this.jumlah = in.readString();
        this.selisih = in.readString();
    }

    public static final Parcelable.Creator<LogData> CREATOR = new Parcelable.Creator<LogData>() {
        @Override
        public LogData createFromParcel(Parcel source) {
            return new LogData(source);
        }

        @Override
        public LogData[] newArray(int size) {
            return new LogData[size];
        }
    };
}
