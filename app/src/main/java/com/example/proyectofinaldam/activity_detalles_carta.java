package com.example.proyectofinaldam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinaldam.Clases.Carta;
import com.example.proyectofinaldam.RecyclerView.CartaViewHolder;
import com.example.proyectofinaldam.UtilidadesImagenes.ImagenesBlobBitmap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class activity_detalles_carta extends AppCompatActivity
{
    public static final String EXTRA_POSICION_DEVUELTA = "es.Esdras.arrobajemail.posicion.com";
    public static final String EXTRA_TIPO = "es.Esdras.arrobajemail.tipo.com";

    // FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    TextView nombreCarta = null;
    TextView textoCarta = null;
    TextView tipoCarta = null;
    TextView precioCarta = null;
    TextView colorCarta = null;
    TextView manaCarta = null;
    TextView fuerza_resistenciaCarta = null;
    int posicion = -1;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;
    ImageView img_detalles_foto_carta = null;
    String Nombre = "";
    Button AñadirCarta;

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
        {
            AñadirCarta.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_carta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // ---

        nombreCarta = (TextView) findViewById(R.id.txt_titulo_carta_detalles);
        textoCarta = (TextView) findViewById(R.id.txt_textoCarta_detalles);
        tipoCarta = (TextView) findViewById(R.id.txt_tipoCarta_detalles);
        precioCarta = (TextView) findViewById(R.id.txt_precioCarta_detalles);
        fuerza_resistenciaCarta = (TextView) findViewById(R.id.txt_fuerza_resistencia_detalles);
        colorCarta = (TextView) findViewById(R.id.txt_color_detalles);
        manaCarta = (TextView) findViewById(R.id.txt_manaCarta_detalles);
        AñadirCarta = (Button) findViewById(R.id.btn_añadirCarta_detalles);
        img_detalles_foto_carta = (ImageView) findViewById(R.id.img_carta_detalles);

        mAuth= FirebaseAuth.getInstance();

        // ---

        Intent intent = getIntent();
        if(intent != null)
        {
            Carta c = (Carta)intent.getSerializableExtra(CartaViewHolder.EXTRA_CARTA_ITEM);
            nombreCarta.setText(c.getNombre());
            textoCarta.setText(c.getTexto());
            tipoCarta.setText(c.getTipoCarta());
            if (c.getTipoCarta().equalsIgnoreCase("Tierra"))
            {
                manaCarta.setVisibility(View.INVISIBLE);
                fuerza_resistenciaCarta.setVisibility(View.INVISIBLE);
            }
            else if (c.getTipoCarta().equalsIgnoreCase("Instantaneo") || c.getTipoCarta().equalsIgnoreCase("Conjuro") || c.getTipoCarta().equalsIgnoreCase("Artefacto") || c.getTipoCarta().equalsIgnoreCase("Encantamiento") )
            {
                fuerza_resistenciaCarta.setVisibility(View.INVISIBLE);
            }
            manaCarta.setText(String.valueOf(c.getMana()));
            colorCarta.setText(c.getColor());
            precioCarta.setText(String.valueOf(c.getPrecio()) + " €");
            fuerza_resistenciaCarta.setText(c.getFuerza_Resistencia());
            // Foto
            byte[] fotoEnBinario = (byte[]) intent.getByteArrayExtra(CartaViewHolder.EXTRA_CARTA_IMAGEN);
            Bitmap fotoBipBop = ImagenesBlobBitmap.bytes_to_bitmap(fotoEnBinario, 200, 200);
            img_detalles_foto_carta.setImageBitmap(fotoBipBop);

            posicion = intent.getIntExtra(CartaViewHolder.EXTRA_CARTA_CASILLA, -1);
        }

    }

    public void VolverInicio(View view)
    {
        Intent intent = new Intent(activity_detalles_carta.this, MainActivity.class);
        startActivity(intent);
    }

    public void añadirCartaWants(View view)
    {
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            String txt = String.valueOf(currentUser.getEmail());
            String [] textoEmail = txt.split("@");
            Nombre = textoEmail[0];
            // Toast.makeText(activity_detalles_carta.this, Nombre, Toast.LENGTH_SHORT).show();
        }

        Intent intent = getIntent();
        Carta c = (Carta)intent.getSerializableExtra(CartaViewHolder.EXTRA_CARTA_ITEM);
        // Toast.makeText(activity_detalles_carta.this, "Carta: " + c.toString() , Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("wants_" + Nombre).child(c.getNombre()).setValue(c);

        Toast.makeText(activity_detalles_carta.this, "Carta añadida correctamente", Toast.LENGTH_SHORT).show();
    }



}