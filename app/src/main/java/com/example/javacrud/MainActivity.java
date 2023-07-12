  package com.example.javacrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

  public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<UsersItem> usersItemsArrayList;
    UsersRecyclerAdapter adapter;

    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Objects.requireNonNull(getSupportActionBar()).hide();

       databaseReference = FirebaseDatabase.getInstance().getReference();

       recyclerView = findViewById(R.id.recyclerView);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       usersItemsArrayList = new ArrayList<>();

       buttonAdd = findViewById(R.id.buttonAdd);

       buttonAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);
           }
       });
       readData();
    }

      private void readData() {
                databaseReference.child("users").orderByChild("userName").addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        usersItemsArrayList.clear();
                        for (DataSnapshot dataSnapshot :  snapshot.getChildren()){
                            UsersItem users =dataSnapshot.getValue(UsersItem.class);
                            usersItemsArrayList.add(users);
                        }
                        adapter = new UsersRecyclerAdapter(MainActivity.this, usersItemsArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
      }

      public class ViewDialogAdd {
            public void showDialog(Context context){
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.activity_alert_dialog_add_new_user);

                EditText textName = dialog.findViewById(R.id.textName);
                EditText textEmail = dialog.findViewById(R.id.textEmail);
                EditText textCountry = dialog.findViewById(R.id.textCountry);

                Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
                Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

                buttonAdd.setText("ADD");
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         String id = "user" + new Date().getTime();
                         String name  = textName.getText().toString();
                         String email = textEmail.getText().toString();
                         String country = textCountry.getText().toString();

                         if(name.isEmpty() || email.isEmpty() || country.isEmpty()) {
                             Toast.makeText(context, "Please enter all data...", Toast.LENGTH_SHORT).show();
                         } else {
                             databaseReference.child("users").child(id).setValue(new UsersItem(id, name, email, country));
                             Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();
                             dialog.dismiss();
                         }
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        }
  }