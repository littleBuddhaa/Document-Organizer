package com.bellatrix.aditi.documentorganizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class SelectCustomTags extends AppCompatActivity {

    private Button backButton, finishButton,addCustomTags;
    private TextView textWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_custom_tags);
        String textRecognized = getIntent().getExtras().getString("textRecognized");
        backButton = (Button) findViewById(R.id.back_button);
        finishButton = (Button) findViewById(R.id.finish_button);
        addCustomTags = (Button) findViewById(R.id.add_custom_tags);
        textWindow = (TextView) findViewById(R.id.tv_tags);
        textWindow.setText(textRecognized);
    }


    }
