package golhar.cocomo.zinger.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import golhar.cocomo.zinger.SellerActivity;

public class TimePickerFragment extends DialogFragment {
    SellerActivity sellerActivity;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) sellerActivity, hour, minute, DateFormat.is24HourFormat(sellerActivity));
    }
}
