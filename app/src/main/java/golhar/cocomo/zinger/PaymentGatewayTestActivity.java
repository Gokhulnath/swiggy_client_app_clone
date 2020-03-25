package golhar.cocomo.zinger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Random;

import golhar.cocomo.zinger.enums.OrderStatus;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.model.TransactionModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class PaymentGatewayTestActivity extends AppCompatActivity {

    Button noBT, yesBT;
    TransactionModel transactionModel;
    OrderItemListModel orderItemListModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway_test);
        noBT = findViewById(R.id.noBT);
        yesBT = findViewById(R.id.yesBT);
        final int min = 200;
        final int max = 999;
        final int random = new Random().nextInt((max - min) + 1) + min;
        String orderId = "O0" + Integer.toString(random);
        String transactionId = "T0" + Integer.toString(random);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.sharedPreferencesOrder, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.order, null);
        Type type = new TypeToken<OrderItemListModel>() {
        }.getType();
        OrderItemListModel orderItemListModel2 = gson.fromJson(json, type);
        orderItemListModel = new OrderItemListModel();
        orderItemListModel.setOrderItemsList(orderItemListModel2.getOrderItemsList());
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setUserModel(orderItemListModel2.getOrderModel().getUserModel());
        orderModel.setShopModel(orderItemListModel2.getOrderModel().getShopModel());
        orderModel.setPrice(orderItemListModel2.getOrderModel().getPrice());
        orderModel.setDeliveryPrice(orderItemListModel2.getOrderModel().getDeliveryPrice());
        orderModel.setDeliveryLocation(orderItemListModel2.getOrderModel().getDeliveryLocation());
        orderModel.setCookingInfo(orderItemListModel2.getOrderModel().getCookingInfo());
        transactionModel = new TransactionModel();
        transactionModel.setTransactionId(transactionId);
        transactionModel.setBankTransactionId("BT0001");
        transactionModel.setCurrency("INR");
        transactionModel.setResponseCode("01");
        transactionModel.setGatewayName("HDFC");
        transactionModel.setBankName("HDFC");
        transactionModel.setPaymentMode("UPI");
        transactionModel.setChecksumHash("ABCDEFG");
        yesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderModel.setOrderStatus(OrderStatus.PLACED);
                transactionModel.setResponseMessage("Txn Successful");
                orderModel.setTransactionModel(transactionModel);
                orderItemListModel.setOrderModel(orderModel);
                orderAPI(orderItemListModel);
            }
        });

        noBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderModel.setOrderStatus(OrderStatus.CANCELLED_BY_USER);
                transactionModel.setResponseMessage(OrderStatus.CANCELLED_BY_USER.name());
                orderModel.setTransactionModel(transactionModel);
                orderItemListModel.setOrderModel(orderModel);
                orderAPI(orderItemListModel);
            }
        });
    }

    void orderAPI(OrderItemListModel orderItemListModel1) {
        String phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        MainRepository.getOrderService().insertOrder(orderItemListModel1, authId, phoneNo, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    Toast.makeText(PaymentGatewayTestActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(PaymentGatewayTestActivity.this, "Failure"+responseFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(PaymentGatewayTestActivity.this, "Payment Failure", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Toast.makeText(PaymentGatewayTestActivity.this, "Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
