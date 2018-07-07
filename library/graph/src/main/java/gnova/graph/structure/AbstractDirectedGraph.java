package gnova.graph.structure;

import java.util.Set;

public class AbstractDirectedGraph<DN extends DirectedNode, DE extends DirectedEdge>
        extends AbstractGraph<DN, DE> implements DirectedGraph<DN, DE> {

    public AbstractDirectedGraph(Set<DN> nodes, Set<DE> edges) {
        super(nodes, edges);
    }

}
