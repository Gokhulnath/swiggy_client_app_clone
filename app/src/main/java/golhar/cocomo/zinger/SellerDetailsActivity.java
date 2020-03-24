package golhar.cocomo.zinger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import golhar.cocomo.zinger.fragment.TimePickerFragment;
import golhar.cocomo.zinger.model.ConfigurationModel;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class SellerDetailsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    EditText priceTV;
    ToggleButton editBT;
    ToggleButton delTakenBT;
    ToggleButton orderTakenBT;
    EditText numberTV;
    ToggleButton mobileBT;
    TextView openTV;
    Button openBT;
    TextView closeTV;
    Button closeBT;
    ConfigurationModel configurationModel;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy    hh:mm:ss a");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        priceTV=findViewById(R.id.priceTV);
        editBT=findViewById(R.id.editBT);
        delTakenBT=findViewById(R.id.delTakenBT);
        orderTakenBT=findViewById(R.id.orderTakenBT);
        numberTV=findViewById(R.id.numberTV);
        mobileBT=findViewById(R.id.mobileBT);
        openTV=findViewById(R.id.openTV);
        openBT=findViewById(R.id.openBT);
        closeTV=findViewById(R.id.closeTV);
        closeBT=findViewById(R.id.closeBT);

        editBT.setEnabled(false);
        editBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    priceTV.setEnabled(true);
                    editBT.setBackground(getDrawable(R.drawable.ic_check));
                   editBT.setText((configurationModel.getDeliveryPrice().intValue()));
                } else {
                    // The toggle is disabled
                    priceTV.setEnabled(false);
                    editBT.setBackground(getDrawable(R.drawable.ic_pencil));
                    editBT.setText((configurationModel.getDeliveryPrice().intValue()));
                }
            }
        });

        delTakenBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    configurationModel.setIsDeliveryAvailable(1);
                } else {
                    // The toggle is disabled
                   configurationModel.setIsDeliveryAvailable(0);
                }
            }
        });

        orderTakenBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    configurationModel.setIsOrderTaken(1);
                } else {
                    // The toggle is disabled
                    configurationModel.setIsOrderTaken(0);
                }
            }
        });

//        numberTV.setText(configurationModel.getShopModel().getMobile());
        mobileBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    numberTV.setEnabled(true);
                    numberTV.setText(configurationModel.getShopModel().getMobile());

                } else {
                    // The toggle is disabled
                    numberTV.setEnabled(false);
                }
            }
        });

        //openTV.setText(configurationModel.getShopModel().setOpeningTime((sdf.format(cal.getTime())));
        openBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        closeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        openTV.setText("Hour: " + hourOfDay + " Minute: " + minute);
        closeTV.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }

}
