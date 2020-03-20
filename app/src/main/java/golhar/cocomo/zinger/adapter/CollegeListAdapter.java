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

public class CollegeListAdapter extends RecyclerView.Adapter<CollegeListAdapter.CollegeNameHolder> {
    ArrayList<CollegeModel> collegeArrayList;
    Context context;

    public CollegeListAdapter(ArrayList<CollegeModel> itemsArrayList, Context context) {
        this.collegeArrayList = itemsArrayList;
        this.context = context;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_list_item, parent, false);
        CollegeNameHolder collegeNameHolder = new CollegeNameHolder(v);
        return collegeNameHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeNameHolder holder, int position) {
        final CollegeModel collegeModel = collegeArrayList.get(position);
        holder.collegeNameTV.setText(collegeModel.getName());
        holder.collegeAddressTV.setText(collegeModel.getAddress());
        Glide.with(context)
                .load(collegeModel.getIconUrl())
                .into(holder.collegeProfileImageCIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.putString(context, "selectedCollege", collegeModel.getName());
                SharedPref.putInt(context, "selectedCollegeId", collegeModel.getId());
                ((CollegeListActivity) context).onBackPressed();
            }
        });
    }

    @Override
    public int getItemCount() {
        return collegeArrayList.size();
    }

    public class CollegeNameHolder extends RecyclerView.ViewHolder {

        TextView collegeNameTV;
        TextView collegeAddressTV;
        CircleImageView collegeProfileImageCIV;

        public CollegeNameHolder(@NonNull View itemView) {
            super(itemView);
            collegeProfileImageCIV = itemView.findViewById(R.id.collegeProfileImageCIV);
            collegeNameTV = itemView.findViewById(R.id.collegeNameTV);
            collegeAddressTV = itemView.findViewById(R.id.collegeAddressTV);
        }
    }
}
