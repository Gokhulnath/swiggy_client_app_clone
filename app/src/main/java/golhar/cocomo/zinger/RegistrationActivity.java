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
import golhar.cocomo.zinger.model.CollegeModel;
import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationActivity extends AppCompatActivity {

    TextView collegeTV;
    TextInputEditText nameTIET;
    TextInputEditText emailTIET;
    Button registerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        collegeTV = (TextView) findViewById(R.id.collegeTV);
        collegeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_select = new Intent(RegistrationActivity.this, CollegeListActivity.class);
                startActivity(college_select);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String phoneNumber = SharedPref.getString(getApplicationContext(), "phone_number");
        String authId = "auth_" + phoneNumber;
        int collegeID;
        String collegeName;
        nameTIET = (TextInputEditText) findViewById(R.id.nameTIET);
        emailTIET = (TextInputEditText) findViewById(R.id.emailTIET);
        registerB = (Button) findViewById(R.id.registerB);
        collegeName = SharedPref.getString(getApplicationContext(), "selected_college");
        collegeID = SharedPref.getInt(getApplicationContext(), "selected_college_id");
        collegeTV.setText(collegeName);
        SharedPref.remove(getApplicationContext(), "selected_college");
        SharedPref.remove(getApplicationContext(), "selected_college_id");

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameTIET.getText().toString().equals("")) {
                    nameTIET.setError("Please enter your name");
                } else {
                    if (emailTIET.getText().toString().equals("")) {
                        emailTIET.setError("Please enter your email");
                    } else {
                        String userName;
                        String userEmail;
                        userName = nameTIET.getText().toString();
                        userEmail = emailTIET.getText().toString();
                        SharedPref.putString(getApplicationContext(), "userName", userName);
                        SharedPref.putString(getApplicationContext(), "userEmail", userEmail);
                        UserCollegeModel userCollegeModel = new UserCollegeModel();
                        UserModel userModel = new UserModel();
                        userModel.setName(userName);
                        userModel.setEmail(userEmail);
                        userModel.setMobile(phoneNumber);
                        userModel.setOauthId(authId);
                        userModel.setRole(UserRole.CUSTOMER);
                        userModel.setIsDelete(0);
                        CollegeModel collegeModel = new CollegeModel();
                        collegeModel.setId(collegeID);
                        userCollegeModel.setUserModel(userModel);
                        userCollegeModel.setCollegeModel(collegeModel);
                        MainRepository.getUserService().updateUserCollegeData(userCollegeModel, authId, phoneNumber, UserRole.CUSTOMER.toString()).enqueue(new Callback<Response<String>>() {
                            @Override
                            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                                Response<String> userDataResponse = response.body();
                                if (userDataResponse.getCode().equals(ErrorLog.CodeSuccess) && userDataResponse.getMessage().equals(ErrorLog.Success) && userDataResponse.getData().equals(ErrorLog.Success)) {
                                    Intent shopList = new Intent(RegistrationActivity.this, ShopListActivity.class);
                                    startActivity(shopList);
                                } else {
                                    if (userDataResponse.getCode().equals(ErrorLog.CodeSuccess) && userDataResponse.getMessage().equals(ErrorLog.Success) && userDataResponse.getData().equals(ErrorLog.CollegeDetailNotUpdated)) {
                                        Toast.makeText(RegistrationActivity.this, "Please Select your college", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (userDataResponse.getCode().equals(ErrorLog.CodeFailure) && userDataResponse.getMessage().equals(ErrorLog.Failure)) {
                                            Toast.makeText(RegistrationActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<String>> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });
    }
}
