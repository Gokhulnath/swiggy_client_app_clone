package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import golhar.cocomo.zinger.adapter.ShopListAdapter;
import golhar.cocomo.zinger.model.CollegeModel;
import golhar.cocomo.zinger.model.ConfigurationModel;
import golhar.cocomo.zinger.model.RatingModel;
import golhar.cocomo.zinger.model.ShopConfigurationModel;
import golhar.cocomo.zinger.model.ShopModel;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.SharedPref;

public class ShopListActivity extends AppCompatActivity {

    Button accountBT;
    RecyclerView shopListRV;
    ShopListAdapter shopListAdapter;
    EditText searchShopET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList = new ArrayList<>();

        //#1
        ShopConfigurationModel shopConfigurationModel = new ShopConfigurationModel();
        RatingModel ratingModel = new RatingModel();
        ShopModel shopModel = new ShopModel();
        shopModel.setName("Sathyas");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        String myTimeOpen = "00:01:00";
        String myTimeClose = "08:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date closingTime = null;
        Date openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        String collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        int collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        CollegeModel collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        ConfigurationModel configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(0);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);


        //#2
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("snow Qube");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "09:00:00";
        myTimeClose = "23:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);


        //#3
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("Main Sathyas");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "09:00:00";
        myTimeClose = "23:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);

        //#4
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("snow Qube");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "00:00:00";
        myTimeClose = "09:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);


        //#5
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("Main Sathyas");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "09:00:00";
        myTimeClose = "23:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);

        //#6
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("snow Qube");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "09:00:00";
        myTimeClose = "23:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);


        //#7
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("Main Sathyas");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "09:00:00";
        myTimeClose = "23:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);

        //#8
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("snow Qube");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "00:00:00";
        myTimeClose = "09:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(1);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);


        //#9
        ratingModel = new RatingModel();
        shopModel = new ShopModel();
        shopModel.setName("Main Sathyas");
        shopModel.setId(1);
        shopModel.setMobile("1111111111");
        shopModel.setPhotoUrl("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg");
        myTimeOpen = "09:00:00";
        myTimeClose = "23:51:00";
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        closingTime = null;
        openingTime = null;
        try {
            closingTime = dateFormat.parse(myTimeClose);
            openingTime = dateFormat.parse(myTimeOpen);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopModel.setClosingTime(closingTime);
        shopModel.setOpeningTime(openingTime);
        shopModel.setIsDelete(0);
        collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
        collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeModel = new CollegeModel();
        collegeModel.setId(collegeId);
        collegeModel.setName(collegeName);
        shopModel.setCollegeModel(collegeModel);
        ratingModel.setShopModel(shopModel);
        ratingModel.setRating(5.0);
        ratingModel.setUserCount(23);
        configurationModel = new ConfigurationModel();
        configurationModel.setIsDeliveryAvailable(1);
        configurationModel.setIsOrderTaken(0);
        configurationModel.setShopModel(shopModel);
        shopConfigurationModel = new ShopConfigurationModel();
        shopConfigurationModel.setConfigurationModel(configurationModel);
        shopConfigurationModel.setRatingModel(ratingModel);
        shopConfigurationModel.setShopModel(shopModel);

        shopConfigurationModelArrayList.add(shopConfigurationModel);


        searchShopET = findViewById(R.id.searchShopET);


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
        /*
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
        */


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
