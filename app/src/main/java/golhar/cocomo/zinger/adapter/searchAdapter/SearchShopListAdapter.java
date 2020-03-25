package golhar.cocomo.zinger.adapter.searchAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.ShopMenuItemListActivity;
import golhar.cocomo.zinger.model.ShopConfigurationModel;

import static android.graphics.Color.parseColor;

public class SearchShopListAdapter extends RecyclerView.Adapter<SearchShopListAdapter.ShopNameHolder> {
    String Time = new SimpleDateFormat("HH:mm:ss").format(new Date());
    Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(Time);
    ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList;
    Context context;

    public SearchShopListAdapter(ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList, Context context) throws ParseException {
        this.shopConfigurationModelArrayList = shopConfigurationModelArrayList;
        this.context = context;
    }

    public ArrayList<ShopConfigurationModel> getShopConfigurationModelArrayList() {
        return shopConfigurationModelArrayList;
    }

    public void setShopConfigurationModelArrayList(ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList) {
        this.shopConfigurationModelArrayList = shopConfigurationModelArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchShopListAdapter.ShopNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_food_restaurant_item, parent, false);
        ShopNameHolder shopNameHolder = new ShopNameHolder(v);
        return shopNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchShopListAdapter.ShopNameHolder holder, int position) {
        final ShopConfigurationModel shopConfigurationModel = shopConfigurationModelArrayList.get(position);
        holder.addItemBT.setVisibility(View.INVISIBLE);
        if (shopConfigurationModel.getConfigurationModel().getIsOrderTaken().equals(0)) {
            holder.priceTV.setText("Currently not taking orders");
            holder.priceTV.setTextColor(parseColor("#ff0000"));;
            holder.clickableLL.setEnabled(false);
            holder.clickableLL.setBackgroundColor(Color.parseColor("#EBEBEB"));
        }
        else{
            holder.clickableLL.setEnabled(true);
            holder.specialItemLL.setVisibility(View.GONE);
            holder.clickableLL.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.nameTV.setText(shopConfigurationModel.getShopModel().getName());
        holder.nameTV.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        Glide.with(context)
                .load(shopConfigurationModel.getShopModel().getPhotoUrl())
                .placeholder(new ColorDrawable(Color.parseColor("#000000")))
                .into(holder.iconIV);
        holder.typeTV.setText("Restaurant");

        holder.clickableLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopItemList = new Intent(context, ShopMenuItemListActivity.class);
                shopItemList.putExtra("shopDetails", shopConfigurationModel);
                context.startActivity(shopItemList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopConfigurationModelArrayList.size();
    }

    public class ShopNameHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView typeTV;
        ImageView iconIV;
        LinearLayout clickableLL;
        LinearLayout specialItemLL;
        TextView priceTV;
        Button addItemBT;


        public ShopNameHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            typeTV = itemView.findViewById(R.id.typeTV);
            iconIV = itemView.findViewById(R.id.iconIV);
            clickableLL = itemView.findViewById(R.id.clickableLL);
            specialItemLL = itemView.findViewById(R.id.specialItemLL);
            priceTV = itemView.findViewById(R.id.priceTV);
            addItemBT = itemView.findViewById(R.id.addItemBT);
        }
    }
}
