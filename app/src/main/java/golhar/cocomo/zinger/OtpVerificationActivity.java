package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class OtpVerificationActivity extends AppCompatActivity {

    Button otpVerfiedB;
    TextView numberTV;
    TextInputEditText otpTIET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        int firebaseResponse = 1; //Temp for firebase authentication
        String phoneNumber = SharedPref.getString(getApplicationContext(), "phone_number");
        String authId = "auth_" + phoneNumber;
        otpVerfiedB = (Button) findViewById(R.id.otpVerfiedB);
        numberTV = (TextView) findViewById(R.id.numberTV);
        numberTV.setText("OTP sent to " + phoneNumber);
        otpTIET = (TextInputEditText) findViewById(R.id.otpTIET);
        otpVerfiedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpTIET.getText().toString().matches("")) {
                    otpTIET.setError("Invalid otp");
                } else {
                    if (firebaseResponse == 1) {
                        //check firebase connection
                        //get auth id and assign it to authid or shared pref
                        UserModel userModel = new UserModel();
                        userModel.setMobile(phoneNumber);
                        userModel.setOauthId(authId);
                        userModel.setRole(UserRole.CUSTOMER);
                        userModel.setIsDelete(0);
                        MainRepository.getUserService().insertCustomer(userModel).enqueue(new Callback<Response<UserCollegeModel>>() {
                            @Override
                            public void onResponse(Call<Response<UserCollegeModel>> call, retrofit2.Response<Response<UserCollegeModel>> response) {
                                Response<UserCollegeModel> userDataResponse = response.body();
                                if (userDataResponse.getCode().equals(ErrorLog.CodeSuccess) && userDataResponse.getMessage().equals(ErrorLog.Success)) {
                                    Intent shopList = new Intent(OtpVerificationActivity.this, ShopListActivity.class);
                                    startActivity(shopList);
                                } else {
                                    if (userDataResponse.getCode().equals(ErrorLog.CodeSuccess) && userDataResponse.getMessage().equals(ErrorLog.CollegeDetailNotAvailable)) {
                                        SharedPref.putString(getApplicationContext(), "AuthId", authId);
                                        Intent registration = new Intent(OtpVerificationActivity.this, RegistrationActivity.class);
                                        startActivity(registration);
                                    } else {
                                        Toast.makeText(OtpVerificationActivity.this, "ERROR OCCURED", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<UserCollegeModel>> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });
    }
}