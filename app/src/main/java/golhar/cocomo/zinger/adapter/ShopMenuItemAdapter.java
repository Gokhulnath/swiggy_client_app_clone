package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;


public class ShopMenuItemAdapter extends RecyclerView.Adapter<ShopMenuItemAdapter.ItemNameHolder> {
    ArrayList<ItemModel> itemModelArrayList;
    Context context;

    public ShopMenuItemAdapter(ArrayList<ItemModel> itemModelArrayList, Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_menu_item_list, parent, false);
        ItemNameHolder itemNameHolder = new ItemNameHolder(v);
        return itemNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNameHolder holder, int position) {
        final ItemModel itemModel = itemModelArrayList.get(position);
        Glide.with(context)
                .load(itemModel.getPhotoUrl())
                .placeholder(new ColorDrawable(Color.parseColor("#000000")))
                .into(holder.itemIconIV);
        holder.categoryTV.setText(itemModel.getCategory());
        holder.itemNameTV.setText(" " + itemModel.getName());
        if (itemModel.getIsVeg() == 0) {
            Drawable img = context.getResources().getDrawable(R.drawable.ic_non_vegetarian_mark);
            holder.itemNameTV.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        holder.priceTv.setText("₹" + itemModel.getPrice().toString());
        if (itemModel.getIsAvailable() == 0) {
            holder.addItemBT.setEnabled(false);
            holder.addItemBT.setText("Not\nAvailable");
            holder.addItemBT.setTextSize(10);
            holder.addItemBT.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.black));
            holder.addItemBT.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    public class ItemNameHolder extends RecyclerView.ViewHolder {

        ImageView itemIconIV;
        TextView categoryTV;
        TextView itemNameTV;
        TextView priceTv;
        Button addItemBT;

        public ItemNameHolder(@NonNull View itemView) {
            super(itemView);
            itemIconIV = itemView.findViewById(R.id.itemIconIV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            itemNameTV = itemView.findViewById(R.id.itemNameTV);
            priceTv = itemView.findViewById(R.id.priceTV);
            addItemBT = itemView.findViewById(R.id.addItemBT);

        }
    }
}
