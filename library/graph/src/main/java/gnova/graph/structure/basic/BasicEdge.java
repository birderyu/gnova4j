package gnova.graph.structure.basic;

import gnova.graph.structure.AbstractGraphable;
import gnova.graph.structure.Edge;
import gnova.graph.structure.Node;

public class BasicEdge
        extends AbstractGraphable implements Edge {

    private Node nodeA;
    private Node nodeB;

    public BasicEdge(int id, Node nodeA, Node nodeB) {
        super(id);
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public Node getNodeA() {
        return nodeA;
    }

    @Override
    public void setNodeA(Node nodeA) {
        this.nodeA = nodeA;
    }

    @Override
    public Node getNodeB() {
        return nodeB;
    }

    @Override
    public void setNodeB(Node nodeB) {
        this.nodeB = nodeB;
    }
}
