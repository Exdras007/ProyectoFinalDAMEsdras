package com.example.proyectofinaldam.Clases;

import java.util.ArrayList;

public class Mazo
{
    private String propietario;
    private String Nombre;
    private ArrayList<Carta> Cartas;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public ArrayList<Carta> getCartas() {
        return Cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        Cartas = cartas;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public Mazo(String propietario, String nombre, ArrayList<Carta> cartas) {
        this.propietario = propietario;
        Nombre = nombre;
        Cartas = cartas;
    }

    @Override
    public String toString() {
        return "Mazo{" +
                "propietario='" + propietario + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Cartas=" + Cartas +
                '}';
    }
}
