package buzzdroids.bddrone.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

import buzzdroids.bddrone.R;
import buzzdroids.bddrone.dataModel.Beacon;
import buzzdroids.bddrone.dataModel.Coordinates;
import buzzdroids.bddrone.dataModel.DroneLocation;
import buzzdroids.bddrone.utils.api.BuzzdroidsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static buzzdroids.bddrone.utils.Util.*;
import static buzzdroids.bddrone.utils.Util.TAG;
import static buzzdroids.bddrone.utils.beacon.BeaconColorToMarkerColorService.getMarkerColor;

/**
 * Created by Adam Piech on 2016-12-07.
 */

public class DroneFollowFragment extends Fragment implements OnMapReadyCallback {

    private BuzzdroidsClient buzzdroidsClient;
    private MapView mapView;
    private GoogleMap googleMap;

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
        View view = inflater.inflate(R.layout.drone_follow_activity, container, false);
        initMap(savedInstanceState, view);
        initBuzzdroidsClient();
        return view;
    }

    private void downloadAllData() {
        if (googleMap != null) {
//            googleMap.clear();
            downloadBeaconList();
            downloadDronePosition();
        }
    }

    private void downloadDronePosition() {
        Call<List<DroneLocation>> drones = buzzdroidsClient.getDroneLocation();
        drones.enqueue(
                new Callback<List<DroneLocation>>() {
                    @Override
                    public void onResponse(Call<List<DroneLocation>> call, Response<List<DroneLocation>> response) {
                        if (response.isSuccessful()) {
                            for (DroneLocation drone : response.body()) {
                                googleMap.addMarker(createDroneMarker(drone));
                                moveMapToLocation(drone.getCoordinates());
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
                                googleMap.addMarker(createBeaconMarker(beacon));
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Beacon>> call, Throwable throwable) {}
                }
        );
    }

    private MarkerOptions createDroneMarker(DroneLocation drone) {
        LatLng latLng = new LatLng(drone.getCoordinates().getLatitude(), drone.getCoordinates().getLongitude());
        return new MarkerOptions()
                .position(latLng)
                .title(drone.getDroneName())
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.drone));
    }

    private MarkerOptions createBeaconMarker(Beacon beacon) {
        LatLng latLng = new LatLng(beacon.getCoordinates().getLatitude(), beacon.getCoordinates().getLongitude());
        return new MarkerOptions()
                .position(latLng)
                .title(beacon.getName())
                .icon(getMarkerColor(beacon.getColor()));
    }

    private void initBuzzdroidsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        buzzdroidsClient = retrofit.create(BuzzdroidsClient.class);
    }

    private void initMap(Bundle savedInstanceState, View view) {
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException ex) {
            Log.e(TAG, ex.getMessage());
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.5f));
    }

    public void moveMapToLocation(Coordinates coordinates) {
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLng(new LatLng(coordinates.getLatitude(), coordinates.getLongitude())));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.5f));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        runnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        handler.removeCallbacks(runnable);
    }

}
