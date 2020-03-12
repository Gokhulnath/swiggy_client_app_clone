package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;

public class RecyclerViewDemoAdapter extends RecyclerView.Adapter<RecyclerViewDemoAdapter.DemoHolder> {


    ArrayList<ItemModel> itemsArrayList;
    Context context;

    public RecyclerViewDemoAdapter(ArrayList<ItemModel> itemsArrayList,Context context) {
        this.itemsArrayList = itemsArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public DemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_order_item,parent,false);
        DemoHolder demoHolder=new DemoHolder(v);
        return demoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DemoHolder holder, int position) {

        final ItemModel itemModel=itemsArrayList.get(position);

        holder.tv_item_name.setText(itemModel.getName());
        //holder.iv_item.setImageResource(R.drawable.ic_launcher_background);
        Glide.with(context)
                .load("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg")
                .into(holder.iv_item);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class DemoHolder extends RecyclerView.ViewHolder{

        ImageView iv_item;
        TextView tv_item_name;

        public DemoHolder(@NonNull View itemView) {
            super(itemView);
            iv_item=itemView.findViewById(R.id.food_itemIV);
            tv_item_name=itemView.findViewById(R.id.itemTV);

        }
    }
}
