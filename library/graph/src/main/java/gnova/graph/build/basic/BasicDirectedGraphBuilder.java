package gnova.graph.build.basic;

import gnova.graph.build.AbstractDirectedGraphBuilder;
import gnova.graph.structure.basic.BasicDirectedEdge;
import gnova.graph.structure.basic.BasicDirectedGraph;
import gnova.graph.structure.basic.BasicDirectedNode;

public abstract class BasicDirectedGraphBuilder
        extends AbstractDirectedGraphBuilder<BasicDirectedNode, BasicDirectedEdge> {

    @Override
    public BasicDirectedGraph build() {
        return new BasicDirectedGraph(getNodesFromCache(), getEdgesFromCache());
    }

    @Override
    public BasicDirectedEdge buildEdge(BasicDirectedNode in, BasicDirectedNode out) {
        return new BasicDirectedEdge(createComponentId(), in, out);
    }

    @Override
    public BasicDirectedNode buildNode() {
        return new BasicDirectedNode(createComponentId());
    }

}
