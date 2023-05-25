package com.example.proyectofinaldam.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinaldam.Clases.Carta;
import com.example.proyectofinaldam.MainActivity;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.UtilidadesImagenes.ImagenesBlobBitmap;
import com.example.proyectofinaldam.activity_detalles_carta;

public class CartaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public static final String EXTRA_CARTA_ITEM = "es.Magic.com.Carta";
    public static final String EXTRA_CARTA_CASILLA = "es.Magic.com.casillaDeLaCarta";
    public static final String EXTRA_CARTA_IMAGEN = "es.Magic.com.imagenDeLaCarta";
    // --------
    private TextView txt_item_NombreCarta;
    private TextView txt_item_TextoCarta;
    private ImageView img_item_carta;
    // ---
    private ListaCartasAdapter lca;
    private Context contexto;

    public CartaViewHolder(@NonNull View itemView, ListaCartasAdapter listaCartasAdapter)
    {
        super(itemView);
        txt_item_NombreCarta = (TextView) itemView.findViewById(R.id.txt_nombreCartaItem);
        txt_item_TextoCarta = (TextView) itemView.findViewById(R.id.txt_descripcionCartaItem);
        img_item_carta = (ImageView) itemView.findViewById(R.id.img_cartaItem);
        // ----------------
        lca = listaCartasAdapter;
        itemView.setOnClickListener(this);
    }

    public ImageView getImg_item_carta() {
        return img_item_carta;
    }

    public void setImg_item_carta(ImageView img_item_carta) {
        this.img_item_carta = img_item_carta;
    }

    public TextView getTxt_item_NombreCarta() {
        return txt_item_NombreCarta;
    }

    public void setTxt_item_NombreCarta(TextView txt_item_NombreCarta) {
        this.txt_item_NombreCarta = txt_item_NombreCarta;
    }

    public TextView getTxt_item_TextoCarta() {
        return txt_item_TextoCarta;
    }

    public void setTxt_item_TextoCarta(TextView txt_item_TextoCarta) {
        this.txt_item_TextoCarta = txt_item_TextoCarta;
    }

    public ListaCartasAdapter getLca() {
        return lca;
    }

    public void setLca(ListaCartasAdapter lca) {
        this.lca = lca;
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public void onClick(View v)
    {
        int posicion = getLayoutPosition();
        Carta ca = lca.getCartas().get(posicion);
        Intent intent = new Intent(lca.getContexto(), activity_detalles_carta.class);
        img_item_carta.buildDrawingCache();
        Bitmap foto_bm = img_item_carta.getDrawingCache();
        intent.putExtra(EXTRA_CARTA_ITEM, ca);
        intent.putExtra(EXTRA_CARTA_IMAGEN, ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm));
        intent.putExtra(EXTRA_CARTA_CASILLA, posicion);
        Context contexto = lca.getContexto();
        ((MainActivity) contexto).startActivityForResult(intent, MainActivity.Peticion_1);

    }
}
