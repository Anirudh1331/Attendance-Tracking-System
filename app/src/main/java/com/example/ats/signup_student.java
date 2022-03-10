package com.example.ats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ats.commonClasses.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_student extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;
    TextView tv;
    Button b;
    String sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        et1=findViewById(R.id.signup_students_name);
        et2=findViewById(R.id.email_signup_students);
        et3=findViewById(R.id.signup_s_pass);
        et4=findViewById(R.id.signup_s_cnpass);
        et5=findViewById(R.id.pno_signup_students);
        tv=findViewById(R.id.signup_s_sub);
        b=findViewById(R.id.signup_s_reg);
        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et5.setVisibility(View.VISIBLE);
            }
        });
        et5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et2.setVisibility(View.VISIBLE);
            }
        });
        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et3.setVisibility(View.VISIBLE);
                et4.setVisibility(View.VISIBLE);
            }
        });
        et4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setVisibility(View.VISIBLE);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu p=new PopupMenu(signup_student.this,tv);
                p.getMenuInflater().inflate(R.menu.sub_regd,p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        sub= (String) item.getTitle();
                        item.setChecked(true);
                        tv.setText(sub);
                        return true;
                    }
                });
                p.show();
                b.setVisibility(View.VISIBLE);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et1.getText().toString();
                String email = et2.getText().toString();
                String pass = et3.getText().toString();
                String confirm_pass = et4.getText().toString();
                String pno=et5.getText().toString();
                if (pass.contentEquals(confirm_pass)) {
                    if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !confirm_pass.isEmpty() && !pno.isEmpty()) {
                        // Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("students");
                        String id = myRef.push().getKey();
                        Student student = new Student(id, name, email, pass,pno,sub,"0","0","false");
                        myRef.child(id).setValue(student);
                        Toast.makeText(signup_student.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                        Intent in = new Intent(getApplicationContext(), login_students.class);
//                        startActivity(in);
                        finish();
                    }
                    else{
                        Toast.makeText(signup_student.this, "Fields Can't be left empty", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(signup_student.this, "Password Mismatched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}