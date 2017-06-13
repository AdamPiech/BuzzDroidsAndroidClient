package buzzdroids.bddrone.utils.beacon;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.HashMap;
import java.util.Map;

import buzzdroids.bddrone.R;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.*;

/**
 * Created by Adam Piech on 2017-06-06.
 */

public class BeaconColorToMarkerColorService {

//    public static BitmapDescriptor getMarkerColor(String beaconColor) {
//        Map<String, BitmapDescriptor> colors = new HashMap<>();
//        colors.put("green", defaultMarker(HUE_GREEN));
//        colors.put("yellow", defaultMarker(HUE_YELLOW));
//        colors.put("red", defaultMarker(HUE_RED));
//        colors.put("black", fromResource(R.mipmap.dead));
//        return colors.get(beaconColor);
//    }

    public static BitmapDescriptor getMarkerColor(String beaconColor) {
        Map<String, BitmapDescriptor> colors = new HashMap<>();
        colors.put("green", fromResource(R.mipmap.green_beacon));
        colors.put("yellow", fromResource(R.mipmap.yellow_beacon));
        colors.put("red", fromResource(R.mipmap.red_beacon));
        colors.put("black", fromResource(R.mipmap.black_beacon));
        return colors.get(beaconColor);
    }

    public static int getListElementColor(String beaconColor) {
        Map<String, Integer> colors = new HashMap<>();
        colors.put("green", Color.GREEN);
        colors.put("yellow", Color.YELLOW);
        colors.put("red", Color.RED);
        colors.put("black", Color.GRAY);
        return colors.get(beaconColor);
    }
}
