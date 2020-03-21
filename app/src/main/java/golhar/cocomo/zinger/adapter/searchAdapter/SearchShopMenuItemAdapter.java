package golhar.cocomo.zinger.adapter.searchAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;


public class SearchShopMenuItemAdapter extends RecyclerView.Adapter<SearchShopMenuItemAdapter.ItemNameHolder> {
    ArrayList<ItemModel> itemModelArrayList;
    Context context;

    public SearchShopMenuItemAdapter(ArrayList<ItemModel> itemModelArrayList, Context context) {
        this.itemModelArrayList = itemModelArrayList;
        this.context = context;
    }

    public ArrayList<ItemModel> getItemModelArrayList() {
        return itemModelArrayList;
    }

    public void setItemModelArrayList(ArrayList<ItemModel> itemModelArrayList) {
        this.itemModelArrayList = itemModelArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_food_restaurant_item, parent, false);
        ItemNameHolder itemNameHolder = new ItemNameHolder(v);
        return itemNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNameHolder holder, int position) {
        final ItemModel itemModel = itemModelArrayList.get(position);
        holder.nameTV.setText(" " + itemModel.getName());
        if (itemModel.getIsVeg() == 0) {
            Drawable img = context.getResources().getDrawable(R.drawable.ic_non_vegetarian_mark);
            holder.nameTV.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        Glide.with(context)
                .load(itemModel.getPhotoUrl())
                .placeholder(new ColorDrawable(Color.parseColor("#000000")))
                .into(holder.iconIV);
        holder.typeTV.setText(itemModel.getShopModel().getName());
        holder.priceTV.setText("₹" + itemModel.getPrice());
        if (itemModel.getIsAvailable() == 0) {
            holder.addItemBT.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    public class ItemNameHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        TextView typeTV;
        ImageView iconIV;
        LinearLayout clickableLL;
        TextView priceTV;
        Button addItemBT;

        public ItemNameHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            typeTV = itemView.findViewById(R.id.typeTV);
            iconIV = itemView.findViewById(R.id.iconIV);
            clickableLL = itemView.findViewById(R.id.clickableLL);
            priceTV = itemView.findViewById(R.id.priceTV);
            addItemBT = itemView.findViewById(R.id.addItemBT);
        }
    }
}
