package gnova.graph.structure;

import gnova.core.annotation.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * 图中的一个节点
 *
 * @author birderyu
 *
 */
public interface Node<E extends Edge>
        extends Graphable, Iterable<E> {

    default int size() {
        return getEdges().size();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

	void add(@NotNull E edge);
	
	void remove(@NotNull E edge);

	default E getEdge(@NotNull Node node) {
        for (E e : this) {
            if (e.getNodeA() == null || e.getNodeB() == null) {
                continue;
            } else if ((e.getNodeA().equals(this) && e.getNodeB().equals(node)) ||
                    (e.getNodeA().equals(node) && e.getNodeB().equals(this))) {
                return e;
            }
        }
        return null;
    }

	default Collection<E> getEdges(@NotNull Node node) {
        Collection<E> edges = new ArrayList<>();
        for (E e : this) {
            if (e.getNodeA() == null || e.getNodeB() == null) {
                continue;
            } else if ((e.getNodeA().equals(this) && e.getNodeB().equals(node)) ||
                    (e.getNodeA().equals(node) && e.getNodeB().equals(this))) {
                edges.add(e);
            }
        }
        return edges;
    }

    @NotNull
	Collection<E> getEdges();
	
	/**
	 * 获取节点的度
	 * @return
	 */
	default int getDegree() {
		int degree = 0;
		for (Edge e : getEdges()) {
			if (e.getNodeA().equals(this)) {
				degree++;
			}
			if (e.getNodeB().equals(this)) {
				degree++;
			}
		}
		return degree;
	}

    @Override
    default Collection<? extends Graphable> getRelated() {
        // 为了能查找到edge，令node的临界元件为edge
        Collection<E> related = new ArrayList<>(size());
        related.addAll(getEdges());
        return related;
    }

    @Override
    default boolean relateTo(Graphable component) {
        // node只与edge相邻接
        if (component == null ||
                !(component instanceof Edge)) {
            return false;
        }

        for (E edge : this) {
            if (edge == null) {
                continue;
            }
            if (edge.equals(component)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default Iterator<E> iterator() {
	    return getEdges().iterator();
    }

}
