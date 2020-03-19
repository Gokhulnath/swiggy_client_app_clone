package golhar.cocomo.zinger.utils;

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
