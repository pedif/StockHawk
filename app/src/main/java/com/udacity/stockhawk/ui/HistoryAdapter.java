package com.udacity.stockhawk.ui;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.StockViewHolder> {

    private final DecimalFormat dollarFormat;
    private String[][] items;

    HistoryAdapter(String[][] items) {


        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        this.items = items;

    }


    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history, parent, false);

        return new StockViewHolder(item);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int position) {



        String description="";

        holder.date.setText(items[position][0]);
        description+= holder.date.getText();
        holder.price.setText(dollarFormat.format(Float.parseFloat(items[position][1])));
        description+= " "+holder.price.getText();

        holder.itemView.setContentDescription(description);

    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    interface StockAdapterOnClickHandler {
        void onClick(String symbol);
    }

    class StockViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.price)
        TextView price;


        StockViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
