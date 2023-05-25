package com.example.proyectofinaldam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinaldam.Clases.Carta;
import com.example.proyectofinaldam.RecyclerView.ListaCartasAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class activity_wants extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String Usuario = "NADA?";
    private double precio = 0.0;
    private TextView txt_precio;

    // --------------------------------

    private RecyclerView rv_cartasWants = null;
    private ArrayList<Carta> cartas_wants;
    private ArrayList<String> nombreCartasWants;
    private ListaCartasAdapter adaptadorCartasWants = null;
    private DatabaseReference myRefCartas = null;

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        String texto = String.valueOf(currentUser.getEmail());
        String [] textoEmail = texto.split("@");
        Usuario = textoEmail[0];
        Usuario.toLowerCase();
        // Toast.makeText(activity_wants.this, "Aqui tus cartas " + Usuario.toUpperCase(), Toast.LENGTH_SHORT).show();

        // ---

        myRefCartas = FirebaseDatabase.getInstance().getReference("wants_" + Usuario);
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                RecogerCartasWants(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Fallo al leer valores
                Log.i( "Fallo", String.valueOf(error.toException()));
            }
        });
        // --------------
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // In landscape
            rv_cartasWants.setLayoutManager(new GridLayoutManager(this,2));
        }
        else
        {
            // In portrait
            rv_cartasWants.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wants);
        // --------------------------
        rv_cartasWants = (RecyclerView) findViewById(R.id.rv_cartasWants);
        txt_precio = (TextView) findViewById(R.id.txt_precio_total);
        // ---
        mAuth = FirebaseAuth.getInstance();
        cartas_wants = new ArrayList<Carta>();
        nombreCartasWants = new ArrayList<String>();
        // ---
        adaptadorCartasWants = new ListaCartasAdapter(this,cartas_wants);
        rv_cartasWants.setAdapter(adaptadorCartasWants);
    }

    private void RecogerCartasWants(DataSnapshot snapshot)
    {
        adaptadorCartasWants = new ListaCartasAdapter(this,cartas_wants);
        rv_cartasWants.setAdapter(adaptadorCartasWants);
        myRefCartas = FirebaseDatabase.getInstance().getReference("Cartas");
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                adaptadorCartasWants.getCartas().clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Carta c = (Carta) dataSnapshot.getValue(Carta.class);
                    cartas_wants.add(c);
                    adaptadorCartasWants.setCartas(cartas_wants);
                    adaptadorCartasWants.notifyDataSetChanged();
                }
                // RecogerCartas(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Fallo al leer valores
                Log.i( "Fallo", String.valueOf(error.toException()));
            }
        });
    }

    private void RecogerNombreCartasWants(DataSnapshot snapshot)
    {
        adaptadorCartasWants = new ListaCartasAdapter(this,cartas_wants);
        rv_cartasWants.setAdapter(adaptadorCartasWants);
        myRefCartas = FirebaseDatabase.getInstance().getReference("wants_" + Usuario);
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                adaptadorCartasWants.getCartas().clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Carta c = (Carta) dataSnapshot.getValue(Carta.class);
                    cartas_wants.add(c);
                    adaptadorCartasWants.setCartas(cartas_wants);
                    adaptadorCartasWants.notifyDataSetChanged();
                }
                // RecogerCartas(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Fallo al leer valores
                Log.i( "Fallo", String.valueOf(error.toException()));
            }
        });
    }

    public void VolverSesion(View view)
    {
        Intent intent = new Intent(activity_wants.this, activity_inicio_sesion.class);
        startActivity(intent);
    }

}