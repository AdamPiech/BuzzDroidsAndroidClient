package buzzdroids.bddrone.utils.api;

import java.util.List;

import buzzdroids.bddrone.dataModel.Beacon;
import buzzdroids.bddrone.dataModel.DroneLocation;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;

/**
 * Created by Adam Piech on 2017-06-02.
 */

public interface BuzzdroidsClient {

    @GET("/beacon/list")
    Call<List<Beacon>> getBeaconList();

    @GET("/drone/location")
    Call<List<DroneLocation>> getDroneLocation();

    @DELETE("/beacon/reset")
    Call<List<Beacon>> getResetBeaconList();

    @DELETE("/drone/location/reset")
    Call<List<DroneLocation>> getResetDroneLocation();

}
