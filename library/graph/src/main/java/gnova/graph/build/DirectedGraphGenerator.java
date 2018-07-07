package gnova.graph.build;

import gnova.graph.structure.DirectedEdge;
import gnova.graph.structure.DirectedGraph;
import gnova.graph.structure.DirectedGraphable;
import gnova.graph.structure.DirectedNode;

public interface DirectedGraphGenerator<DN extends DirectedNode, DE extends DirectedEdge>
        extends GraphGenerator<DN, DE> {

    @Override
    DirectedGraphable get(Object obj);

    @Override
    DirectedGraphable add(Object obj);

    @Override
    DirectedGraphable remove(Object obj);

    @Override
    DirectedGraphBuilder<DN, DE> getGraphBuilder();

    @Override
    DirectedGraph<DN, DE> build();

}
