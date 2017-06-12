package buzzdroids.bddrone.dataModel;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightArea {

    private double pathResolution;
    private List<PointLocation> fullFlightArea;
    private List<FlightAreaPart> flightAreaParts;

    public double getPathResolution() {
        return pathResolution;
    }

    public void setPathResolution(double pathResolution) {
        this.pathResolution = pathResolution;
    }

    public List<PointLocation> getFullFlightArea() {
        return fullFlightArea;
    }

    public void setFullFlightArea(List<PointLocation> fullFlightArea) {
        this.fullFlightArea = fullFlightArea;
    }

    public List<FlightAreaPart> getFlightAreaParts() {
        return flightAreaParts;
    }

    public void setFlightAreaParts(List<FlightAreaPart> flightAreaParts) {
        this.flightAreaParts = flightAreaParts;
    }

}
