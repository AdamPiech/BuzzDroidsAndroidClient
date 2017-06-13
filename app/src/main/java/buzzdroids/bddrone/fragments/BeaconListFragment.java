package buzzdroids.bddrone.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import buzzdroids.bddrone.R;
import buzzdroids.bddrone.dataModel.Beacon;
import buzzdroids.bddrone.dataModel.DroneLocation;
import buzzdroids.bddrone.utils.api.BuzzdroidsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static buzzdroids.bddrone.utils.Util.SERVER_URL;
import static buzzdroids.bddrone.utils.Util.TAG;
import static buzzdroids.bddrone.utils.beacon.BeaconColorToMarkerColorService.*;

/**
 * Created by Adam Piech on 2017-06-02.
 */

public class BeaconListFragment extends Fragment {

    private BuzzdroidsClient buzzdroidsClient;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            downloadAllData();
            handler.postDelayed(runnable, 2000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.beacon_list_activity, container, false);
        initBuzzdroidsClient();
        downloadAllData();
        return view;
    }

    private void downloadAllData() {
        downloadBeaconList();
        downloadDronePosition();
    }

    private void downloadDronePosition() {
        Call<List<DroneLocation>> drones = buzzdroidsClient.getDroneLocation();
        drones.enqueue(
                new Callback<List<DroneLocation>>() {
                    @Override
                    public void onResponse(Call<List<DroneLocation>> call, Response<List<DroneLocation>> response) {
                        if (response.isSuccessful()) {
                            for (DroneLocation drone : response.body()) {
                                createDroneListElement(drone);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<DroneLocation>> call, Throwable throwable) {}
                }
        );
    }

    private void downloadBeaconList() {
        Call<List<Beacon>> beacons = buzzdroidsClient.getBeaconList();
        beacons.enqueue(
                new Callback<List<Beacon>>() {
                    @Override
                    public void onResponse(Call<List<Beacon>> call, Response<List<Beacon>> response) {
                        if (response.isSuccessful()) {
                            for (Beacon beacon : response.body()) {
                                createBeaconListElement(beacon);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Beacon>> call, Throwable throwable) {}
                }
        );
    }

    private void createDroneListElement(DroneLocation drone) {
        LinearLayout container = (LinearLayout) getActivity().findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.beacon_list_element, null);
        ((TextView) element.findViewById(R.id.name)).setText(drone.getDroneName());
        ((TextView) element.findViewById(R.id.latitude)).setText(drone.getCoordinates().getLatitude() + "");
        ((TextView) element.findViewById(R.id.longitude)).setText(drone.getCoordinates().getLongitude() + "");
        element.setBackgroundColor(Color.WHITE);
        container.addView(element);
    }

    private void createBeaconListElement(Beacon beacon) {
        LinearLayout container = (LinearLayout) getActivity().findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.beacon_list_element, null);
        ((TextView) element.findViewById(R.id.name)).setText(beacon.getName());
        ((TextView) element.findViewById(R.id.latitude)).setText(beacon.getCoordinates().getLatitude() + "");
        ((TextView) element.findViewById(R.id.longitude)).setText(beacon.getCoordinates().getLongitude() + "");
        element.setBackgroundColor(getListElementColor(beacon.getColor()));
        container.addView(element);
    }

    private void initBuzzdroidsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        buzzdroidsClient = retrofit.create(BuzzdroidsClient.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        runnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

}