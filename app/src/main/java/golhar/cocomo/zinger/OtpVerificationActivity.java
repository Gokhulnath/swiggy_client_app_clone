package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Constants;
import golhar.cocomo.zinger.utils.ErrorLog;
import golhar.cocomo.zinger.utils.Response;
import golhar.cocomo.zinger.utils.SharedPref;
import retrofit2.Call;
import retrofit2.Callback;

public class OtpVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PhoneAuthProvider phoneAuth;
    private String verificationId;

    Button otpVerifiedBT;
    Button sendOtpBT;
    TextView numberTV;
    TextInputEditText otpTIET;
    String phNumber;
    String authId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        mAuth = FirebaseAuth.getInstance();
        phoneAuth = PhoneAuthProvider.getInstance();
        phNumber = SharedPref.getString(getApplicationContext(), Constants.phoneNumber);
        otpVerifiedBT = findViewById(R.id.otpVerifiedBT);
        numberTV = findViewById(R.id.numberTV);
        numberTV.setText("OTP sent to " + phNumber);
        otpTIET = findViewById(R.id.otpTIET);
        sendOtpBT = findViewById(R.id.sendOtpBT);
        otpVerifiedBT.setEnabled(false);
        sendOtpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpVerifiedBT.setEnabled(true);
                sendOtpBT.setEnabled(false);
                new CountDownTimer(10000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        sendOtpBT.setText("RESEND OTP IN " + millisUntilFinished / 1000 + "sec");
                    }

                    public void onFinish() {
                        sendOtpBT.setText("RESEND OTP");
                    }
                }.start();
                sendOtpBT.setText("RESEND OTP IN 5sec");
                Timer sendOtpTimer = new Timer();
                sendOtpTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sendOtpBT.setEnabled(true);
                            }
                        });
                    }
                }, 10000);
                String phoneNumber = "+91" + phNumber;

                phoneAuth.verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        OtpVerificationActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(OtpVerificationActivity.this, "Auto verification called", Toast.LENGTH_SHORT).show();
                                otpTIET.setText(phoneAuthCredential.getSmsCode());
                                verifyUser(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OtpVerificationActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Toast.makeText(OtpVerificationActivity.this, "Code sent!", Toast.LENGTH_SHORT).show();
                                verificationId = s;
                            }
                        }
                );
            }
        });

        otpVerifiedBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpTIET.getEditableText().toString().matches("")) {
                    otpTIET.setError("Invalid otp");
                } else {
                    String otp = otpTIET.getEditableText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                    verifyUser(credential);
                }
            }
        });
    }

    void verifyUser(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(OtpVerificationActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    authId = FirebaseAuth.getInstance().getUid();
                    SharedPref.putString(getApplicationContext(), Constants.authId, authId);
                    UserModel userModel = new UserModel();
                    userModel.setMobile(phNumber);
                    userModel.setOauthId(authId);
                    userModel.setRole(UserRole.CUSTOMER);
                    MainRepository.getUserService().insertCustomer(userModel).enqueue(new Callback<Response<UserCollegeModel>>() {
                        @Override
                        public void onResponse(Call<Response<UserCollegeModel>> call, retrofit2.Response<Response<UserCollegeModel>> response) {
                            Response<UserCollegeModel> userDataResponse = response.body();
                            if (userDataResponse.getCode().equals(ErrorLog.CodeSuccess) && userDataResponse.getMessage().equals(ErrorLog.Success)) {
                                SharedPref.putString(getApplicationContext(), Constants.userName, userDataResponse.getData().getUserModel().getName());
                                SharedPref.putString(getApplicationContext(), Constants.userEmail, userDataResponse.getData().getUserModel().getEmail());
                                SharedPref.putInt(getApplicationContext(), Constants.collegeId, userDataResponse.getData().getCollegeModel().getId());
                                SharedPref.putString(getApplicationContext(), Constants.collegeName, userDataResponse.getData().getCollegeModel().getName());
                                SharedPref.putString(getApplicationContext(), Constants.collegeAddress, userDataResponse.getData().getCollegeModel().getAddress());
                                SharedPref.putString(getApplicationContext(), Constants.collegeUrl, userDataResponse.getData().getCollegeModel().getIconUrl());
                                SharedPref.putInt(getApplicationContext(), Constants.loginStatus, 1);
                                SharedPref.putInt(getApplicationContext(),Constants.shopId,-1);
                                Intent shopList = new Intent(OtpVerificationActivity.this, ShopListActivity.class);
                                startActivity(shopList);
                            } else if (userDataResponse.getCode().equals(ErrorLog.CodeSuccess) && userDataResponse.getMessage().equals(ErrorLog.CollegeDetailNotAvailable)) {
                                SharedPref.putString(getApplicationContext(), Constants.authId, authId);
                                Intent registration = new Intent(OtpVerificationActivity.this, RegistrationActivity.class);
                                startActivity(registration);
                            } else {
                                Toast.makeText(OtpVerificationActivity.this, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onFailure(Call<Response<UserCollegeModel>> call, Throwable t) {
                            Toast.makeText(OtpVerificationActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(OtpVerificationActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}