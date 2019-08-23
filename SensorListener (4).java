package lab2_204_06.uwaterloo.ca.lab2_204_06;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

import static lab2_204_06.uwaterloo.ca.lab2_204_06.MainActivity.linkedlistArrayX;
import static lab2_204_06.uwaterloo.ca.lab2_204_06.MainActivity.linkedlistArrayY;
import static lab2_204_06.uwaterloo.ca.lab2_204_06.MainActivity.linkedlistArrayZ;

/**
 * Created by Tarek on 03/06/2017.
 */

class SensorListener extends GestureFSM implements SensorEventListener {

    TextView output;
    TextView outputGesture;
    enum resultGesture{LEFT, RIGHT, UP, DOWN, UNKNOWN};

    public SensorListener(TextView outputView, TextView gesture) {
        output = outputView;
        outputGesture = gesture;
    }

    public void onAccuracyChanged(Sensor s, int i) {
    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {


            output.setText(String.format("Current Accelerometer reading is: \r\n %.6s %.6s %.6s \r\n\r\n", se.values[0], se.values[1],se.values[2] ));
            // Log.d("test", "Accelerometer \r\n"+ se.values[0] +" "+ se.values[1 ]+" "+ se.values[2] + "\r\n\r\n");

            // array[0] stores the previous x-value reading [filteredReading], se.values[0] is the new reading captured [newReading] when OnSensorChanged is called
            // Low Pass Filter implementation where
                MainActivity.array[0] += (se.values[0]- MainActivity.array[0])/5;
                MainActivity.array[1] += (se.values[1]- MainActivity.array[1])/5;
                MainActivity.array[2] += (se.values[2]- MainActivity.array[2])/5;



                linkedlistArrayX.addFirst(MainActivity.array[0]);
                linkedlistArrayY.addFirst(MainActivity.array[1]);
                linkedlistArrayZ.addFirst(MainActivity.array[2]);

            while(linkedlistArrayX.size()>100){
                linkedlistArrayX.removeLast();
                linkedlistArrayY.removeLast();
                linkedlistArrayZ.removeLast();
            }

            MainActivity.graph.addPoint(MainActivity.array);

            String xSignature = currentGesture(MainActivity.array[0], thresholdAX, thresholdBX, currentStateX, currentTypeX, xTypeBMin, xTypeBMax, XaMax, XaMin, sampleCountX, prevValueX);
            Log.d("Sig: ", String.format("Check Sig: %s", xSignature));
            //String ySignature = currentGesture(MainActivity.array[1], thresholdAY, thresholdBY, currentStateY, currentTypeY, yTypeBMin, yTypeBMax, YaMax, YaMin, sampleCountY, prevValueY);

            String result;
            if (xSignature == "B"){ result = resultGesture.LEFT.toString();}
            else if(xSignature == "A"){ result = resultGesture.RIGHT.toString();}
            //else if(ySignature == "A"){ result = resultGesture.UP.toString();}
           // else if(ySignature == "B"){ result = resultGesture.DOWN.toString();}
            else result = resultGesture.UNKNOWN.toString();

            if(result!= "UNKNOWN") {
                outputGesture.setText(result);
            }
        }
    }
}
