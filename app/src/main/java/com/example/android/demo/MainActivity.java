package com.example.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.kircherelectronics.fsensor.observer.SensorSubject;
import com.kircherelectronics.fsensor.sensor.FSensor;
import com.kircherelectronics.fsensor.sensor.acceleration.ComplementaryLinearAccelerationSensor;

import java.util.Arrays;

public class MainActivity extends Activity {
    private final String TAG = "TestPlayActivity";

    private FSensor fSensor;
    private TextView txtLog;

    private SensorSubject.SensorObserver sensorObserver = new SensorSubject.SensorObserver() {
        @Override
        public void onSensorChanged(float[] values) {
            Log.d(TAG, "values = "+ Arrays.toString(values));
            txtLog.setText(Arrays.toString(values));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLog = findViewById(R.id.txt_log);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fSensor = new ComplementaryLinearAccelerationSensor(this);
        fSensor.register(sensorObserver);
        fSensor.start();
    }

    @Override
    protected void onPause() {
        fSensor.unregister(sensorObserver);
        fSensor.stop();
        super.onPause();
    }
}
