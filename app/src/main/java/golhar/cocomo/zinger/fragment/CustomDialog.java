package golhar.cocomo.zinger.fragment;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        super(context);
        this.setCancelable(false);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        this.dismiss();
    }
}
