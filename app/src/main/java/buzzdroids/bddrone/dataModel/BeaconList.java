package buzzdroids.bddrone.dataModel;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-08.
 */

public class BeaconList {

    private String command;
    private List<Beacon> beacons;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

}
