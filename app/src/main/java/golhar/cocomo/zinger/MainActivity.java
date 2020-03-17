package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import golhar.cocomo.zinger.adapter.ViewPageAdapter;
import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.fragment.Frag1;
import golhar.cocomo.zinger.fragment.Frag2;
import golhar.cocomo.zinger.fragment.Frag3;
import golhar.cocomo.zinger.model.UserModel;

public class MainActivity extends AppCompatActivity {


    Button login;
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


        login = (Button) findViewById(R.id.loginB);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
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