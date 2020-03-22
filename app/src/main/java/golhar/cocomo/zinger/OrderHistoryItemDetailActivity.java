package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import golhar.cocomo.zinger.adapter.OrderHistoryDetailAdapter;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;

//import golhar.cocomo.zinger.adapter.OrderHistoryDetailAdapter;

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
    OrderHistoryDetailAdapter orderHistoryDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);
        Intent detail= getIntent();
        OrderItemListModel orderItemListModel= detail.getParcelableExtra("FullOrderDetails");
        orderNumTV=findViewById(R.id.orderNumTV);
        statusTV=findViewById(R.id.statusTV);
        itemNumTV=findViewById(R.id.itemNumTV);
        itemCostTV=findViewById(R.id.itemCostTV);
        hotelTV=findViewById(R.id.hotelTV);
        collegeNameTV=findViewById(R.id.collegeNameTV);
        toTV=findViewById(R.id.toTV);
        toAddTV=findViewById(R.id.toAddTV);
        delDateTV=findViewById(R.id.delDateTV);
        costTV=findViewById(R.id.costTV);
        deliveryCostTV=findViewById(R.id.deliveryCostTV);
        totalCostTV=findViewById(R.id.totalCostTV);
        viaTV=findViewById(R.id.viaTV);
        itemsLV=findViewById(R.id.itemsLV);

        List<OrderItemModel> orderItemList = orderItemListModel.getOrderItemsList();

        statusTV.setText(String.valueOf(orderItemListModel.getOrderModel().getOrderStatus()));
        itemNumTV.setText(orderItemList.size()+ "items");
        itemCostTV.setText(String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        hotelTV.setText(orderItemListModel.getOrderModel().getShopModel().getName());
        collegeNameTV.setText(orderItemListModel.getOrderModel().getShopModel().getCollegeModel().getName());
        costTV.setText(String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        toTV.setText(orderItemListModel.getOrderModel().getDeliveryLocation());
        toAddTV.setText(orderItemListModel.getOrderModel().getShopModel().getCollegeModel().getName());
        delDateTV.setText(dateFormat.format(orderItemListModel.getOrderModel().getDate()));
        deliveryCostTV.setText(String.valueOf(orderItemListModel.getOrderModel().getDeliveryPrice()));
        totalCostTV.setText(String.valueOf(orderItemListModel.getOrderModel().getPrice()));
        viaTV.setText(orderItemListModel.getOrderModel().getTransactionModel().getPaymentMode());

        orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(this,R.layout.activity_order_items,orderItemList);
        itemsLV.setAdapter(orderHistoryDetailAdapter);
    }
}
