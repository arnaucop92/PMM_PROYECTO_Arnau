package com.simarro.pmm_proyecto_arnau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout tilCorreo;
    private TextInputLayout tilContrasenya;
    private TextInputLayout tilUsuario;
    private TextInputLayout tilRepetirContra;

    private EditText etUsuario;
    private EditText etCorreo;
    private EditText etContrasenya;
    private EditText etRepetirContra;

    private Button registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        tilCorreo = findViewById(R.id.iCorreo);
        tilUsuario = findViewById(R.id.iUsuario);
        tilContrasenya = findViewById(R.id.iContrasenya);
        tilRepetirContra = findViewById(R.id.iRepetirContra);

        etUsuario = findViewById(R.id.usuario);
        etCorreo = findViewById(R.id.correo);
        etContrasenya = findViewById(R.id.contrasenya);
        etRepetirContra = findViewById(R.id.repetirContrasenya);

        registrarse = findViewById(R.id.b_registrar);
        registrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String email = etCorreo.getText().toString();
        String contrasenya = etContrasenya.getText().toString();
        String repetContra = etRepetirContra.getText().toString();

        if(email.contains("@") && email.contains(".com") || email.contains(".es")){
            tilCorreo.setError(null);
            if(contrasenya.equals(repetContra)){
                tilRepetirContra.setError(null);
                Intent intent = new Intent(Registro.this, MainActivity.class);
                intent.putExtra("Usuario",etUsuario.getText().toString());
                intent.putExtra("Correo",etCorreo.getText().toString());
                intent.putExtra("Contrasenya",etContrasenya.getText().toString());

                setResult(1,intent);

                finish();
            }else{
                tilRepetirContra.setError("Las contraseñas deben coincidir");
            }
        }else{
            tilCorreo.setError("Correo no válido");
        }
    }
}
