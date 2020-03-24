 package golhar.cocomo.zinger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.adapter.SellerActivityAdapter;
import golhar.cocomo.zinger.model.ItemModel;


import android.os.Bundle;

import java.util.ArrayList;

public class SellerActivity extends AppCompatActivity {
    SellerActivityAdapter sellerActivityAdapter;
    RecyclerView sellerMenuRV;
    ArrayList<ItemModel> ItemModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        sellerActivityAdapter= new SellerActivityAdapter(new ArrayList<>(),getApplicationContext(),SellerActivity.this);
        ItemModel= new ArrayList<>();
        sellerMenuRV = findViewById(R.id.sellerMenuRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        sellerMenuRV.setLayoutManager(linearLayoutManager);
        sellerMenuRV.setAdapter(sellerActivityAdapter);

        ItemModel itemModel= new ItemModel();
        itemModel.setId(2);
        itemModel.setCategory("delivered");
        itemModel.setIsAvailable(1);
        itemModel.setIsDelete(0);
        itemModel.setIsVeg(0);
        itemModel.setName("pizza");
        itemModel.setPrice(60.0);
        itemModel.setPhotoUrl(" ");
        ItemModel.add(itemModel);


        ItemModel itemModel1= new ItemModel();
        itemModel1.setId(1);
        itemModel1.setCategory("delivered");
        itemModel1.setIsAvailable(1);
        itemModel1.setIsDelete(0);
        itemModel1.setIsVeg(1);
        itemModel1.setName("biryani");
        itemModel1.setPrice(50.0);
        itemModel1.setPhotoUrl(" ");
        ItemModel.add(itemModel1);

        //todo toggle veg non-veg and change appropriately--M
        //todo add rupee symbol before edit text--M
        //todo toggle tick --M
        //todo image better for button--M
        //todo add a button below recycler view to update to show items that have been checked in the logcat by adding Log.d by adding itemmodel.tostring

        sellerActivityAdapter.setItemModelList(ItemModel);
        sellerActivityAdapter.notifyDataSetChanged();

    }
}