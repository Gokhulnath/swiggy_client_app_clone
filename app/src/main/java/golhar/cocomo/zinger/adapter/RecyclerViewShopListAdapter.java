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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.RatingModel;

public class RecyclerViewShopListAdapter extends RecyclerView.Adapter<RecyclerViewShopListAdapter.ShopNameHolder> {
    String Time = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(Time);
    ArrayList<RatingModel> ratingModelArrayList;
    Context context;

    public RecyclerViewShopListAdapter(ArrayList<RatingModel> ratingModelArrayList, Context context) throws ParseException {
        this.ratingModelArrayList = ratingModelArrayList;
        this.context = context;
    }

    public ArrayList<RatingModel> getRatingModelArrayList() {
        return ratingModelArrayList;
    }

    public void setRatingModelArrayList(ArrayList<RatingModel> ratingModelArrayList) {
        this.ratingModelArrayList = ratingModelArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewShopListAdapter.ShopNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        ShopNameHolder shopNameHolder = new ShopNameHolder(v);
        return shopNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewShopListAdapter.ShopNameHolder holder, int position) {
        final RatingModel ratingModel = ratingModelArrayList.get(position);

        holder.restaurantNameTV.setText(ratingModel.getShopModel().getName());
        Date closingTime = ratingModel.getShopModel().getClosingTime();
        Date openingTime = ratingModel.getShopModel().getOpeningTime();
        if (currentTime.after(openingTime) && currentTime.before(closingTime)) {
            holder.statusTV.setText("Opened");
        } else {
            holder.statusTV.setText("Closed");
        }
        holder.ratingTV.setText(ratingModel.getRating().toString());
        holder.numberOfRatingTV.setText(ratingModel.getUserCount()+" ratings");
        Glide.with(context)
                .load(ratingModel.getShopModel().getPhotoUrl())
                .into(holder.shopIconIV);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShopNameHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameTV;
        TextView statusTV;
        TextView ratingTV;
        ImageView shopIconIV;
        TextView numberOfRatingTV;

        public ShopNameHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameTV = itemView.findViewById(R.id.restaurantNameTV);
            statusTV = itemView.findViewById(R.id.statusTV);
            ratingTV = itemView.findViewById(R.id.ratingTV);
            shopIconIV = itemView.findViewById(R.id.shopIconIV);
            numberOfRatingTV = itemView.findViewById(R.id.numberOfRatingTV);
        }
    }
}
