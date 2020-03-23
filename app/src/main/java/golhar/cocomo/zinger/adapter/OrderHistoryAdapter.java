package golhar.cocomo.zinger.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import golhar.cocomo.zinger.OrderHistoryItemDetailActivity;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.model.ShopModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHolder> {

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy    hh:mm:ss a");
    List<OrderItemListModel> itemList;
    Context context, activityContext;

    public OrderHistoryAdapter(List<OrderItemListModel> itemList, Context context, Context activityContext) {
        this.itemList = itemList;
        this.context = context;
        this.activityContext = activityContext;
    }

    public void setItemList(List<OrderItemListModel> itemList) {
        this.itemList = itemList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_row, parent, false);
        OrderHolder orderHolder = new OrderHolder(v);
        return orderHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        final OrderItemListModel orderItemListModel = itemList.get(position);
        holder.hotelNameTV.setText(orderItemListModel.getOrderModel().getShopModel().getName());
        holder.hotelPriceTV.setText("₹" + String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        holder.orderDateTV.setText(dateFormat.format(orderItemListModel.getOrderModel().getDate()));
        String status = String.valueOf(orderItemListModel.getOrderModel().getOrderStatus());
        holder.hotelStatusTV.setText(status);
        switch (status) {
            case "PENDING":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#006400"));
                break;
            case "TXN_FAILURE":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "PLACED":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "CANCELLED_BY_USER":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "ACCEPTED":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#e25822"));
                break;
            case "CANCELLED_BY_SELLER":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "READY":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#e25822"));
                break;
            case "OUT_FOR_DELIVERY":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#e25822"));
                break;
            case "COMPLETED":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "DELIVERED":
                holder.hotelStatusTV.setTextColor(Color.parseColor("#00FF00"));
                break;
        }
        String itemName, item, Quantity, tempItem;
        item = "";
        List<OrderItemModel> orderItemList = orderItemListModel.getOrderItemsList();
        for (int i = 0; i < orderItemList.size(); i++) {
            if(i<3){
            Quantity = orderItemList.get(i).getQuantity().toString();
            itemName = orderItemList.get(i).getItemModel().getName();
            tempItem = itemName + " x " + Quantity + "   ";
            item = item.concat(tempItem);}
            else{
                item = item.concat("..... See More");
                break;
            }
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
        holder.fullOrderRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(activityContext, OrderHistoryItemDetailActivity.class);
                history.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                history.putExtra("FullOrderDetails", orderItemListModel);
                //todo pass the current clicked orderitemlist to next class
                context.startActivity(history);
            }
        });

        //TODO rename OHIDA
        holder.rateBT.setOnClickListener((View.OnClickListener) view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityContext);
            View v = LayoutInflater.from(context).inflate(R.layout.activity_rating_bar, null);
            Button submitRatingBT = v.findViewById(R.id.submitRatingBT);
            RatingBar ratingBar = v.findViewById(R.id.ratingBarRB);
            dialogBuilder.setView(v);
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
            submitRatingBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderItemListModel orderItemListModel1;
                    holder.rateBT.setVisibility(View.GONE);
                    holder.orderRatingTV.setVisibility(View.VISIBLE);
                    holder.starImgIV.setVisibility(View.VISIBLE);
                    holder.orderRateTV.setVisibility(View.VISIBLE);
                    holder.orderRatingTV.setText(String.valueOf(ratingBar.getRating()));
                    holder.orderRateTV.setText("Your rating is ");
                    dialog.dismiss();
                    String phoneNo = SharedPref.getString(context, Constants.phoneNumber);
                    String authId = SharedPref.getString(context, Constants.authId);
                    OrderModel orderModel = new OrderModel();
                    ShopModel shopModel = new ShopModel();
                    shopModel.setId(orderItemListModel.getOrderModel().getShopModel().getId());
                    orderModel.setRating(Double.valueOf(ratingBar.getRating()));
                    orderModel.setId(orderItemListModel.getOrderModel().getId());
                    orderModel.setShopModel(shopModel);
                    MainRepository.getOrderService().updateOrderRating(orderModel, authId, phoneNo, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<String>>() {
                        @Override
                        public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                            Response<String> responseFromServer = response.body();
                            if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                                Toast.makeText(context, "Thanks for rating", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<String>> call, Throwable t) {
                            Toast.makeText(context, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        });
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
        RelativeLayout fullOrderRL;

        //todo change rv to rl in xml
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTV = itemView.findViewById(R.id.hotelNameTV);
            hotelPriceTV = itemView.findViewById(R.id.hotelPriceTV);
            hotelStatusTV = itemView.findViewById(R.id.hotelStatusTV);
            orderItemTV = itemView.findViewById(R.id.orderItemsTV);
            orderDateTV = itemView.findViewById(R.id.orderDateTV);
            orderRatingTV = itemView.findViewById(R.id.orderRatingTV);
            rateBT = itemView.findViewById(R.id.rateBT);
            starImgIV = itemView.findViewById(R.id.starImgIV);
            orderRateTV = itemView.findViewById(R.id.orderRateTV);
            fullOrderRL = itemView.findViewById(R.id.fullOrderRL);
        }
    }
}