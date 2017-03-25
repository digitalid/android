package com.thinkitlimited.digitalid.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.thinkitlimited.digitalid.R;
import com.thinkitlimited.digitalid.classes.Cards;

import java.util.ArrayList;

public class AllCardsAdapter extends RecyclerView.Adapter<AllCardsAdapter.ViewHolder> {

    ArrayList<Cards> data= new ArrayList<>();
    Context mcontext;
    int lastPosition=0;
    public AllCardsAdapter(ArrayList<Cards> data, Context contxt){
        this.data = data;
        this.mcontext= contxt;

    }
    public void remove(int pos){
        data.remove(pos);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vh = null;
        try {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_list_item, parent, false);

            vh = new ViewHolder(v);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt_id.setText(data.get(position).getId()+"");
        holder.txt_name.setText(data.get(position).getName());
        holder.txt_expiry.setText(data.get(position).getDate());
        holder.txt_nationality.setText(data.get(position).getNationality());

        holder.rel_top.setTag(data.get(position));
        holder.rel_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cards obj= (Cards) v.getTag();
                ArrayList<Cards> array= new ArrayList<Cards>();
                array.add(obj);

                //id=obj.getId();
                Toast.makeText(mcontext, "Card ID: "+obj.getId(),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txt_id;
        public TextView txt_name;
        public TextView txt_nationality;
        public TextView txt_expiry;
        public TextView txt_title;

        CardView rel_top;
        //RelativeLayout background;
        public ViewHolder(View v) {
            super(v);
            txt_id = (TextView) v.findViewById(R.id.inputIDNumber);
            txt_name = (TextView) v.findViewById(R.id.inputIDName);
            txt_nationality = (TextView) v.findViewById(R.id.inputNationality);
            txt_expiry = (TextView) v.findViewById(R.id.inputExpiryDate);
            rel_top = (CardView) v.findViewById(R.id.cv);

        }
    }

}
