package com.example.ats;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.ats.commonClasses.Student;
import com.example.ats.commonClasses.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class login_students extends AppCompatActivity  {
    EditText et1,et2;
    Button b1,b2,b3;
    AlertDialog.Builder builder,build;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_students);
        et1 = findViewById(R.id.email_student);
        et2 = findViewById(R.id.password_student);
        b1 = findViewById(R.id.button2_login);
        b2 = findViewById(R.id.forget_login2);
        b3 = findViewById(R.id.signup_stu);
        builder = new AlertDialog.Builder(this);
        build=new AlertDialog.Builder(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("students");
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent In = new Intent(login_students.this, signup_student.class);
                startActivity(In);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Please Wait").setTitle("Login").setCancelable(true);
                build.setMessage("Invalid Credentials").setTitle("Invalid").setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et2.setText("");
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(in);
                            }
                        });
                AlertDialog alert=builder.create();
                AlertDialog alert2=build.create();
                alert.show();
                myRef.addValueEventListener(new ValueEventListener() {
                    int abc=1;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if ((dataSnapshot1.getValue(Student.class).getEmail().toString().equals(et1.getText().toString()) || (dataSnapshot1.getValue(Student.class).getPno().toString().equals(et1.getText().toString()))) && dataSnapshot1.getValue(Student.class).getPass().toString().equals("" + et2.getText())) {
                                SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("name",dataSnapshot1.getValue(Student.class).getName());
                                editor.putString("std_id",dataSnapshot1.getValue(Student.class).getId());
                                editor.commit();
                                et1.setText("");
                                et2.setText("");
                                alert.cancel();
                                abc=0;
                                Intent in=new Intent(getApplicationContext(),std_course.class);
                                Toast.makeText(login_students.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(in);
                            }
                        }
                        if(abc==1){
                            alert.cancel();
                            alert2.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        alert.cancel();
                        Toast.makeText(login_students.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),verify_student.class);
                startActivity(in);

            }
        });
    }
}