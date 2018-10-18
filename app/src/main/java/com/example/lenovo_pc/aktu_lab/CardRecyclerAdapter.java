package com.example.lenovo_pc.aktu_lab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder> {
    ArrayList<CardClass> arrayList=new ArrayList<>();
    Context mContext;

    public CardRecyclerAdapter(ArrayList<CardClass> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @Override
    public CardRecyclerAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.card_recycler,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.college_name.setText((arrayList.get(position).getCollage_name()));
        holder.price_tag.setText(arrayList.get(position).getPrice_tag());
        Picasso.with(mContext)
                .load(arrayList.get(position).getCardimage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)                    //later change it
                 .into(holder.collegeimage);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView college_name;
        TextView price_tag;
        ImageView collegeimage;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            college_name=(TextView)itemView.findViewById(R.id.collegename);
            price_tag=(TextView)itemView.findViewById(R.id.pricetag);
            collegeimage=(ImageView)itemView.findViewById(R.id.collegeimage);
        }


    }

}
