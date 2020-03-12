package golhar.cocomo.zinger.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonUtils {


    /************************************************************************/

    //Hides Keyboard

    public static void hideKeyboard(Activity activity){

        // todo refactor

        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /************************************************************************/

}
