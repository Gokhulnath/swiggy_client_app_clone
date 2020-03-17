package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import golhar.cocomo.zinger.utils.SharedPref;

public class LoginActivity extends AppCompatActivity {

    Button otp_verification;
    TextInputEditText ph_numberTIET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ph_numberTIET = (TextInputEditText) findViewById(R.id.ph_numberTIET);
        otp_verification = (Button) findViewById(R.id.phn_otpB);
        otp_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.putString(getApplicationContext(),"phone_number", ph_numberTIET.getText().toString());
                Intent otp = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                startActivity(otp);
            }
        });



    }
}
