package buzzdroids.bddrone.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Call<List<DroneLocation>> beacons = buzzdroidsClient.getDronesLocations();
        beacons.enqueue(
                new Callback<List<DroneLocation>>() {
                    @Override
                    public void onResponse(Call<List<DroneLocation>> call, Response<List<DroneLocation>> response) {
                        Log.i(TAG, "onResponse: " + response.isSuccessful() + " " + response.body());
                        if (response.isSuccessful()) {
                            for (DroneLocation drone : response.body()) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DroneLocation>> call, Throwable throwable) {
                        Log.e(TAG, "getDronePosition: ", throwable);
                    }
                }
        );
    }

    private void downloadBeaconList() {
        Call<List<Beacon>> beacons = buzzdroidsClient.getBeaconList();
        beacons.enqueue(
                new Callback<List<Beacon>>() {
                    @Override
                    public void onResponse(Call<List<Beacon>> call, Response<List<Beacon>> response) {
                        Log.i(TAG, "onResponse: " + response.isSuccessful() + " " + response.body());
                        if (response.isSuccessful()) {
                            for (Beacon beacon : response.body()) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Beacon>> call, Throwable throwable) {
                        Log.e(TAG, "getBeacons: ", throwable);
                    }
                }
        );
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