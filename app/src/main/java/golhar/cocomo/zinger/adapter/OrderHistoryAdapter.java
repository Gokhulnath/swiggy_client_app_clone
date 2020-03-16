package golhar.cocomo.zinger.adapter;


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



public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.DemoHolder>{



   List<OrderItemListModel> itemList;
    Context context;

    public OrderHistoryAdapter(List<OrderItemListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context=context;
    }

    @NonNull
    @Override
    public DemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_row,parent,false);
        DemoHolder demoHolder=new DemoHolder(v);
        return demoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DemoHolder holder, int position) {

        final OrderItemListModel orderItemListModel = itemList.get(position);

        holder.hotel_name.setText(orderItemListModel.getOrderModel().getShopModel().getName());
//        holder.hotel_price.setText(orderItemListModel.getHotel_price());
//        holder.hotel_status.setText(orderItemListModel.getHotel_status());
//        holder.order_date.setText(orderItemListModel.getOrder_date());
//        holder.order_items.setText(orderItemListModel.getOrder_items());
//        holder.order_yourrating.setText(orderItemListModel.getYour_rating());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class DemoHolder extends RecyclerView.ViewHolder{

        TextView hotel_name;
        TextView hotel_area ;
        TextView hotel_price ;
        TextView hotel_status;
        TextView order_items ;
        TextView order_date ;
        TextView order_yourrating;

        public DemoHolder(@NonNull View itemView){
            super(itemView);
            hotel_name = itemView.findViewById(R.id.hotel_name);
            hotel_price = itemView.findViewById(R.id.hotel_price);
            hotel_status = itemView.findViewById(R.id.hotel_status);
            order_items = itemView.findViewById(R.id.order_items);
            order_date = itemView.findViewById(R.id.order_date);
            order_yourrating = itemView.findViewById(R.id.order_yourrating);

        }
    }
}