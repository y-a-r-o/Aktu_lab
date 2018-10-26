package com.example.lenovo_pc.aktu_lab;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder>  {
    ArrayList<CardClass> arrayList=new ArrayList<>();
    Context mContext;
    CardClass temp;
    String category_name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public CardRecyclerAdapter(ArrayList<CardClass> arrayList, Context mContext,String category_name) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.category_name = category_name;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.card_recycler,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.college_name.setText((arrayList.get(position).getCollege_name()));
        holder.price_tag.setText(arrayList.get(position).getPrice_tag());
        Picasso.with(mContext)
                .load(arrayList.get(position).getCardimage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)                    //later change it
                .into(holder.collegeimage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemclick(View v, int pos) {
//          write from here
                temp=arrayList.get(pos);
                int key = temp.getKey();

                if(category_name.compareTo("string")!=0) {
                    Intent intent = new Intent(mContext, LabDetailsActivity.class).putExtra("key", key).putExtra("category", category_name);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView college_name;
        TextView price_tag;
        ImageView collegeimage;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            college_name=(TextView)itemView.findViewById(R.id.collegename);
            price_tag=(TextView)itemView.findViewById(R.id.pricetag);
            collegeimage=(ImageView)itemView.findViewById(R.id.collegeimage);
            itemView.setOnClickListener(this);
        }
        public void onClick(View v) {
            this.itemClickListener.onItemclick(v,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }

    }

}
