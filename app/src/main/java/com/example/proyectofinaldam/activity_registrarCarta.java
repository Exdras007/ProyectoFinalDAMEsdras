package com.example.proyectofinaldam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectofinaldam.Clases.Carta;
import com.example.proyectofinaldam.UtilidadesImagenes.ImagenFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class activity_registrarCarta extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner sp_Colores;
    private Spinner sp_tipoCarta;
    private EditText edt_nombre_carta;
    private EditText edt_manaCarta;
    private EditText edt_Texto;
    private EditText edt_Precio;
    private EditText edt_Fuerza;
    private EditText edt_Resistencia;
    private String ColorSeleccionado;
    private String TipoSeleccionado;
    private ImageView img_add_card;
    private String Mana;

    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_carta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // ------------------------------------------------

        edt_nombre_carta = (EditText) findViewById(R.id.edt_nombreCarta);
        edt_manaCarta = (EditText) findViewById(R.id.edt_manaCarta);
        edt_Texto = (EditText) findViewById(R.id.edt_descCarta);
        edt_Precio = (EditText) findViewById(R.id.edt_precioCarta);
        edt_Fuerza = (EditText) findViewById(R.id.edt_Fuerza);
        edt_Resistencia = (EditText) findViewById(R.id.edt_Resistencia);
        img_add_card = (ImageView) findViewById(R.id.img_subir_carta);

        // --------------------------------------------------

        sp_Colores = (Spinner) findViewById(R.id.sp_colores);
        // Estos son los tipos de colores que hay en las opciones
        ColorSeleccionado = "Rojo";
        String[] Colores ={"Rojo", "Verde", "Azul", "Negro", "Blanco", "Incoloro", "Multicolor"};
        ArrayAdapter<String> adaptador_colores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Colores);
        adaptador_colores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Colores.setAdapter(adaptador_colores);
        sp_Colores.setOnItemSelectedListener(this);
        sp_Colores.setSelection(0);

        // --------------------------------------------------

        sp_tipoCarta = (Spinner) findViewById(R.id.sp_tipoCarta);
        // Estos son los tipos de carta que hay en las opciones
        TipoSeleccionado = "Criatura";
        String[] Tipo ={"Criatura", "Artefacto", "Encantamiento", "Instantaneo", "Conjuro", "Tierra", "Planeswalker"};
        ArrayAdapter<String> adaptador_tipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Tipo);
        adaptador_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipoCarta.setAdapter(adaptador_tipo);
        sp_tipoCarta.setOnItemSelectedListener(this);
        sp_tipoCarta.setSelection(0);

    }

    public void VolverSesion(View view)
    {
        Intent intent = new Intent(activity_registrarCarta.this, activity_inicio_sesion.class);
        startActivity(intent);
    }

    public void subirCarta(View view)
    {
        String NombreCarta = String.valueOf(edt_nombre_carta.getText());
        if (String.valueOf(edt_manaCarta.getText()).equalsIgnoreCase(null) || String.valueOf(edt_manaCarta.getText()).equalsIgnoreCase(""))
        {
            Mana = "0";
        }
        else
        {
            Mana = String.valueOf(edt_manaCarta.getText());
        }
        Integer ManaCarta = Integer.valueOf(Mana);
        String TextoCarta = String.valueOf(edt_Texto.getText());
        String ColorCarta = String.valueOf(sp_Colores.getSelectedItem());
        String TipoCarta = String.valueOf(sp_tipoCarta.getSelectedItem());
        String FuerzaResistenciaCarta = String.valueOf(edt_Fuerza.getText() + "/" + edt_Resistencia.getText());
        double PrecioCarta = Float.valueOf(String.valueOf(edt_Precio.getText()));

        if(NombreCarta.isEmpty())
        {
            edt_nombre_carta.setError("Debes poner el nombre de la carta");
            return;
        }
        else
        {
            Carta nuevaCarta = new Carta(NombreCarta, ManaCarta, TextoCarta, ColorCarta, TipoCarta, FuerzaResistenciaCarta, PrecioCarta);

            if(imagen_seleccionada != null)
            {
                String carpetaImagen = "ImagenesCartas";
                ImagenFirebase.subirFoto(carpetaImagen, nuevaCarta.getNombre(), img_add_card);
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.child("Cartas").child(nuevaCarta.getNombre()).setValue(nuevaCarta);

            Toast.makeText(activity_registrarCarta.this, "Carta subida correctamente", Toast.LENGTH_SHORT).show();
        }

        edt_Texto.setText("");
        edt_nombre_carta.setText("");
        // edt_Fuerza.setText(0);
        // edt_Resistencia.setText(0);
        // edt_manaCarta.setText(0);
        // edt_Precio.setText(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        ColorSeleccionado = adapterView.getItemAtPosition(i).toString();
        TipoSeleccionado = adapterView.getItemAtPosition(i).toString();
        if (TipoSeleccionado.equalsIgnoreCase("Tierra") || TipoSeleccionado.equalsIgnoreCase("Planeswalker") || TipoSeleccionado.equalsIgnoreCase("Instantaneo") || TipoSeleccionado.equalsIgnoreCase("Conjuro") || TipoSeleccionado.equalsIgnoreCase("Artefacto") || TipoSeleccionado.equalsIgnoreCase("Encantamiento"))
        {
            edt_Fuerza.setVisibility(View.INVISIBLE);
            edt_Resistencia.setVisibility(View.INVISIBLE);
            edt_manaCarta.setVisibility(View.VISIBLE);
            if (TipoSeleccionado.equalsIgnoreCase("Tierra"))
            {
                edt_manaCarta.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            edt_Fuerza.setVisibility(View.VISIBLE);
            edt_Resistencia.setVisibility(View.VISIBLE);
            edt_manaCarta.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        ColorSeleccionado = "null";
        TipoSeleccionado = "null";
    }

    //--------CODIGO PARA CAMBIAR LA IMAGEN----------------
    public void subirImagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                img_add_card.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}