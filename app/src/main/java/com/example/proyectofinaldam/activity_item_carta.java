package com.example.proyectofinaldam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.DecimalFormat;

public class activity_item_carta extends AppCompatActivity implements Serializable
{
    private TextView NombreCarta;
    private TextView TextoCarta;
    private TextView PrecioCarta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_carta);
        // -------------------------
        NombreCarta = (TextView) findViewById(R.id.txt_nombreCartaItem);
        TextoCarta = (TextView) findViewById(R.id.txt_descripcionCartaItem);
        PrecioCarta = (TextView) findViewById(R.id.txt_precioCartaItem);
        // ----------
        Intent intent = getIntent();
    }
}