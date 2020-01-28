package com.simarro.pmm_proyecto_arnau.ui.camara;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.simarro.pmm_proyecto_arnau.Main2Activity;
import com.simarro.pmm_proyecto_arnau.R;
import com.simarro.pmm_proyecto_arnau.Utilidades;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ToolsFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "galeriaDos";
    private ToolsViewModel toolsViewModel;
    public Button hacerFoto;

    private TextView texto;
    private StringBuilder mensajes;
    private ImageView foto;
    Uri imageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);

        hacerFoto = root.findViewById(R.id.b_hacer);
        hacerFoto.setOnClickListener(this);

        foto = root.findViewById(R.id.fotoHecha);

        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(R.string.camara);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            abrirCamara();
        }else{
            Utilidades.solicitarPermiso(Manifest.permission.CAMERA, "Acceso denegado a la camara sin su permiso.",
                    1,getActivity());
        }
    }
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int [] granResults){
        if (requestCode == 1){
            if (granResults.length == 1 && granResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirCamara();
            }else{
                Toast.makeText(getContext(), "Acceso denegado a la camara sin su permiso." + granResults.length +  granResults[0] + PackageManager.PERMISSION_GRANTED,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void abrirCamara(){
        Intent galeria = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(galeria, REQUEST_IMAGE_CAPTURE);
        Log.d(TAG,"Se ha accedido a la camara.");
        if(mensajes != null){
            mensajes.append("Se ha accedido a la camara.");
            texto.setText(mensajes.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
        }
    }
}