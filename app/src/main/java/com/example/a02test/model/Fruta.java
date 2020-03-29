package com.example.a02test.model;

public class Fruta {
    private String nombre;
    private String origen;
    private int icono;

    public Fruta(){

    }

    public Fruta (String nombre, String origen, int icono){
        this.nombre = nombre;
        this.origen = origen;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
