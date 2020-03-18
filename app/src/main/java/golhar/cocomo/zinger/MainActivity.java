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
import golhar.cocomo.zinger.utils.SharedPref;

public class MainActivity extends AppCompatActivity {


    Button loginB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int loginStatus = SharedPref.getInt(getApplicationContext(), "loginStatus");
        if (loginStatus == 1) {
            Intent shopList = new Intent(MainActivity.this, ShopListActivity.class);
            startActivity(shopList);
        } else {
            ViewPager viewPager = findViewById(R.id.viewPager);

            ViewPageAdapter adpater = new ViewPageAdapter(getSupportFragmentManager());

            adpater.addFragment(new Frag1());
            adpater.addFragment(new Frag2());
            adpater.addFragment(new Frag3());

            viewPager.setAdapter(adpater);

            loginB = (Button) findViewById(R.id.loginB);
            loginB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}