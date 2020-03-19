package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import golhar.cocomo.zinger.adapter.RecyclerViewShopListAdapter;
import golhar.cocomo.zinger.model.CollegeModel;
import golhar.cocomo.zinger.model.RatingModel;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.SharedPref;

public class ShopListActivity extends AppCompatActivity {

    Button accountBT;
    RecyclerView shopListRV;
    RecyclerViewShopListAdapter shopListAdapter;
    ArrayList<RatingModel> ratingList;
    EditText searchShopET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);


        searchShopET = findViewById(R.id.searchShopET);
        try {
            shopListAdapter = new RecyclerViewShopListAdapter(new ArrayList<>(), this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopListRV = findViewById(R.id.shopListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        shopListRV.setLayoutManager(linearLayoutManager);
        shopListRV.setAdapter(shopListAdapter);
        searchShopET.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ArrayList<RatingModel> modifiedShopList = (ArrayList<RatingModel>) ratingList
                        .stream()
                        .filter(ratingModel -> ratingModel.getShopModel().getName().toLowerCase().contains(s.toString().toLowerCase()))
                        .collect(Collectors.toList());
                shopListAdapter.setRatingModelArrayList(modifiedShopList);
                shopListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        int collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        CollegeModel collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        /*
        MainRepository.getShopService().getShopsByCollegeId(collegeModel, authId, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<ShopConfigurationModel>>>() {
            @Override
            public void onResponse(Call<Response<List<ShopConfigurationModel>>> call, retrofit2.Response<Response<List<ShopConfigurationModel>>> response) {
                Response<List<ShopConfigurationModel>> response1 = response.body();
                if (response1.getCode().equals(ErrorLog.CodeSuccess) && response1.getMessage().equals(ErrorLog.Success)) {
                    Log.d("Retrofit2", "success");
                } else {
                    Log.d("RetroFit2", "failure");
                }
            }

            @Override
            public void onFailure(Call<Response<List<ShopConfigurationModel>>> call, Throwable t) {
                Log.d("ResponseFail", t.getMessage());
            }
        });*/
        accountBT = findViewById(R.id.accountBT);
        accountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(ShopListActivity.this, OrderHistoryActivity.class);
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

}
