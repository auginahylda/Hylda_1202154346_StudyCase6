package com.example.android.hylda_1202154346_studycase6;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by hp on 01/04/2018.
 */

public class PhotoPost extends AppCompatActivity {

    //deklarasi variable yang akan digunakan
    EditText editTitle, editCaption;
    ImageView gambar;
    Button pilihgambar;
    ProgressDialog pd;

    String currentuser;
    StorageReference nStorage;
    DatabaseReference nDatabaseRef;
    Uri nUri;
    private final int SELECT_PICTURES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postphoto);
        //mengenali edit text, image view dan button pada layout
        editTitle = (EditText) findViewById(R.id.editTitle);
        editCaption = (EditText) findViewById(R.id.editCaption);
        gambar = (ImageView) findViewById(R.id.ambilgambar);
        pilihgambar = (Button) findViewById(R.id.butonambilgambar);
        //untuk progress dialog
        pd = new ProgressDialog(this);
        //firebase authentification
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        nStorage = FirebaseStorage.getInstance().getReference();
        nDatabaseRef = FirebaseDatabase.getInstance().getReference().child("post");

    }

    //method untuk menyimpan post foto
    public void savepost(View view) {
        //menampilkan dialog
        pd.setTitle("Uploading Image");
        pd.setMessage("Please wait, the image is being uploaded...");
        pd.show();

        StorageReference imagename = nStorage.child(editTitle.getText().toString());

        //Mendapatkan gambar dari imageview untuk mengupload gambar
        gambar.setDrawingCacheEnabled(true);
        gambar.buildDrawingCache();
        Bitmap nBitmap2 = gambar.getDrawingCache();
        ByteArrayOutputStream nOutStream = new ByteArrayOutputStream();
        nBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, nOutStream);
        byte[] nByte = nOutStream.toByteArray();
        UploadTask nTask = imagename.putBytes(nByte);

        //mengupload gambar ke firebasestorage
        nTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //ketika berhasil post gambar
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String gmbr = taskSnapshot.getDownloadUrl().toString();
                db_post usr = new db_post(gmbr, editTitle.getText().toString(), editCaption.getText().toString(), currentuser);

                //Menyimpan pada database
                nDatabaseRef.push().setValue(usr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    //ketika berhasil menyimpan gambar
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PhotoPost.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PhotoPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                //menutup dialog
                pd.dismiss();
            }
            //ketika gagal upload gammbar
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Storage Error :", e.getMessage());
                Toast.makeText(PhotoPost.this, "Cannot upload", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    //untuk mengambil gambar dari galeri
    public void ambilgambar(View view) {
        Intent choosegambar = new Intent(Intent.ACTION_PICK);
        choosegambar.setType("image/*");
        //untuk memulai memilih foto dan mendapakan gambarnya
        startActivityForResult(choosegambar,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //ketika pengguna memilih foto
        if (resultCode == RESULT_OK) {
            if (requestCode== SELECT_PICTURES) {
                //mendapatkan data
                nUri = data.getData();
                try {
                    //merubah inputan menjadi stream
                    InputStream nStream = getContentResolver().openInputStream(nUri);
                    Bitmap nBitmap = BitmapFactory.decodeStream(nStream);
                    //dan menempatkan pada image view
                    gambar.setImageBitmap(nBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to load the image!", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            //ketika pengguna tidak memilih foto
            Toast.makeText(this, "Picture is not selected yet", Toast.LENGTH_SHORT).show();
        }
    }
}
