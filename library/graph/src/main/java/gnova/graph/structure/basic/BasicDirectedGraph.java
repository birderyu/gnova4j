package gnova.graph.structure.basic;

import gnova.graph.structure.AbstractDirectedGraph;

import java.util.Set;

public class BasicDirectedGraph
        extends AbstractDirectedGraph<BasicDirectedNode, BasicDirectedEdge> {

    public BasicDirectedGraph(Set<BasicDirectedNode> nodes,
                              Set<BasicDirectedEdge> edges) {
        super(nodes, edges);
    }

}
