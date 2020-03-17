package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import golhar.cocomo.zinger.utils.SharedPref;

public class LoginActivity extends AppCompatActivity {

    Button phnOtpB;
    TextInputEditText otpTIET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        otpTIET = (TextInputEditText) findViewById(R.id.otpTIET);
        phnOtpB = (Button) findViewById(R.id.phnOtpB);
        phnOtpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = otpTIET.getText().toString();
                if (phoneNumber.length() == 10 && phoneNumber != null) {
                    SharedPref.putString(getApplicationContext(), "phone_number", phoneNumber);
                    Intent otp = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                    startActivity(otp);
                } else {
                    otpTIET.setError("Please enter valid phone number");
                }
            }
        });
    }
}
