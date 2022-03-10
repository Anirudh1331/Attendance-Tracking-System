package com.example.ats;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ats.commonClasses.Courses;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_class_std extends RecyclerView.Adapter<adapter_class_std.CoursesViewHolder> {
    @NonNull
    private final Context mctx;
    private List<Courses> courseList;

    public adapter_class_std(@NonNull Context mctx, List<Courses> courseList) {
        this.mctx = mctx;
        this.courseList = courseList;
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view=inflater.inflate(R.layout.card_view,null);
        return new CoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        Courses course=courseList.get(position);
        holder.course_name.setText(course.getCourse_name());
        holder.teacher_name.setText(course.getTeacher_name());
        Picasso.with(mctx).load(course.getImage_uri()).into(holder.img);
//        holder.img.setImageResource(R.drawable.student);

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder {
        TextView course_name,teacher_name;
        ImageView img;
        CardView cv;
        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            course_name=itemView.findViewById(R.id.textViewTitle);
            teacher_name=itemView.findViewById(R.id.textViewShortDesc);
            img=itemView.findViewById(R.id.imageView);
            cv=itemView.findViewById(R.id.click_class);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(mctx,StudentLocation.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    mctx.startActivity(in);

                }
            });
        }
    }
}