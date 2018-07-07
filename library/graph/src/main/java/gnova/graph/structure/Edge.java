package gnova.graph.structure;

import gnova.core.annotation.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 图中的一条边
 *
 * @author birderyu
 *
 */
public interface Edge<N extends Node>
        extends Graphable {
	
	/**
	 * 同边且同向
	 */
	int EQUAL_NODE_ORIENTATION = 0;
	
	/**
	 * 不同边
	 */
	int UNEQUAL_NODE_ORIENTATION = 1;
	
	/**
	 * 同边且反向
	 */
	int OPPOSITE_NODE_ORIENTATION = -1;

    N getNodeA();

	void setNodeA(N nodeA);

    N getNodeB();

    void setNodeB(N nodeB);

	default N getOtherNode(@NotNull N node) {
        if (node.equals(getNodeA())) {
            return getNodeB();
        } else if (node.equals(getNodeB())) {
            return getNodeA();
        }
        return null;
    }

	default void reverse() {
        N n = getNodeA();
        setNodeA(getNodeB());
        setNodeB(n);
    }

	default int compareNodes(Edge edge) {
		if (getNodeA().equals(edge.getNodeA())
                && getNodeB().equals(edge.getNodeB())) {
			// 当前边与e相同，且方向相同（nodeA、nodeB）
			return EQUAL_NODE_ORIENTATION;
		} else if (getNodeA().equals(edge.getNodeB())
                && getNodeB().equals(edge.getNodeA())) {
			// 当前边与e相同，且方向相反（nodeA、nodeB）
			return OPPOSITE_NODE_ORIENTATION;
		}
		// 当前边与e不相同
		return UNEQUAL_NODE_ORIENTATION;
	}

    @Override
    default Collection<? extends Graphable> getRelated() {
        // 为了能查找到node，令edge的临界元件为node
        Collection<Node> related = new ArrayList<>(2);
        if (getNodeA() != null) related.add(getNodeA());
        if (getNodeB() != null) related.add(getNodeB());
        return related;
    }

    @Override
    default boolean relateTo(Graphable component) {
        // edge只与node相邻接
        if (component == null) {
            return false;
        }
        if (component.equals(getNodeA())
                || component.equals(getNodeB())) {
            return true;
        }
        return false;
    }
}
