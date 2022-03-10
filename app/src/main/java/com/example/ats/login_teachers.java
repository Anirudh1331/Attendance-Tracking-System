package com.example.ats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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


public class login_teachers extends AppCompatActivity  {
    EditText et1,et2;
    Button b1,b2,b3;
    String sub;
    AlertDialog.Builder builder,build,bui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_teachers);
        et1=findViewById(R.id.email_teacher);
        et2=findViewById(R.id.password_teacher);
        b1=findViewById(R.id.button_login);
        b2=findViewById(R.id.forget_login1);
        b3=findViewById(R.id.signup_tea);
        builder = new AlertDialog.Builder(this);
        build=new AlertDialog.Builder(this);
        bui=new AlertDialog.Builder(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("teachers");
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
                bui.setMessage("Reset Previous Details").setTitle("Reset").setCancelable(false)
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference myRef1 = database.getReference().child("students");

                                // Read from the database
                                myRef1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                            if(dataSnapshot1.getValue(Student.class).getSubject().equals(sub)){
                                                Student student =dataSnapshot1.getValue(Student.class);
                                                String id=dataSnapshot1.getValue(Student.class).getId();
//                                    Log.d("key",id);
                                                myRef1.child(id).setValue(new Student(student.getId(),student.getName(),student.getEmail(),student.getPass(),student.getPno(),student.getSubject(),"0","0","false"));

                                            }
                                        }
                                        dialog.cancel();
                                        Intent in=new Intent(getApplicationContext(),att_1st.class);
                                        startActivity(in);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        Toast.makeText(login_teachers.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        })
                        .setNegativeButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent in=new Intent(getApplicationContext(),att_1st.class);
                                startActivity(in);
                            }
                        });
                AlertDialog alert=builder.create();
                AlertDialog alert2=build.create();
                AlertDialog alert3=bui.create();
                alert.show();

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    int abc=1;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if((dataSnapshot1.getValue(Teacher.class).getEmail().toString().equals(et1.getText().toString()) || (dataSnapshot1.getValue(Teacher.class).getPno().toString().equals(et1.getText().toString()))) && dataSnapshot1.getValue(Teacher.class).getPass().toString().equals(""+et2.getText())){
                                Toast.makeText(login_teachers.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                sub=dataSnapshot1.getValue(Teacher.class).getSubject();
                                editor.putString("Course_Name",sub);
                                editor.commit();
                                et1.setText("");
                                et2.setText("");
                                alert.cancel();
                                alert3.show();
                                abc=0;
                            }
                        }
                        if(abc==1) {
                            alert.cancel();
                            alert2.show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                            alert.cancel();
                        Toast.makeText(login_teachers.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),verify_teacher.class);
                startActivity(in);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent In=new Intent(login_teachers.this,signup_teachers.class);
                startActivity(In);
            }
        });

    }
}