package com.example.asitakipson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class CustomAdapter extends ArrayAdapter<Asi> {


    private final LayoutInflater inflater;
    private final Context context;
    private RecyclerView.ViewHolder holder;
    private List<Asi> AsiList;

//    private final ArrayList<Kullanici> kulllanicilar;

    public CustomAdapter(@NonNull Context context,int resource, List<Asi> asiList) {
        super(context,0, asiList);
        inflater=LayoutInflater.from(context);
        this.context = context;
        this.AsiList = asiList;

//        this.kulllanicilar = kullanicis;
    }

    @Override
    public int getCount() {
        if(AsiList != null){
            return AsiList.size();
        }else{
            return 0;
        }
    }

    @Nullable



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(context);
            v=vi.inflate(R.layout.listview_item_layout,null);

        }
        Asi k =AsiList.get(position);
        TextView AsiId =(TextView)v.findViewById(R.id.adi_label);
        TextView AsiTarihi=(TextView)v.findViewById(R.id.dyeri_label);
        TextView HastahaneAdi=(TextView)v.findViewById(R.id.dyeri_label);
        AsiId.setText(k.getAsiID());
        AsiTarihi.setText(k.getAsiTarih());
        HastahaneAdi.setText(k.getHastahaneAdi());
        return  v;
    }
}

