package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import golhar.cocomo.zinger.adapter.ViewPageAdapter;
import golhar.cocomo.zinger.fragment.Frag1;
import golhar.cocomo.zinger.fragment.Frag2;
import golhar.cocomo.zinger.fragment.Frag3;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.Callback;

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

        UserModel userModel=new UserModel();
        userModel.setMobile("9176019343");
        userModel.setOauthId("auth_9176019343");
        userModel.setRole(UserRole.CUSTOMER);
        userModel.setIsDelete(0);

        /* Gokhul example
        MainRepository.getService().insertCustomer(userModel).enqueue(new Callback<Response<UserCollegeModel>>() {
            @Override
            public void onResponse(Call<Response<UserCollegeModel>> call, retrofit2.Response<Response<UserCollegeModel>> response) {
                // success
                Response<UserCollegeModel> responseFromServer=response.body();
                if(responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.equals(ErrorLog.Success)){
                    Response<UserCollegeModel> userCollegeModelResponse=response.body();
                    Log.d("RetroFit",userCollegeModelResponse.toString());
                }else{
                    // TODO toast the message
                    Log.d("RetroFit","failure");
                }
            }

            @Override
            public void onFailure(Call<Response<UserCollegeModel>> call, Throwable t) {
                Log.d("RetroFit","failure");
            }
        });*/


    }
}