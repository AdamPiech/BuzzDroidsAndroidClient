package buzzdroids.bddrone;

import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import buzzdroids.bddrone.dataModel.Beacon;
import buzzdroids.bddrone.dataModel.DroneLocation;
import buzzdroids.bddrone.utils.api.BuzzdroidsClient;
import buzzdroids.bddrone.utils.network.NetworkChangeReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static buzzdroids.bddrone.utils.Util.SERVER_URL;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private BuzzdroidsClient buzzdroidsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBuzzdroidsClient();

        pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new FragmentActivity(getSupportFragmentManager(), getResources().getConfiguration());
        pager.setAdapter(pagerAdapter);

        checkInternetConnection();
    }

    public void resetDronePosition(View view) {
        Call<List<DroneLocation>> beacons = buzzdroidsClient.getResetDroneLocation();
        beacons.enqueue(
                new Callback<List<DroneLocation>>() {
                    @Override
                    public void onResponse(Call<List<DroneLocation>> call, Response<List<DroneLocation>> response) {}
                    @Override
                    public void onFailure(Call<List<DroneLocation>> call, Throwable throwable) {}
                }
        );
    }

    public void resetBeaconList(View view) {
        Call<List<Beacon>> beacons = buzzdroidsClient.getResetBeaconList();
        beacons.enqueue(
                new Callback<List<Beacon>>() {
                    @Override
                    public void onResponse(Call<List<Beacon>> call, Response<List<Beacon>> response) {}
                    @Override
                    public void onFailure(Call<List<Beacon>> call, Throwable throwable) {}
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

    private void checkInternetConnection() {
        if (!NetworkChangeReceiver.isConnected(this)) {
            String alertTitle = "No Internet connection.";
            String alertMessage = "You have no internet connection. Please turn it on!";
            showResourceNotEnabledExitAlert(alertTitle, alertMessage);
        }
    }

    private void showResourceNotEnabledExitAlert(String alertTitle, String alertMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage(alertMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
