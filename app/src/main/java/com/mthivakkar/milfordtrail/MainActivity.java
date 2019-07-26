package com.mthivakkar.milfordtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kontakt.sdk.android.common.KontaktSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KontaktSDK.initialize("uONVvSjhWhXtcRiHMqaQHzxMQBRXBnRK");
    }


}
