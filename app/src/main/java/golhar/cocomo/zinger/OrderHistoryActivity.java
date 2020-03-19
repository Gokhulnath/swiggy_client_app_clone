package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import golhar.cocomo.zinger.adapter.OrderHistoryAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderItemListModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        userNameTV = findViewById(R.id.userNameTV);
        userNumTV = findViewById(R.id.userNumTV);
        userEmailTV = findViewById(R.id.userEmailTV);

        String phoneNo, authId, email, userName;
        phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        email = SharedPref.getString(getApplicationContext(), Constants.userEmail);
        userName = SharedPref.getString(getApplicationContext(), Constants.userName);
        userNameTV.setText(userName);
        userEmailTV.setText(email);
        userNumTV.setText(phoneNo);
        ArrayList<OrderItemListModel> orderItemListModelArrayList = new ArrayList<>();
        orderListRV = findViewById(R.id.orderListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        orderListRV.setLayoutManager(linearLayoutManager);
        logoutBT = (Button) findViewById(R.id.logoutBT);
        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.removeAll(getApplicationContext());
                FirebaseAuth.getInstance().signOut();
                Intent MainActivity = new Intent(OrderHistoryActivity.this, OnBoardingActivity.class);
                startActivity(MainActivity);
            }
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
}
