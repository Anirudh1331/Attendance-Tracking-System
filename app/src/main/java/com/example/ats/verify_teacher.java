package com.example.ats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ats.commonClasses.Student;
import com.example.ats.commonClasses.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class verify_teacher extends AppCompatActivity {
    EditText et1,et2,et3,et4;
    Button b1,b2,b3 ;
    SmsManager sm;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_teacher);
        et1=findViewById(R.id.verify_emailst);
        et2=findViewById(R.id.otp_text);
        et3=findViewById(R.id.newt_password1);
        et4=findViewById(R.id.newt_password2);
        b1=findViewById(R.id.otp_send);
        b2=findViewById(R.id.verification_t);
        b3=findViewById(R.id.savet_newpass);
        ActivityCompat.requestPermissions(verify_teacher.this, new String[]{Manifest.permission.SEND_SMS}, 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("teachers");
        sm = SmsManager.getDefault();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = gen();
                num=number;
//                sendMessage(et1.getText().toString(),"Your OTP is");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            Log.d("kyaname",dataSnapshot1.getValue(Student.class).getPno());
                            if (!dataSnapshot1.getValue(Teacher.class).getPno().toString().equals(""+et1.getText().toString()) ) {
                                Toast.makeText(verify_teacher.this, "Invalid Phone number!!!", Toast.LENGTH_SHORT).show();
                            }else{
                                sendMessage(et1.getText().toString(),"Your OTP is  "+number);
                                et2.setVisibility(View.VISIBLE);
                                b2.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        ;
                    }
                });

            }

            public void sendMessage(String phoneNo, String msg) {
//                Log.d("kkkk",phoneNo);

                try {

//                        SmsManager smsManager = SmsManager.getDefault();
                    sm.sendTextMessage(phoneNo, null, msg, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();

                }
                catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=et2.getText().toString();
                int otp=Integer.parseInt(s);
                if(otp!=num){
                    Toast.makeText(verify_teacher.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    et3.setVisibility(View.VISIBLE);
                    et4.setVisibility(View.VISIBLE);
                    Toast.makeText(verify_teacher.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                    et3.setVisibility(View.VISIBLE);
                    et4.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            Log.d("kyaname",dataSnapshot1.getValue(Student.class).getPno());
                            if (!dataSnapshot1.getValue(Teacher.class).getPno().toString().equals(""+et1.getText().toString()) ) {
                                Toast.makeText(verify_teacher.this, "Invalid Email!!!", Toast.LENGTH_SHORT).show();
                            }else{
                                if(et3.getText().toString().equals(et4.getText().toString())) {
                                    Teacher teacher =dataSnapshot1.getValue(Teacher.class);
                                    String id=dataSnapshot1.getValue(Teacher.class).getId();
//                                    Log.d("key",id);
                                    myRef.child(id).setValue(new Teacher(teacher.getId(),teacher.getName(),teacher.getEmail(),et3.getText().toString(),teacher.getPno(),teacher.getSubject()));
                                    Toast.makeText(verify_teacher.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                    Toast.makeText(verify_teacher.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        ;
                    }
                });
            }
        });
    }
    private int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
}