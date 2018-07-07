package gnova.graph.structure;

import java.util.Collection;

public interface DirectedNode
		extends Node, DirectedGraphable {
	
	void addIn(DirectedEdge edge);
	
	void addOut(DirectedEdge edge);
	
	void removeIn(DirectedEdge edge);
	
	void removeOut(DirectedEdge edge);
	
	DirectedEdge getInEdge(DirectedNode other);

	Collection<? extends DirectedEdge> getInEdges(DirectedNode other);

	Collection<? extends DirectedEdge> getInEdges();
	
	DirectedEdge getOutEdge(DirectedNode other);

	Collection<? extends DirectedEdge> getOutEdges(DirectedNode other);

	Collection<? extends DirectedEdge> getOutEdges();
	
	int getInDegree();
	
	int getOutDegree();

	@Override
	default int size() {
        return getInEdges().size() + getOutEdges().size();
    }

}
