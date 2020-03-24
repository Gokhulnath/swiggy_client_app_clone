package golhar.cocomo.zinger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import golhar.cocomo.zinger.adapter.OrderHistoryDetailAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderItemModel;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.model.ShopModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

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
    OrderItemListModel orderItemListModel;
    RadioGroup orderstatusRG;
    RadioButton deliveryRB;
    RadioButton pickUpRB;
    EditText deliveryLocationET;
    int pickup;
    String deliveryLocation;
    TextView deliveryFeeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        pickup = 0;
        SharedPref.putInt(getApplicationContext(), Constants.orderId, 15);
        SharedPref.putInt(getApplicationContext(), Constants.transactionId, 15);
        itemTotal = 0.0;
        deliveryFee = 0.0;
        orderstatusRG = findViewById(R.id.orderStatusRG);
        deliveryRB = findViewById(R.id.deliveryRB);
        pickUpRB = findViewById(R.id.pickUpRB);
        deliveryLocationET = findViewById(R.id.deliveryLocationET);
        resNameTV = findViewById(R.id.resNameTV);
        resAddTV = findViewById(R.id.resAddTV);
        collegeIconIV = findViewById(R.id.collegeIconIV);
        cookingInfoET = findViewById(R.id.cookingInfoET);
        itemCostTV = findViewById(R.id.itemCostTV);
        delFeeTV = findViewById(R.id.delFeeTV);
        totalPayTV = findViewById(R.id.totalPayTV);
        placeOrderBT = findViewById(R.id.placeOrderBT);
        deliveryFeeTV = findViewById(R.id.deliveryFeeTV);


        int shopId = SharedPref.getInt(getApplicationContext(), Constants.shopId);
        if (shopId == -1) {
            clearOrderList();
            SharedPref.putInt(getApplicationContext(), Constants.cartShopId, -1);
            itemTotal = 0.0;
            deliveryFee = 0.0;
        }
        cartLV = findViewById(R.id.cartLV);
        clearOrderBT = findViewById(R.id.clearOrderBT);
        clearOrderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(getApplicationContext(), R.layout.order_items, clearOrderList());
                cartLV.setAdapter(orderHistoryDetailAdapter);
                SharedPref.putInt(getApplicationContext(), Constants.cartShopId, -1);
                itemTotal = 0.0;
                deliveryFee = 0.0;
                finish();
            }
        });

        orderItemModelArrayList = RetrieveData();
        LoadData(orderItemModelArrayList);
        orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(this, R.layout.order_items, orderItemModelArrayList);
        cartLV.setAdapter(orderHistoryDetailAdapter);

        resNameTV.setText(SharedPref.getString(getApplicationContext(), Constants.cartShopName));
        resAddTV.setText(SharedPref.getString(getApplicationContext(), Constants.collegeName));
        Glide.with(getApplicationContext())
                .load(SharedPref.getString(getApplicationContext(), Constants.collegeUrl))
                .placeholder(new ColorDrawable(Color.parseColor("#000000")))
                .into(collegeIconIV);
        orderItemModelArrayList = RetrieveData();
        for (OrderItemModel oIM : orderItemModelArrayList) {
            itemTotal += oIM.getPrice();
        }
        deliveryFee = Double.parseDouble(Long.toString(SharedPref.getLong(getApplicationContext(), Constants.shopDeliveryPrice)));
        itemCostTV.setText(itemTotal.toString());
        delFeeTV.setText(deliveryFee.toString());
        totalFee = itemTotal + deliveryFee;
        totalPayTV.setText(totalFee.toString());
        orderstatusRG.clearCheck();
        orderstatusRG.check(deliveryRB.getId());
        orderstatusRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != deliveryRB) {
                    pickup = 1;
                    delFeeTV.setVisibility(View.INVISIBLE);
                    deliveryFeeTV.setVisibility(View.INVISIBLE);
                    totalPayTV.setText(itemTotal.toString());
                    deliveryLocationET.setVisibility(View.INVISIBLE);
                } else {
                    deliveryLocationET.setVisibility(View.VISIBLE);
                    delFeeTV.setVisibility(View.VISIBLE);
                    deliveryFeeTV.setVisibility(View.VISIBLE);
                    totalPayTV.setText(totalFee.toString());
                }
            }
        });

        placeOrderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderItemListModel = new OrderItemListModel();
                OrderModel orderModel = new OrderModel();
                UserModel userModel = new UserModel();
                ShopModel shopModel = new ShopModel();
                userModel.setMobile(SharedPref.getString(getApplicationContext(), Constants.phoneNumber));
                orderModel.setUserModel(userModel);
                shopModel.setId(SharedPref.getInt(getApplicationContext(), Constants.cartShopId));
                orderModel.setShopModel(shopModel);
                if (pickup == 0) {
                    orderModel.setDeliveryPrice(deliveryFee);
                    deliveryLocation = deliveryLocationET.getEditableText().toString();
                    orderModel.setDeliveryLocation(deliveryLocation);
                    orderModel.setPrice(totalFee);
                } else {
                    orderModel.setDeliveryPrice(null);
                    orderModel.setDeliveryLocation(null);
                    orderModel.setPrice(itemTotal);
                }
                orderItemListModel.setOrderModel(orderModel);
                orderItemListModel.setOrderItemsList(RetrieveData());
                if (pickup == 0) {
                    if (deliveryLocation.equals("")) {
                        Toast.makeText(CartActivity.this, "Please enter the delivery location", Toast.LENGTH_SHORT).show();
                    } else {
                        verifyOrder();
                    }
                } else {
                    verifyOrder();
                }
            }
        });
    }

    void verifyOrder() {
        String phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        MainRepository.getOrderService().verifyOrder(orderItemListModel, authId, phoneNo, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    OrderItemListModel orderItemListModels = new OrderItemListModel();
                    orderItemListModels.setOrderItemsList(RetrieveData());
                    OrderModel orderModel = new OrderModel();
                    UserModel userModel = new UserModel();
                    userModel.setMobile(phoneNo);
                    orderModel.setUserModel(userModel);
                    ShopModel shopModel = new ShopModel();
                    shopModel.setId(SharedPref.getInt(getApplicationContext(), Constants.cartShopId));
                    orderModel.setShopModel(shopModel);
                    orderModel.setPrice(orderItemListModel.getOrderModel().getPrice());
                    orderModel.setDeliveryPrice(orderItemListModel.getOrderModel().getDeliveryPrice());
                    orderModel.setDeliveryLocation(orderItemListModel.getOrderModel().getDeliveryLocation());
                    orderModel.setCookingInfo(cookingInfoET.getEditableText().toString());
                    orderItemListModels.setOrderModel(orderModel);
                    LoadOrderData(orderItemListModels);
                    Intent payment = new Intent(CartActivity.this, PaymentGatewayTestActivity.class);
                    startActivity(payment);
                    orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(getApplicationContext(), R.layout.order_items, clearOrderList());
                    cartLV.setAdapter(orderHistoryDetailAdapter);
                    SharedPref.putInt(getApplicationContext(), Constants.cartShopId, -1);
                    itemTotal = 0.0;
                    deliveryFee = 0.0;
                    finish();
                } else {
                    Toast.makeText(CartActivity.this, responseFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                    orderHistoryDetailAdapter = new OrderHistoryDetailAdapter(getApplicationContext(), R.layout.order_items, clearOrderList());
                    cartLV.setAdapter(orderHistoryDetailAdapter);
                    SharedPref.putInt(getApplicationContext(), Constants.cartShopId, -1);
                    itemTotal = 0.0;
                    deliveryFee = 0.0;
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    void LoadOrderData(OrderItemListModel orderItemListModel) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.sharedPreferencesOrder, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orderItemListModel);
        editor.putString(Constants.order, json);
        editor.apply();
    }
}
