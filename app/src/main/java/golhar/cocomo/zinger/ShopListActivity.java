package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import golhar.cocomo.zinger.adapter.ShopListAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.ShopConfigurationModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class ShopListActivity extends AppCompatActivity {

    Button accountBT;
    RecyclerView shopListRV;
    ShopListAdapter shopListAdapter;
    TextView searchShopET;
    ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        shopConfigurationModelArrayList = new ArrayList<ShopConfigurationModel>();
        searchShopET = findViewById(R.id.searchShopET);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        try {
            shopListAdapter = new ShopListAdapter(new ArrayList<>(), this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopListAdapter.setShopConfigurationModelArrayList(shopConfigurationModelArrayList);
        shopListRV = findViewById(R.id.shopListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        shopListRV.setLayoutManager(linearLayoutManager);
        shopListRV.setAdapter(shopListAdapter);
        getShopList();
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getShopList();
            }
        });

        searchShopET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(ShopListActivity.this, SearchItemShopActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Shopdetails", shopConfigurationModelArrayList);
                search.putExtras(bundle);
                startActivity(search);
            }
        });
        accountBT = findViewById(R.id.accountBT);
        accountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(ShopListActivity.this, SellerActivity.class);
                startActivity(order);
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    void getShopList(){
        int collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        MainRepository.getShopService().getShopsByCollegeId(Integer.toString(collegeId), authId, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<ShopConfigurationModel>>>() {
            @Override
            public void onResponse(Call<Response<List<ShopConfigurationModel>>> call, retrofit2.Response<Response<List<ShopConfigurationModel>>> response) {
                Response<List<ShopConfigurationModel>> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    shopListAdapter.setShopConfigurationModelArrayList((ArrayList<ShopConfigurationModel>) responseFromServer.getData());
                    shopListAdapter.notifyDataSetChanged();
                    shopConfigurationModelArrayList = (ArrayList<ShopConfigurationModel>) responseFromServer.getData();
                    pullToRefresh.setRefreshing(false);
                } else {
                    Log.d("RetroFit2", "failure");
                }
            }

            @Override
            public void onFailure(Call<Response<List<ShopConfigurationModel>>> call, Throwable t) {
                Log.d("ResponseFail", t.getMessage());
            }
        });
    }
}