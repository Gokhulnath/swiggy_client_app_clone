package golhar.cocomo.zinger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import golhar.cocomo.zinger.adapter.RecyclerViewCollegeListAdapter;
import golhar.cocomo.zinger.model.ItemModel;

public class
CollegeList extends AppCompatActivity {
    RecyclerView rv_items;
    RecyclerViewCollegeListAdapter collegeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);


        ArrayList<ItemModel> itemModelArrayList=new ArrayList<>();

        ItemModel item=new ItemModel();
        item.setName("ssn");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("srm");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("vit");
        itemModelArrayList.add(item);

        item=new ItemModel();
        item.setName("rec");
        itemModelArrayList.add(item);


        collegeAdapter =new RecyclerViewCollegeListAdapter(itemModelArrayList,this);
        rv_items=findViewById(R.id.item_listRV);



        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setAdapter(collegeAdapter);

    }
}
