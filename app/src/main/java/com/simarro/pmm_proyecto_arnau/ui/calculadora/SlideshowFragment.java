package com.simarro.pmm_proyecto_arnau.ui.calculadora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.simarro.pmm_proyecto_arnau.R;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    private TextView pantalla;
    private double resultado = 0;
    private String operador;
    private double numeroSel;
    private double numeroSel2;
    private int cont = 0;
    private String valor;
    private String textoPantalla;
    private String valorInsertar = "";
    private int cont2 = 0;
    private ArrayList<String> listaOperaciones = new ArrayList <>();

    private Button siete;
    private Button ocho;
    private Button nueve;
    private Button dividir;
    private Button cuatro;
    private Button cinco;
    private Button seis;
    private Button multiplica;
    private Button uno;
    private Button dos;
    private Button tres;
    private Button resta;
    private Button cero;
    private Button igual;
    private Button borrar;
    private Button sumar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        pantalla = root.findViewById(R.id.pantalla);


        siete = root.findViewById(R.id.siete);
        siete.setOnClickListener(this);
        ocho = root.findViewById(R.id.ocho);
        ocho.setOnClickListener(this);
        nueve = root.findViewById(R.id.nueve);
        nueve.setOnClickListener(this);
        dividir = root.findViewById(R.id.dividir);
        dividir.setOnClickListener(this);
        cuatro = root.findViewById(R.id.cuatro);
        cuatro.setOnClickListener(this);
        cinco = root.findViewById(R.id.cinco);
        cinco.setOnClickListener(this);
        seis = root.findViewById(R.id.seis);
        seis.setOnClickListener(this);
        multiplica = root.findViewById(R.id.multiplica);
        multiplica.setOnClickListener(this);
        uno = root.findViewById(R.id.uno);
        uno.setOnClickListener(this);
        dos = root.findViewById(R.id.dos);
        dos.setOnClickListener(this);
        tres = root.findViewById(R.id.tres);
        tres.setOnClickListener(this);
        resta = root.findViewById(R.id.resta);
        resta.setOnClickListener(this);
        cero = root.findViewById(R.id.cero);
        cero.setOnClickListener(this);
        igual = root.findViewById(R.id.igual);
        igual.setOnClickListener(this);
        borrar = root.findViewById(R.id.borrar);
        borrar.setOnClickListener(this);
        sumar = root.findViewById(R.id.sumar);
        sumar.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() !=  R.id.dividir && v.getId() !=  R.id.multiplica && v.getId() !=  R.id.resta
                && v.getId() !=  R.id.sumar && v.getId() !=  R.id.igual && v.getId() != R.id.borrar ){

            Button botonSel = getActivity().findViewById(v.getId());
            valor = (String) botonSel.getText();
            valorInsertar += valor;
            textoPantalla = pantalla.getText().toString();
            textoPantalla += valor;
            pantalla.setText(textoPantalla);

        }else{
            if(cont2 == 0){
                listaOperaciones.add(valorInsertar);
                valorInsertar = "";
            }
            switch (v.getId()){
                case R.id.dividir:
                    valor = (String) dividir.getText();
                    String textoPantalla = pantalla.getText().toString();
                    textoPantalla += valor;
                    pantalla.setText(textoPantalla);
                    listaOperaciones.add("/");
                    cont2 = 0;
                    break;
                case R.id.multiplica:
                    valor = (String) multiplica.getText();
                    textoPantalla = pantalla.getText().toString();
                    textoPantalla += valor;
                    pantalla.setText(textoPantalla);
                    listaOperaciones.add("*");
                    cont2 = 0;
                    break;
                case R.id.resta:
                    valor = (String) resta.getText();
                    textoPantalla = pantalla.getText().toString();
                    textoPantalla += valor;
                    pantalla.setText(textoPantalla);
                    listaOperaciones.add("-");
                    cont2 = 0;
                    break;
                case R.id.sumar:
                    valor = (String) sumar.getText();
                    textoPantalla = pantalla.getText().toString();
                    textoPantalla += valor;
                    pantalla.setText(textoPantalla);
                    listaOperaciones.add("+");
                    cont2 = 0;
                    break;
                case R.id.igual:
                    cont = 0;
                    resultado = 0;
                    numeroSel = 0;
                    numeroSel2 = 0;
                    for (int i = 0; i < listaOperaciones.size();i++){
                        if (listaOperaciones.get(i).equals("-") || listaOperaciones.get(i).equals("+")
                                || listaOperaciones.get(i).equals("/") || listaOperaciones.get(i).equals("*")){
                            if(cont==1){
                                if(operador.equals("-")){
                                    resultado = (numeroSel - numeroSel2);
                                }
                                if(operador.equals("+")){
                                    resultado = (numeroSel + numeroSel2);
                                }
                                if(operador.equals("/")){
                                    resultado = (numeroSel / numeroSel2);
                                }
                                if(operador.equals("*")){
                                    resultado = (numeroSel * numeroSel2);
                                }
                                numeroSel = resultado;
                                operador = listaOperaciones.get(i);
                            }else if(cont == 0){
                                cont ++;
                            }
                            operador = listaOperaciones.get(i);
                        }else if (cont == 0){
                            numeroSel =  Double.parseDouble(listaOperaciones.get(i));
                        }else if (cont == 1){
                            numeroSel2 = Double.parseDouble(listaOperaciones.get(i));
                        }
                    }
                    if(cont==1 ){
                        if(operador.equals("-")){
                            resultado = (numeroSel - numeroSel2);
                        }
                        if(operador.equals("+")){
                            resultado = (numeroSel + numeroSel2);
                        }
                        if(operador.equals("/")){
                            resultado = (numeroSel / numeroSel2);
                        }
                        if(operador.equals("*")){
                            resultado = (numeroSel * numeroSel2);
                        }
                    }
                    cont2 ++;
                    pantalla.setText(String.valueOf(resultado));
                    break;
                case R.id.borrar:
                    pantalla.setText("");
                    listaOperaciones.clear();
                    valorInsertar = "";
                    resultado = 0;
                    cont = 0;
                    cont2 = 0;
                    break;
            }
        }
    }
}