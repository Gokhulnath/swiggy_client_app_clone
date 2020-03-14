package golhar.cocomo.zinger.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import golhar.cocomo.zinger.R;

public class Frag3 extends Fragment {

    ImageView iv_image;


    public Frag3()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag1,container,false);
         iv_image=view.findViewById(R.id.iv_frag1);
        iv_image.setImageResource(R.drawable.img3);

        TextView tv_text=view.findViewById(R.id.Frag1TV);
        tv_text.setText("Cash on Delivery");


        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        if(menuVisible){
            YoYo.with(Techniques.Bounce)
                    .duration(900)
                    .repeat(2)
                    .playOn(iv_image);
        }
        super.setMenuVisibility(menuVisible);
    }
}
