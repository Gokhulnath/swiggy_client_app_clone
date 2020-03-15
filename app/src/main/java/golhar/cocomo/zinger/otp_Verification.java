package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class otp_Verification extends AppCompatActivity {

    Button otp_verified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__verification);

        otp_verified = (Button) findViewById(R.id.otp_verfiedB);
        otp_verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration = new Intent(otp_Verification.this,Registration.class);
                startActivity(registration);
            }
        });
    }
}
