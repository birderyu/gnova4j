package gnova.geometry.model.impl.jts;

import gnova.geometry.factory.jts.CoordinateSequenceFactoryAdaptor;
import gnova.geometry.model.BoundingBox;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.CoordinateSequence;

/**
 * Created by Birderyu on 2017/6/21.
 */
public final class CoordinateSequenceAdaptor
        implements CoordinateSequence {

    private com.vividsolutions.jts.geom.CoordinateSequence jtsCoordinateSequence;

    public CoordinateSequenceAdaptor(com.vividsolutions.jts.geom.CoordinateSequence jtsCoordinateSequence) {
        this.jtsCoordinateSequence = jtsCoordinateSequence;
    }

    @Override
    public int getDimension() {
        return jtsCoordinateSequence.getDimension();
    }

    @Override
    public Coordinate getCoordinateAt(int n) {
        return CoordinateSequenceFactoryAdaptor.fromJtsCoordinate(jtsCoordinateSequence.getCoordinate(n));
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
        com.vividsolutions.jts.geom.Coordinate jtsCoordinate = jtsCoordinateSequence.getCoordinate(n);
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
        com.vividsolutions.jts.geom.Envelope envelope
                = jtsCoordinateSequence.expandEnvelope(new com.vividsolutions.jts.geom.Envelope());
        return new BoundingBox(envelope.getMinX(), envelope.getMaxX(),
                envelope.getMinY(), envelope.getMaxY());
    }

    @Override
    public Coordinate[] toArray() {
        Coordinate[] coords = new Coordinate[jtsCoordinateSequence.size()];
        for (int i = 0; i < jtsCoordinateSequence.size(); i++) {
            coords[i] = CoordinateSequenceFactoryAdaptor.fromJtsCoordinate(jtsCoordinateSequence.getCoordinate(i));
        }
        return coords;
    }

    @Override
    public CoordinateSequence clone() {
        return new CoordinateSequenceAdaptor((com.vividsolutions.jts.geom.CoordinateSequence)
                jtsCoordinateSequence.clone());
    }

    @Override
    public String toString() {
        return jtsCoordinateSequence.toString();
    }

    public com.vividsolutions.jts.geom.CoordinateSequence getJtsCoordinateSequence() {
        return jtsCoordinateSequence;
    }

}
