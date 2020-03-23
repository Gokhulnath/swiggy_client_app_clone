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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import golhar.cocomo.zinger.adapter.OrderHistoryDetailAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

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
    SwipeRefreshLayout pullToRefresh;
    OrderModel newOrderModel;
    OrderItemListModel orderItemListModel;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_item_detail);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        Intent detail = getIntent();
        orderItemListModel = detail.getParcelableExtra("FullOrderDetails");
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
        itemsLV.setScrollbarFadingEnabled(false);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date date1 = orderItemListModel.getOrderModel().getLastStatusUpdatedTime();
        String status = String.valueOf(orderItemListModel.getOrderModel().getOrderStatus());
        statusChange(date1,status);

        backArrowIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(this, R.layout.order_items, orderItemList);
        itemsLV.setAdapter(orderHistoryDetailAdapter);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    getOrderById();
            }
        });

    }

    void getOrderById()
    {
        String phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        MainRepository.getOrderService().getOrderById(orderItemListModel.getOrderModel().getId(),authId,phoneNo, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<OrderModel>>() {
            @Override
            public void onResponse(Call<Response<OrderModel>> call, retrofit2.Response<Response<OrderModel>> response) {
                Response<OrderModel> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)){
                    newOrderModel = responseFromServer.getData();
                    statusChange(newOrderModel.getLastStatusUpdatedTime(),newOrderModel.getOrderStatus().toString());
                    pullToRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Response<OrderModel>> call, Throwable t) {

            }
        });
    }

    void statusChange(Date date1,String status){
        if (date1 != null) {
            Calendar c = Calendar.getInstance();
            Date date2 = c.getTime();
            long diff = date2.getTime() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            if (days >0) {
                lastUpdatedTimeTV.setText("Lasted updated " + days + " day ago");
            }else if (minutes < 60) {
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
    }
}
