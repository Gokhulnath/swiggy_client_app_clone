package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import golhar.cocomo.zinger.utils.SharedPref;

public class Registration extends AppCompatActivity {

TextView college_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        college_list = (TextView) findViewById(R.id.collegeTV);
        college_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent college_select = new Intent(Registration.this,CollegeList.class);
                startActivity(college_select);

            }
        });

        Toast.makeText(this, SharedPref.getString(getApplicationContext(),"selected_college"), Toast.LENGTH_SHORT).show();
    }
}
