package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHolder> {

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy    hh:mm:ss A");
    List<OrderItemListModel> itemList;
    Context context, activityContext;


    public OrderHistoryAdapter(List<OrderItemListModel> itemList, Context context, Context activityContext) {
        this.itemList = itemList;
        this.context = context;
        this.activityContext= activityContext;
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
        holder.hotelPriceTV.setText("₹"+ String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        holder.hotelStatusTV.setText(String.valueOf(orderItemListModel.getOrderModel().getOrderStatus()));
        holder.orderDateTV.setText(dateFormat.format(orderItemListModel.getOrderModel().getDate()));
        String itemName, item, Quantity, tempItem;
        item = "";
        List<OrderItemModel> orderItemList = orderItemListModel.getOrderItemsList();
        for (int i = 0; i < orderItemList.size(); i++) {
            Quantity = orderItemList.get(i).getQuantity().toString();
            itemName = orderItemList.get(i).getItemModel().getName();
            tempItem = itemName + " x " + Quantity + "   ";
            item = item.concat(tempItem);
        }
        holder.orderItemTV.setText(item);
        holder.orderRatingTV.setText(String.valueOf(orderItemListModel.getOrderModel().getRating()));
        if (String.valueOf(holder.orderRatingTV.getText()).equals("0.0")) {
            holder.rateBT.setVisibility(View.VISIBLE);
            holder.orderRatingTV.setVisibility(View.GONE);
            holder.starImgIV.setVisibility(View.GONE);
            holder.orderRateTV.setVisibility(View.GONE);
        } else {
            holder.rateBT.setVisibility(View.GONE);
            holder.orderRatingTV.setVisibility(View.VISIBLE);
            holder.starImgIV.setVisibility(View.VISIBLE);
            holder.orderRateTV.setVisibility(View.VISIBLE);
        }
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
        Button rateBT;
        ImageView starImgIV;
        TextView orderRateTV;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTV = itemView.findViewById(R.id.hotelNameTV);
            hotelPriceTV = itemView.findViewById(R.id.hotelPriceTV);
            hotelStatusTV = itemView.findViewById(R.id.hotelStatusTV);
            orderItemTV = itemView.findViewById(R.id.orderItemsTV);
            orderDateTV = itemView.findViewById(R.id.orderDateTV);
            orderRatingTV = itemView.findViewById(R.id.orderRatingTV);
            rateBT = itemView.findViewById(R.id.rateBT);
            starImgIV=itemView.findViewById(R.id.starImgIV);
            orderRateTV=itemView.findViewById(R.id.orderRateTV);

            rateBT.setOnClickListener((View.OnClickListener) view -> {

                AlertDialog.Builder dialogBuilder= new AlertDialog.Builder(activityContext);
                View v = LayoutInflater.from(context).inflate(R.layout.activity_rating_bar,null);
                Button submitRatingBT = v.findViewById(R.id.submitRatingBT);
                final RatingBar ratingBar = v.findViewById(R.id.ratingBarRB);
                final TextView ratingDisplayTV=v.findViewById(R.id.ratingDisplayTV);

                dialogBuilder.setView(v);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                submitRatingBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Your rating is " + String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                        orderRatingTV.setText(String.valueOf(ratingBar.getRating()));
                        ratingDisplayTV.setText("Your rating is " + String.valueOf(ratingBar.getRating()));
                        dialog.dismiss();
                        //todo call API(not for now)
                        //todo make rating below rateBT in ui --done M
                    }
                });
            });
        }
    }
}