package com.example.kamusbetawiindo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamusbetawiindo.Model.Kamus;
import com.example.kamusbetawiindo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KamusBetawiAdapter extends RecyclerView.Adapter<KamusBetawiAdapter.MyViewHolder> {
    List<Kamus> kamusList;
    Context context;

    public KamusBetawiAdapter(List<Kamus> kamusList, Context context){
        this.kamusList = kamusList;
        this.context = context;
    }

    @NonNull
    @Override
    public KamusBetawiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kamus_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusBetawiAdapter.MyViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(position+1));
        holder.tvBasanya.setText(kamusList.get(position).getBetawi());
        holder.tvIndo.setText(kamusList.get(position).getIndo());
    }

    @Override
    public int getItemCount() {
        return kamusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvBasanya;
        TextView tvIndo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = (TextView)itemView.findViewById(R.id.tvId);
            tvBasanya = (TextView)itemView.findViewById(R.id.tvBasanya);
            tvIndo = (TextView)itemView.findViewById(R.id.tvIndo);
        }
    }
}
