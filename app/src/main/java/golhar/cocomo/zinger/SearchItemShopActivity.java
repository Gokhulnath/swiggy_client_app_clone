package golhar.cocomo.zinger;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import golhar.cocomo.zinger.adapter.ShopListAdapter;
import golhar.cocomo.zinger.adapter.ShopMenuItemAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.ItemModel;
import golhar.cocomo.zinger.model.ShopConfigurationModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class SearchItemShopActivity extends AppCompatActivity {

    RecyclerView shopMenuItemListRV;
    ShopMenuItemAdapter shopMenuItemAdapter;
    RecyclerView shopListRV;
    ShopListAdapter shopListAdapter;
    EditText searchET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item_shop);
        ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList = this.getIntent().getExtras().getParcelableArrayList("Shopdetails");
        searchET = findViewById(R.id.searchET);
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authid = SharedPref.getString(getApplicationContext(), Constants.authId);
        int collegeId = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        //items
        shopMenuItemAdapter = new ShopMenuItemAdapter(new ArrayList<>(), this);
        shopMenuItemListRV = findViewById(R.id.shopMenuItemListRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        shopMenuItemListRV.setLayoutManager(gridLayoutManager);
        shopMenuItemListRV.setAdapter(shopMenuItemAdapter);
        //shop
        try {
            shopListAdapter = new ShopListAdapter(new ArrayList<>(), this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopListRV = findViewById(R.id.shopListRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        shopListRV.setLayoutManager(linearLayoutManager);
        shopListRV.setAdapter(shopListAdapter);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ArrayList<ShopConfigurationModel> nullShopList = new ArrayList<ShopConfigurationModel>();
                ArrayList<ItemModel> nullItemList = new ArrayList<ItemModel>();
                shopListAdapter.setShopConfigurationModelArrayList(nullShopList);
                shopListAdapter.notifyDataSetChanged();
                shopMenuItemAdapter.setItemModelArrayList(nullItemList);
                shopMenuItemAdapter.notifyDataSetChanged();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    //shop
                    ArrayList<ShopConfigurationModel> modifiedShopList = (ArrayList<ShopConfigurationModel>) shopConfigurationModelArrayList
                            .stream()
                            .filter(shopConfigurationModel -> shopConfigurationModel.getShopModel().getName().toLowerCase().contains(s.toString().toLowerCase()))
                            .collect(Collectors.toList());
                    shopListAdapter.setShopConfigurationModelArrayList(modifiedShopList);
                    shopListAdapter.notifyDataSetChanged();

                    //item
                    MainRepository.getItemService().getItemsByName(collegeId, s.toString(), authid, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<ItemModel>>>() {
                        @Override
                        public void onResponse(Call<Response<List<ItemModel>>> call, retrofit2.Response<Response<List<ItemModel>>> response) {
                            Response<List<ItemModel>> responseFromServer = response.body();
                            if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                                shopMenuItemAdapter.setItemModelArrayList((ArrayList<ItemModel>) responseFromServer.getData());
                                shopMenuItemAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(SearchItemShopActivity.this, "first" + responseFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<List<ItemModel>>> call, Throwable t) {
                            Toast.makeText(SearchItemShopActivity.this, "Unable to reach the server" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
