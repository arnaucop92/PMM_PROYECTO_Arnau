package com.simarro.pmm_proyecto_arnau;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String usuario;
    private String correo;
    private String contrasenya;

    public Jugador(String usuario, String correo, String contrasenya) {
        this.usuario = usuario;
        this.correo = correo;
        this.contrasenya = contrasenya;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
