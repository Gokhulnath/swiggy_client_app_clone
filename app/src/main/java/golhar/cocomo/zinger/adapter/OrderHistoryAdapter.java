package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.hotelPriceTV.setText(String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        holder.hotelStatusTV.setText(String.valueOf(orderItemListModel.getOrderModel().getOrderStatus()));
        holder.orderDateTV.setText(dateFormat.format(orderItemListModel.getOrderModel().getDate()));
        String itemName, item, Quantity, tempItem;
        item = "";
        List<OrderItemModel> orderItemList = orderItemListModel.getOrderItemsList();
        for (int i = 0; i < orderItemList.size(); i++) {
            Quantity = orderItemList.get(i).getQuantity().toString();
            itemName = orderItemList.get(i).getItemModel().getName();
            tempItem = itemName + "x" + Quantity + " ,";
            item = item.concat(tempItem);
        }
        holder.orderItemTV.setText(item);
        holder.orderRatingTV.setText(String.valueOf(orderItemListModel.getOrderModel().getRating()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        TextView hotelNameTV;
        TextView hotelPriceTV;
        TextView hotelStatusTV;
        TextView orderItemTV;
        TextView orderDateTV;
        TextView orderRatingTV;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTV = itemView.findViewById(R.id.hotelNameTV);
            hotelPriceTV = itemView.findViewById(R.id.hotelPriceTV);
            hotelStatusTV = itemView.findViewById(R.id.hotelStatusTV);
            orderItemTV = itemView.findViewById(R.id.orderItemsTV);
            orderDateTV = itemView.findViewById(R.id.orderDateTV);
            orderRatingTV = itemView.findViewById(R.id.orderRatingTV);

            Button rateBT = itemView.findViewById(R.id.rateBT);
            rateBT.setVisibility(View.VISIBLE);
            if (String.valueOf(orderRatingTV).equals("0.0")) {
                rateBT.setVisibility(View.GONE);
            } else {
                rateBT.setVisibility(View.VISIBLE);
            }
        }
    }
}