package com.simarro.pmm_proyecto_arnau;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simarro.pmm_proyecto_arnau.ui.novedades.GalleryFragment;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 10;
    private static final String TAG = "galeriaDos";
    Uri imageUri;
    private StringBuilder mensajes;
    private TextView texto;

    private EditText nombre;
    private EditText descripcion;
    private EditText anyo;
    private ImageView foto;
    private Button crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        nombre = findViewById(R.id.et_nombre);
        descripcion = findViewById(R.id.et_descripcion);
        anyo = findViewById(R.id.et_anyo);

        crear = findViewById(R.id.b_crear);
        crear.setOnClickListener(this);

        foto = findViewById(R.id.fotoJuego);
        foto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.b_crear){


            Intent intent = new Intent(Main3Activity.this, GalleryFragment.class);

            Bitmap bitmap = ((BitmapDrawable)foto.getDrawable()).getBitmap();
            Bitmap imageScaled = Bitmap.createScaledBitmap(bitmap, 300, 300, false);

            intent.putExtra("Nombre",nombre.getText().toString());
            intent.putExtra("Descripcion",descripcion.getText().toString());
            intent.putExtra("Anyo",anyo.getText().toString());
            intent.putExtra("Foto",imageScaled);

            if(!nombre.getText().toString().isEmpty()&!descripcion.getText().toString().isEmpty()
                    &&!anyo.getText().toString().isEmpty()){
                setResult(1,intent);
            }else{
                setResult(0,intent);
            }
            finish();
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                abrirGaleria();
            }else{
                Utilidades.solicitarPermiso(Manifest.permission.READ_EXTERNAL_STORAGE, "Acceso denegado a la galeria sin su permiso.",
                        1,this);
            }
        }

    }
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int [] granResults){
        if (requestCode == 1){
            if (granResults.length == 1 && granResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirGaleria();
            }else{
                Toast.makeText(this, "Acceso denegado a la galeria sin su permiso." + granResults.length +  granResults[0] + PackageManager.PERMISSION_GRANTED,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void abrirGaleria(){
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, PICK_IMAGE);
        Log.d(TAG,"Se ha accedido a la galeria.");
        if(mensajes != null){
            mensajes.append("Se ha accedido a la galeria.");
            texto.setText(mensajes.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            foto.setImageIcon(Icon.createWithContentUri(imageUri));
        }
    }
}
