package lab2_204_06.uwaterloo.ca.lab2_204_06;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

import sensortoy.LineGraphView;

import static lab2_204_06.uwaterloo.ca.lab2_204_06.MainActivity.linkedlistArrayX;
import static lab2_204_06.uwaterloo.ca.lab2_204_06.MainActivity.linkedlistArrayY;
import static lab2_204_06.uwaterloo.ca.lab2_204_06.MainActivity.linkedlistArrayZ;

public class MainActivity extends AppCompatActivity {

    static LineGraphView graph;
    static float[] array = new float[3];
    static LinkedList<Float> linkedlistArrayX = new LinkedList<>();
    static LinkedList<Float> linkedlistArrayY = new LinkedList<>();
    static LinkedList<Float> linkedlistArrayZ = new LinkedList<>();

    PrintWriter printWriter = null;
    File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        file = new File(getExternalFilesDir("data"), "XYZdata.csv"); //point to the file we want to create

        try{
            printWriter = new PrintWriter(file); //For buffer Writing
        }
        catch (IOException e){
            Log.d("Lab2", "File Write Fail" + e.toString());
        }

        TextView heading = (TextView) findViewById(R.id.label1);
        heading.setText("Sensor Readings");
        LinearLayout rl = (LinearLayout) findViewById(R.id.parentLayout);

        graph = new LineGraphView(getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
        rl.addView(graph);
        graph.setVisibility(View.VISIBLE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        /*ACCELEROMETER*/
        TextView accelerometer = new TextView(getApplicationContext());
        TextView gestureOut = new TextView(getApplicationContext());
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(new SensorListener(accelerometer, gestureOut), accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        /*END ACCELEROMETER*/

        Button dataButton = new Button(getApplicationContext());
        dataButton.setText("Store the latest 100 datapoints in a CSV file");

        dataButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeToFile();
            }
        });
        rl.addView(dataButton);

        //Button -> Turn current [100][3] array into Array[100] of CSV -> Store in file (delete first?)
        rl.addView(accelerometer);
        rl.addView(gestureOut);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(file != null){
            printWriter.close();
        }
    }

    public void writeToFile(){
            printWriter.println("");
        Log.d("New File Path: ", file.getAbsolutePath());
            for (int i = 0; i<linkedlistArrayX.size(); i++){
                printWriter.println(String.format(Locale.getDefault(), "%f,%f,%f", linkedlistArrayX.get(i), linkedlistArrayY.get(i), linkedlistArrayZ.get(i)));
                //Log.d("test","++"+ linkedlistArray.get(i)[0]+ " " + linkedlistArray.get(i)[1] + " " +linkedlistArray.get(i)[2]);
            }
    }
}




