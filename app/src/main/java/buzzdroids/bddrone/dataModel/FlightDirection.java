package buzzdroids.bddrone.dataModel;

/**
 * Created by Adam Piech on 2017-05-10.
 */
public class FlightDirection {

    private String nextPointName;
    private Coordinates coordinates;

    public String getNextPointName() {
        return nextPointName;
    }

    public void setNextPointName(String nextPointName) {
        this.nextPointName = nextPointName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}
