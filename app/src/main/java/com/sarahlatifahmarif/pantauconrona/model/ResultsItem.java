package com.sarahlatifahmarif.pantauconrona.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultsItem{

	@SerializedName("kabupaten_kota")
	private String kabupatenKota;

	@SerializedName("covid_meninggal")
	private String covidMeninggal;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("odp_dalam_pemantauan")
	private String odpDalamPemantauan;

	@SerializedName("pdp_masih_dirawat")
	private String pdpMasihDirawat;

	@SerializedName("covid_dirawat")
	private String covidDirawat;

	@SerializedName("covid_isolasi_dirumah")
	private String covidIsolasiDirumah;

	@SerializedName("odp_selesai")
	private String odpSelesai;

	@SerializedName("tgl_update")
	private String tglUpdate;

	@SerializedName("pdp_suspec")
	private String pdpSuspec;

	@SerializedName("positif")
	private String positif;

	@SerializedName("kode_kota")
	private String kodeKota;

	@SerializedName("covid_sembuh")
	private String covidSembuh;

	@SerializedName("id")
	private String id;

	@SerializedName("total_odp")
	private String totalOdp;

	@SerializedName("pdp")
	private String pdp;

	@SerializedName("pdp_pulangdan_sehat")
	private String pdpPulangdanSehat;

	@SerializedName("longitude")
	private String longitude;

	public String getKabupatenKota(){
		return kabupatenKota;
	}

	public String getCovidMeninggal(){
		return covidMeninggal;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getOdpDalamPemantauan(){
		return odpDalamPemantauan;
	}

	public String getPdpMasihDirawat(){
		return pdpMasihDirawat;
	}

	public String getCovidDirawat(){
		return covidDirawat;
	}

	public String getCovidIsolasiDirumah(){
		return covidIsolasiDirumah;
	}

	public String getOdpSelesai(){
		return odpSelesai;
	}

	public String getTglUpdate(){
		return tglUpdate;
	}

	public String getPdpSuspec(){
		return pdpSuspec;
	}

	public String getPositif(){
		return positif;
	}

	public String getKodeKota(){
		return kodeKota;
	}

	public String getCovidSembuh(){
		return covidSembuh;
	}

	public String getId(){
		return id;
	}

	public String getTotalOdp(){
		return totalOdp;
	}

	public String getPdp(){
		return pdp;
	}

	public String getPdpPulangdanSehat(){
		return pdpPulangdanSehat;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"kabupaten_kota = '" + kabupatenKota + '\'' + 
			",covid_meninggal = '" + covidMeninggal + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",odp_dalam_pemantauan = '" + odpDalamPemantauan + '\'' + 
			",pdp_masih_dirawat = '" + pdpMasihDirawat + '\'' + 
			",covid_dirawat = '" + covidDirawat + '\'' + 
			",covid_isolasi_dirumah = '" + covidIsolasiDirumah + '\'' + 
			",odp_selesai = '" + odpSelesai + '\'' + 
			",tgl_update = '" + tglUpdate + '\'' + 
			",pdp_suspec = '" + pdpSuspec + '\'' + 
			",positif = '" + positif + '\'' + 
			",kode_kota = '" + kodeKota + '\'' + 
			",covid_sembuh = '" + covidSembuh + '\'' + 
			",id = '" + id + '\'' + 
			",total_odp = '" + totalOdp + '\'' + 
			",pdp = '" + pdp + '\'' + 
			",pdp_pulangdan_sehat = '" + pdpPulangdanSehat + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}