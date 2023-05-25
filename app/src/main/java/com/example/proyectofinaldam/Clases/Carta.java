package com.example.proyectofinaldam.Clases;

import java.io.Serializable;
import java.util.Objects;

public class Carta implements Serializable
{
    private String Nombre;
    private int Mana;
    private String Texto;
    private String Color;
    private String TipoCarta;
    private String Fuerza_Resistencia;
    private double Precio;

    public Carta(String nombre, int mana, String texto, String color, String tipoCarta, String fuerza_resistencia, double precio) {
        Nombre = nombre;
        Mana = mana;
        Texto = texto;
        Color = color;
        TipoCarta = tipoCarta;
        Fuerza_Resistencia = fuerza_resistencia;
        Precio = precio;
    }

    public Carta(String nombre)
    {
        Nombre = nombre;
        Mana = 0;
        Texto = "";
        Color = "";
        TipoCarta = "";
        this.Fuerza_Resistencia = "";
        Precio = 0;
    }

    public Carta()
    {
        Nombre = "";
        Mana = 0;
        Texto = "";
        Color = "";
        TipoCarta = "";
        this.Fuerza_Resistencia = "No vas :(";
        Precio = 0;
    }

    public String getFuerza_Resistencia() {
        return Fuerza_Resistencia;
    }

    public void setFuerza_Resistencia(String fuerza_Resistencia) {
        Fuerza_Resistencia = fuerza_Resistencia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getMana() {
        return Mana;
    }

    public void setMana(int mana) {
        Mana = mana;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getTipoCarta() {
        return TipoCarta;
    }

    public void setTipoCarta(String tipoCarta) {
        TipoCarta = tipoCarta;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carta)) return false;
        Carta carta = (Carta) o;
        return Nombre.equals(carta.Nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Nombre);
    }

    @Override
    public String toString() {
        return "Carta{" +
                "Nombre='" + Nombre + '\'' +
                ", Mana=" + Mana +
                ", Texto='" + Texto + '\'' +
                ", Color='" + Color + '\'' +
                ", TipoCarta='" + TipoCarta + '\'' +
                ", fuerza_resistencia='" + Fuerza_Resistencia + '\'' +
                ", Precio=" + Precio +
                '}';
    }
}
