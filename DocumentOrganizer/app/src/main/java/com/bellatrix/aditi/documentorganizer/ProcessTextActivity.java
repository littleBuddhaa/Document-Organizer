package com.bellatrix.aditi.documentorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ProcessTextActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_select_custom_tags);
        CharSequence text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        // process the text
        Log.d("Hello", "onCreate : " + text.toString());
        
    finish();
    }
}
