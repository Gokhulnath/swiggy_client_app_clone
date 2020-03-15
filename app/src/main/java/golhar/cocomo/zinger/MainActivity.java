package golhar.cocomo.zinger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserModel userModel=new UserModel();
        userModel.setMobile("9176019343");
        userModel.setOauthId("auth_9176019343");
        userModel.setRole(UserRole.CUSTOMER);
        userModel.setIsDelete(0);

        // Gokhul example
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
        });


        // Mahima Example
        MainRepository.getService().getOrderByMobile("9566220635",1,5,"auth_9566220635",
                "9566220635",UserRole.CUSTOMER.name()).enqueue(new Callback<Response<List<OrderModel>>>() {
            @Override
            public void onResponse(Call<Response<List<OrderModel>>> call, retrofit2.Response<Response<List<OrderModel>>> response) {

                Response<List<OrderModel>> responseFromServer=response.body();

                if(responseFromServer.getCode().equals(ErrorLog.CodeSuccess)&&responseFromServer.getMessage().equals(ErrorLog.Success)){
                    Log.d("RetroFit",responseFromServer.toString());
                }else{
                    Log.d("RetroFit","errorr");
                }

            }

            @Override
            public void onFailure(Call<Response<List<OrderModel>>> call, Throwable t) {
                Log.d("RetroFit","errorr");
            }
        });




    }
}