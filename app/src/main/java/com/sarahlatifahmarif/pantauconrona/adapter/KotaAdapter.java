package com.sarahlatifahmarif.pantauconrona.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sarahlatifahmarif.pantauconrona.InfoKotaActivity;
import com.sarahlatifahmarif.pantauconrona.R;
import com.sarahlatifahmarif.pantauconrona.apiservice.BaseApiService;
import com.sarahlatifahmarif.pantauconrona.apiservice.UtilsApi;
import com.sarahlatifahmarif.pantauconrona.model.GetKabResponse;
import com.sarahlatifahmarif.pantauconrona.model.Kota;
import com.sarahlatifahmarif.pantauconrona.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KotaAdapter extends RecyclerView.Adapter<KotaAdapter.GridViewHolder> {
    private List<Kota> listKota;
    private Context mContext;
    private String tab;
    private BaseApiService mApiService;
    private LineChart lineChart;
    String[] arrayKota;
    String[] arrayKodeKota;
    private List<ResultsItem> daysData = new ArrayList<>();

    public  KotaAdapter(Context context, List<Kota> list, String tab, String[] aKota, String[] aKodekota){
        this.listKota = list;
        this.mContext = context;
        this.tab = tab;
        mApiService = UtilsApi.getKab();
        this.arrayKota = aKota;
        this.arrayKodeKota = aKodekota;
    }
    @NonNull
    @Override
    public KotaAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kota_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KotaAdapter.GridViewHolder holder, final int position) {
        getKab(holder.graph,listKota.get(position).getKode());
        String kota = listKota.get(position).getNama();
        kota = kota.replace("Kota ", "");
        kota = kota.replace("Kepulauan ", "");
        kota = kota.replace("Padang ", "Kota ");
        kota = kota.replace("Kota Panjang", "Padang Panjang");
        holder.txtItemKota.setText(kota);
        holder.txtItemJumlah.setText(""+listKota.get(position).getJumlah());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InfoKotaActivity.class);
                intent.putExtra("sKode", listKota.get(position).getKode());
                intent.putExtra("sKota",listKota.get(position).getNama());
                intent.putExtra("aKota", arrayKota);
                intent.putExtra("aKodeKota", arrayKodeKota);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKota.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemKota, txtItemJumlah;
        private LineChart graph;
        CardView card;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemJumlah = itemView.findViewById(R.id.item_jumlah);
            txtItemKota = itemView.findViewById(R.id.item_kota);
            graph = itemView.findViewById(R.id.item_graph);
            card = itemView.findViewById(R.id.card_kota);
        }
    }
    void getKab(final LineChart lineChart, String kode){
        mApiService.getKab(kode)
                .enqueue(new Callback<GetKabResponse>() {
                    @Override
                    public void onResponse(Call<GetKabResponse> call, Response<GetKabResponse> response) {
                        //Log.d("yolo", response.body().getResults().toString());
                        daysData.clear();
                        daysData.addAll(response.body().getResults());
                        setChart(lineChart, R.color.colorAccent, tab,"test");
                    }

                    @Override
                    public void onFailure(Call<GetKabResponse> call, Throwable t) {

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
        lineDataSet.setColor(ContextCompat.getColor(mContext, color));
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

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);

        lineChart.invalidate();
    }
    private ArrayList getData(String type){
        ArrayList<Entry> entries = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < daysData.size(); i++){
            if(type.equals("sehat"))
                entries.add(new Entry(i, Integer.parseInt(daysData.get(i).getCovidSembuh())));
            else if(type.equals("positif"))
                entries.add(new Entry(i, Integer.parseInt(daysData.get(i).getPositif())));
            else
                entries.add(new Entry(i, Integer.parseInt(daysData.get(i).getCovidMeninggal())));
        }
        return entries;
    }
}
