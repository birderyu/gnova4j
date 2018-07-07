package gnova.graph.structure.basic;

import gnova.graph.structure.DirectedEdge;
import gnova.graph.structure.DirectedNode;

public class BasicDirectedEdge
        extends BasicEdge implements DirectedEdge {

    public BasicDirectedEdge(int id, DirectedNode in, DirectedNode out) {
        super(id, in, out);
    }

    @Override
    public DirectedNode getNodeA() {
        return (DirectedNode) super.getNodeA();
    }

    @Override
    public DirectedNode getNodeB() {
        return (DirectedNode) super.getNodeB();
    }

    @Override
    public void reverse() {

        getInNode().removeOut(this);
        getOutNode().removeIn(this);

        super.reverse();

        getInNode().addOut(this);
        getOutNode().addIn(this);

    }
}
