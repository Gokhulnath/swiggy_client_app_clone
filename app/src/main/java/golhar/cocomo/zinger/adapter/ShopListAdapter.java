package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopNameHolder> {
    String Time = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(Time);
    ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList;
    Context context;

    public ShopListAdapter(ArrayList<ShopConfigurationModel> shopConfigurationModelArrayList, Context context) throws ParseException {
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
    public ShopListAdapter.ShopNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        ShopNameHolder shopNameHolder = new ShopNameHolder(v);
        return shopNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ShopNameHolder holder, int position) {
        final ShopConfigurationModel shopConfigurationModel = shopConfigurationModelArrayList.get(position);
        holder.restaurantNameTV.setText(shopConfigurationModel.getShopModel().getName());
        Date closingTime = shopConfigurationModel.getShopModel().getClosingTime();
        Date openingTime = shopConfigurationModel.getShopModel().getOpeningTime();
        if (shopConfigurationModel.getConfigurationModel().getIsOrderTaken().equals(0)) {
            holder.cliclableLL.setEnabled(false);
            holder.statusTV.setText("Currently not taking orders");
            holder.statusTV.setTextColor(parseColor("#ff0000"));
            holder.cliclableLL.setBackgroundColor(Color.parseColor("#EBEBEB"));
        } else if (currentTime.after(openingTime) && currentTime.before(closingTime)) {
            holder.statusTV.setText("Opened");
            holder.statusTV.setTextColor(parseColor("#008000"));
        } else {
            holder.statusTV.setText("Closed");
            holder.statusTV.setTextColor(parseColor("#ff0000"));
            holder.cliclableLL.setBackgroundColor(Color.parseColor("#EBEBEB"));
        }
        if(shopConfigurationModel.getRatingModel()==null || shopConfigurationModel.getRatingModel().getRating().equals(0) || shopConfigurationModel.getRatingModel().getUserCount().equals(0)) {
            holder.ratingTV.setText("No ratings");
            holder.numberOfRatingTV.setVisibility(View.INVISIBLE);
        }
        else{
            holder.ratingTV.setText(shopConfigurationModel.getRatingModel().getRating().toString());
            holder.numberOfRatingTV.setText("(" + shopConfigurationModel.getRatingModel().getUserCount().toString() + ")");
        }
        Glide.with(context)
                .load(shopConfigurationModel.getShopModel().getPhotoUrl())
                .placeholder(new ColorDrawable(Color.parseColor("#000000")))
                .into(holder.shopIconIV);
        holder.cliclableLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopItemList = new Intent(context, ShopMenuItemListActivity.class);
                shopItemList.putExtra("shopDetails",shopConfigurationModel);
                context.startActivity(shopItemList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopConfigurationModelArrayList.size();
    }

    public class ShopNameHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameTV;
        TextView statusTV;
        TextView ratingTV;
        ImageView shopIconIV;
        TextView numberOfRatingTV;
        LinearLayout cliclableLL;

        public ShopNameHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.restaurantNameTV);
            statusTV = itemView.findViewById(R.id.statusTV);
            ratingTV = itemView.findViewById(R.id.ratingTV);
            shopIconIV = itemView.findViewById(R.id.shopIconIV);
            numberOfRatingTV = itemView.findViewById(R.id.numberOfRatingTV);
            cliclableLL = itemView.findViewById(R.id.clickableLL);
        }
    }
}
