package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.ItemModel;

public class OrderHistoryDetailAdapter extends ArrayAdapter<ItemModel> {
    public OrderHistoryDetailAdapter(Context context, int resource, List<ItemModel> objects) {
        super(context, resource, objects);
    }

    public OrderHistoryDetailAdapter(@NonNull Context context, int resource) {
        super(context, resource);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ItemModel itemModel = getItem(position);
        TextView eachItemCostTV = convertView.findViewById(R.id.eachItemCostTV);
        TextView itemNameTV = convertView.findViewById(R.id.itemNameTV);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_order_items, parent, false);
        }
        if (itemModel.getIsVeg() == 0) {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_non_vegetarian_mark);
            itemNameTV.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        return convertView;
    }
}







