package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    Button viewMoreBT;
    Button userEditBT;
    ArrayList<OrderItemListModel> orderItemListModels;
    int pageNum;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        orderItemListModels = new ArrayList<>();
        pageNum = 1;
        userNameTV = findViewById(R.id.userNameTV);
        userNumTV = findViewById(R.id.userNumTV);
        userEmailTV = findViewById(R.id.userEmailTV);
        userEditBT = findViewById(R.id.userEditBT);
        orderHistoryAdapter = new OrderHistoryAdapter(new ArrayList<>(), getApplicationContext(), OrderHistoryActivity.this);
        viewMoreBT = findViewById(R.id.viewMoreBT);
        String phoneNo, email, userName;
        phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        email = SharedPref.getString(getApplicationContext(), Constants.userEmail);
        userName = SharedPref.getString(getApplicationContext(), Constants.userName);
        userNameTV.setText(userName);
        userEmailTV.setText(email);
        userNumTV.setText(phoneNo);
        orderListRV = findViewById(R.id.orderListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        orderListRV.setLayoutManager(linearLayoutManager);
        orderListRV.setAdapter(orderHistoryAdapter);
        userEditBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(OrderHistoryActivity.this, UpdateUserProfileActivity.class);
                startActivity(edit);
            }
        });
        logoutBT = findViewById(R.id.logoutBT);
        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.removeAll(getApplicationContext());
                FirebaseAuth.getInstance().signOut();
                Intent MainActivity = new Intent(OrderHistoryActivity.this, OnBoardingActivity.class);
                finishAffinity();
                startActivity(MainActivity);
            }
        });

        getOrderListByPage(1);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setRefreshing(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderItemListModels.clear();
                orderHistoryAdapter.setItemList(orderItemListModels);
                orderHistoryAdapter.notifyDataSetChanged();
                for (int i = 1; i <= pageNum; i++) {
                    getOrderListByPage(i);
                }
            }
        });


        viewMoreBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderItemListModels.size() < 5 && orderItemListModels.size() > 0) {
                    viewMoreBT.setVisibility(View.GONE);
                } else {
                    pageNum += 1;
                    pullToRefresh.setRefreshing(true);
                    getOrderListByPage(pageNum);
                }
            }
        });
    }

    public void getOrderListByPage(int pageNumFunc) {
        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(getApplicationContext()) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        String phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        MainRepository.getOrderService().getOrderByMobile(phoneNo, pageNumFunc, 5, authId,
                phoneNo, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<OrderItemListModel>>>() {
            @Override
            public void onResponse(Call<Response<List<OrderItemListModel>>> call, retrofit2.Response<Response<List<OrderItemListModel>>> response) {

                Response<List<OrderItemListModel>> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    ArrayList<OrderItemListModel> tempOrderItemListModels = (ArrayList<OrderItemListModel>) responseFromServer.getData();
                    for (OrderItemListModel orderItemListModel : tempOrderItemListModels)
                        orderItemListModels.add(orderItemListModel);
                    orderHistoryAdapter.setItemList(orderItemListModels);
                    orderHistoryAdapter.notifyDataSetChanged();
                    if (orderItemListModels.size() < (pageNumFunc * 5)) {
                        viewMoreBT.setVisibility(View.GONE);
                    }
                    smoothScroller.setTargetPosition(((pageNumFunc - 1) * 5));
                    orderListRV.getLayoutManager().startSmoothScroll(smoothScroller);
                    pullToRefresh.setRefreshing(false);
                } else {
                    viewMoreBT.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Response<List<OrderItemListModel>>> call, Throwable t) {
                Log.d("RetroFit", "error");
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String phoneNo, email, userName;
        phoneNo = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        email = SharedPref.getString(getApplicationContext(), Constants.userEmail);
        userName = SharedPref.getString(getApplicationContext(), Constants.userName);
        userNameTV.setText(userName);
        userEmailTV.setText(email);
        userNumTV.setText(phoneNo);
        orderItemListModels.clear();
        orderHistoryAdapter.setItemList(orderItemListModels);
        orderHistoryAdapter.notifyDataSetChanged();
        pullToRefresh.setRefreshing(true);
        for (int i = 1; i <= pageNum; i++) {
            getOrderListByPage(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
