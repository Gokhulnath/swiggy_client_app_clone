package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import golhar.cocomo.zinger.adapter.ViewPageAdapter;
import golhar.cocomo.zinger.fragment.Frag1;
import golhar.cocomo.zinger.fragment.Frag2;
import golhar.cocomo.zinger.fragment.Frag3;

public class MainActivity extends AppCompatActivity {

    TextView Login_tv;
    Button location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager=findViewById(R.id.viewPager);

        ViewPageAdapter adpater=new ViewPageAdapter(getSupportFragmentManager());

        adpater.addFragment(new Frag1());
        adpater.addFragment(new Frag2());
        adpater.addFragment(new Frag3());

        viewPager.setAdapter(adpater);

        Login_tv = (TextView) findViewById(R.id.LoginTV);
        Login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login_activity = new Intent(MainActivity.this,Login.class);
                startActivity(Login_activity);
                //startActivity(new Intent(this, RecyclerViewDemoActivity.class));
            }
        });

        location = (Button) findViewById(R.id.locationB);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_loco = new Intent(MainActivity.this,CollegeList.class);
                startActivity(college_loco);
            }
        });
    }
}
//comment from mahima