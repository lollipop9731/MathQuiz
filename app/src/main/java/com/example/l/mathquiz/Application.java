package com.example.l.mathquiz;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by l on 16.12.2016.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/PT_Serif-Web-Bold.ttf").setFontAttrId(R.attr.fontPath).build());
    }
}
