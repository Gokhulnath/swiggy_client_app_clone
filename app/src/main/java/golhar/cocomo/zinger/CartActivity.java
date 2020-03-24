package golhar.cocomo.zinger;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

import golhar.cocomo.zinger.adapter.OrderHistoryDetailAdapter;
import golhar.cocomo.zinger.model.OrderItemModel;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.SharedPref;

public class CartActivity extends AppCompatActivity {
    OrderHistoryDetailAdapter orderHistoryDetailAdapter;
    ListView cartLV;
    Button clearOrderBT;
    ArrayList<OrderItemModel> orderItemModelArrayList;
    TextView resNameTV;
    TextView resAddTV;
    ImageView collegeIconIV;
    EditText cookingInfoET;
    TextView itemCostTV;
    TextView delFeeTV;
    TextView totalPayTV;
    Button placeOrderBT;
    Double itemTotal;
    Double deliveryFee;
    Double totalFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        itemTotal=0.0;
        deliveryFee=0.0;
        resNameTV=findViewById(R.id.resNameTV);
        resAddTV=findViewById(R.id.resAddTV);
        collegeIconIV=findViewById(R.id.collegeIconIV);
        cookingInfoET=findViewById(R.id.cookingInfoET);
        itemCostTV=findViewById(R.id.itemCostTV);
        delFeeTV=findViewById(R.id.delFeeTV);
        totalPayTV=findViewById(R.id.totalPayTV);
        placeOrderBT=findViewById(R.id.placeOrderBT);
        int shopId = SharedPref.getInt(getApplicationContext(), Constants.shopId);
        if (shopId == -1) {
            clearOrderList();
            SharedPref.putInt(getApplicationContext(),Constants.cartShopId,-1);
        }
        cartLV = findViewById(R.id.cartLV);
        clearOrderBT = findViewById(R.id.clearOrderBT);
        clearOrderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(getApplicationContext(), R.layout.order_items, clearOrderList());
                cartLV.setAdapter(orderHistoryDetailAdapter);
                SharedPref.putInt(getApplicationContext(),Constants.cartShopId,-1);
            }
        });
        orderItemModelArrayList = RetrieveData();
        HashSet<OrderItemModel> listToSet = new HashSet<OrderItemModel>(orderItemModelArrayList);
        orderItemModelArrayList = new ArrayList<OrderItemModel>(listToSet);
        LoadData(orderItemModelArrayList);
        orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(this, R.layout.order_items, orderItemModelArrayList);
        cartLV.setAdapter(orderHistoryDetailAdapter);

        resNameTV.setText(SharedPref.getString(getApplicationContext(),Constants.cartShopName));
        resAddTV.setText(SharedPref.getString(getApplicationContext(),Constants.collegeName));
        Glide.with(getApplicationContext())
                .load(SharedPref.getString(getApplicationContext(),Constants.collegeUrl))
                .placeholder(new ColorDrawable(Color.parseColor("#000000")))
                .into(collegeIconIV);
        orderItemModelArrayList=RetrieveData();
        for(OrderItemModel oIM: orderItemModelArrayList){
            itemTotal+=oIM.getPrice();
        }
        deliveryFee=Double.parseDouble(Long.toString(SharedPref.getLong(getApplicationContext(),Constants.shopDeliveryPrice)));
        itemCostTV.setText(itemTotal.toString());
        delFeeTV.setText(deliveryFee.toString());
        totalFee=itemTotal+deliveryFee;
        totalPayTV.setText(totalFee.toString());
    }

    ArrayList<OrderItemModel> clearOrderList() {
        ArrayList<OrderItemModel> orderItemModelArrayList = new ArrayList<>();
        LoadData(orderItemModelArrayList);
        return orderItemModelArrayList;

    }

    ArrayList<OrderItemModel> RetrieveData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.sharedPreferencesCart, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.cart, null);
        Type type = new TypeToken<ArrayList<OrderItemModel>>() {
        }.getType();
        ArrayList<OrderItemModel> orderItemModelArrayList = gson.fromJson(json, type);
        return orderItemModelArrayList;
    }

    void LoadData(ArrayList<OrderItemModel> orderItemModelArrayList) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.sharedPreferencesCart, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orderItemModelArrayList);
        editor.putString(Constants.cart, json);
        editor.apply();
    }
}
