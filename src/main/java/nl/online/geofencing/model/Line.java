package nl.online.geofencing.model;
/**
 * @author Urfan Beijlerbeijli
 *
 * */
public class Line {
    private Geo from;
    private Geo to;

    public Line(Geo from, Geo to) {
        this.from = from;
        this.to = to;
    }

    public Geo getFrom() {
        return from;
    }

    public Geo getTo() {
        return to;
    }


}
