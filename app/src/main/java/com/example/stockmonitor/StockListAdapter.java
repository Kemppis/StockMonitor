package com.example.stockmonitor;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StockListAdapter extends ArrayAdapter<Stock> {
    public StockListAdapter(Context context, ArrayList<Stock> stocks) {
        super(context,0, stocks);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stock stock = getItem(position);

        if (convertView == null) {
            int layoutId = 0;
            layoutId = R.layout.list_item_stock;
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }
        TextView textViewCompanyName = convertView.findViewById(R.id.textViewCompanyName);
        textViewCompanyName.setText(stock.getCompanyName());

        TextView textViewPrice = convertView.findViewById(R.id.textViewStockPrice);
        textViewPrice.setText("" + stock.getPrice() + " USD");
        return convertView;
    }
}
