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
import com.example.proyectofinaldam.activity_wants;

public class CartaViewHolderWants extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public static final String EXTRA_CARTA_ITEM = "es.Magic.com.Carta";
    public static final String EXTRA_CARTA_CASILLA = "es.Magic.com.casillaDeLaCarta";
    public static final String EXTRA_CARTA_IMAGEN = "es.Magic.com.imagenDeLaCarta";
    // --------
    private TextView txt_item_NombreCarta;
    private TextView txt_item_TextoCarta;
    private TextView txt_item_PrecioCarta;
    private ImageView img_item_carta;
    // ---
    private ListaCartasAdapterWants lca;
    private Context contexto;

    public CartaViewHolderWants(@NonNull View itemView, ListaCartasAdapterWants listaCartasAdapterWants)
    {
        super(itemView);
        txt_item_NombreCarta = (TextView) itemView.findViewById(R.id.txt_nombreCartaItem);
        txt_item_TextoCarta = (TextView) itemView.findViewById(R.id.txt_descripcionCartaItem);
        txt_item_PrecioCarta = (TextView) itemView.findViewById(R.id.txt_precioCartaItem);
        img_item_carta = (ImageView) itemView.findViewById(R.id.img_cartaItem);
        // ----------------
        lca = listaCartasAdapterWants;
        itemView.setOnClickListener(this);
    }

    public TextView getTxt_item_PrecioCarta() {
        return txt_item_PrecioCarta;
    }

    public void setTxt_item_PrecioCarta(TextView txt_item_PrecioCarta) {
        this.txt_item_PrecioCarta = txt_item_PrecioCarta;
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

    public ListaCartasAdapterWants getLca() {
        return lca;
    }

    public void setLca(ListaCartasAdapterWants lca) {
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
        ((activity_wants) contexto).startActivityForResult(intent, activity_wants.Peticion_1);

    }
}
