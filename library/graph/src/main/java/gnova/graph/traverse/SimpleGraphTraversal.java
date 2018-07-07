package gnova.graph.traverse;

import gnova.graph.structure.Graph;
import gnova.graph.structure.Graphable;

public class SimpleGraphTraversal<T extends Graphable>
	implements GraphTraversal<T> {
	
	protected final Graph graph;
	
	public SimpleGraphTraversal(Graph graph) {
		this.graph = graph;
	}

    public Graph getGraph() {
	    return this.graph;
    }

}
