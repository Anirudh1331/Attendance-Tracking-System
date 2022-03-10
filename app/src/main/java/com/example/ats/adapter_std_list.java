package com.example.ats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ats.commonClasses.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adapter_std_list extends RecyclerView.Adapter<adapter_std_list.ListViewHolder> {
    @NonNull
    private final Context mctx;
    private int count=1;
    private int count1=1;
    private List<String> stdList;
    public ArrayList<String> finalarray;
//, ArrayList<String> lis
    public adapter_std_list(@NonNull Context mctx, List<String> stdList) {
        this.mctx = mctx;
        this.stdList = stdList;
//        this.finalarray=lis;
    }

    @Override
    public adapter_std_list.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view=inflater.inflate(R.layout.std_list,null);
        return new ListViewHolder(view);
    }

//    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull adapter_std_list.ListViewHolder holder, int position) {
        String str=stdList.get(position);
        holder.tv1.setText(position+1+") ");
        count++;
        count1=1;
        holder.tv2.setText(str);
//        if(finalarray.contains(str)){
//            holder.ct.setChecked(true);
//        holder.tv3.setText("Present");
//        holder.tv3.setTextColor(R.color.black);
//        }
//        else{
//            holder.ct.setChecked(false);
//            holder.tv3.setText("Absent");
//        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("students");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(count1==1 && dataSnapshot1.getValue(Student.class).getAts().equals("true") && dataSnapshot1.getValue(Student.class).getName().equals(str)){
//                        holder.ct.setChecked(true);
//                        holder.tv3.setText("Present");
//                        holder.tv3.setTextColor(R.color.black);
                        count1=0;
//                        break;
                    }
                }
                if(count1==0){
                    holder.ct.setChecked(true);
                    holder.tv3.setText("Present");
                    holder.tv3.setTextColor(R.color.black);
                    count1=1;
                }else{
            holder.ct.setChecked(false);
            holder.tv3.setText("Absent");
            holder.tv3.setTextColor(R.color.red);
        }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(mctx, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stdList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3;
        CheckBox ct;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.serial_number);
            tv2=itemView.findViewById(R.id.name_std);
            tv3=itemView.findViewById(R.id.Pres_abs);
            ct=itemView.findViewById(R.id.std_check);
        }
    }
}