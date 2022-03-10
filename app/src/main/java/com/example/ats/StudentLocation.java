package com.example.ats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ats.commonClasses.Student;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class StudentLocation extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    int i=0;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private LatLng currentLocation;
    ImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_location);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        imgview = findViewById(R.id.imgview);
        ActivityCompat.requestPermissions(StudentLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        SharedPreferences sp = getSharedPreferences("mysp",MODE_PRIVATE);
        String lats=sp.getString("lats","0");
        String longs=sp.getString("longs","0");
        String std_id1=sp.getString("std_id","NA");
        Log.d("hellolats",lats);
        Log.d("hellolongss",longs);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] lats1 = new String[1];
                final String[] longs2 = new String[1];
                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            String student_id=dataSnapshot1.getValue(Student.class).getId();
                            if(student_id.equals(std_id1)){
                                lats1[0] =dataSnapshot1.getValue(Student.class).getLatitude();
                                longs2[0] =dataSnapshot1.getValue(Student.class).getLongitude();
//                                double dist = GetLocation(Double.parseDouble(lats.toString()), Double.parseDouble(longs.toString()), Double.parseDouble(lats1[0].toString()), Double.parseDouble(longs2[0].toString()));
                                double dist = distance(Double.parseDouble(lats.toString()), Double.parseDouble(lats1[0].toString()), Double.parseDouble(longs.toString()), Double.parseDouble(longs2[0].toString()));
//                                double dist = distance(53.32055555555556,53.31861111111111,-1.7297222222222221,-1.6997222222222223);
                                dist= Double.parseDouble(String.format("%.5f",dist*1000));
                                Toast.makeText(getApplicationContext(), ""+dist, Toast.LENGTH_SHORT).show();
                                if(dist<=10){
                                    Student student =dataSnapshot1.getValue(Student.class);
                                    myRef.child(student_id).setValue(new Student(student.getId(),student.getName(),student.getEmail(),student.getPass(),student.getPno(),student.getSubject(),student.getLatitude(),student.getLongitude(),"true"));
                                }
                            }
                        }
//                        Log.d("helloboi1",lats1[0]);
//                        Log.d("helloboi2",longs2[0]);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
//                Toast.makeText(StudentLocation.this, ""+lats+" "+longs, Toast.LENGTH_SHORT).show();

                // double dist = GetLocation(Double.parseDouble(lats.toString()), Double.parseDouble(longs.toString()), Double.parseDouble(lats1[0].toString()), Double.parseDouble(longs2[0].toString()));
//                Toast.makeText(getApplicationContext(), ""+dist, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
    private double GetLocation(double lat1, double lon1, double lat2, double lon2){
//        double R = 6371000f; // Radius of the earth in m
//        double dLat = (lat1 - lat2) * Math.PI / 180f;
//        double dLon = (lon1 - lon2) * Math.PI / 180f;
//        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                Math.cos(lat1 * Math.PI / 180f) * Math.cos(lat2 * Math.PI / 180f) *
//                        Math.sin(dLon/2) * Math.sin(dLon/2);
//        double c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//        double d = R * c;
//        return d;

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (currentLocation == null) {
            Toast.makeText(this, "Please Wait GPS is on Working", Toast.LENGTH_SHORT).show();
        } else {
            Geocoder gc = new Geocoder(StudentLocation.this);
            try {
                ArrayList<Address> al = (ArrayList) gc.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1);
                android.location.Address ad = al.get(0);
//                String str = ad.getSubLocality() + "," + ad.getLocality();
//                LatLng sydney = new LatLng(currentLocation.latitude, currentLocation.longitude);
//                Toast.makeText(getApplicationContext(), ""+currentLocation.latitude+":::"+currentLocation.longitude, Toast.LENGTH_SHORT).show();
//                Log.d("partytime",str);
                if(i==0){
                    SharedPreferences sp = getSharedPreferences("mysp",MODE_PRIVATE);
//                    Toast.makeText(getApplicationContext(), ""+sp.getString("std_id","NA"), Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("students");
                    String std_id=sp.getString("std_id","NA");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                if(i==0) {
                                    Student student = dataSnapshot1.getValue(Student.class);
                                    String id = dataSnapshot1.getValue(Student.class).getId();
                                    Log.d("key1", id);
                                    Log.d("key2", std_id);
                                    if (id.equals(std_id)) {
                                        myRef.child(id).setValue(new Student(student.getId(), student.getName(), student.getEmail(), student.getPass(), student.getPno(), student.getSubject(), "" + currentLocation.latitude, "" + currentLocation.longitude,student.getAts()));
                                        Toast.makeText(StudentLocation.this, "Database updated", Toast.LENGTH_SHORT).show();
//                                         imgview.setVisibility(View.VISIBLE);
                                        i++;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });


                    Toast.makeText(getApplicationContext(), "Location Added Succesfully", Toast.LENGTH_SHORT).show();
                }//Firebase Code i++
            } catch (IOException e) {
                Log.d("kyahumerror",e.getLocalizedMessage());
                Toast.makeText(StudentLocation.this, "error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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