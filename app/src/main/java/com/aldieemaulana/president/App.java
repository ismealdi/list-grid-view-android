package com.aldieemaulana.president;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by aldieemaulana on 7/27/17.
 */

public class App extends Application {

    public static String API = "http://127.0.0.1/presidents/api/v1/";
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

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }


}
