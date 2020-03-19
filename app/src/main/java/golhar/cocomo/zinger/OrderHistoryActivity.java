package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import golhar.cocomo.zinger.adapter.OrderHistoryAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class OrderHistoryActivity extends AppCompatActivity {
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView orderListRV;
    TextView userNameTV;
    TextView userNumTV;
    TextView userEmailTV;
    Button logoutBT;
    Button rateBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        userNameTV = findViewById(R.id.userNameTV);
        userNumTV = findViewById(R.id.userNumTV);
        userEmailTV = findViewById(R.id.userEmailTV);
        rateBT = findViewById(R.id.rateBT);

        String phoneNo, authId, email, userName;
        phoneNo = SharedPref.getString(getApplicationContext(), "phone_number");
        authId = SharedPref.getString(getApplicationContext(), "authId");
        email = SharedPref.getString(getApplicationContext(), "userEmail");
        userName = SharedPref.getString(getApplicationContext(), "userName");
        userNameTV.setText(userName);
        userEmailTV.setText(email);
        userNumTV.setText(phoneNo);
        orderListRV = findViewById(R.id.orderListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        orderListRV.setLayoutManager(linearLayoutManager);
        logoutBT = findViewById(R.id.logoutBT);
        logoutBT.setOnClickListener(view -> {
            SharedPref.putInt(getApplicationContext(), "loginStatus", 0);
            Intent MainActivity = new Intent(OrderHistoryActivity.this, golhar.cocomo.zinger.MainActivity.class);
            startActivity(MainActivity);
        });

        MainRepository.getOrderService().getOrderByMobile(phoneNo, 1, 5, authId,
                phoneNo, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<OrderItemListModel>>>() {
            @Override
            public void onResponse(Call<Response<List<OrderItemListModel>>> call, retrofit2.Response<Response<List<OrderItemListModel>>> response) {

                Response<List<OrderItemListModel>> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    Log.d("RetroFit", responseFromServer.toString());
                    orderHistoryAdapter = new OrderHistoryAdapter(responseFromServer.getData(), getApplicationContext());
                    orderListRV.setAdapter(orderHistoryAdapter);

                } else {
                    Log.d("RetroFit", "error");
                }
            }

            @Override
            public void onFailure(Call<Response<List<OrderItemListModel>>> call, Throwable t) {
                Log.d("RetroFit", "error");
            }
        });


    }
};





