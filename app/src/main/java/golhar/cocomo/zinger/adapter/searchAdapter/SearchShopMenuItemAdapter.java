package golhar.cocomo.zinger.adapter.searchAdapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;
import golhar.cocomo.zinger.model.OrderItemModel;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.SharedPref;


public class SearchShopMenuItemAdapter extends RecyclerView.Adapter<SearchShopMenuItemAdapter.ItemNameHolder> {
    ArrayList<ItemModel> itemModelArrayList;
    Context context;
    ArrayList<OrderItemModel> orderItemModelArrayList = new ArrayList<>();

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
        for (int i = 0; i < RetrieveData().size(); i++) {
            if (itemModel.getId() == RetrieveData().get(i).getItemModel().getId()) {
                holder.numberButtonENB.setNumber(RetrieveData().get(i).getQuantity().toString());
                holder.addItemBT.setVisibility(View.GONE);
                holder.numberButtonENB.setVisibility(View.VISIBLE);
            }
            else{
                holder.addItemBT.setVisibility(View.VISIBLE);
                holder.numberButtonENB.setVisibility(View.GONE);
            }
        }
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
        holder.addItemBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                if (SharedPref.getInt(context, Constants.cartShopId) == -1 || itemModel.getShopModel().getId()==SharedPref.getInt(context, Constants.cartShopId) || RetrieveData().size()==0) {
                    SharedPref.putInt(context,Constants.cartShopId,itemModel.getShopModel().getId());
                    SharedPref.putString(context,Constants.cartShopName,itemModel.getShopModel().getName());
                    holder.addItemBT.setVisibility(View.GONE);
                    holder.numberButtonENB.setNumber(Integer.toString(1));
                    holder.numberButtonENB.setVisibility(View.VISIBLE);
                    OrderItemModel orderItemModel = new OrderItemModel();
                    orderItemModel.setItemModel(itemModel);
                    orderItemModel.setQuantity(1);
                    orderItemModel.setPrice(itemModel.getPrice());
                    ArrayList<OrderItemModel> orderItemModelArrayList2 = RetrieveData();
                    orderItemModelArrayList2.add(orderItemModel);
                    LoadData(orderItemModelArrayList2);
                }
                else{
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    View v = LayoutInflater.from(context).inflate(R.layout.activity_cart_shop_change_prompt, null);
                    TextView msgTV = v.findViewById(R.id.msgTV);
                    Button noBT = v.findViewById(R.id.noBT);
                    Button yesBT = v.findViewById(R.id.yesBT);
                    dialogBuilder.setView(v);
                    AlertDialog dialog = dialogBuilder.create();
                    msgTV.setText("Your cart contains dishes from "+SharedPref.getString(context,Constants.cartShopName)+". Do you want to discard the selection and add dishes from "+itemModel.getShopModel().getName()+"?");
                    dialog.show();
                    yesBT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPref.putInt(context,Constants.cartShopId,itemModel.getShopModel().getId());
                            ArrayList<OrderItemModel> orderItemModelArrayList = new ArrayList<>();
                            LoadData(orderItemModelArrayList);
                            SharedPref.putString(context,Constants.cartShopName,itemModel.getShopModel().getName());
                            holder.addItemBT.setVisibility(View.GONE);
                            holder.numberButtonENB.setNumber(Integer.toString(1));
                            holder.numberButtonENB.setVisibility(View.VISIBLE);
                            OrderItemModel orderItemModel = new OrderItemModel();
                            orderItemModel.setItemModel(itemModel);
                            orderItemModel.setQuantity(1);
                            orderItemModel.setPrice(itemModel.getPrice());
                            ArrayList<OrderItemModel> orderItemModelArrayList2 = RetrieveData();
                            orderItemModelArrayList2.add(orderItemModel);
                            LoadData(orderItemModelArrayList2);
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }
                    });
                    noBT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        holder.numberButtonENB.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            ArrayList<OrderItemModel> orderItemModelArrayList1 = new ArrayList<>();
            if (holder.numberButtonENB.getNumber().equals(Integer.toString(0))) {
                holder.numberButtonENB.setVisibility(View.GONE);
                holder.addItemBT.setVisibility(View.VISIBLE);
                ArrayList<OrderItemModel> orderItemModelArrayList = RetrieveData();
                int itemId = itemModel.getId();
                for (int i = 0; i < orderItemModelArrayList.size(); i++) {
                    if (orderItemModelArrayList.get(i).getItemModel().getId() != itemId) {
                        orderItemModelArrayList1.add(orderItemModelArrayList.get(i));
                    }
                }
            } else {
                ArrayList<OrderItemModel> orderItemModelArrayList = RetrieveData();
                int itemId = itemModel.getId();
                for (int i = 0; i < orderItemModelArrayList.size(); i++) {
                    if (orderItemModelArrayList.get(i).getItemModel().getId() != itemId) {
                        orderItemModelArrayList1.add(orderItemModelArrayList.get(i));
                    } else {
                        OrderItemModel orderItemModel1 = new OrderItemModel();
                        orderItemModel1 = orderItemModelArrayList.get(i);
                        orderItemModel1.setQuantity(Integer.parseInt(holder.numberButtonENB.getNumber()));
                        orderItemModel1.setPrice(Integer.parseInt(holder.numberButtonENB.getNumber()) * orderItemModel1.getItemModel().getPrice());
                        orderItemModelArrayList1.add(orderItemModel1);
                    }
                }
            }
            LoadData(orderItemModelArrayList1);
        });

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
        ElegantNumberButton numberButtonENB;

        public ItemNameHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            typeTV = itemView.findViewById(R.id.typeTV);
            iconIV = itemView.findViewById(R.id.iconIV);
            clickableLL = itemView.findViewById(R.id.clickableLL);
            priceTV = itemView.findViewById(R.id.priceTV);
            addItemBT = itemView.findViewById(R.id.addItemBT);
            numberButtonENB = itemView.findViewById(R.id.numberButtonENB);
            numberButtonENB.setRange(0, 10);
        }
    }


    ArrayList<OrderItemModel> RetrieveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.sharedPreferencesCart, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constants.cart, null);
        Type type = new TypeToken<ArrayList<OrderItemModel>>() {
        }.getType();
        ArrayList<OrderItemModel> orderItemModelArrayList = gson.fromJson(json, type);
        return orderItemModelArrayList;
    }

    void LoadData(ArrayList<OrderItemModel> orderItemModelArrayList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.sharedPreferencesCart, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orderItemModelArrayList);
        editor.putString(Constants.cart, json);
        editor.apply();
    }
}
