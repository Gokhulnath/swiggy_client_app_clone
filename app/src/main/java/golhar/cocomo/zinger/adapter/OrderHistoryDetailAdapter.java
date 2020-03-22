package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.OrderItemModel;

public class OrderHistoryDetailAdapter extends ArrayAdapter<OrderItemModel> {

    Context context;
    int resource;
    List<OrderItemModel> objects =null;
    public OrderHistoryDetailAdapter(Context context, int resource, List<OrderItemModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final OrderItemModel orderItemModel = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_order_items, parent, false);
        }
        TextView eachItemCostTV = convertView.findViewById(R.id.eachItemCostTV);
        TextView itemNameTV = convertView.findViewById(R.id.itemNameTV);
        if (orderItemModel.getItemModel().getIsVeg() == 0) {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_non_vegetarian_mark);
            itemNameTV.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        itemNameTV.setText(" "+ orderItemModel.getItemModel().getName()+" X "+orderItemModel.getQuantity());
        eachItemCostTV.setText("₹"+ orderItemModel.getPrice().toString());
        return convertView;
    }
}







