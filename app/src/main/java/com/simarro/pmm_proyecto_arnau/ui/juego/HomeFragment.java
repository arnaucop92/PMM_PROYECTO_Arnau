package com.simarro.pmm_proyecto_arnau.ui.juego;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.simarro.pmm_proyecto_arnau.R;
import com.simarro.pmm_proyecto_arnau.ui.camara.ToolsViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private ToolsViewModel toolsViewModel;
    private Button[] boton;
    private TableLayout tabla;
    private Button barajar;
    private CardView ganar;
    private TextView puntuacion;
    private int total = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);

        ganar = root.findViewById(R.id.cardViewGanar);
        ganar.setOnClickListener(this);
        ganar.setVisibility(View.INVISIBLE);

        puntuacion = root.findViewById(R.id.puntuacion);

        boton = new Button[9];
        boton [0] = root.findViewById(R.id.uno);
        boton [1] = root.findViewById(R.id.dos);
        boton [2] = root.findViewById(R.id.tres);
        boton [3] = root.findViewById(R.id.cuatro);
        boton [4] = root.findViewById(R.id.cinco);
        boton [5] = root.findViewById(R.id.seis);
        boton [6] = root.findViewById(R.id.siete);
        boton [7] = root.findViewById(R.id.ocho);
        boton [8] = root.findViewById(R.id.vacio);


        for (int i=0; i < boton.length;i++){

            boton[i].setOnClickListener(this);
        }
        barajar();
        barajar = root.findViewById(R.id.baraja);
        barajar.setOnClickListener(this);
        tabla = root.findViewById(R.id.tabla);

        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("!!HAS GANADO!!");
            }
        });
        return root;
    }

    public void barajar(){

        int boton1;
        int boton2;
        for (int j=0 ; j < 50;j++){
            boton1 = (int) (Math.random()*8);
            boton2 = (int) (Math.random()*8);
            while (boton1==boton2){
                boton1 = (int) (Math.random()*8);
                boton2 = (int) (Math.random()*8);
            }
            String texto1 = boton[boton2].getText().toString();
            String texto2 = boton[boton1].getText().toString();
            boton[boton1].setText(texto1);
            boton[boton2].setText(texto2);
        }
    }
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.baraja){

            barajar();
        }else if(v.getId() == R.id.cardViewGanar) {
            ganar.setVisibility(View.INVISIBLE);
            barajar();
        }else{

            total ++;
            puntuacion.setText("PUNTUACION: " + String.valueOf(total));

            int pos = 0;
            for (int j=0 ; j < boton.length;j++){

                if( boton[j].getText().equals(""))
                    pos = j;
            }

            for (int i=0; i<  boton.length; i++){
                if (v.getId() ==  boton[i].getId()){

                    if(i == pos + 3){
                        boton[pos].setText(boton[i].getText());
                        boton[i].setText("");
                    }else if(i == pos - 3){
                        boton[pos].setText(boton[i].getText());
                        boton[i].setText("");
                    }else if(i == pos + 1){
                        boton[pos].setText(boton[i].getText());
                        boton[i].setText("");
                    }else if(i == pos - 1){
                        boton[pos].setText(boton[i].getText());
                        boton[i].setText("");
                    }
                }
            }
            if(boton[0].getText().equals("1") && boton[1].getText().equals("2") && boton[2].getText().equals("3")&&
                    boton[3].getText().equals("4") && boton[4].getText().equals("5") && boton[5].getText().equals("6")&&
                    boton[6].getText().equals("7") && boton[7].getText().equals("8")){

                ganar.setVisibility(View.VISIBLE);

            }
        }
    }
}