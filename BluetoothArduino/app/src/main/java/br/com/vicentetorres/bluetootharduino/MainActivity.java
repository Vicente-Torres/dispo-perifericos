package br.com.vicentetorres.bluetootharduino;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BluetoothService bluetoothService;

    private RecyclerView deviceViewList;

    private List<String[]> bluetoothDevices = new ArrayList<>();

    private EditText messageTextInput;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothService = new BluetoothService(this);
        deviceViewList = findViewById(R.id.deviceList);
        messageTextInput = findViewById(R.id.messageTextInput);

        bluetoothService.checkBluetooth();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        deviceViewList.setLayoutManager(layoutManager);

        updatePairedDevicesList();
    }

    @SuppressLint("ResourceAsColor")
    private View.OnClickListener itemClickListener() {
        View.OnClickListener clickListener = v -> {
//            View rootView = v.getRootView(); // TODO getParent
            View parentView = (View) v.getParent();
            Button deviceConnectButton = v.findViewById(R.id.deviceConnectBtn);
            TextView deviceNameTextView = parentView.findViewById(R.id.device_item_name);
            TextView deviceAddressTextView = parentView.findViewById(R.id.device_item_address);

            Boolean deviceIsConnected = bluetoothService.connectToDevice(deviceAddressTextView.getText().toString());

            if (deviceIsConnected) {
                String message = String.format("Conectado ao dispositivo %s - %s", deviceNameTextView.getText().toString(), deviceAddressTextView.getText().toString());
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                deviceConnectButton.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_100));
                deviceConnectButton.setText("CONECTADO");
            }else {
                Toast.makeText(this, "Erro ao conectar", Toast.LENGTH_LONG).show();
            }
        };
        return clickListener;
    }

    public void sendMessage(View view) {
        String message = messageTextInput.getText().toString();
        bluetoothService.sendMessage(message);
        messageTextInput.setText("");
    }

    private void updatePairedDevicesList() {
        bluetoothDevices = bluetoothService.getPairedDeviceList();
        deviceViewList.setAdapter(new DeviceListAdapter(bluetoothDevices, itemClickListener()));
    }

    public void updatePairedDevicesList(View view) {
        updatePairedDevicesList();
    }
}