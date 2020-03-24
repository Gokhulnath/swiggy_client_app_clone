package golhar.cocomo.zinger;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

public class ShopMenuItemListActivity extends AppCompatActivity {

    TextView restaurantNameTV;
    TextView ratingTV;
    TextView numberOfRatingTV;
    TextView deliveryTV;
    ImageButton backIB;
    ImageButton callShopIB;
    RecyclerView shopMenuItemListRV;
    ShopMenuItemAdapter shopMenuItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu_item_list);
        Intent shop = getIntent();
        ShopConfigurationModel shopConfigurationModel = shop.getParcelableExtra("shopDetails");
        SharedPref.putInt(getApplicationContext(),Constants.shopId,shopConfigurationModel.getShopModel().getId());
        SharedPref.putString(getApplicationContext(),Constants.shopName,shopConfigurationModel.getShopModel().getName());
        SharedPref.putLong(getApplicationContext(),Constants.shopDeliveryPrice,(shopConfigurationModel.getConfigurationModel().getDeliveryPrice()).longValue());
        restaurantNameTV = findViewById(R.id.restaurantNameTV);
        ratingTV = findViewById(R.id.ratingTV);
        numberOfRatingTV = findViewById(R.id.numberOfRatingTV);
        deliveryTV = findViewById(R.id.deliveryTV);
        backIB = findViewById(R.id.backIB);
        callShopIB = findViewById(R.id.shopCallIB);
        restaurantNameTV.setText(shopConfigurationModel.getShopModel().getName());
        if (shopConfigurationModel.getRatingModel() == null || shopConfigurationModel.getRatingModel().getRating().equals(0) || shopConfigurationModel.getRatingModel().getUserCount().equals(0)) {
            ratingTV.setText("No ratings");
            numberOfRatingTV.setVisibility(View.GONE);
        } else {
            ratingTV.setText(shopConfigurationModel.getRatingModel().getRating().toString());
            numberOfRatingTV.setText("(" + shopConfigurationModel.getRatingModel().getUserCount().toString() + ")" + "\nRatings");
        }
        Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_no_delivery);
        if (shopConfigurationModel.getConfigurationModel().getIsDeliveryAvailable() == 0) {
            deliveryTV.setText("No Delivery");
            deliveryTV.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
        } else {
            deliveryTV.setText("₹" + shopConfigurationModel.getConfigurationModel().getDeliveryPrice().toString() + "\nDelivery");
        }
        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        callShopIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + shopConfigurationModel.getShopModel().getMobile()));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });
        shopMenuItemAdapter = new ShopMenuItemAdapter(new ArrayList<>(), this);
        shopMenuItemListRV = findViewById(R.id.shopMenuItemListRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        shopMenuItemListRV.setLayoutManager(gridLayoutManager);
        shopMenuItemListRV.setAdapter(shopMenuItemAdapter);
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authid = SharedPref.getString(getApplicationContext(), Constants.authId);
        int shopId = shopConfigurationModel.getShopModel().getId();
        MainRepository.getItemService().getItemsByShopId(shopId, authid, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<ItemModel>>>() {
            @Override
            public void onResponse(Call<Response<List<ItemModel>>> call, retrofit2.Response<Response<List<ItemModel>>> response) {
                Response<List<ItemModel>> responseFromServer = response.body();
                if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                    shopMenuItemAdapter.setItemModelArrayList((ArrayList<ItemModel>) responseFromServer.getData());
                    shopMenuItemAdapter.notifyDataSetChanged();
                    ViewCompat.setNestedScrollingEnabled(shopMenuItemListRV, false);
                } else {
                    Toast.makeText(ShopMenuItemListActivity.this, responseFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<List<ItemModel>>> call, Throwable t) {
                Toast.makeText(ShopMenuItemListActivity.this, "Unable to reach the server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
