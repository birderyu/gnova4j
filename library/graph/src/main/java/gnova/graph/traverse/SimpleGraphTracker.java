package gnova.graph.traverse;

import java.util.HashMap;
import java.util.Map;

import gnova.graph.structure.Graphable;

public class SimpleGraphTracker<T extends Graphable>
        extends AbstractGraphTracker<T> {

    @Override
    protected Map<T, Boolean> buildMap() {
	    return new HashMap<>();
    }
}
