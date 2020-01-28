package com.simarro.pmm_proyecto_arnau;

import android.graphics.Bitmap;
import java.io.Serializable;

public class Juego implements Serializable {

    private String nombre;
    private String descripcion;
    private String anyo;
    private Bitmap foto;

    public Juego(String nombre, String descripcion, String anyo, Bitmap foto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.anyo = anyo;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnyo() {
        return anyo;
    }

    public void setAnyo(String anyo) {
        this.anyo = anyo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}
