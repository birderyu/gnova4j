package gnova.graph.build.basic;

import gnova.graph.build.AbstractGraphBuilder;
import gnova.graph.structure.basic.BasicEdge;
import gnova.graph.structure.basic.BasicGraph;
import gnova.graph.structure.basic.BasicNode;

public abstract class BasicGraphBuilder
        extends AbstractGraphBuilder<BasicNode, BasicEdge> {

    @Override
    public BasicGraph build() {
        return new BasicGraph(getNodesFromCache(), getEdgesFromCache());
    }

    @Override
    public BasicEdge buildEdge(BasicNode nodeA, BasicNode nodeB) {
        return new BasicEdge(createComponentId(), nodeA, nodeB);
    }

    @Override
    public BasicNode buildNode() {
        return new BasicNode(createComponentId());
    }

}
