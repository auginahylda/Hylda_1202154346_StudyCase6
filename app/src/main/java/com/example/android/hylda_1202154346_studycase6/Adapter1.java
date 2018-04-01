package com.example.android.hylda_1202154346_studycase6;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.*;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {

    Context context;
    java.util.List<Database> list;

    //konstruktor dari adapterc
    public AdapterC(Context context, java.util.List<Database> list){
        this.context=context;
        this.list=list;
    }

    //return viewholder dari recyclerview
    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comments, parent, false));
    }

    //mengikat nilai dari list dengan view
    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Database crnt = list.get(position);
        //set textview commenter dengan nilai yang didapatatkan
        holder.commenter.setText(crnt.getCommenters());
        //set textview comments dengan nilai yang didapatkan
        holder.comments.setText(crnt.getComments());
    }


    //mendapatkan jumlah item pada recyclernya
    @Override
    public int getItemCount() {
        return list.size();
    }


    //class yang akan dipanggil dan dijalankan sebagai viewholder
    class CommentViewHolder extends RecyclerView.ViewHolder{
        //deklarasi variable yang digunakan
        TextView commenter, comments;
        public CommentViewHolder(View itemView){
            super(itemView);
            //mengakses id textview pada layout
            commenter = itemView.findViewById(R.id.commenter);
            comments = itemView.findViewById(R.id.comments);
        }
    }
}
