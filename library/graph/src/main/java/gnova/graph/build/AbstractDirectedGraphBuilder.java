package gnova.graph.build;

import gnova.graph.structure.DirectedEdge;
import gnova.graph.structure.DirectedNode;

public abstract class AbstractDirectedGraphBuilder<DN extends DirectedNode, DE extends DirectedEdge>
        extends AbstractGraphBuilder<DN, DE> implements DirectedGraphBuilder<DN, DE> {

    @Override
    public void addEdge(DE edge) {
        edge.getInNode().addOut(edge);
        edge.getOutNode().addIn(edge);
        addEdgeToCache(edge);
    }

}
