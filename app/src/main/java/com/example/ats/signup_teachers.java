package com.example.ats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ats.commonClasses.Teacher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ats.R.id.signup_t_sub;

public class signup_teachers extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;
    Button b;
    TextView tv;
    String sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_teachers);
        et1=findViewById(R.id.signup_teachers_name);
        et2=findViewById(R.id.email_signup_teachers);
        et3=findViewById(R.id.signup_t_pass);
        et4=findViewById(R.id.signup_t_cnpass);
        et5=findViewById(R.id.pno_signup_teachers);
        b=findViewById(R.id.signup_t_reg);
        tv=findViewById(signup_t_sub);
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
                PopupMenu p=new PopupMenu(signup_teachers.this,tv);
                p.getMenuInflater().inflate(R.menu.sub_regd,p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        sub= (String) item.getTitle();
                        tv.setText(sub);
                        item.setChecked(true);
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
                        DatabaseReference myRef = database.getReference("teachers");
                        String id = myRef.push().getKey();
                        Teacher teacher = new Teacher(id, name, email, pass,pno,sub);
                        myRef.child(id).setValue(teacher);
                        Toast.makeText(signup_teachers.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                        Intent in = new Intent(getApplicationContext(), login_teachers.class);
//                        startActivity(in);
                        finish();
                    }
                    else{
                        Toast.makeText(signup_teachers.this, "Fields Can't be left empty", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(signup_teachers.this, "Password Mismatched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}