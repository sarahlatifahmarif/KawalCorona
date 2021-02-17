package com.sarahlatifahmarif.pantauconrona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarahlatifahmarif.pantauconrona.R;
import com.sarahlatifahmarif.pantauconrona.model.LogData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private Context context;
    private List<LogData> data;

    public DetailAdapter(Context context, List<LogData> data){
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder holder, int position) {
        holder.txtTanggal.setText(data.get(position).getTanggal());
        holder.txtJumlah.setText(data.get(position).getJumlah());
        holder.txtSelisih.setText(data.get(position).getSelisih());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtJumlah, txtSelisih;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txt_tanggal);
            txtJumlah = itemView.findViewById(R.id.txt_jumlah);
            txtSelisih = itemView.findViewById(R.id.txt_selisih);
        }
    }
}
