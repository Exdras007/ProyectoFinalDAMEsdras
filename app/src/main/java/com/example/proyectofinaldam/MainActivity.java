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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.proyectofinaldam.Clases.Carta;
import com.example.proyectofinaldam.RecyclerView.ListaCartasAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button btn_inicioSesion;
    private ImageButton btn_abrirBuscar;
    private ImageButton btn_buscarCarta;
    private ImageButton btn_cerrarBuscar;
    private FirebaseAuth mAuth;
    private Spinner sp_filtros_colores;
    private Spinner sp_filtros_tipo;
    private String filtroColorSeleccionado;
    private String filtroTipoSeleccionado;
    private EditText edt_BuscarCartas;
    public static int Peticion_1 = 1;

    // ---------------------------------

    private RecyclerView rv_cartas = null;
    private ArrayList<Carta> cartas;
    private ListaCartasAdapter adaptadorCartas = null;
    private DatabaseReference myRefCartas = null;

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            btn_inicioSesion.setText("Mi cuenta");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // -----------------------------------------------
        btn_inicioSesion = (Button) findViewById(R.id.btn_inicioSesion);
        rv_cartas = (RecyclerView) findViewById(R.id.rv_cartas);
        sp_filtros_colores = (Spinner) findViewById(R.id.sp_filtroColor);
        sp_filtros_tipo = (Spinner) findViewById(R.id.sp_filtroTipo);
        edt_BuscarCartas = (EditText) findViewById(R.id.edt_buscarCarta);

        btn_buscarCarta = (ImageButton) findViewById(R.id.img_buscarCartaFiltro);
        btn_abrirBuscar = (ImageButton) findViewById(R.id.img_abrirBuscar);
        btn_cerrarBuscar = (ImageButton) findViewById(R.id.img_cerrarBuscar);
        // --------
        mAuth = FirebaseAuth.getInstance();
        cartas = new ArrayList<Carta>();
        // --------------------------------------------------
        // Estos son los tipos de filtros que hay en las opciones de colores
        filtroColorSeleccionado = "Colores";
        String[] FiltrosColores ={"Colores", "Rojo", "Verde", "Azul", "Negro", "Blanco", "Incoloro", "Multicolor"};

        filtroTipoSeleccionado = "Tipos";
        String[] FiltroTipo ={"Tipos", "Criatura", "Instantaneo", "Conjuro", "Artefacto", "Encantamiento", "Tierra", "Planeswalker"};
        // ------
        ArrayAdapter<String> adaptador_filtro_colores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FiltrosColores);
        adaptador_filtro_colores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_filtros_colores.setAdapter(adaptador_filtro_colores);
        sp_filtros_colores.setOnItemSelectedListener(this);
        sp_filtros_colores.setSelection(0);

        ArrayAdapter<String> adaptador_filtro_tipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FiltroTipo);
        adaptador_filtro_tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_filtros_tipo.setAdapter(adaptador_filtro_tipos);
        sp_filtros_tipo.setOnItemSelectedListener(this);
        sp_filtros_tipo.setSelection(0);
        // --------
        adaptadorCartas = new ListaCartasAdapter(this,cartas);
        rv_cartas.setAdapter(adaptadorCartas);
        myRefCartas = FirebaseDatabase.getInstance().getReference("Cartas");
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                RecogerCartas(snapshot);
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
            rv_cartas.setLayoutManager(new GridLayoutManager(this,2));
        }
        else
        {
            // In portrait
            rv_cartas.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void RecogerCartas(DataSnapshot snapshot)
    {
        adaptadorCartas.getCartas().clear();
        for (DataSnapshot dataSnapshot : snapshot.getChildren())
        {
            Carta c = (Carta) dataSnapshot.getValue(Carta.class);
            cartas.add(c);
            adaptadorCartas.setCartas(cartas);
            adaptadorCartas.notifyDataSetChanged();
        }
    }

    public void IrInicioSesion(View view)
    {
        Intent intent = new Intent(MainActivity.this, activity_inicio_sesion.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        filtroColorSeleccionado = adapterView.getItemAtPosition(i).toString();
        filtroTipoSeleccionado = adapterView.getItemAtPosition(i).toString();

        // --- Filtros por color ---

        if (filtroColorSeleccionado.equalsIgnoreCase("Rojo"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        else if (filtroColorSeleccionado.equalsIgnoreCase("Azul"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        else if (filtroColorSeleccionado.equalsIgnoreCase("Verde"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        else if (filtroColorSeleccionado.equalsIgnoreCase("Blanco"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        else if (filtroColorSeleccionado.equalsIgnoreCase("Negro"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        else if (filtroColorSeleccionado.equalsIgnoreCase("Incoloro"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        else if (filtroColorSeleccionado.equalsIgnoreCase("Multicolor"))
        {
            sp_filtros_tipo.setSelection(0);
            filtroCarta(filtroColorSeleccionado);
        }
        // --- Filtros por tipo ---
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Criatura"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Encantamiento"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Artefacto"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Instantaneo"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Conjuro"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Tierra"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        else if (filtroTipoSeleccionado.equalsIgnoreCase("Planeswalker"))
        {
            sp_filtros_colores.setSelection(0);
            filtroCarta(filtroTipoSeleccionado);
        }
        // --- Todos ---
        /*
        else if (filtroColorSeleccionado.equalsIgnoreCase("Todo"))
        {
            adaptadorCartas = new ListaCartasAdapter(this,cartas);
            rv_cartas.setAdapter(adaptadorCartas);
            myRefCartas = FirebaseDatabase.getInstance().getReference("Cartas");
            myRefCartas.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot snapshot)
                {
                    adaptadorCartas.getCartas().clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Carta c = (Carta) dataSnapshot.getValue(Carta.class);
                        cartas.add(c);
                        adaptadorCartas.setCartas(cartas);
                        adaptadorCartas.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    // Fallo al leer valores
                    Log.i( "Fallo", String.valueOf(error.toException()));
                }
            });
        }
        */
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    // --------------------

    public void BuscarCarta(View view)
    {
        sp_filtros_colores.setVisibility(View.INVISIBLE);
        sp_filtros_tipo.setVisibility(View.INVISIBLE);
        edt_BuscarCartas.setVisibility(View.VISIBLE);
        btn_cerrarBuscar.setVisibility(View.VISIBLE);
        btn_abrirBuscar.setVisibility(View.INVISIBLE);
        btn_buscarCarta.setVisibility(View.VISIBLE);
    }

    public void cerrarBuscar(View view)
    {
        sp_filtros_colores.setVisibility(View.VISIBLE);
        sp_filtros_tipo.setVisibility(View.VISIBLE);
        edt_BuscarCartas.setVisibility(View.INVISIBLE);
        btn_cerrarBuscar.setVisibility(View.INVISIBLE);
        btn_abrirBuscar.setVisibility(View.VISIBLE);
        btn_buscarCarta.setVisibility(View.INVISIBLE);
        // ---
        adaptadorCartas = new ListaCartasAdapter(this,cartas);
        rv_cartas.setAdapter(adaptadorCartas);
        myRefCartas = FirebaseDatabase.getInstance().getReference("Cartas");
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                RecogerCartas(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Fallo al leer valores
                Log.i( "Fallo", String.valueOf(error.toException()));
            }
        });
    }

    public void buscarCartaFiltro(View view)
    {
        String CartaBuscar = String.valueOf(edt_BuscarCartas.getText());
        filtroCartaBuscar(CartaBuscar);
    }

    private void filtroCartaBuscar(String nombreCartaBuscar)
    {
        adaptadorCartas = new ListaCartasAdapter(this,cartas);
        rv_cartas.setAdapter(adaptadorCartas);
        myRefCartas = FirebaseDatabase.getInstance().getReference("Cartas");
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                adaptadorCartas.getCartas().clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Carta c = (Carta) dataSnapshot.getValue(Carta.class);
                    if (c.getNombre().equalsIgnoreCase(nombreCartaBuscar))
                    {
                    cartas.add(c);
                    adaptadorCartas.setCartas(cartas);
                    adaptadorCartas.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Fallo al leer valores
                Log.i( "Fallo", String.valueOf(error.toException()));
            }
        });
    }

    private void filtroCarta(String filtroColorSeleccionado)
    {
        adaptadorCartas = new ListaCartasAdapter(this,cartas);
        rv_cartas.setAdapter(adaptadorCartas);
        myRefCartas = FirebaseDatabase.getInstance().getReference("Cartas");
        myRefCartas.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                adaptadorCartas.getCartas().clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Carta c = (Carta) dataSnapshot.getValue(Carta.class);
                    if (c.getColor().equalsIgnoreCase(filtroColorSeleccionado))
                    {
                        cartas.add(c);
                        adaptadorCartas.setCartas(cartas);
                        adaptadorCartas.notifyDataSetChanged();
                    }
                    else if (c.getTipoCarta().equalsIgnoreCase(filtroTipoSeleccionado))
                    {
                        cartas.add(c);
                        adaptadorCartas.setCartas(cartas);
                        adaptadorCartas.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Fallo al leer valores
                Log.i( "Fallo", String.valueOf(error.toException()));
            }
        });
    }

}