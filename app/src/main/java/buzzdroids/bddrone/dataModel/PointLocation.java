package buzzdroids.bddrone.dataModel;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class PointLocation {

    private String pointName;
    private int order;
    private Coordinates coordinates;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}
