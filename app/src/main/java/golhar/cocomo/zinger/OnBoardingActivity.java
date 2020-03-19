package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import golhar.cocomo.zinger.adapter.ViewPageAdapter;
import golhar.cocomo.zinger.fragment.Frag1;
import golhar.cocomo.zinger.fragment.Frag2;
import golhar.cocomo.zinger.fragment.Frag3;

public class OnBoardingActivity extends AppCompatActivity {


    Button loginBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPageAdapter adpater = new ViewPageAdapter(getSupportFragmentManager());

        adpater.addFragment(new Frag1());
        adpater.addFragment(new Frag2());
        adpater.addFragment(new Frag3());

        viewPager.setAdapter(adpater);

        loginBT = findViewById(R.id.loginBT);
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }
}