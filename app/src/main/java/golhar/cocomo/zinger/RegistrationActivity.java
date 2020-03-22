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
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationActivity extends AppCompatActivity {

    TextView collegeTV;
    TextInputEditText nameTIET;
    TextInputEditText emailTIET;
    Button registerBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        collegeTV = findViewById(R.id.collegeTV);
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
        String phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        String authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        int collegeID;
        String collegeName;
        nameTIET = findViewById(R.id.nameTIET);
        emailTIET = findViewById(R.id.emailTIET);
        registerBT = findViewById(R.id.registerBT);
        collegeName = SharedPref.getString(getApplicationContext(), "selectedCollege");
        collegeID = SharedPref.getInt(getApplicationContext(), "selectedCollegeId");
        collegeTV.setText(collegeName);
        SharedPref.remove(getApplicationContext(), "selectedCollege");
        SharedPref.remove(getApplicationContext(), "selectedCollegeId");
        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameTIET.getText().toString().equals("")) {
                    nameTIET.setError("Please enter your name");
                } else if (emailTIET.getText().toString().equals("")) {
                    emailTIET.setError("Please enter your email");
                } else if (collegeTV.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please choose your college", Toast.LENGTH_SHORT).show();
                } else {
                    String userName;
                    String userEmail;
                    userName = nameTIET.getText().toString();
                    userEmail = emailTIET.getText().toString();
                    SharedPref.putString(getApplicationContext(), Constants.userName, userName);
                    SharedPref.putString(getApplicationContext(), Constants.userEmail, userEmail);
                    SharedPref.putInt(getApplicationContext(),Constants.collegeId,collegeID);
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
                                SharedPref.putInt(getApplicationContext(), Constants.loginStatus, 1);
                                Intent shopList = new Intent(RegistrationActivity.this, ShopListActivity.class);
                                startActivity(shopList);
                            } else {
                                Toast.makeText(RegistrationActivity.this, userDataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<String>> call, Throwable t) {
                            Toast.makeText(RegistrationActivity.this, "Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
