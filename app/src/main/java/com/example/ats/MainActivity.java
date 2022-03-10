package com.example.ats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton ib1,ib2;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ib1=findViewById(R.id.teacher_login);
        ib2=findViewById(R.id.student_login);
        b1=findViewById(R.id.teacher_login2);
        b2=findViewById(R.id.student_login2);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginteacher();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginteacher();
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginstudent();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginstudent();
            }
        });
    }

    private void loginstudent() {
        Intent in=new Intent(MainActivity.this,login_students.class);
        startActivity(in);
    }
    private void loginteacher(){
        Intent in=new Intent(MainActivity.this,login_teachers.class);
        startActivity(in);
    }
}