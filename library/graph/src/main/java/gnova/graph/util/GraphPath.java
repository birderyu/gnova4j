package gnova.graph.util;

import gnova.graph.structure.Graphable;

/**
 * 轨迹栈
 *
 */
public interface GraphPath<T extends Graphable>
		extends Iterable<T> {
	
	boolean isEmpty();
	
	int size();

	void push(T component);
	
	T pop();
	
	void append(GraphPath<T> path);
	
	/**
	 * 栈顶
	 * 
	 * @return
	 */
	T top();
	
	/**
	 * 栈顶下面的元素
	 * 
	 * @return
	 */
	T top2();
	
	void clear();

}
