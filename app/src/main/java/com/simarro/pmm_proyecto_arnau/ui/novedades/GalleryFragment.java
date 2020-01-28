package com.simarro.pmm_proyecto_arnau.ui.novedades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.simarro.pmm_proyecto_arnau.AdaptadorJuegos;
import com.simarro.pmm_proyecto_arnau.Juego;
import com.simarro.pmm_proyecto_arnau.Main3Activity;
import com.simarro.pmm_proyecto_arnau.Main4Activity;
import com.simarro.pmm_proyecto_arnau.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;

    private RecyclerView recyclerView;
    public AdaptadorJuegos adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Juego> juegos = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView vacio;

    private Button anyadir;
    private int posicion;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        swipeRefreshLayout = root.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adaptador.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView = root.findViewById(R.id.rv_puntuaciones);
        adaptador = new AdaptadorJuegos(juegos,getContext());
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        vacio = root.findViewById(R.id.text_gallery);
        anyadir = root.findViewById(R.id.anyadirJuegos);
        anyadir.setOnClickListener(this);

        final TextView textView = root.findViewById(R.id.text_gallery);

        ItemTouchHelper.SimpleCallback myCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                switch (direction){
                    case ItemTouchHelper.RIGHT:
                        juegos.remove(viewHolder.getAdapterPosition());
                        adaptador.notifyItemRemoved(viewHolder.getAdapterPosition());
                        break;
                    case ItemTouchHelper.LEFT:

                        Juego editar = juegos.get(viewHolder.getAdapterPosition());

                        for(int i = 0 ; i < juegos.size(); i++){
                            Juego sel = juegos.get(i);
                            if(sel == editar){
                                posicion = i;
                            }
                        }
                        Intent intent = new Intent (getActivity(), Main4Activity.class);
                        intent.putExtra("Nombre",editar.getNombre());
                        intent.putExtra("Descripcion", editar.getDescripcion());
                        intent.putExtra("Foto",editar.getFoto());
                        intent.putExtra("Anyo",editar.getAnyo());
                        startActivityForResult(intent,1);
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                Paint pincel = new Paint();
                pincel.setColor(Color.WHITE);
                int sizeText = getResources().getDimensionPixelSize(R.dimen.textSize);
                pincel.setTextSize(sizeText);

                //Derecha
                if(dX >0){
                    c.clipRect(viewHolder.itemView.getLeft(),viewHolder.itemView.getTop(),dX,viewHolder.itemView.getBottom());
                    if (dX < recyclerView.getWidth() / 3){
                        c.drawColor(Color.GRAY);
                    }else{
                        c.drawColor(Color.RED);
                    }
                    int margen = 40;
                    Drawable delete = getActivity().getDrawable(R.drawable.delete);
                    delete.setBounds(viewHolder.itemView.getLeft()+margen,
                            viewHolder.itemView.getTop()+margen,
                            viewHolder.itemView.getHeight()-margen,
                            viewHolder.itemView.getBottom()-margen);
                    delete.draw(c);

                    pincel.setTextAlign(Paint.Align.LEFT);
                    c.drawText(getResources().getString(R.string.eliminar),viewHolder.itemView.getHeight(),
                            viewHolder.itemView.getBottom()-viewHolder.itemView.getHeight()/2+sizeText/2,pincel);
                    if(!juegos.isEmpty()){
                        vacio.setVisibility(View.INVISIBLE);
                    }else{
                        vacio.setVisibility(View.VISIBLE);
                    }
                }else if (dX < 0){

                    c.clipRect(viewHolder.itemView.getRight(),viewHolder.itemView.getTop(),dX,viewHolder.itemView.getBottom());
                    if (dX < -recyclerView.getWidth() / 3){
                        c.drawColor(Color.GREEN);
                    }else{
                        c.drawColor(Color.GRAY);
                    }

                    int margen = 40;
                    Drawable editar = getActivity().getDrawable(R.drawable.edit);
                    editar.setBounds(viewHolder.itemView.getRight() - viewHolder.itemView.getHeight()+margen,
                            viewHolder.itemView.getTop() + margen,
                            viewHolder.itemView.getRight() - margen,
                            viewHolder.itemView.getBottom() - margen);

                    editar.draw(c);

                    pincel.setTextAlign(Paint.Align.RIGHT);
                    c.drawText(getResources().getString(R.string.editar),viewHolder.itemView.getRight() - viewHolder.itemView.getHeight(),
                            viewHolder.itemView.getBottom()-viewHolder.itemView.getHeight()/2+sizeText/2,pincel);


                }
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(R.string.lista_juegos);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.anyadirJuegos){
            Intent intent = new Intent(getActivity(), Main3Activity.class);
            startActivityForResult(intent,0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2){

            Bitmap bmp = (Bitmap) data.getParcelableExtra("Foto");
            String nombre = data.getStringExtra("Nombre");
            String descripcion = data.getStringExtra("Descripcion");
            String anyo = data.getStringExtra("Anyo");

            Juego editaJuego = new Juego(nombre,descripcion,anyo,bmp);

            juegos.set(posicion,editaJuego);
            adaptador.notifyDataSetChanged();

        }
        if(resultCode == 1){

            Bitmap bmp = (Bitmap) data.getParcelableExtra("Foto");
            String nombre = data.getStringExtra("Nombre");
            String descripcion = data.getStringExtra("Descripcion");
            String anyo = data.getStringExtra("Anyo");



            Juego nuevoJuego = new Juego(nombre,descripcion,anyo,bmp);
            juegos.add(nuevoJuego);
            adaptador.notifyDataSetChanged();

            Snackbar.make(recyclerView, "Nuevo juego "+ nuevoJuego.getNombre(),Snackbar.LENGTH_LONG)
                    .setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            juegos.remove(juegos.size()-1);
                            adaptador.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Operación deshecha",Toast.LENGTH_SHORT).show();
                            if(!juegos.isEmpty()){
                                vacio.setVisibility(View.INVISIBLE);
                            }else{
                                vacio.setVisibility(View.VISIBLE);
                            }
                        }
                    }).show();
            if(!juegos.isEmpty()){
                vacio.setVisibility(View.INVISIBLE);
            }else{
                vacio.setVisibility(View.VISIBLE);
            }
        }
    }
}