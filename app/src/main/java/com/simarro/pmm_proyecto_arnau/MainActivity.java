package com.simarro.pmm_proyecto_arnau;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;

import java.time.temporal.JulianFields;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener {

    private Button entrar;
    private Button olvidar;
    private Button registro;
    private TextInputLayout tilCorreo;
    private TextInputLayout tilContrasenya;
    private EditText etCorreo;
    private EditText etContrasenya;
    private Switch mostrar;

    private ArrayList <Jugador> jugadores = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilCorreo = findViewById(R.id.iCorreo);
        tilCorreo.setErrorEnabled(true);
        tilContrasenya = findViewById(R.id.iContrasenya);

        etCorreo = findViewById(R.id.correo);
        etContrasenya = findViewById(R.id.contrasenya);

        mostrar = findViewById(R.id.sContrasenya);
        mostrar.setOnCheckedChangeListener(this);

        entrar = findViewById(R.id.entrar);
        entrar.setOnClickListener(this);

        olvidar = findViewById(R.id.olvidado);
        olvidar.setOnClickListener(this);

        registro = findViewById(R.id.b_registro);
        registro.setOnClickListener(this);

        jugadores.add(new Jugador("admin","admin@admin","admin"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.entrar:
                for(int i = 0; i < jugadores.size();i++){
                    Jugador seleccionado = jugadores.get(i);
                    if(etCorreo.getText().toString().equals(seleccionado.getCorreo())
                            || etCorreo.getText().toString().equals(seleccionado.getUsuario())
                    && etContrasenya.getText().toString().equals(seleccionado.getContrasenya())){

                        tilContrasenya.setError(null);
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("Jugador", seleccionado);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.olvidado:
                if(etCorreo.getText().toString().isEmpty()){
                        tilContrasenya.setError(null);
                }else{
                    for(int i = 0; i < jugadores.size();i++){
                        Jugador seleccionado = jugadores.get(i);
                        if(etCorreo.getText().toString().equals(seleccionado.getUsuario())
                                || etCorreo.getText().toString().equals(seleccionado.getCorreo())){
                            tilContrasenya.setError(seleccionado.getContrasenya());
                        }
                    }

                }
                break;
            case R.id.b_registro:
                Intent intent = new Intent(MainActivity.this,Registro.class);
                startActivityForResult(intent,0);
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(mostrar.isChecked()){
            etContrasenya.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else {
            etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1){
            String usuario = data.getStringExtra("Usuario");
            String correo = data.getStringExtra("Correo");
            String contrasenya = data.getStringExtra("Contrasenya");

            jugadores.add(new Jugador(usuario,correo,contrasenya));
        }
    }
}
