package com.fg.frostyghost.my_wm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class TextSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_settings);

        TextView textView = findViewById(R.id.formatText);
        EditText editText = findViewById(R.id.editText3);


    }
}
