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

public class Frag1 extends Fragment {

    public Frag1()
    { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag1,container,false);
        ImageView iv_image=view.findViewById(R.id.frag1IV);
        iv_image.setImageResource(R.drawable.img1);

        TextView tv_text=view.findViewById(R.id.Frag1TV);
        tv_text.setText("Fresh food\nlower price");
        YoYo.with(Techniques.Shake)
                .duration(900)
                .repeat(2)
                .playOn(view.findViewById(R.id.frag1IV));


        return view;
    }
}
