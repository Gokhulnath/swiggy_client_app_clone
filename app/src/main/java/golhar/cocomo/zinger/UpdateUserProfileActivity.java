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

public class UpdateUserProfileActivity extends AppCompatActivity {
    TextView collegeTV;
    TextInputEditText nameTIET;
    TextInputEditText emailTIET;
    Button updateBT;
    String name;
    String email;
    String collegeName;
    String phoneNumber;
    String authId;
    int collegeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        collegeTV = findViewById(R.id.collegeTV);
        nameTIET = findViewById(R.id.nameTIET);
        emailTIET = findViewById(R.id.emailTIET);
        updateBT = findViewById(R.id.updateBT);
        name = SharedPref.getString(getApplicationContext(), Constants.userName);
        email = SharedPref.getString(getApplicationContext(), Constants.userEmail);
        emailTIET.setText(email);
        nameTIET.setText(name);
        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });
        collegeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_select = new Intent(UpdateUserProfileActivity.this, CollegeListActivity.class);
                startActivity(college_select);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        phoneNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        authId = SharedPref.getString(getApplicationContext(), Constants.authId);
        collegeName = SharedPref.getString(getApplicationContext(), "selectedCollege");
        collegeID = SharedPref.getInt(getApplicationContext(), "selectedCollegeId");
        if (collegeName == null) {
            collegeName = SharedPref.getString(getApplicationContext(), Constants.collegeName);
            collegeID = SharedPref.getInt(getApplicationContext(), Constants.collegeId);
        }
        collegeTV.setText(collegeName);
        SharedPref.remove(getApplicationContext(),"selectedCollege");
        SharedPref.remove(getApplicationContext(),"selectedCollegeId");
        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });
    }

    void updateUserDetails() {
        if (nameTIET.getText().toString().equals("")) {
            nameTIET.setError("Please enter your name");
        } else if (emailTIET.getText().toString().equals("")) {
            emailTIET.setError("Please enter your email");
        } else if (collegeTV.getText().toString().equals("")) {
            Toast.makeText(UpdateUserProfileActivity.this, "Please choose your college", Toast.LENGTH_SHORT).show();
        } else {
            UserCollegeModel userCollegeModel = new UserCollegeModel();
            CollegeModel collegeModel = new CollegeModel();
            UserModel userModel = new UserModel();
            userModel.setName(nameTIET.getText().toString());
            userModel.setEmail(emailTIET.getText().toString());
            userModel.setMobile(phoneNumber);
            userModel.setRole(UserRole.CUSTOMER);
            collegeModel.setId(collegeID);
            userCollegeModel.setUserModel(userModel);
            userCollegeModel.setCollegeModel(collegeModel);

            MainRepository.getUserService().updateUserCollegeData(userCollegeModel, authId, phoneNumber, UserRole.CUSTOMER.name()).enqueue(new Callback<Response<String>>() {
                @Override
                public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                    Response<String> responseFromServer = response.body();
                    if (responseFromServer.getCode().equals(ErrorLog.CodeSuccess) && responseFromServer.getMessage().equals(ErrorLog.Success)) {
                        Toast.makeText(UpdateUserProfileActivity.this, "User Profile Updated!!", Toast.LENGTH_SHORT).show();
                        SharedPref.putString(getApplicationContext(), Constants.userName, nameTIET.getText().toString());
                        SharedPref.putString(getApplicationContext(), Constants.userEmail, emailTIET.getText().toString());
                        SharedPref.putInt(getApplicationContext(), Constants.collegeId, collegeID);
                        SharedPref.putString(getApplicationContext(), Constants.collegeName, collegeName);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Response<String>> call, Throwable t) {
                    Toast.makeText(UpdateUserProfileActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
