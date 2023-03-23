package br.com.vicentetorres.bluetootharduino;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothService {

    private BluetoothSocket socket;

    private BluetoothDevice device;

    private OutputStream outputStreamBluetooth;

    private AppCompatActivity appCompatActivity;

    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothService(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @SuppressLint("MissingPermission")
    public List<String[]> getPairedDeviceList() {
        List<String[]> bluetoothDevices = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice pairedDevice : pairedDevices) {
                bluetoothDevices.add(new String[]{pairedDevice.getAddress(), pairedDevice.getName()});
            }
        }

        return bluetoothDevices;
    }

    public void sendMessage(String message) {
        try {
            outputStreamBluetooth.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public Boolean connectToDevice(String address) {
        device = bluetoothAdapter.getRemoteDevice(address);
        try {
            socket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
            socket.connect();
            outputStreamBluetooth = socket.getOutputStream();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void checkBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(appCompatActivity, "O dispositivo não possue bluetooth", Toast.LENGTH_LONG);
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText(appCompatActivity, "O bluetooth do dis positivo está desativado! Ligue e atualize a lista.", Toast.LENGTH_LONG);
            } else {
                if (ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                }
            }
        }
    }
}
