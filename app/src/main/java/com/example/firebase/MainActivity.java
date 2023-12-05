package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
  Button btnAdd,btnUpdate,btnDelete,btnGetList;
  EditText edId,edName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         btnAdd = findViewById(R.id.btnAdd);
         btnUpdate = findViewById(R.id.btnUpdate);
         btnDelete = findViewById(R.id.btnDelete);
         btnGetList = findViewById(R.id.btnGetList);
         edId = findViewById(R.id.edtID);
         edName = findViewById(R.id.edtName);
         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                 DatabaseReference myRef = database.getReference("cars");
                 String id = edId.getText().toString();
                 String name = edName.getText().toString();
                 Car car = new Car(Integer.parseInt(id),name);
                 int idRandom = new Random().nextInt(100);
                 myRef.child(""+ idRandom)
                         .setValue(car, ((error, ref) -> {
                             Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                         }));
             }
         });
         btnUpdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                 DatabaseReference myRef = database.getReference("cars");
                 Car car = new Car(4, "Rolroy");
                 int idRandom = 64;
                 myRef.child(""+idRandom)
                         .setValue(car , ((error, ref) -> {
                             Toast.makeText(MainActivity.this, "Update Thành công", Toast.LENGTH_SHORT).show();
                         }));
             }
         });
         btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                 DatabaseReference myRef = database.getReference("cars");
                 int idRandom = 64;
                 myRef.child(""+idRandom)
                         .removeValue(((error, ref) -> {
                             Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                         }));
             }
         });
         btnGetList.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseDatabase database = FirebaseDatabase.getInstance();
                 DatabaseReference myRef = database.getReference("cars");
                 myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         if(snapshot.exists()){
                             List<Car> list = new ArrayList<>();
                             for (DataSnapshot snap:snapshot.getChildren()){
                                 Car car = snap.getValue(Car.class);
                                 list.add(car);
                             }
                             Toast.makeText(MainActivity.this,""+ list.size(), Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }
         });
    }
}