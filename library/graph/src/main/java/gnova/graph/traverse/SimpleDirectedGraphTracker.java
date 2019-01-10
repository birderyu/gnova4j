package gnova.graph.traverse;

import gnova.graph.structure.DirectedGraphable;

import java.util.HashMap;
import java.util.Map;

public class SimpleDirectedGraphTracker<T extends DirectedGraphable>
        extends AbstractDirectedGraphTracker<T> {

    @Override
    protected Map<T, Boolean> buildMap() {
        return new HashMap<>();
    }
}
