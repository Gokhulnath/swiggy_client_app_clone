package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;

public class SellerActivityAdapter extends RecyclerView.Adapter<SellerActivityAdapter.SellerHolder> {
    List<ItemModel> itemModelList;
    Context context, activityContext;


    public SellerActivityAdapter(List<ItemModel> itemModelList, Context context, Context activityContext) {
        this.itemModelList = itemModelList;
        this.context = context;
        this.activityContext = activityContext;
    }

    public void setItemModelList(List<ItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public SellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_menu, parent, false);
        SellerActivityAdapter.SellerHolder sellerHolder = new SellerActivityAdapter.SellerHolder(v);
        return sellerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellerHolder holder, int position) {
        final ItemModel itemModel = itemModelList.get(position);
        //holder.checkboxCB.setText(String.valueOf(itemModel.getIsDelete()));
        holder.checkboxCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemModel.setIsDelete(1);
            }
        });
        holder.foodTV.setText(itemModel.getName());
        holder.priceTV.setEnabled(false);
        holder.priceTV.setText(String.valueOf(itemModel.getPrice()));

//        if(itemModel.getIsVeg()==1)
//        {
//            holder.vegIV.setImageDrawable(getContext().getDrawable(R.drawable.ic_non_vegetarian_mark));
//        }
        holder.editBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    holder.priceTV.setEnabled(true);
                    holder.editBT.setBackground(getContext().getDrawable(R.drawable.ic_check));
                } else {
                    // The toggle is disabled
                    holder.priceTV.setEnabled(false);
                    holder.editBT.setBackground(getContext().getDrawable(R.drawable.ic_pencil));
                }
            }
        });
        holder.vegBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    holder.vegBT.setBackground(getContext().getDrawable(R.drawable.ic_vegetarian_mark));
                    holder.vegBT.setText(String.valueOf(itemModel.getIsVeg()));
                } else {
                    // The toggle is disabled
                    holder.vegBT.setBackground(getContext().getDrawable(R.drawable.ic_non_vegetarian_mark));
                    holder.vegBT.setText(String.valueOf(itemModel.getIsVeg()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public class SellerHolder extends RecyclerView.ViewHolder {
        CheckBox checkboxCB;
        ToggleButton vegBT;
        TextView foodTV;
        EditText priceTV;
        ToggleButton editBT;


        public SellerHolder(@NonNull View itemView) {
            super(itemView);
            checkboxCB = itemView.findViewById(R.id.checkboxCB);
            vegBT = itemView.findViewById(R.id.vegBT);
            foodTV = itemView.findViewById(R.id.foodTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            editBT = itemView.findViewById(R.id.editBT);
        }
    }
}