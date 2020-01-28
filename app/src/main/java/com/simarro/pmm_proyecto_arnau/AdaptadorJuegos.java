package com.simarro.pmm_proyecto_arnau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorJuegos extends RecyclerView.Adapter<JuegosViewHolder> implements View.OnClickListener {

    private ArrayList<Juego> juegos;
    private Context context;

    private View.OnClickListener mListener;

    public AdaptadorJuegos(ArrayList<Juego> juegos, Context context) {
        this.juegos = juegos;
        this.context = context;
    }

    @Override
    public JuegosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_lista,parent, false);
        JuegosViewHolder viewHolder = new JuegosViewHolder(itemView,context);
        itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (JuegosViewHolder viewHolder, int position){
        Juego juego = juegos.get(position);
        viewHolder.bindAlumno(juego);
    }

    @Override
    public  int getItemCount(){
        return juegos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        mListener = listener;
    }
    @Override
    public void onClick(View v) {
        if(mListener != null){
            mListener.onClick(v);
        }
    }
}
