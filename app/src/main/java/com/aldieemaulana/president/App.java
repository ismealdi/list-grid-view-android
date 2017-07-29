package com.aldieemaulana.president;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by aldieemaulana on 7/27/17.
 */

public class App extends Application {

    public static String API = "http://rest.aldieemaulana.com/api/v1/";
    public static String URL = "http://rest.aldieemaulana.com/";

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/latoRegular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
        );
    }


}
