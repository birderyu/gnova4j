package gnova.geometry.model.impl.jts;

import gnova.geometry.model.BoundingBox;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.CoordinateSequence;

/**
 * Created by Birderyu on 2017/6/21.
 */
final class CoordinateSequenceAdaptor
        implements CoordinateSequence {

    private org.locationtech.jts.geom.CoordinateSequence jtsCoordinateSequence;

    public CoordinateSequenceAdaptor(org.locationtech.jts.geom.CoordinateSequence jtsCoordinateSequence) {
        this.jtsCoordinateSequence = jtsCoordinateSequence;
    }

    @Override
    public int getDimension() {
        return jtsCoordinateSequence.getDimension();
    }

    @Override
    public Coordinate getCoordinateAt(int n) {
        org.locationtech.jts.geom.Coordinate jtsCoordinate = jtsCoordinateSequence.getCoordinate(n);
        return new Coordinate(jtsCoordinate.x, jtsCoordinate.y, jtsCoordinate.z);
    }

    @Override
    public double getXAt(int n) {
        return jtsCoordinateSequence.getX(n);
    }

    @Override
    public double getYAt(int n) {
        return jtsCoordinateSequence.getY(n);
    }

    @Override
    public double getZAt(int n) {
        org.locationtech.jts.geom.Coordinate jtsCoordinate = jtsCoordinateSequence.getCoordinate(n);
        return jtsCoordinate.z;
    }

    @Override
    public double getMAt(int n) {
        return Coordinate.NULL_ORDINATE_VALUE;
    }

    @Override
    public double getOrdinateAt(int n, int ordinateId) {
        return jtsCoordinateSequence.getOrdinate(n, ordinateId);
    }

    @Override
    public int size() {
        return jtsCoordinateSequence.size();
    }

    @Override
    public BoundingBox getBoundingBox() {
        org.locationtech.jts.geom.Envelope envelope
                = jtsCoordinateSequence.expandEnvelope(new org.locationtech.jts.geom.Envelope());
        return new BoundingBox(envelope.getMinX(), envelope.getMaxX(),
                envelope.getMinY(), envelope.getMaxY());
    }

    @Override
    public Coordinate[] toArray() {
        Coordinate[] coordinates = new Coordinate[jtsCoordinateSequence.size()];
        for (int i = 0; i < jtsCoordinateSequence.size(); i++) {
            org.locationtech.jts.geom.Coordinate jtsCoordinate = jtsCoordinateSequence.getCoordinate(i);
            coordinates[i] = new Coordinate(jtsCoordinate.x, jtsCoordinate.y, jtsCoordinate.z);
        }
        return coordinates;
    }

    @Override
    public CoordinateSequence clone() {
        return new CoordinateSequenceAdaptor(jtsCoordinateSequence.copy());
    }

    @Override
    public String toString() {
        return jtsCoordinateSequence.toString();
    }

    public org.locationtech.jts.geom.CoordinateSequence getJtsCoordinateSequence() {
        return jtsCoordinateSequence;
    }

}
