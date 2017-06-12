package buzzdroids.bddrone.dataModel;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightAreaPart {

    private String droneName;
    private List<PointLocation> flightArea;

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public List<PointLocation> getFlightArea() {
        return flightArea;
    }

    public void setFlightArea(List<PointLocation> flightArea) {
        this.flightArea = flightArea;
    }

}
