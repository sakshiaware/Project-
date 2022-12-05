package com.smc.smcsystem;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {
    Context context;
    ArrayList<ModelPost> dataUserArrayList;
    Locale id = new Locale("in","ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy",id);

    public AdapterItem(Context context, ArrayList<ModelPost> dataUserArrayList) {
        this.context = context;
        this.dataUserArrayList = dataUserArrayList;
    }


    @NonNull
    @Override
    public AdapterItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.viewBind(dataUserArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataUserArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,
                tv_jk,
                tv_jurusan,
                tv_tanggal_pendaftaran;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.tv_title);
            tv_jk = itemView.findViewById(R.id.tv_des);
            tv_jurusan = itemView.findViewById(R.id.tv_status);
            tv_tanggal_pendaftaran = itemView.findViewById(R.id.tv_time);
        }

        public void viewBind(ModelPost dataUser) {
            tv_nama.setText(dataUser.getTitle());
            tv_jk.setText(dataUser.getDescription());
            tv_jurusan.setText(dataUser.getStatus());
            tv_tanggal_pendaftaran.setText(simpleDateFormat.format(dataUser.getPtime()));
        }
    }
}