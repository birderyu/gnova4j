package gnova.graph.traverse;

import gnova.graph.structure.DirectedGraph;
import gnova.graph.structure.DirectedGraphable;

public class SimpleDirectedGraphTraversal<T extends DirectedGraphable>
        extends SimpleGraphTraversal<T> implements DirectedGraphTraversal<T> {

    public SimpleDirectedGraphTraversal(DirectedGraph graph) {
        super(graph);
    }

    @Override
    public DirectedGraph getGraph() {
        return (DirectedGraph) super.getGraph();
    }

}
