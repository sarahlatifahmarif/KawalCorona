package com.sarahlatifahmarif.pantauconrona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sarahlatifahmarif.pantauconrona.adapter.DetailAdapter;
import com.sarahlatifahmarif.pantauconrona.model.LogData;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    RecyclerView rvBerita;
    DetailAdapter mAdapter;
    List<LogData> data;
    TextView txtHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rvBerita = findViewById(R.id.rv_info);
        txtHeader = findViewById(R.id.txt_header);
        txtHeader.setText(getIntent().getStringExtra("header"));
        data = new ArrayList<>();
        String[] tanggal =  getIntent().getStringArrayExtra("tanggal");
        String[] jumlah =  getIntent().getStringArrayExtra("jumlah");
        String[] selisih =  getIntent().getStringArrayExtra("selisih");
        for (int i = 0; i < jumlah.length; i++){
            LogData log = new LogData();
            log.setTanggal(tanggal[i]);
            log.setJumlah(jumlah[i]);
            log.setSelisih(selisih[i]);
            data.add(log);
        }
        setRecycleView();
    }
    void setRecycleView(){
        rvBerita.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DetailAdapter(this, data);
        rvBerita.setHasFixedSize(true);
        rvBerita.setAdapter(mAdapter);
    }
}
