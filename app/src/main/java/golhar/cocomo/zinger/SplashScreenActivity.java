package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.SharedPref;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView logoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoIV = findViewById(R.id.logoIV);
        YoYo.with(Techniques.Flash)
                .duration(900)
                .repeat(2)
                .playOn(findViewById(R.id.logoIV));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int loginStatus = SharedPref.getInt(getApplicationContext(), Constants.loginStatus);
                if (loginStatus == 1) {
                    Intent shopList = new Intent(SplashScreenActivity.this, ShopListActivity.class);
                    startActivity(shopList);
                    finish();
                } else {
                    Intent main = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                    startActivity(main);
                    finish();
                }
            }
        }, 2000);

    }
}
