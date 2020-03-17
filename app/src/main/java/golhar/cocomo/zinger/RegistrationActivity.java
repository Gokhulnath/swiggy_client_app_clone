package golhar.cocomo.zinger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import golhar.cocomo.zinger.utils.SharedPref;

public class RegistrationActivity extends AppCompatActivity {

TextView college_list;
TextView college_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        college_list = (TextView) findViewById(R.id.collegeTV);
        college_list.setOnClickListener(new View.OnClickListener() {
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
        int collegeID;
        String collegeName;
        collegeName = SharedPref.getString(getApplicationContext(),"selected_college");
        collegeID = SharedPref.getInt(getApplicationContext(),"selected_college_id");
        college_Name=(TextView)findViewById(R.id.collegeTV);
        college_Name.setText(collegeName);
        SharedPref.remove(getApplicationContext(),"selected_college");
    }

}
