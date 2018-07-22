package gnova.geometry.model.impl.jts;

import gnova.geometry.model.CoordinateSequenceFactory;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.CoordinateSequence;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class CoordinateSequenceFactoryAdaptor
        implements CoordinateSequenceFactory {

    private org.locationtech.jts.geom.CoordinateSequenceFactory jtsCoordinateSequenceFactory;

    public CoordinateSequenceFactoryAdaptor(org.locationtech.jts.geom.CoordinateSequenceFactory jtsCoordinateSequenceFactory) {
        this.jtsCoordinateSequenceFactory = jtsCoordinateSequenceFactory;
    }

    @Override
    public CoordinateSequence create(Coordinate[] coordinates) {
        return new CoordinateSequenceAdaptor(jtsCoordinateSequenceFactory.create(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)
        ));
    }

    @Override
    public CoordinateSequence create(CoordinateSequence coordinateSequence) {
        return new CoordinateSequenceAdaptor(jtsCoordinateSequenceFactory.create(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinateSequence)
        ));
    }

    protected CoordinateSequence create(int size, int dimension) {
        return new CoordinateSequenceAdaptor(jtsCoordinateSequenceFactory.create(size, dimension));
    }

    public org.locationtech.jts.geom.CoordinateSequenceFactory getJts() {
        return jtsCoordinateSequenceFactory;
    }

    static public org.locationtech.jts.geom.Coordinate toJtsCoordinate(Coordinate coord) {
        return new org.locationtech.jts.geom.Coordinate(coord.getX(), coord.getY(), coord.getZ());
    }

    static public org.locationtech.jts.geom.Coordinate[] toJtsCoordinateArray(Coordinate[] coords) {
        org.locationtech.jts.geom.Coordinate[] jtsCoords
                = new org.locationtech.jts.geom.Coordinate[coords.length];
        for (int i = 0; i < coords.length; i++) {
            jtsCoords[i] = CoordinateSequenceFactoryAdaptor.toJtsCoordinate(coords[i]);
        }
        return jtsCoords;
    }

    static public org.locationtech.jts.geom.Coordinate[] toJtsCoordinateArray(CoordinateSequence coordSeq) {
        org.locationtech.jts.geom.Coordinate[] jtsCoords
                = new org.locationtech.jts.geom.Coordinate[coordSeq.size()];
        for (int i = 0; i < jtsCoords.length; i++) {
            jtsCoords[i] = CoordinateSequenceFactoryAdaptor.toJtsCoordinate(coordSeq.getCoordinateAt(i));
        }
        return jtsCoords;
    }

    static public org.locationtech.jts.geom.CoordinateSequence toJtsCoordinateSequence(CoordinateSequence coordinateSequence) {
        if (coordinateSequence instanceof CoordinateSequenceAdaptor) {
            return ((CoordinateSequenceAdaptor) coordinateSequence).getJtsCoordinateSequence();
        }
        return new org.locationtech.jts.geom.impl.CoordinateArraySequence(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinateSequence)
        );
    }

}
