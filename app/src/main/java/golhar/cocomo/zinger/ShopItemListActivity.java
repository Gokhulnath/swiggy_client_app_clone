package golhar.cocomo.zinger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import golhar.cocomo.zinger.model.ShopConfigurationModel;

public class ShopItemListActivity extends AppCompatActivity {

    TextView restaurantNameTV;
    TextView ratingTV;
    TextView numberOfRatingTV;
    TextView deliveryTV;
    ImageButton backIB;
    FloatingActionButton callShopFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_list);
        Intent shop = getIntent();
        ShopConfigurationModel shopConfigurationModel = shop.getParcelableExtra("shopDetails");
        restaurantNameTV = findViewById(R.id.restaurantNameTV);
        ratingTV = findViewById(R.id.ratingTV);
        numberOfRatingTV = findViewById(R.id.numberOfRatingTV);
        deliveryTV = findViewById(R.id.deliveryTV);
        backIB = findViewById(R.id.backIB);
        callShopFAB = findViewById(R.id.callShopFAB);
        restaurantNameTV.setText(shopConfigurationModel.getShopModel().getName());
        ratingTV.setText(shopConfigurationModel.getRatingModel().getRating().toString());
        numberOfRatingTV.setText(shopConfigurationModel.getRatingModel().getUserCount() + "\nRatings");
        Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_no_delivery);
        if (shopConfigurationModel.getConfigurationModel().getIsDeliveryAvailable() == 0) {
            deliveryTV.setText("No Delivery");
            deliveryTV.setCompoundDrawables(null, img, null, null);
        }
        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        callShopFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(shopConfigurationModel.getShopModel().getMobile()));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
    }
}
