package com.example.proyectofinaldam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_inicio_sesion extends AppCompatActivity
{
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText edt_emailUsuario;
    private EditText edt_claveUsuario;
    private EditText edt_busquedaUsuario;
    private TextView txt_tituloEmail;
    private TextView txt_tituloClave;
    private Button btn_CrearCarta;
    private Button btn_CerrarSesion;
    private Button btn_Mazos;
    private Button btn_IniciarSesion;
    private Button btn_RegistroUsuario;
    private ImageView img_usuario;

    // --------------------------------------------

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            String admin = String.valueOf(currentUser.getEmail());
            String [] textoEmail = admin.split("@");
            String Nombre = textoEmail[0];
            if(admin.equalsIgnoreCase("admin@gmail.com"))
            {
                btn_CrearCarta.setVisibility(View.VISIBLE);
                btn_Mazos.setVisibility(View.INVISIBLE);
            }
            btn_CerrarSesion.setVisibility(View.VISIBLE);
            btn_IniciarSesion.setVisibility(View.INVISIBLE);
            btn_RegistroUsuario.setVisibility(View.INVISIBLE);
            btn_Mazos.setVisibility(View.VISIBLE);
            txt_tituloEmail.setText("Esta es tu pagina personal " + Nombre.toUpperCase());
            txt_tituloClave.setVisibility(View.INVISIBLE);
            edt_emailUsuario.setVisibility(View.INVISIBLE);
            edt_claveUsuario.setVisibility(View.INVISIBLE);
            img_usuario.setVisibility(View.VISIBLE);

            // Toast.makeText(activity_inicio_sesion.this, admin, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // -----------------Enlace de los atributos-----------------------

        edt_emailUsuario = (EditText) findViewById(R.id.edt_correo);
        edt_claveUsuario = (EditText) findViewById(R.id.edt_clave);
        edt_busquedaUsuario = (EditText) findViewById(R.id.edt_busquedaUsuarios);
        btn_CrearCarta = (Button) findViewById(R.id.btn_Carta);
        btn_CerrarSesion = (Button) findViewById(R.id.btn_cerrarSesion);
        btn_Mazos = (Button) findViewById(R.id.btn_mazos);
        btn_IniciarSesion = (Button) findViewById(R.id.btn_Iniciar_sesion);
        btn_RegistroUsuario = (Button) findViewById(R.id.btn_registrarse);
        txt_tituloEmail = (TextView) findViewById(R.id.titulo_correo);
        txt_tituloClave = (TextView) findViewById(R.id.titulo_clave);
        img_usuario = (ImageView) findViewById(R.id.img_usuario);
        // --- Fire Base ---
        mAuth = FirebaseAuth.getInstance();
        // Pruebas firebase realTime
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
    }
    public void registrarUsuario(View view)
    {
        String email = String.valueOf(edt_emailUsuario.getText()).trim() + "@gmail.com";
        String clave = String.valueOf(edt_claveUsuario.getText()).trim();

        if(email.isEmpty())
        {
            edt_emailUsuario.setError("El email no puede estar vacio");
            return;
        }
        else if(clave.length()<5)
        {
            edt_claveUsuario.setError("La clave debe tener al menos 6 caracteres");
            return;
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, clave)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Registro completado, update UI with the signed-in user's information
                                Toast.makeText(activity_inicio_sesion.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity_inicio_sesion.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                // Si el logeo falla, mandas un mensaje al usuario
                                Toast.makeText(activity_inicio_sesion.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void loguearUsuario(View view)
    {
        String email = String.valueOf(edt_emailUsuario.getText() + "@gmail.com");
        String clave = String.valueOf(edt_claveUsuario.getText());

        if(email.isEmpty())
        {
            edt_emailUsuario.setError("Debes poner tu nombre");
            return;
        }
        else if(clave.length()<5)
        {
            edt_claveUsuario.setError("La clave no es correcta");
            return;
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, clave)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(activity_inicio_sesion.this, "Inicio de sesion correcto, bienvenido " + edt_emailUsuario.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(activity_inicio_sesion.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Si falla el inicio de sesion, avisar al usuario
                                Toast.makeText(activity_inicio_sesion.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void BuscarUsuario(View view)
    {
        String UsuarioABuscar = String.valueOf(edt_busquedaUsuario.getText());
    }
    public void VolverInicio(View view)
    {
        Intent intent = new Intent(activity_inicio_sesion.this, MainActivity.class);
        startActivity(intent);
    }

    public void CerrarSesion(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(activity_inicio_sesion.this, "Se ha cerrado la sesion correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity_inicio_sesion.this, MainActivity.class);
        startActivity(intent);
    }

    public void Ir_a_crearCarta(View view)
    {
        Intent intent = new Intent(activity_inicio_sesion.this, activity_registrarCarta.class);
        startActivity(intent);
    }

    public void Ir_a_wants(View view)
    {
        Intent intent = new Intent(activity_inicio_sesion.this, activity_wants.class);
        startActivity(intent);
    }
}