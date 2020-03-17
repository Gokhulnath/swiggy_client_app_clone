package golhar.cocomo.zinger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import golhar.cocomo.zinger.CollegeListActivity;
import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.model.CollegeModel;
import golhar.cocomo.zinger.utils.SharedPref;

public class RecyclerViewCollegeListAdapter extends RecyclerView.Adapter<RecyclerViewCollegeListAdapter.CollegeNameHolder> {


    ArrayList<CollegeModel> collegeArrayList;
    Context context;

    public RecyclerViewCollegeListAdapter(ArrayList<CollegeModel> itemsArrayList,Context context) {
        this.collegeArrayList =itemsArrayList;
        this.context=context;
    }

    public ArrayList<CollegeModel> getCollegeArrayList() {
        return collegeArrayList;
    }

    public void setCollegeArrayList(ArrayList<CollegeModel> collegeArrayList) {
        this.collegeArrayList = collegeArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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
        final CollegeModel collegeModel= collegeArrayList.get(position);

        holder.tv_item_name.setText(collegeModel.getName());
        holder.tv_item_address.setText(collegeModel.getAddress());
        Glide.with(context)
                .load("https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-oranges-ice-349x436.jpg")
                .into(holder.iv_item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.putString(context,"selected_college",collegeModel.getName());
                SharedPref.putInt(context,"selected_college_id",collegeModel.getId());
                ((CollegeListActivity)context).onBackPressed();
            }
        });

    }

    @Override
    public int getItemCount() {
        return collegeArrayList.size();
    }

    public class CollegeNameHolder extends RecyclerView.ViewHolder{

        TextView tv_item_name;
        TextView tv_item_address;
        CircleImageView iv_item;

        public CollegeNameHolder(@NonNull View itemView) {
            super(itemView);
            iv_item=itemView.findViewById(R.id.CollegeProfileImageCIV);
            tv_item_name=itemView.findViewById(R.id.CollegNameTV);
            tv_item_address=itemView.findViewById(R.id.CollegeAddressTV);

        }
    }
}
