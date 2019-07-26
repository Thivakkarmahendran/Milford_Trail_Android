package com.mthivakkar.milfordtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.SpaceListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ProximityManager proximityManager;


    public Set<String> beacons = new HashSet<>();

    public String CurrentStopText = "";
    public int counter = 0;

    public TextToSpeech speechSynthesizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KontaktSDK.initialize("uONVvSjhWhXtcRiHMqaQHzxMQBRXBnRK");

        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());


        speechSynthesizer = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speechSynthesizer.setLanguage(Locale.US);
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
    }


    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {

                //Log.i("IBeaconListener", "IBeacon discovered: " + ibeacon.toString());
                //Log.i("entered", determineStop(ibeacon.getProximityUUID().toString()));

                beacons.add(determineStop(ibeacon.getProximityUUID().toString()));
                beaconsPresent();

            }

            @Override
            public void onIBeaconLost(IBeaconDevice ibeacon, IBeaconRegion region) {
                super.onIBeaconLost(ibeacon, region);

                //Log.i("lost", determineStop(ibeacon.getProximityUUID().toString()));

                beacons.remove(determineStop(ibeacon.getProximityUUID().toString()));
                beaconsPresent();
            }

        };
    }

    ////////////////////////////////////////////////////////////////////////

    private String determineStop(String UUID){

        String stopID = "";

        switch(UUID) {
            case "f7826da6-4fa2-4e98-8024-bc5b71e0893a":
                stopID =  "Stop 1";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0893b":
                stopID =  "Stop 2";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0893c":
                stopID =  "Stop 3";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0893d":
                stopID =  "Stop 4";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0893e":
                stopID =  "Stop 5";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0893f":
                stopID =  "Stop 6";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0894a":
                stopID =  "Stop 7";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0894b":
                stopID =  "Stop 8";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0894c":
                stopID =  "Stop 9";
                break;
            case "f7826da6-4fa2-4e98-8024-bc5b71e0894d":
                stopID =  "Stop 10";
                break;

            default:
                stopID = "Stop Error";
        }

        return stopID;

    }

    private void beaconsPresent(){

        if(CurrentStopText.equals("") && beacons.contains("Stop 1") && counter == 0){

            CurrentStopText = "Stop 1";

            counter = counter++;
            speechtoText(CurrentStopText);

            counter = counter + 2;
            CurrentStopText = "Stop " + counter;

        }
        else if(beacons.contains(CurrentStopText)){

            speechtoText(CurrentStopText);

            counter++;
            CurrentStopText = "Stop " + counter;


        }

    }

    private void speechtoText(String currStop){


        speechSynthesizer.speak(currStop, TextToSpeech.QUEUE_ADD, null);

        Log.i("speech", currStop);
    }




}
