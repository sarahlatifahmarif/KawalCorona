package com.sarahlatifahmarif.pantauconrona;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.sarahlatifahmarif.pantauconrona.adapter.KotaAdapter;
import com.sarahlatifahmarif.pantauconrona.apiservice.BaseApiService;
import com.sarahlatifahmarif.pantauconrona.apiservice.UtilsApi;
import com.sarahlatifahmarif.pantauconrona.model.DayEachKabResponse;
import com.sarahlatifahmarif.pantauconrona.model.Kota;
import com.sarahlatifahmarif.pantauconrona.model.LatestResponse;
import com.sarahlatifahmarif.pantauconrona.model.PerDayResponse;
import com.sarahlatifahmarif.pantauconrona.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    private RecyclerView rvKota;
    private BaseApiService mApiService, mApiDaysInfo, mApiDayEachKab;
    private DecoView mDecoView, mDecoValue;
    private TextView txtOdp, txtPositif, txtPersen;
    private LineChart graphTotalOdp, graphPositif;
    private LineChart lineChart;
    private TextView txtLatestPositive, txtLatestRecovery, txtLatestDeath, txtInfoTanggal;
    private RelativeLayout btnTotal, btnPulang;
    private CardView btnPositif, btnSehat, btnMeninggal;
    private RelativeLayout tabPositif,tabSehat, tabMeninggal;
    Context mContext;
    List<PerDayResponse> daysData = new ArrayList<>();
    List<ResultsItem> dataDayEchKab = new ArrayList<>();
    List<Kota> kotaPositif = new ArrayList();
    List<Kota> kotaSehat = new ArrayList<>();
    List<Kota> kotaMeninggal = new ArrayList<>();

    String[] aKota;
    String[] aKodeKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (android.os.Build.VERSION.SDK_INT >= 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mDecoView = findViewById(R.id.char_background);
        mDecoValue = findViewById(R.id.char_value);
        txtOdp = findViewById(R.id.txt_odp);
        txtPositif = findViewById(R.id.txt_posotif);
        txtPersen = findViewById(R.id.txt_persen);

        txtInfoTanggal = findViewById(R.id.txt_info_tanggal);
        txtLatestPositive = findViewById(R.id.txt_latest_positif);
        txtLatestRecovery = findViewById(R.id.txt_latest_recovery);
        txtLatestDeath = findViewById(R.id.txt_latest_death);
        rvKota = findViewById(R.id.rv_kota);

        btnPulang = findViewById(R.id.btn_pulang);
        btnPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity("pulang");
            }
        });
        btnTotal = findViewById(R.id.btn_total);
        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity("total_odp");
            }
        });
        btnPositif = findViewById(R.id.btn_positif);
        btnPositif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity("positif");
            }
        });
        btnSehat = findViewById(R.id.btn_sehat);
        btnSehat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity("sehat");
            }
        });
        btnMeninggal = findViewById(R.id.btn_meniggal);
        btnMeninggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveActivity("meninggal");
            }
        });

        tabPositif = findViewById(R.id.tab_positif);
        tabPositif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateTab("positif");
            }
        });
        tabSehat = findViewById(R.id.tab_sehat);
        tabSehat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateTab("sehat");
            }
        });
        tabMeninggal = findViewById(R.id.tab_meninggal);
        tabMeninggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateTab("meninggal");
            }
        });

        mApiService = UtilsApi.getLatestInfo();
        mApiDaysInfo = UtilsApi.getDaysInfo();
        mApiService = UtilsApi.getDayEachKab();

        graphTotalOdp = findViewById(R.id.graph_odp);
        graphPositif = findViewById(R.id.graph_positif);
        mContext = this;
        setBackgroudChart();
        latestInfo();
        daysInfo();
        getDayEachKab();

    }
    void activateTab(String tab){
        if(dataDayEchKab.isEmpty()){
            getDayEachKab();
        }
        disableTab();
        List<Kota> kota = new ArrayList<>();
        kota.clear();
        if(tab.equals("positif")){
            kota.addAll(kotaPositif);
            tabPositif.setBackground(getResources().getDrawable(R.drawable.backgroud_tab));
        }else if(tab.equals("sehat")){
            kota.addAll(kotaSehat);
            tabSehat.setBackground(getResources().getDrawable(R.drawable.backgroud_tab));
        }else{
            kota.addAll(kotaMeninggal);
            tabMeninggal.setBackground(getResources().getDrawable(R.drawable.backgroud_tab));
        }
        showRecyclerGrid(kota, tab);
    }
    void disableTab(){
        tabPositif.setBackgroundColor(getResources().getColor(R.color.white));
        tabSehat.setBackgroundColor(getResources().getColor(R.color.white));
        tabMeninggal.setBackgroundColor(getResources().getColor(R.color.white));
    }
    private void showRecyclerGrid(List<Kota> kota, String tab){
        rvKota.setLayoutManager(new GridLayoutManager(mContext, kota.size()));
        KotaAdapter gridHeroAdapter = new KotaAdapter(mContext, kota, tab, aKota, aKodeKota);
        rvKota.setAdapter(gridHeroAdapter);
    }
    void getDayEachKab(){
        mApiService.getDayEachKab()
                .enqueue(new Callback<DayEachKabResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<DayEachKabResponse> call, Response<DayEachKabResponse> response) {
                        dataDayEchKab.addAll(response.body().getResults());
                        aKota = new String[response.body().getResults().size()];
                        aKodeKota = new String[response.body().getResults().size()];
                        for (int i =0; i < dataDayEchKab.size(); i++){
                            aKota[i] = dataDayEchKab.get(i).getKabupatenKota();
                            aKodeKota[i] = dataDayEchKab.get(i).getKodeKota();
                            kotaPositif.add(new Kota(dataDayEchKab.get(i).getKodeKota(),
                                    dataDayEchKab.get(i).getKabupatenKota(),
                                    dataDayEchKab.get(i).getPositif()));
                            kotaSehat.add(new Kota(dataDayEchKab.get(i).getKodeKota(),
                                    dataDayEchKab.get(i).getKabupatenKota(),
                                    dataDayEchKab.get(i).getCovidSembuh()));
                            kotaMeninggal.add(new Kota(dataDayEchKab.get(i).getKodeKota(),
                                    dataDayEchKab.get(i).getKabupatenKota(),
                                    dataDayEchKab.get(i).getCovidMeninggal()));
                        }
                        activateTab("positif");
                    }

                    @Override
                    public void onFailure(Call<DayEachKabResponse> call, Throwable t) {

                    }
                });
    }
    void moveActivity(String type){
        if(daysData.size() == 0)
            return;

        int sum = daysData.size() - 1;
        String[] tanggal = new String[sum];
        String[] jumlah = new String[sum];
        String[] selisih = new String[sum];
        String header = "";
        int index = 0;
        for(int i = sum; i > 0 ; i--){
            tanggal[index] = tanggal(daysData.get(i).getWaktu());
            int before = i -1;
            if(type.equals("total_odp")){
                header = "Total dalam pantauan";
                jumlah[index] = daysData.get(i).getTotalOdp();
                int selisihTmp = Integer.parseInt(daysData.get(i).getTotalOdp()) -
                        Integer.parseInt(daysData.get(before).getTotalOdp());
                selisih[index] = ""+selisihTmp;
            }else if(type.equals("pulang")){
                header = "ODP pulang dan sehat";
                jumlah[index] = daysData.get(i).getPdpPulangdanSehat();
                int selisihTmp = Integer.parseInt(daysData.get(i).getPdpPulangdanSehat()) -
                        Integer.parseInt(daysData.get(before).getPdpPulangdanSehat());
                selisih[index] = ""+selisihTmp;
            }else if(type.equals("positif")){
                header = "Positif Covid-19";
                jumlah[index] = daysData.get(i).getPositif();
                int selisihTmp = Integer.parseInt(daysData.get(i).getPositif()) -
                        Integer.parseInt(daysData.get(before).getPositif());
                selisih[index] = ""+selisihTmp;
            }else if(type.equals("sehat")){
                header = "Telah Sembuh Covid-19";
                jumlah[index] = daysData.get(i).getCovidSembuh();
                int selisihTmp = Integer.parseInt(daysData.get(i).getCovidSembuh()) -
                        Integer.parseInt(daysData.get(before).getCovidSembuh());
                selisih[index] = ""+selisihTmp;
            }else if(type.equals("meninggal")){
                header = "Meninggal";
                jumlah[index] = daysData.get(i).getCovidMeninggal();
                int selisihTmp = Integer.parseInt(daysData.get(i).getCovidMeninggal()) -
                        Integer.parseInt(daysData.get(before).getCovidMeninggal());
                selisih[index] = ""+selisihTmp;
            }
            index++;
        }

        Intent intent = new Intent(InfoActivity.this, DetailActivity.class);
        intent.putExtra("header", header);
        intent.putExtra("tanggal", tanggal);
        intent.putExtra("jumlah", jumlah);
        intent.putExtra("selisih", selisih);
        startActivity(intent);
    }
    public void latestInfo(){
        mApiService.getLatestInfo()
                .enqueue(new Callback<LatestResponse>() {
                    @Override
                    public void onResponse(Call<LatestResponse> call, Response<LatestResponse> response) {
                        txtOdp.setText(response.body().getTotalOdp());
                        txtPositif.setText(response.body().getPdpPulangdanSehat());
                        int total  = Integer.parseInt(response.body().getTotalOdp());
                        int selesai = Integer.parseInt(response.body().getPdpPulangdanSehat());
                        setValueChart(total, selesai);
                        int persen = (selesai * 100) / total;
                        txtPersen.setText(""+persen+"%");
                        setDate(response.body().getWaktu());
                        txtLatestDeath.setText(response.body().getCovidMeninggal());
                        txtLatestPositive.setText(response.body().getPositif());
                        txtLatestRecovery.setText(response.body().getCovidSembuh());
                        daysInfo();
                    }
                    @Override
                    public void onFailure(Call<LatestResponse> call, Throwable t) {
                    }
                });
    }
    public void daysInfo(){
        mApiDaysInfo.getDaysInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PerDayResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("status", "proses");
                    }
                    @Override
                    public void onNext(List<PerDayResponse> perDayResponses) {
                        daysData.addAll(perDayResponses);
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //isError();
                    }

                    @Override
                    public void onComplete() {
                        setChart(graphTotalOdp, R.color.yellow,"total_odp", "total odp");
                        setChart(graphPositif, R.color.green,"positif", "total Positif");
                    }
                });
    }
    private String tanggal(String infoWaktu){
        String[] waktu = infoWaktu.split(" ");
        String[] tmpTanggal = waktu[0].split("-");
        String tmpBulan = tmpTanggal[1];
        String bulan = null;
        if(tmpBulan.equals("01"))  bulan = "Jan";
        else if(tmpBulan.equals("02"))  bulan = "Feb";
        else if(tmpBulan.equals("03"))  bulan = "Mar";
        else if(tmpBulan.equals("04"))  bulan = "Apr";
        else if(tmpBulan.equals("05"))  bulan = "Mey";
        else if(tmpBulan.equals("06"))  bulan = "Jun";
        else if(tmpBulan.equals("07"))  bulan = "Jul";
        else if(tmpBulan.equals("08"))  bulan = "Aug";
        else if(tmpBulan.equals("09"))  bulan = "Sep";
        else if(tmpBulan.equals("10"))  bulan = "Oct";
        else if(tmpBulan.equals("11"))  bulan = "Nov";
        else  bulan = "Des";

        return tmpTanggal[2]+" "+bulan;
    }
    void setDate(String infoWaktu){
        String[] waktu = infoWaktu.split(" ");
        String[] tmpTanggal = waktu[0].split("-");
        String tmpBulan = tmpTanggal[1];
        String bulan = null;
        String[] jam = waktu[1].split(":");
        if(tmpBulan.equals("01"))  bulan = "Jan";
        else if(tmpBulan.equals("02"))  bulan = "Feb";
        else if(tmpBulan.equals("03"))  bulan = "Mar";
        else if(tmpBulan.equals("04"))  bulan = "Apr";
        else if(tmpBulan.equals("05"))  bulan = "Mey";
        else if(tmpBulan.equals("06"))  bulan = "Jun";
        else if(tmpBulan.equals("07"))  bulan = "Jul";
        else if(tmpBulan.equals("08"))  bulan = "Aug";
        else if(tmpBulan.equals("09"))  bulan = "Sep";
        else if(tmpBulan.equals("10"))  bulan = "Oct";
        else if(tmpBulan.equals("11"))  bulan = "Nov";
        else if(tmpBulan.equals("12"))  bulan = "Des";

        txtInfoTanggal.setText("Terakhir update "+tmpTanggal[2] +" "+bulan+", "+jam[0]+":"+jam[1]+" WIB");
    }
    void setChart(LineChart mLineChart,int color, String type, String judul){
        lineChart = mLineChart;
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        LineDataSet lineDataSet = new LineDataSet(getData(type), "Inducesmile");
        lineDataSet.setColor(ContextCompat.getColor(this, color));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        XAxis xAxis = lineChart.getXAxis();

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setEnabled(false);


        LineData data = new LineData(lineDataSet);
        //lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineChart.setData(data);

        lineChart.invalidate();
    }
    private ArrayList getData(String type){
        ArrayList<Entry> entries = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < daysData.size(); i++){
            if(type.equals("total_odp"))
                entries.add(new Entry(i, Integer.parseInt(daysData.get(i).getTotalOdp())));
            else if(type.equals("positif"))
                entries.add(new Entry(i, Integer.parseInt(daysData.get(i).getPdpPulangdanSehat())));
        }
        return entries;
    }
    void setBackgroudChart(){
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#3374ADA2"))
                .setRange(0, 100, 100)
                .setLineWidth(50f)
                .build();
        int series1Index = mDecoView.addSeries(seriesItem1);
    }
    void setValueChart(int total, int value){
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#74ADA2"))
                .setRange(0, total, value)
                .setLineWidth(50f)
                .build();
        int series1Index = mDecoValue.addSeries(seriesItem1);
    }
}
