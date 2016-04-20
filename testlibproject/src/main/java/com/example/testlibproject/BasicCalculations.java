package com.example.testlibproject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kushal.Desai on 4/20/2016.
 */
public class BasicCalculations {

    public static String ConvertDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US);
        DateFormat targetFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());
        String formattedDate = null;
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
            System.out.println(dateString);
            formattedDate = targetFormat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formattedDate);
        return formattedDate;
    }

}
