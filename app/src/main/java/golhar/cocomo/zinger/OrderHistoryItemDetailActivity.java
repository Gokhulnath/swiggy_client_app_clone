package golhar.cocomo.zinger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import golhar.cocomo.zinger.adapter.OrderHistoryDetailAdapter;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.SharedPref;

public class OrderHistoryItemDetailActivity extends AppCompatActivity {

    List<OrderItemListModel> itemList;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy    hh:mm:ss a");
    TextView orderNumTV;
    TextView statusTV;
    TextView itemNumTV;
    TextView itemCostTV;
    TextView hotelTV;
    TextView collegeNameTV;
    TextView toTV;
    TextView toAddTV;
    TextView delDateTV;
    ListView itemsLV;
    TextView costTV;
    TextView deliveryCostTV;
    TextView totalCostTV;
    TextView viaTV;
    ImageButton backArrowIB;
    OrderHistoryDetailAdapter orderHistoryDetailAdapter;
    TextView lastUpdatedTimeTV;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);
        Intent detail = getIntent();
        OrderItemListModel orderItemListModel = detail.getParcelableExtra("FullOrderDetails");
        backArrowIB = findViewById(R.id.backArrowIB);
        orderNumTV = findViewById(R.id.orderNumTV);
        statusTV = findViewById(R.id.statusTV);
        itemNumTV = findViewById(R.id.itemNumTV);
        itemCostTV = findViewById(R.id.itemCostTV);
        hotelTV = findViewById(R.id.hotelTV);
        collegeNameTV = findViewById(R.id.collegeNameTV);
        toTV = findViewById(R.id.toTV);
        toAddTV = findViewById(R.id.toAddTV);
        costTV = findViewById(R.id.costTV);
        deliveryCostTV = findViewById(R.id.deliveryCostTV);
        totalCostTV = findViewById(R.id.totalCostTV);
        viaTV = findViewById(R.id.viaTV);
        itemsLV = findViewById(R.id.itemsLV);
        lastUpdatedTimeTV = findViewById(R.id.lastUpdatedTimeTV);
        List<OrderItemModel> orderItemList = orderItemListModel.getOrderItemsList();
        orderNumTV.setText("ORDER " + "#" + orderItemListModel.getOrderModel().getId());
        itemNumTV.setText(orderItemList.size() + "items");
        itemCostTV.setText("₹" + String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        hotelTV.setText(orderItemListModel.getOrderModel().getShopModel().getName());
        collegeNameTV.setText(SharedPref.getString(getApplicationContext(), Constants.collegeName));
        costTV.setText("₹" + String.valueOf(orderItemListModel.getOrderModel().getPrice() - orderItemListModel.getOrderModel().getDeliveryPrice()));
        toTV.setText(orderItemListModel.getOrderModel().getDeliveryLocation());
        toAddTV.setText(SharedPref.getString(getApplicationContext(), Constants.collegeName));
        deliveryCostTV.setText("₹" + String.valueOf(orderItemListModel.getOrderModel().getDeliveryPrice()));
        totalCostTV.setText("₹" + String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        viaTV.setText("Paid Via " + orderItemListModel.getOrderModel().getTransactionModel().getPaymentMode());
        String status = String.valueOf(orderItemListModel.getOrderModel().getOrderStatus());
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date date1 = orderItemListModel.getOrderModel().getLastStatusUpdatedTime();
        if (date1 != null) {
            Calendar c = Calendar.getInstance();
            Date date2 = c.getTime();
            long diff = date2.getTime() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            if (days == 1) {
                lastUpdatedTimeTV.setVisibility(View.INVISIBLE);
            }
            if (minutes < 60) {
                lastUpdatedTimeTV.setText("Lasted updated " + minutes + " minutes ago");
            } else {
                lastUpdatedTimeTV.setText("Lasted updated " + hours + " hour " + minutes % 60 + " minute ago");
            }
        } else {
            lastUpdatedTimeTV.setVisibility(View.INVISIBLE);
        }
        statusTV.setText(status);
        switch (status) {
            case "PENDING":
                statusTV.setTextColor(Color.parseColor("#006400"));
                break;
            case "TXN_FAILURE":
                statusTV.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "PLACED":
                statusTV.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "CANCELLED_BY_USER":
                statusTV.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "ACCEPTED":
                statusTV.setTextColor(Color.parseColor("#e25822"));
                break;
            case "CANCELLED_BY_SELLER":
                statusTV.setTextColor(Color.parseColor("#FF0000"));
                break;
            case "READY":
                statusTV.setTextColor(Color.parseColor("#e25822"));
                break;
            case "OUT_FOR_DELIVERY":
                statusTV.setTextColor(Color.parseColor("#e25822"));
                break;
            case "COMPLETED":
                statusTV.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "DELIVERED":
                statusTV.setTextColor(Color.parseColor("#00FF00"));
                lastUpdatedTimeTV.setText("Order Delivered on " + dateFormat.format(orderItemListModel.getOrderModel().getDate()));
                break;
        }
        backArrowIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(this, R.layout.order_items, orderItemList);
        itemsLV.setAdapter(orderHistoryDetailAdapter);
    }
}
