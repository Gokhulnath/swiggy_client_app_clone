package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHolder> {


    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    List<OrderItemListModel> itemList;
    Context context;

    public OrderHistoryAdapter(List<OrderItemListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_row, parent, false);
        OrderHolder orderHolder = new OrderHolder(v);
        return orderHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {

        final OrderItemListModel orderItemListModel = itemList.get(position);

        holder.hotelNameTV.setText(orderItemListModel.getOrderModel().getShopModel().getName());
        holder.hotel_price.setText(String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        holder.hotel_status.setText(String.valueOf(orderItemListModel.getOrderModel().getOrderStatus()));
        holder.order_date.setText(dateFormat.format(orderItemListModel.getOrderModel().getDate()));
        String item_name, item, Quantity, temp_item;
        item = "";
      //  Toast.makeText(context, getItemCount(), Toast.LENGTH_SHORT).show();
        List<OrderItemModel> orderItemList= orderItemListModel.getOrderItemsList();
        for (int i = 0; i < orderItemList.size(); i++) {
            Quantity = orderItemList.get(i).getQuantity().toString();
            item_name = orderItemList.get(i).getItemModel().getName();
            temp_item = item_name + "x" + Quantity + " ,";
            item = item.concat(temp_item);
        }
        holder.order_items.setText(item);
        //TODO run a loop and print with quantity
        holder.order_yourrating.setText(String.valueOf(orderItemListModel.getOrderModel().getRating()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        TextView hotelNameTV;
        TextView hotel_price;
        TextView hotel_status;
        TextView order_items;
        TextView order_date;
        TextView order_yourrating;


//TODO camelcase all names and rename xml variables, remove empty line
        //TODO ratefoodBT based on if statement
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTV = itemView.findViewById(R.id.hotelNameTV);
            hotel_price = itemView.findViewById(R.id.hotelPriceTV);
            hotel_status = itemView.findViewById(R.id.hotelStatusTV);
            order_items = itemView.findViewById(R.id.orderItemsTV);
            order_date = itemView.findViewById(R.id.orderDateTV);
            order_yourrating = itemView.findViewById(R.id.orderRatingTV);
        }
    }
}