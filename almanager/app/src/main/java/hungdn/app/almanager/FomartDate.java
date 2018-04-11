package hungdn.app.almanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MR_HUNG on 7/31/2017.
 */

public class FomartDate {


    public static String formartdatelocalEN(String strdate) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        SimpleDateFormat dateFormatEN = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date = dateFormat.parse(strdate);
            Log.i("format", "" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatEN.format(date);

    }

    public static long convertdatetolong(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dates = new Date();
        try {
            dates = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates.getTime();
    }

    public static Date sortdate(String date) {
        try {
            SimpleDateFormat dateFormatserver = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormatserver.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formartdatelocalEN_yyyy_mm_dd(String strdate) {
        if (strdate.equals("1900-01-01T00:00:00")) {
            return "";
        } else {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatEN = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);

            try {
                date = dateFormat.parse(strdate);
                Log.i("format", "" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return dateFormatEN.format(date);

        }

    }

    public static String formatdateyyyy_mm_ddtomm_dd_yyyy(String strdate) {
        Date date = new Date();
        SimpleDateFormat dateFormatEN = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            date = dateFormatEN.parse(strdate);
            Log.i("format", "" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    public static DatePickerDialog setdateButton(Context mContext, final Button ed) {
        Calendar mcurrentDate;
        int mDay, mMonth, mYear;
        mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker = new DatePickerDialog(mContext, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                String date = selectedday + "-" + (selectedmonth + 1) + "-" + selectedyear;
                String result = FomartDate.formartdatelocalEN(date);
                ed.setText(result);
            }

        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
        mDatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy", (DialogInterface.OnClickListener) null);
        mDatePicker.setButton(DialogInterface.BUTTON_POSITIVE, "Đồng ý", mDatePicker);
        return mDatePicker;

    }
    public static DatePickerDialog setdateEdittext(Context mContext, final EditText ed) {
        Calendar mcurrentDate;
        int mDay, mMonth, mYear;
        mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker = new DatePickerDialog(mContext, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                String date = selectedday + "-" + (selectedmonth + 1) + "-" + selectedyear;
                String result = FomartDate.formartdatelocalEN(date);
                ed.setText(result);
            }

        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
        mDatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy", (DialogInterface.OnClickListener) null);
        mDatePicker.setButton(DialogInterface.BUTTON_POSITIVE, "Đồng ý", mDatePicker);
        return mDatePicker;

    }
    public static String getfirtdateofmounth() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat dateFormatEN = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormatEN.format(date.getTime());
    }

    public static String getdatenow() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dtf.format(date);
    }

    public static String PricefromString(String number, int decima_fomat) {
        double amount = Double.parseDouble(number);

        if (amount == 0) {
            String a = "0";
            for (int i = 0; i < decima_fomat; i++) {
                a = a + "0";
            }
            return a;
        }
        String fomat = "#,###";
        for (int i = 0; i < decima_fomat; i++) {
            fomat = fomat + "0";
        }
        DecimalFormat formatter = new DecimalFormat(fomat);
        String fomatresult = formatter.format(amount);
        if (amount < 1) {
            fomatresult = "0" + fomatresult;
        }

        return fomatresult;
    }
}
