package buzzdroids.bddrone.dataModel;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DronePath {

    private String droneName;
    private List<PointLocation> path;

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public List<PointLocation> getPath() {
        return path;
    }

    public void setPath(List<PointLocation> path) {
        this.path = path;
    }

}
