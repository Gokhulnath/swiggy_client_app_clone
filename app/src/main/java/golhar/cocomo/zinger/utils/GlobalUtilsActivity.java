package golhar.cocomo.zinger.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import golhar.cocomo.zinger.R;
import golhar.cocomo.zinger.fragment.CustomDialog;
import golhar.cocomo.zinger.service.DialogCallback;
//
//public class GlobalUtilsActivity {
//    public static void showDialog(Context context, DialogCallback dialogCallback){
//        final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialogTheme);
//        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v=inflater.inflate(R.layout.activity_rating_bar,null);
//
//        dialog.setContentView(v);
//
//        Button submitRatingBT = dialog.findViewById(R.id.submitRatingBT);
//
//        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
//
//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//
//            }
//        });
//        submitRatingBT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(dialogCallback != null)
//                        dialogCallback.callback("3.0");
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//}
