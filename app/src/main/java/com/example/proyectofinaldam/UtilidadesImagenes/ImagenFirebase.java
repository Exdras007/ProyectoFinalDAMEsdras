package com.example.proyectofinaldam.UtilidadesImagenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.proyectofinaldam.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ImagenFirebase
{

    public static void subirFoto( String nombre_carpeta, String nombre, ImageView img_add_foto)
    {
        //  Transformo la imagen en BitMap
        img_add_foto.setDrawingCacheEnabled(true);
        img_add_foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img_add_foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // StorageReference imagesRef = storageRef.child("imagenes");
        StorageReference fotoRef = storageRef.child(nombre_carpeta+"/"+String.valueOf(nombre)+".png");
        UploadTask uploadTask = fotoRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception exception)
            {
                // Handle unsuccessful uploads
                Log.i("firebase1","la foto no se ha subido correctamente");

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.i("firebase1","la foto se ha subido correctamente");
                // ...
            }
        });
    }

    public static void descargarFoto(String nombre_carpeta, String nombre, ImageView imagendescargada)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(nombre_carpeta+"/"+nombre+".png");

        final long tam_foto = 10240 * 1024; // tamaño máximo de la descarga de la imagen, si es mayor la descarga falla.
        islandRef.getBytes(tam_foto).addOnSuccessListener(new OnSuccessListener<byte[]>()
        {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.i("firebase1","la foto se ha descargado correctamente");
                Bitmap imagenbitmapdescargada = bytes_to_bitmap(bytes, 380, 195);
                imagendescargada.setImageBitmap(imagenbitmapdescargada);
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception exception)
            {
                // Handle any errors
                byte[] bytes = null;
                Log.i("firebase1","la foto no se pudo descargar");
                int errorCode = ((StorageException) exception).getErrorCode();
                String errorMessage = exception.getMessage();
                Log.i("firebase1",errorMessage);
                Log.i("firebase1","error code" + String.valueOf(errorCode));
                imagendescargada.setImageResource(R.drawable.magiccard);
            }
        });
    }

    public static Bitmap bytes_to_bitmap(byte[] b, int width, int height){
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(width, height,config);
        try{
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        } catch (Exception e){
        }
        return bitmap;
    }
}
