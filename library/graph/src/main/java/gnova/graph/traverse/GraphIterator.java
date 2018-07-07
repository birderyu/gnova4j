package gnova.graph.traverse;

import gnova.graph.structure.Graphable;
import gnova.graph.util.GraphPath;

import java.util.Collection;

/**
 * 图追踪迭代器
 *
 * 图追踪迭代器用于区别图的追踪方向
 *
 * @author birderyu
 *
 */
public interface GraphIterator<T extends Graphable> {

    /**
     * 获取源点
     *
     * @return
     */
    T getSource();
	
	/**
	 * 初始化
	 *
	 * @param source 源点单元，图追踪的起点
	 */
	void initialize(T source);

    /**
     * 获取下一个即将访问的图单元
     *
     * @param tracker
     * @return
     */
	T next(GraphTracker<T> tracker);

	/**
	 * 继续前进（无方向）
	 *
	 * @param tracker
	 * @param current
	 */
	default void process(GraphTracker<T> tracker, T current) {
		process(tracker, current, current.getRelated());
	}

    /**
     * 继续前进（指定方向）
     *
     * @param tracker
     * @param current
     * @param nexts
     */
	void process(GraphTracker<T> tracker, T current, Collection nexts);

    /**
     * 杀掉当前分支
     *
     * @param tracker
     * @param current
     */
	void killBranch(GraphTracker<T> tracker, T current);

    /**
     * 将当前的单元添加进路径中（无方向）
     *
     * @param component
     */
	default void addToPath(T component) {
		if (getPath(component) != null) {
			return;
		}
		addToPath(component, component.getRelated());
	}

    /**
     * 将当前的单元添加进路径中（指定方向）
     *
     * @param component
     * @param relateds
     */
    void addToPath(T component, Collection relateds);
	
	T getLast(T component);
	
	GraphPath<T> getPath(T component);
}
