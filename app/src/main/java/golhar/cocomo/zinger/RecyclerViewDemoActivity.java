package golhar.cocomo.zinger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import golhar.cocomo.zinger.adapter.RecyclerViewDemoAdapter;
import golhar.cocomo.zinger.model.ItemModel;

public class RecyclerViewDemoActivity extends AppCompatActivity {


    RecyclerView rv_items;
    RecyclerViewDemoAdapter demoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo_activity);


        ArrayList<ItemModel> itemModelArrayList=new ArrayList<>();

        ItemModel item=new ItemModel();
        item.setName("Item 1");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("Item 2");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("Item 3");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("Item 4");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("Item 5");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("Item 6");
        itemModelArrayList.add(item);

        demoAdapter=new RecyclerViewDemoAdapter(itemModelArrayList,this);
        rv_items=findViewById(R.id.rv_item_list);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setAdapter(demoAdapter);

    }
}
