package com.example.medicinereminder.MostrarAlarma;

public class MostrarModelo {
    String nombre, notas;

    public MostrarModelo() {
    }

    public MostrarModelo(String nombre, String notas) {
        this.nombre = nombre;
        this.notas = notas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

}
