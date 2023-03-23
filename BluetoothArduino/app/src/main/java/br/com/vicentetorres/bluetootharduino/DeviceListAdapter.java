package br.com.vicentetorres.bluetootharduino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.MyViewHolder>{

    private List<String[]> items;

    private View.OnClickListener clickListener;

    public DeviceListAdapter(List<String[]> items, View.OnClickListener clickListener) {
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_iten, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String[] item = items.get(position);
        holder.bind(item, clickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView deviceNameTextView;
        private TextView deviceAddressTextView;
        private Button deviceConnectButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.device_item_name);
            deviceAddressTextView = itemView.findViewById(R.id.device_item_address);
            deviceConnectButton = itemView.findViewById(R.id.deviceConnectBtn);
        }

        public void bind(String[] item, View.OnClickListener clickListener) {
            deviceNameTextView.setText(item[1]);
            deviceAddressTextView.setText(item[0]);
            deviceConnectButton.setOnClickListener(clickListener);
        }
    }

}
