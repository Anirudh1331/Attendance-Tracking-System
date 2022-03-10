package com.example.ats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ats.commonClasses.Atendance;
import com.example.ats.commonClasses.Student;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class att_1st extends AppCompatActivity implements LocationListener{
    List<String> stdList;
    TextView tv;
    RecyclerView recyclerView;
    Button bt,bt2;
    ImageView img;
    ArrayList<Student> data;
    ArrayList<String> bug;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private LatLng currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att_1st);
        data= new ArrayList<>();
        bug= new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView2);
        bt=findViewById(R.id.start_taking_attendance);
        bt2=findViewById(R.id.save_attendance);
        img=findViewById(R.id.imgview);
        tv=findViewById(R.id.course_title);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stdList=new ArrayList<>();
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        String Course_Name=sp.getString("Course_Name","NA");
        tv.setText("Welcome To "+Course_Name+" Course");
        Log.d("coursename",Course_Name);
        ActivityCompat.requestPermissions(att_1st.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Sorry GPS is Off", Toast.LENGTH_SHORT).show();
        } else {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120000, 0, (LocationListener) this);
            Toast.makeText(this, "GPS is working", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "--><--"+lm, Toast.LENGTH_SHORT).show();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("students");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if ((dataSnapshot1.getValue(Student.class).getSubject().toString().equals(Course_Name))) {
                        stdList.add(dataSnapshot1.getValue(Student.class).getName());
                        if(dataSnapshot1.getValue(Student.class).getAts().equals("true")){
                            String s1= dataSnapshot1.getValue(Student.class).getId();
                            String s2= dataSnapshot1.getValue(Student.class).getName();
                            String s3= dataSnapshot1.getValue(Student.class).getEmail();
                            String s4= dataSnapshot1.getValue(Student.class).getPass();
                            String s5= dataSnapshot1.getValue(Student.class).getPno();
                            String s6= dataSnapshot1.getValue(Student.class).getSubject();
                            String s7= dataSnapshot1.getValue(Student.class).getLatitude();
                            String s8= dataSnapshot1.getValue(Student.class).getLongitude();
                            String s9= dataSnapshot1.getValue(Student.class).getAts();
                            Student s= new Student(s1,s2,s3,s4,s5,s6,s7,s8,s9);
                            bug.add(s2);
                            data.add(s);

                        }
                    }
                }
                Toast.makeText(att_1st.this, ""+bug.size(), Toast.LENGTH_SHORT).show();
                adapter_std_list adapter=new adapter_std_list(att_1st.this,stdList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                img.setVisibility(View.VISIBLE);
//                img.setEnabled(true);
                Toast.makeText(att_1st.this, "Attendance Started", Toast.LENGTH_SHORT).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                DatabaseReference myRef1 = database.getReference("Attendance");
                String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));
                myRef1.setValue(date);
                myRef1= myRef1.child(date);
                myRef1.setValue(Course_Name);
                myRef1= myRef1.child(Course_Name);
                for(int i =0; i<data.size();i++){
                    Student s= data.get(i);
                    String id = myRef1.push().getKey();
                    Atendance at= new Atendance(date,s.getName(),s.getEmail(),id);
                    myRef1.child(id).setValue(at);



                }
                Toast.makeText(att_1st.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        if (currentLocation == null) {
            Toast.makeText(this, "Please Wait GPS is on Working", Toast.LENGTH_SHORT).show();
        } else {
            Geocoder gc = new Geocoder(att_1st.this);
            try {
                ArrayList<Address> al = (ArrayList) gc.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1);
                android.location.Address ad = al.get(0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("lats",""+currentLocation.latitude);
                editor.putString("longs",""+currentLocation.longitude);
                editor.commit();
//                Toast.makeText(getApplicationContext(), ""+currentLocation.latitude+":::"+currentLocation.longitude, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.d("kyahumerror",e.getLocalizedMessage());
                Toast.makeText(att_1st.this, "error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}