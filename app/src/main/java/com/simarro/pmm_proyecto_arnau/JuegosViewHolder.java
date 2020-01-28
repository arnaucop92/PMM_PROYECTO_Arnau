package com.simarro.pmm_proyecto_arnau;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class JuegosViewHolder extends RecyclerView.ViewHolder {

    private ImageView imgJuego;
    private TextView tvNombre;
    private TextView tvAnyo;
    private TextView tvDescripcion;
    private Context contexto;

    public JuegosViewHolder(View itemView, Context context){
        super(itemView);

        imgJuego = itemView.findViewById(R.id.img_juego);
        tvNombre = itemView.findViewById(R.id.tv_nombre);
        tvAnyo = itemView.findViewById(R.id.tv_anyo);
        tvDescripcion = itemView.findViewById(R.id.tv_descripcion);
        contexto = context;
    }

    public  void bindAlumno(Juego p){

        tvNombre.setText(p.getNombre());
        tvAnyo.setText(p.getAnyo());
        tvDescripcion.setText(p.getDescripcion());
        imgJuego.setImageBitmap(p.getFoto());
    }
}
