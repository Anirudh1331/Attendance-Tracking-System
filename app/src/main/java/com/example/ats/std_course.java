package com.example.ats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ats.commonClasses.Courses;
import com.example.ats.commonClasses.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class std_course extends AppCompatActivity {
    List<Courses> courseList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_course);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseList=new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("bTech");
        SharedPreferences sp=getSharedPreferences("mysp",MODE_PRIVATE);
        String name=sp.getString("name","NA");
        setTitle("Welcome "+name+"!!");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String s=dataSnapshot1.getValue(Courses.class).getCourse_name();
                    String s1=dataSnapshot1.getValue(Courses.class).getTeacher_name();
                    String s2=dataSnapshot1.getValue(Courses.class).getImage_uri();
                    String s3=dataSnapshot1.getValue(Courses.class).getTitle();
//                    Toast.makeText(std_course.this, ""+s+" "+s1+" "+s2, Toast.LENGTH_SHORT).show();
                    Courses course=new Courses(s,s1,s2,s3);

                    courseList.add(course);
//                    Log.d("akshaykumar",s+"--"+s1+"--"+s2);
                }
//                Toast.makeText(this, ""+courseList.size(), Toast.LENGTH_SHORT).show();
                adapter_class_std adapter=new adapter_class_std(getApplicationContext(),courseList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

}