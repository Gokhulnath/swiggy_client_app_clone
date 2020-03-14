package golhar.cocomo.zinger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import golhar.cocomo.zinger.enums.UserRole;
import golhar.cocomo.zinger.model.OrderModel;
import golhar.cocomo.zinger.model.UserCollegeModel;
import golhar.cocomo.zinger.model.UserModel;
import golhar.cocomo.zinger.service.MainRepository;
import golhar.cocomo.zinger.utils.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
 //comment from mahima