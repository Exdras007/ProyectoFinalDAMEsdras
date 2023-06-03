package com.example.proyectofinaldam.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinaldam.Clases.Carta;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.UtilidadesImagenes.ImagenFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListaCartasAdapterWants extends RecyclerView.Adapter<CartaViewHolderWants>
{
    private Context contexto = null;
    private ArrayList<Carta> cartas = null;
    private LayoutInflater inflate = null;
    private FirebaseAuth mAuth;

    public ListaCartasAdapterWants(Context contexto, ArrayList<Carta> cartas) {
        this.contexto = contexto;
        this.cartas = cartas;
        this.inflate = LayoutInflater.from(this.contexto);
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public LayoutInflater getInflate() {
        return inflate;
    }

    public void setInflate(LayoutInflater inflate) {
        this.inflate = inflate;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @NonNull
    @Override
    public CartaViewHolderWants onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mItemView = inflate.inflate(R.layout.activity_item_carta,parent,false);
        CartaViewHolderWants cvh = new CartaViewHolderWants(mItemView, this);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartaViewHolderWants holder, int position)
    {
        Carta c = this.getCartas().get(position);
        holder.getTxt_item_NombreCarta().setText(c.getNombre());
        double precio = c.getPrecio();
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(precio));
        holder.getTxt_item_PrecioCarta().setText(precio + " €");
        if(c.getTexto().equalsIgnoreCase("null"))
        {
            holder.getTxt_item_TextoCarta().setText("");
        }
        else
        {
            holder.getTxt_item_TextoCarta().setText(c.getTexto());
        }
        // ---Imagen---
        String carpeta = "ImagenesCartas";
        ImageView imagen = holder.getImg_item_carta();
        ImagenFirebase.descargarFoto(carpeta, c.getNombre(), imagen);
        ImageView imagen1 = imagen;
        holder.setImg_item_carta(imagen1);
    }

    @Override
    public int getItemCount() {
        return this.cartas.size();
    }
}
