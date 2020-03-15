package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;
import golhar.cocomo.zinger.utils.SharedPref;

public class RecyclerViewCollegeListAdapter extends RecyclerView.Adapter<RecyclerViewCollegeListAdapter.CollegeNameHolder> {


    ArrayList<ItemModel> itemsArrayList;
    Context context;

    public RecyclerViewCollegeListAdapter(ArrayList<ItemModel> itemsArrayList,Context context) {
        this.itemsArrayList=itemsArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public CollegeNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.college_list_item,parent,false);
        CollegeNameHolder collegeNameHolder =new CollegeNameHolder(v);
        return collegeNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeNameHolder holder, int position) {
        final ItemModel itemModel=itemsArrayList.get(position);

        holder.tv_item_name.setText(itemModel.getName());
        Glide.with(context)
                .load("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg")
                .into(holder.iv_item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.putString(context,"selected_college",itemModel.getName());
                Toast.makeText(context, itemModel.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class CollegeNameHolder extends RecyclerView.ViewHolder{

        TextView tv_item_name;
        ImageView iv_item;

        public CollegeNameHolder(@NonNull View itemView) {
            super(itemView);
            iv_item=itemView.findViewById(R.id.food_itemIV);
            tv_item_name=itemView.findViewById(R.id.itemTV);

        }
    }
}
