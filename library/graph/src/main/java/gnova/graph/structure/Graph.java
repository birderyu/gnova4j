package gnova.graph.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 图
 *
 * @param <N> 节点的类型
 * @param <E> 边的类型
 * @author birderyu
 */
public interface Graph<N extends Node, E extends Edge> {

    /**
     * 获取属于这张网络的单元数量
     *
     * @return
     */
    default int size() {
        return nodeSize() + edgeSize();
    }

    /**
     * 获取节点的数量
     *
     * @return
     */
    int nodeSize();

    /**
     * 获取边的数量
     *
     * @return
     */
    int edgeSize();

    /**
     * 图是否为空
     *
     * @return
     */
    default boolean isEmpty() {
        return isNodeEmpty() && isEdgeEmpty();
    }

    /**
     * 节点是否为空
     *
     * @return
     */
    default boolean isNodeEmpty() {
        return nodeSize() == 0;
    }

    /**
     * 边是否为空
     *
     * @return
     */
    default boolean isEdgeEmpty() {
        return edgeSize() == 0;
    }

    /**
     * 获取节点的集合
     * 
     * @return
     */
    Set<N> nodeSet();
    
    /**
     * 获取边的集合
     *
     * @return
     */
    Set<E> edgeSet();

    /**
     * 访问所有单元
     * 
     * @param visitor
     */
    default void visit(Predicate<? super Graphable> visitor) {
        visitEdges(visitor);
        visitNodes(visitor);
    }

    /**
     * 访问所有节点
     * 
     * @param visitor
     */
    default void visitNodes(Predicate<? super N> visitor) {
        if (isNodeEmpty()) {
            return;
        }
        for (N node : nodeSet()) {
            if (!visitor.test(node)) {
                return;
            }
        }
    }

    /**
     * 访问所有边
     * 
     * @param visitor
     */
    default void visitEdges(Predicate<? super E> visitor) {
        if (isEdgeEmpty()) {
            return;
        }
        for (E edge : edgeSet()) {
            if (!visitor.test(edge)) {
                return;
            }
        }
    }

    /**
     * 获取度为特定值的节点的集合
     * 
     * @param degree 节点的度
     * @return
     */
    default Collection<N> getNodesOfDegree(int degree) {
        final Collection<N> nodes = new ArrayList<>();
        visitNodes(component -> {
            if (component.getDegree() == degree) {
                nodes.add(component);
            }
            return true;
        });
        return nodes;
    }

}
