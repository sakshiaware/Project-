package com.smc.smcsystem;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAdapterPost extends FirebaseRecyclerAdapter<ModelPost,AdminAdapterPost.myviewholder> {
    public AdminAdapterPost(@NonNull FirebaseRecyclerOptions<ModelPost> options) {
        super(options);
    }

    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ModelPost model)
    {
        holder.title.setText(model.getTitle());
        holder.des.setText(model.getDescription());
        holder.comment.setText(model.getPcomments());
        holder.status.setText("Status "+model.getStatus());
        Glide.with(holder.img.getContext()).load(model.getUimage()).into(holder.img);
       if (model.getStatus().equals("Completed"))
        {
            holder.mascom.setEnabled(false);
        }
        holder.mascom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Posts").child(model.getPtime()).child("status").setValue("Completed").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.status.setText("Status: Completed");
                    }
                });



            }
        });
    }

    @NonNull
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminpostrow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView title,des,comment,status;
        Button mascom;
        CardView card;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.apicturetv);
            title=(TextView)itemView.findViewById(R.id.atitletv);
            des=(TextView)itemView.findViewById(R.id.adestv);
            comment=(TextView)itemView.findViewById(R.id.addresstv);
            status=(TextView)itemView.findViewById(R.id.status);
            mascom= (Button) itemView.findViewById(R.id.btnmarkcom);


        }
    }
}

