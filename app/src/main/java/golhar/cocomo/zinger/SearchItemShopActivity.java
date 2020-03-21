package golhar.cocomo.zinger;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import golhar.cocomo.zinger.adapter.searchAdapter.SearchShopListAdapter;
import golhar.cocomo.zinger.adapter.searchAdapter.SearchShopMenuItemAdapter;
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

    RecyclerView searchShopMenuItemListRV;
    SearchShopMenuItemAdapter searchShopMenuItemAdapter;
    RecyclerView shopListRV;
    SearchShopListAdapter searchShopListAdapter;
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
        searchShopMenuItemAdapter = new SearchShopMenuItemAdapter(new ArrayList<>(), this);
        searchShopMenuItemListRV = findViewById(R.id.shopMenuItemListRV);
        LinearLayoutManager linearLayoutManagerItem = new LinearLayoutManager(this);
        searchShopMenuItemListRV.setLayoutManager(linearLayoutManagerItem);
        searchShopMenuItemListRV.setAdapter(searchShopMenuItemAdapter);
        //shop
        try {
            searchShopListAdapter = new SearchShopListAdapter(new ArrayList<>(), this);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        shopListRV = findViewById(R.id.shopListRV);
        LinearLayoutManager linearLayoutManagerShop = new LinearLayoutManager(this);
        linearLayoutManagerShop.setOrientation(RecyclerView.VERTICAL);
        shopListRV.setLayoutManager(linearLayoutManagerShop);
        shopListRV.setAdapter(searchShopListAdapter);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (searchET.toString().trim().isEmpty() || after < 4) {
                    searchShopListAdapter.setShopConfigurationModelArrayList(new ArrayList<>());
                    searchShopListAdapter.notifyDataSetChanged();
                    searchShopMenuItemAdapter.setItemModelArrayList(new ArrayList<>());
                    searchShopMenuItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1 && count == 0) {
                    searchShopListAdapter.setShopConfigurationModelArrayList(new ArrayList<>());
                    searchShopListAdapter.notifyDataSetChanged();
                    searchShopMenuItemAdapter.setItemModelArrayList(new ArrayList<>());
                    searchShopMenuItemAdapter.notifyDataSetChanged();
                }
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<ShopConfigurationModel> modifiedShopList = new ArrayList<>();
                ArrayList<ItemModel> modifiedArraylist = new ArrayList<>();
                if (s.length() > 3) {
                    //shop
                    modifiedShopList = (ArrayList<ShopConfigurationModel>) shopConfigurationModelArrayList
                            .stream()
                            .filter(shopConfigurationModel -> shopConfigurationModel.getShopModel().getName().toLowerCase().contains(s.toString().toLowerCase()))
                            .collect(Collectors.toList());
                    searchShopListAdapter.setShopConfigurationModelArrayList(modifiedShopList);
                    searchShopListAdapter.notifyDataSetChanged();
                    //item
                    MainRepository.getItemService().getItemsByName(collegeId, s.toString(), authid, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<ItemModel>>>() {
                        @Override
                        public void onResponse(Call<Response<List<ItemModel>>> call, retrofit2.Response<Response<List<ItemModel>>> response) {
                            Response<List<ItemModel>> responseFromServer = response.body();
                            if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                                ArrayList<ItemModel> modifiedArraylist = (ArrayList<ItemModel>) responseFromServer.getData();
                                searchShopMenuItemAdapter.setItemModelArrayList(modifiedArraylist);
                                searchShopMenuItemAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<List<ItemModel>>> call, Throwable t) {
                            Toast.makeText(SearchItemShopActivity.this, "Unable to reach the server" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    modifiedShopList.clear();
                    searchShopListAdapter.setShopConfigurationModelArrayList(modifiedShopList);
                    searchShopListAdapter.notifyDataSetChanged();
                    modifiedArraylist.clear();
                    searchShopMenuItemAdapter.setItemModelArrayList(modifiedArraylist);
                    searchShopMenuItemAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
