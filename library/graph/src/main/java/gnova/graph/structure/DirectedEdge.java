package gnova.graph.structure;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 有向边
 * 
 * @author birderyu
 *
 */
public interface DirectedEdge 
	extends Edge, DirectedGraphable {

    @Override
    DirectedNode getNodeA();

    @Override
    DirectedNode getNodeB();
	
	default DirectedNode getInNode() {
        return getNodeA();
    }

    default void setInNode(DirectedNode inNode) {
        setNodeA(inNode);
    }

    default DirectedNode getOutNode() {
	    return getNodeB();
    }

    default void setOutNode(DirectedNode outNode) {
        setNodeA(outNode);
    }

    @Override
    default int compareNodes(Edge other) {
        if (other instanceof DirectedEdge) {
            DirectedEdge de = (DirectedEdge)other;
            if (de.getInNode().equals(getInNode())
                    && de.getOutNode().equals(getOutNode()))
                return EQUAL_NODE_ORIENTATION;
            if (de.getInNode().equals(getOutNode())
                    && de.getOutNode().equals(getInNode()))
                return OPPOSITE_NODE_ORIENTATION;
        }
        return UNEQUAL_NODE_ORIENTATION;
    }

    @Override
    default Collection<DirectedGraphable> getInRelated() {
        // 为了能查找到node，令edge的临界元件为node
        Collection<DirectedGraphable> inRelated = new ArrayList(1);
        if (getInNode() != null) inRelated.add(getInNode());
        return inRelated;
    }

    @Override
    default Collection<DirectedGraphable> getOutRelated() {
        // 为了能查找到node，令edge的临界元件为node
        Collection<DirectedGraphable> outRelated = new ArrayList(1);
        if (getOutNode() != null) outRelated.add(getOutNode());
        return outRelated;
    }

    @Override
    default boolean relateToIn(DirectedGraphable component) {
        // edge只与node相邻接
        if (component == null) {
            return false;
        }
        return component.equals(getInNode());
    }

    @Override
    default boolean relateToOut(DirectedGraphable component) {
        // edge只与node相邻接
        if (component == null) {
            return false;
        }
        return component.equals(getOutNode());
    }

}
