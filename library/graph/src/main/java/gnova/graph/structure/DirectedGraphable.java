package gnova.graph.structure;

import java.util.Collection;

public interface DirectedGraphable 
	extends Graphable {

	Collection<? extends DirectedGraphable> getInRelated();

	Collection<? extends DirectedGraphable> getOutRelated();
	
	boolean relateToIn(DirectedGraphable component);
	
	boolean relateToOut(DirectedGraphable component);

}
