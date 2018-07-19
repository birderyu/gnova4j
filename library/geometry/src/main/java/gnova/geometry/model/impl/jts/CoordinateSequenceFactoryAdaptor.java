package gnova.geometry.model.impl.jts;

import gnova.geometry.model.CoordinateSequenceFactory;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.CoordinateSequence;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class CoordinateSequenceFactoryAdaptor
        implements CoordinateSequenceFactory {

    private com.vividsolutions.jts.geom.CoordinateSequenceFactory jtsCoordinateSequenceFactory;

    public CoordinateSequenceFactoryAdaptor(com.vividsolutions.jts.geom.CoordinateSequenceFactory jtsCoordinateSequenceFactory) {
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

    public com.vividsolutions.jts.geom.CoordinateSequenceFactory getJts() {
        return jtsCoordinateSequenceFactory;
    }

    static public com.vividsolutions.jts.geom.Coordinate toJtsCoordinate(Coordinate coord) {
        return new com.vividsolutions.jts.geom.Coordinate(coord.getX(), coord.getY(), coord.getZ());
    }

    static public com.vividsolutions.jts.geom.Coordinate[] toJtsCoordinateArray(Coordinate[] coords) {
        com.vividsolutions.jts.geom.Coordinate[] jtsCoords
                = new com.vividsolutions.jts.geom.Coordinate[coords.length];
        for (int i = 0; i < coords.length; i++) {
            jtsCoords[i] = CoordinateSequenceFactoryAdaptor.toJtsCoordinate(coords[i]);
        }
        return jtsCoords;
    }

    static public com.vividsolutions.jts.geom.Coordinate[] toJtsCoordinateArray(CoordinateSequence coordSeq) {
        com.vividsolutions.jts.geom.Coordinate[] jtsCoords
                = new com.vividsolutions.jts.geom.Coordinate[coordSeq.size()];
        for (int i = 0; i < jtsCoords.length; i++) {
            jtsCoords[i] = CoordinateSequenceFactoryAdaptor.toJtsCoordinate(coordSeq.getCoordinateAt(i));
        }
        return jtsCoords;
    }

    static public com.vividsolutions.jts.geom.CoordinateSequence toJtsCoordinateSequence(CoordinateSequence coordinateSequence) {
        if (coordinateSequence instanceof CoordinateSequenceAdaptor) {
            return ((CoordinateSequenceAdaptor) coordinateSequence).getJtsCoordinateSequence();
        }
        return new com.vividsolutions.jts.geom.impl.CoordinateArraySequence(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinateSequence)
        );
    }

    static public Coordinate fromJtsCoordinate(com.vividsolutions.jts.geom.Coordinate coord) {
        return new Coordinate(coord.x, coord.y, coord.z);
    }

    static public Coordinate[] fromJtsCoordinateArray(com.vividsolutions.jts.geom.Coordinate[] jtsCoords) {
        Coordinate[] coords = new Coordinate[jtsCoords.length];
        for (int i = 0; i < jtsCoords.length; i++) {
            coords[i] = fromJtsCoordinate(jtsCoords[i]);
        }
        return coords;
    }
}
