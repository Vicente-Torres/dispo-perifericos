package br.com.vicentetorres.servocontrol;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private BluetoothService bluetoothService;

    private RecyclerView deviceViewList;

    private List<String[]> bluetoothDevices = new ArrayList<>();

    private TextView accelerometerValueTextView;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothService = new BluetoothService(this);
        deviceViewList = findViewById(R.id.deviceList);
        accelerometerValueTextView = findViewById(R.id.accelerometerValueTextView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bluetoothService.checkBluetooth();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        deviceViewList.setLayoutManager(layoutManager);

        updatePairedDevicesList();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @SuppressLint("ResourceAsColor")
    private View.OnClickListener itemClickListener() {
        View.OnClickListener clickListener = v -> {
            View parentView = (View) v.getParent();
            Button deviceConnectButton = v.findViewById(R.id.deviceConnectBtn);
            TextView deviceNameTextView = parentView.findViewById(R.id.device_item_name);
            TextView deviceAddressTextView = parentView.findViewById(R.id.device_item_address);

            Boolean deviceIsConnected = bluetoothService.connectToDevice(deviceAddressTextView.getText().toString());

            if (deviceIsConnected) {
                String message = format("Conectado ao dispositivo %s - %s", deviceNameTextView.getText().toString(), deviceAddressTextView.getText().toString());
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                deviceConnectButton.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_100));
                deviceConnectButton.setText("CONECTADO");
            }else {
                Toast.makeText(this, "Erro ao conectar", Toast.LENGTH_LONG).show();
            }
        };
        return clickListener;
    }

    private void updatePairedDevicesList() {
        bluetoothDevices = bluetoothService.getPairedDeviceList();
        deviceViewList.setAdapter(new DeviceListAdapter(bluetoothDevices, itemClickListener()));
    }

    public void updatePairedDevicesList(View view) {
        updatePairedDevicesList();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        Float x = event.values[0];
        Float y = event.values[1];
        Float z = event.values[2];

        accelerometerValueTextView.setText(format("X: %.4f | Y: %.4f | Z: %.4f", x, y, z));

        bluetoothService.sendMessage(x.toString().concat("|"));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}