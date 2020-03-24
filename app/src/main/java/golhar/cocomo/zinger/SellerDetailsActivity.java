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

public class SellerDetailsActivity extends AppCompatActivity {
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
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        ConfigurationModel configurationModel=new ConfigurationModel();
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
        priceTV.setEnabled(false);

        editBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    priceTV.setEnabled(true);
                    editBT.setBackground(getDrawable(R.drawable.ic_check));
                   editBT.setText(String.valueOf(configurationModel.getDeliveryPrice()));
                } else {
                    // The toggle is disabled
                    priceTV.setEnabled(false);
                    editBT.setBackground(getDrawable(R.drawable.ic_pencil));
                    editBT.setText(String.valueOf(configurationModel.getDeliveryPrice()));
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
        numberTV.setEnabled(false);
//        numberTV.setText(configurationModel.getShopModel().getMobile());
        mobileBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    numberTV.setEnabled(true);
                    numberTV.setText(configurationModel.getShopModel().getMobile());
                    mobileBT.setBackground(getDrawable(R.drawable.ic_check));
                } else {
                    // The toggle is disabled
                    numberTV.setEnabled(false);
                    mobileBT.setBackground(getDrawable(R.drawable.ic_pencil));
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        closeTV.setText(currentHour+":"+currentMinute);
        openTV.setText(currentHour+":"+currentMinute);

        closeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//                int currentMinute = calendar.get(Calendar.MINUTE);
//                closeTV.setText(currentHour+":"+currentMinute+amPm);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SellerDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        closeTV.setText(String.format("%02d:%02d", hourOfDay , minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();

            }
        });
       openBT.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               TimePickerDialog timePickerDialog = new TimePickerDialog(SellerDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                       if (hourOfDay >= 12) {
                           amPm = "PM";
                       } else {
                           amPm = "AM";
                       }
                       openTV.setText(String.format("%02d:%02d", hourOfDay , minutes) + amPm);
                   }
               }, currentHour, currentMinute, false);
               timePickerDialog.show();

           }
       });
    }
}
