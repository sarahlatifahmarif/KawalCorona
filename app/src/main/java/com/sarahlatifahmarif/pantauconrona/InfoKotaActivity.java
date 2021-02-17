package com.sarahlatifahmarif.pantauconrona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sarahlatifahmarif.pantauconrona.apiservice.BaseApiService;
import com.sarahlatifahmarif.pantauconrona.apiservice.UtilsApi;
import com.sarahlatifahmarif.pantauconrona.model.GetKabResponse;
import com.sarahlatifahmarif.pantauconrona.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

public class InfoKotaActivity extends AppCompatActivity {
    TextView txtLastPositif, txtLastSehat, txtLastMeninggal, txtInfoTanggal;
    Spinner spnLokasi;
    BaseApiService mApiService;
    private List<ResultsItem> daysData = new ArrayList<>();
    String selected = "";
    private String[] spnKota;
    private String[] aKodekota;
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_kota);
        spnLokasi = findViewById(R.id.spn_lokasi);
        lineChart = findViewById(R.id.item_graph);

        txtLastPositif = findViewById(R.id.txt_latest_positif);
        txtLastSehat = findViewById(R.id.txt_latest_recovery);
        txtLastMeninggal = findViewById(R.id.txt_latest_death);
        txtInfoTanggal = findViewById(R.id.txt_info_tanggal);

        selected = getIntent().getStringExtra("sKode");
        int sum = getIntent().getStringArrayExtra("aKota").length + 1;
        spnKota = new String[sum];
        aKodekota = new String[sum];

        spnKota[0] = getIntent().getStringExtra("sKota");
        aKodekota[0] = getIntent().getStringExtra("aKode");

        for(int i= 0; i < getIntent().getStringArrayExtra("aKota").length; i++){
            spnKota[i+1] =  getIntent().getStringArrayExtra("aKota")[i];
            aKodekota[i+1] = getIntent().getStringArrayExtra("aKodeKota")[i];
        }
        mApiService = UtilsApi.getKab();
        getKab(selected);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout, spnKota);
        spnLokasi.setAdapter(adapter);
        spnLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getKab(aKodekota[i]);
                setChart(lineChart, R.color.green, "odp", "null");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    void setChart(LineChart mLineChart, int color, String type, String judul){
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
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(ContextCompat.getColor(this, color));
        lineDataSet.setLineWidth(3);
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
        lineChart.setData(data);
        lineChart.invalidate();
    }
    private ArrayList getData(String type){
        ArrayList<Entry> entries = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < daysData.size(); i++){
            entries.add(new Entry(i, Integer.parseInt(daysData.get(i).getTotalOdp())));
        }
        return entries;
    }
    void getKab(String kode){
        mApiService.getKab(kode)
                .enqueue(new Callback<GetKabResponse>() {
                    @Override
                    public void onResponse(Call<GetKabResponse> call, Response<GetKabResponse> response) {
                        int latest = response.body().getResults().size() -1;
                        daysData.clear();
                        daysData.addAll(response.body().getResults());
                        txtLastPositif.setText(daysData.get(latest).getPositif());
                        txtLastMeninggal.setText(daysData.get(latest).getCovidMeninggal());
                        txtLastSehat.setText(daysData.get(latest).getCovidSembuh());
                        setDate(daysData.get(latest).getTglUpdate());
                        setChart(lineChart, R.color.green, "odp", "null");
                    }

                    @Override
                    public void onFailure(Call<GetKabResponse> call, Throwable t) {

                    }
                });
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
}
