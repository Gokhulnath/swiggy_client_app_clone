package golhar.cocomo.zinger.adapter;

import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.OrderItemListModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.DemoHolder> {


    DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyy hh:mm:ss");
    List<OrderItemListModel> itemList;
    Context context;

    public OrderHistoryAdapter(List<OrderItemListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public DemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_row, parent, false);
        DemoHolder demoHolder = new DemoHolder(v);
        return demoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DemoHolder holder, int position) {

        final OrderItemListModel orderItemListModel = itemList.get(position);

        holder.hotel_name.setText(orderItemListModel.getOrderModel().getShopModel().getName());
        holder.hotel_price.setText(String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        holder.hotel_status.setText(String.valueOf(orderItemListModel.getOrderModel().getOrderStatus()));
        holder.order_date.setText(dateFormat.format(orderItemListModel.getOrderModel().getDate()));
        holder.order_items.setText((CharSequence) orderItemListModel.getOrderItemsList());
        holder.order_yourrating.setText(String.valueOf(orderItemListModel.getOrderModel().getRating()));


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class DemoHolder extends RecyclerView.ViewHolder {

        TextView hotel_name;
        TextView hotel_price;
        TextView hotel_status;
        TextView order_items;
        TextView order_date;
        TextView order_yourrating;

        public DemoHolder(@NonNull View itemView) {
            super(itemView);
            hotel_name = itemView.findViewById(R.id.hotel_nameTV);
            hotel_price = itemView.findViewById(R.id.hotel_priceTV);
            hotel_status = itemView.findViewById(R.id.hotel_statusTV);
            order_items = itemView.findViewById(R.id.order_itemsTV);
            order_date = itemView.findViewById(R.id.order_dateTV);
            order_yourrating = itemView.findViewById(R.id.order_yourratingTV);

        }
    }
}