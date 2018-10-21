package com.example.lenovo_pc.aktu_lab;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    ArrayList<LabcatClass> arrayList;
    Context context;
    LabcatClass temp;

    public CatAdapter(ArrayList<LabcatClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public CatAdapter() {
        super();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.labcatcard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        Picasso.with(context)
                .load(arrayList.get(position).getBg())
                .placeholder(R.drawable.loading)
                .into(holder.bg);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemclick(View v, int pos) {
                temp = arrayList.get(pos);
                String title = temp.getTitle();

                Intent intent= new Intent(context,CardRecyclerActivity.class);
                intent.putExtra("key",title);
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView bg;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            bg = (ImageView)itemView.findViewById(R.id.bg);
            itemView.setOnClickListener(this);

        }
        public void onClick(View v){
            this.itemClickListener.onItemclick(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }

}
